package com.example.gotogether.reservation.service.impl;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.entity.User;
import com.example.gotogether.auth.repository.UserRepository;
import com.example.gotogether.global.response.PageResponseDTO;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductOption;
import com.example.gotogether.product.repository.ProductOptionRepository;
import com.example.gotogether.product.repository.ProductRepository;
import com.example.gotogether.reservation.dto.ReservationDTO;
import com.example.gotogether.reservation.dto.ReservationDetailDTO;
import com.example.gotogether.reservation.entity.PaymentMethod;
import com.example.gotogether.reservation.entity.Reservation;
import com.example.gotogether.reservation.entity.ReservationDetail;
import com.example.gotogether.reservation.entity.ReservationStatus;
import com.example.gotogether.reservation.repository.ReservationDetailRepository;
import com.example.gotogether.reservation.repository.ReservationRepository;
import com.example.gotogether.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static com.example.gotogether.global.config.PageSizeConfig.ADMIN_RESERVATION_LIST_SIZE;
import static com.example.gotogether.global.config.PageSizeConfig.USER_RESERVATION_LIST_SIZE;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationDetailRepository reservationDetailRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    /**
     * 현 페이지의 전체 예약 목록 조회
     *
     * @param page 현 페이지 번호
     */
    @Override
    public ResponseEntity<?> findAllList(int page) {

        try {
            PageRequest pageRequest = PageRequest.of(page - 1, ADMIN_RESERVATION_LIST_SIZE);
            Page<Reservation> reservationPage = reservationRepository.findAll(pageRequest);
            if (reservationPage == null) {
                throw new NullPointerException();
            }
            if (reservationPage.getTotalElements() < 1) {

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Page<ReservationDTO.AdminListResDTO> reservationListResDTO = reservationPage.map(ReservationDTO.AdminListResDTO::new);

            return new ResponseEntity<>(new PageResponseDTO(reservationListResDTO), HttpStatus.OK);
        } catch (NullPointerException e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 예약상태 수정
     *
     * @param reservationDetailId 수정할 예약 상세 아이디
     * @param modifyStatusReqDTO  수정할 예약상태 (수정 후)
     */
    @Transactional
    @Override
    public ResponseEntity<?> modifyReservationStatus(Long reservationDetailId, ReservationDTO.ModifyStatusReqDTO modifyStatusReqDTO) {
        try {
            ReservationDetail reservationDetail = reservationDetailRepository.findById(reservationDetailId).orElseThrow(NoSuchElementException::new);
            ReservationStatus reqStatus = ReservationStatus.from(modifyStatusReqDTO.getReservationStatus());
            if (!canStatusChange(reqStatus, reservationDetail)) {

                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            reservationDetail.updateStatus(reqStatus);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 예약상태 변경 가능 여부 확인
     *
     * @param request     수정할 예약상태 (수정 후)
     * @param reservation 수정할 예약 상세
     */
    public boolean canStatusChange(ReservationStatus request, ReservationDetail reservation) {

        ReservationStatus current = reservation.getReservationStatus();
        if (request.equals(ReservationStatus.PAYMENT_PENDING)) {
            if (!current.equals(ReservationStatus.CANCEL_REQUESTED)) {
                return false;
            }
        }
        if (request.equals(ReservationStatus.CONFIRMED)) {
            if (!(current.equals(ReservationStatus.PAYMENT_PENDING) || current.equals(ReservationStatus.CANCEL_REQUESTED))) {
                return false;
            }
        }
        if (request.equals(ReservationStatus.CANCEL_REQUESTED)) {
            return false;
        }
        if (request.equals(ReservationStatus.CANCELLED)) {
            if (!(current.equals(ReservationStatus.CANCEL_REQUESTED) || current.equals(ReservationStatus.CONFIRMED)
                    || current.equals(ReservationStatus.PAYMENT_PENDING))) {
                return false;
            }
        }
        if (request.equals(ReservationStatus.COMPLETED)) {
            if (!(current.equals(ReservationStatus.CONFIRMED) && LocalDate.now().isAfter(reservation.getStartDate()))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 로그인 중인 회원의 예약 목록 조회
     *
     * @param userAccessDTO 토큰 정보
     * @param pageNumber    현 페이지 번호
     */
    @Override
    public ResponseEntity<?> findList(UserDTO.UserAccessDTO userAccessDTO, int pageNumber) {

        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            PageRequest pageRequest = PageRequest.of(pageNumber - 1, USER_RESERVATION_LIST_SIZE);
            Page<Reservation> reservationPage = reservationRepository.findByUser(user, pageRequest);
            if (reservationPage == null) {
                throw new NullPointerException();
            }
            if (reservationPage.getTotalElements() < 1) {

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Page<ReservationDTO.UserListResDTO> reservationListResDTO = reservationPage.map(ReservationDTO.UserListResDTO::new);

            return new ResponseEntity<>(new PageResponseDTO(reservationListResDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NullPointerException e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 예약 상세 정보 조회
     *
     * @param userAccessDTO       토큰 정보
     * @param reservationDetailId 조회할 예약 상세 아이디
     */
    @Override
    public ResponseEntity<?> findDetail(UserDTO.UserAccessDTO userAccessDTO, Long reservationDetailId) {

        try {
            ReservationDetail reservationDetail = reservationDetailRepository.findById(reservationDetailId).orElseThrow(NoSuchElementException::new);
            Reservation reservation = reservationDetail.getReservation();
            if (!userAccessDTO.getRole().equals("ROLE_ADMIN")) {
                if (!userAccessDTO.getEmail().equals(reservation.getUser().getEmail())) {

                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            }
            ReservationDetailDTO.DetailResDTO detailResDTO = ReservationDetailDTO.DetailResDTO.builder()
                    .reservation(reservation)
                    .reservationDetail(reservationDetail)
                    .build();

            return new ResponseEntity<>(detailResDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 회원 예약 취소
     *
     * @param userAccessDTO       토큰 정보
     * @param reservationDetailId 취소할 예약 상세 아이디
     */
    @Transactional
    @Override
    public ResponseEntity<?> cancelReservation(UserDTO.UserAccessDTO userAccessDTO, Long reservationDetailId) {

        try {
            ReservationDetail reservationDetail = reservationDetailRepository.findById(reservationDetailId).orElseThrow(NoSuchElementException::new);
            Reservation reservation = reservationDetail.getReservation();
            if (!userAccessDTO.getEmail().equals(reservation.getUser().getEmail())) {

                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if (!(reservationDetail.getReservationStatus().equals(ReservationStatus.PAYMENT_PENDING)
                    || reservationDetail.getReservationStatus().equals(ReservationStatus.CONFIRMED))) {

                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (reservation.getPaymentMethod().equals(PaymentMethod.BANK_TRANSFER)) {
                reservationDetail.updateStatus(ReservationStatus.CANCELLED);
            }
            if (reservation.getPaymentMethod().equals(PaymentMethod.NON_BANK_ACCOUNT)) {
                reservationDetail.updateStatus(ReservationStatus.CANCEL_REQUESTED);
            }

            ProductOption productOption = productOptionRepository.findById(reservationDetail.getProductOptionId()).orElseThrow(NoSuchElementException::new);
            productOption.subtractPresentPeopleFrom(reservationDetail.getNumberOfPeople());
            productOption.subtractPresentSingleRoomFrom(reservationDetail.getSingleRoomNumber());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 회원 예약 추가
     *
     * @param userAccessDTO 토큰 정보
     * @param addReqDTO     추가할 예약 정보
     */
    @Transactional
    @Override
    public ResponseEntity<?> addReservation(UserDTO.UserAccessDTO userAccessDTO, ReservationDTO.AddReqDTO addReqDTO) {

        try {
            for (ReservationDetailDTO.AddReqDTO reqDTO : addReqDTO.getReservationList()) {
                ProductOption productOption = productOptionRepository.findById(reqDTO.getProductOptionId()).orElseThrow(NoSuchElementException::new);
                if (!isPeopleLessThanMax(productOption, reqDTO.getReservationPeopleNumber())
                        || !isSingleRoomLessThanMax(productOption, reqDTO.getReservationSingleRoomNumber())) {

                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            Reservation reservation = addReqDTO.toEntity(user);
            reservationRepository.save(reservation);

            for (ReservationDetailDTO.AddReqDTO reqDTO : addReqDTO.getReservationList()) {
                Product product = productRepository.findById(reqDTO.getProductId()).orElseThrow(NoSuchElementException::new);
                ProductOption productOption = productOptionRepository.findById(reqDTO.getProductOptionId()).orElseThrow(NoSuchElementException::new);
                ReservationDetail reservationDetail = null;
                if (addReqDTO.getPaymentMethod().equals(PaymentMethod.BANK_TRANSFER.getValue())) {
                    reservationDetail = reqDTO.toEntity(reservation, product, productOption, ReservationStatus.CONFIRMED);
                }
                if (addReqDTO.getPaymentMethod().equals(PaymentMethod.NON_BANK_ACCOUNT.getValue())) {
                    reservationDetail = reqDTO.toEntity(reservation, product, productOption, ReservationStatus.PAYMENT_PENDING);
                }
                reservationDetailRepository.save(reservationDetail);
            }

            for (ReservationDetailDTO.AddReqDTO reqDTO : addReqDTO.getReservationList()) {
                ProductOption productOption = productOptionRepository.findById(reqDTO.getProductOptionId()).orElseThrow(NoSuchElementException::new);
                productOption.addPresentPeopleTo(reqDTO.getReservationPeopleNumber());
                productOption.addPresentSingleRoomTo(reqDTO.getReservationSingleRoomNumber());
            }

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 예약 인원수가 예약가능 인원수를 넘지 않는지 체크
     *
     * @param productOption     체크할 상품옵션
     * @param reservationNumber 예약 인원수
     */
    public boolean isPeopleLessThanMax(ProductOption productOption, int reservationNumber) {
        return (productOption.getMaxPeople() - productOption.getPresentPeopleNumber() >= reservationNumber) ? true : false;
    }

    /**
     * 예약 싱글룸개수가 예약가능 싱글룸개수를 넘지 않는지 체크
     *
     * @param productOption     체크할 상품옵션
     * @param reservationNumber 예약 싱글룸개수
     */
    public boolean isSingleRoomLessThanMax(ProductOption productOption, int reservationNumber) {
        return (productOption.getMaxSingleRoom() - productOption.getPresentSingleRoomNumber() >= reservationNumber) ? true : false;
    }
}
