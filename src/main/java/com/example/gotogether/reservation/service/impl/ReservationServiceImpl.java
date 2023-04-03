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
     * @param pageNumber 현 페이지 번호
     * @return
     */
    @Override
    public ResponseEntity<?> findAllList(int pageNumber) {

        try {
            PageRequest pageRequest = PageRequest.of(pageNumber - 1, ADMIN_RESERVATION_LIST_SIZE);
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
     * @param reservationId 예약상태 수정할 예약 아이디
     * @param modifyStatusReqDTO 수정할 예약상태 (수정 후)
     */
    @Transactional
    @Override
    public ResponseEntity<?> modifyReservationStatus(Long reservationId, ReservationDTO.ModifyStatusReqDTO modifyStatusReqDTO) {
        try {
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(NoSuchElementException::new);
            reservation.updateStatus(modifyStatusReqDTO.getReservationStatus());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 로그인 중인 회원의 예약 목록 조회
     *
     * @param userAccessDTO 토큰 정보
     * @param pageNumber 현 페이지 번호
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
     * @param userAccessDTO 토큰 정보
     * @param reservationId 조회할 예약 아이디
     */
    @Override
    public ResponseEntity<?> findDetailInfo(UserDTO.UserAccessDTO userAccessDTO, Long reservationId) {

        try {
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(NoSuchElementException::new);
            if (!userAccessDTO.getRole().equals("ROLE_ADMIN")) {
                if (!userAccessDTO.getEmail().equals(reservation.getUser().getEmail())) {

                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            }
            ReservationDTO.DetailInfoResDTO detailInfoResDTO = new ReservationDTO.DetailInfoResDTO(reservation);

            return new ResponseEntity<>(detailInfoResDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 회원 예약 취소
     *
     * @param userAccessDTO 토큰 정보
     * @param reservationId 취소할 예약 아이디
     */
    @Transactional
    @Override
    public ResponseEntity<?> cancelReservation(UserDTO.UserAccessDTO userAccessDTO, Long reservationId) {

        try {
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(NoSuchElementException::new);
            if (!userAccessDTO.getEmail().equals(reservation.getUser().getEmail())) {

                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if (reservation.getPaymentMethod().equals(PaymentMethod.BANK_TRANSFER)) {
                reservation.updateStatus(ReservationStatus.CANCELLED);
            }
            if (reservation.getPaymentMethod().equals(PaymentMethod.NON_BANK_ACCOUNT)) {
                reservation.updateStatus(ReservationStatus.CANCEL_REQUESTED);
            }

            for (ReservationDetail detail : reservation.getReservationDetails()) {
                ProductOption option = productOptionRepository.findById(detail.getProductOptionId()).orElseThrow(NoSuchElementException::new);
                option.subtractPresentPeopleFrom(detail.getNumberOfPeople());
                option.subtractPresentSingleRoomFrom(detail.getSingleRoomNumber());
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 회원 예약 추가
     *
     * @param userAccessDTO 토큰 정보
     * @param addReqDTO 추가할 예약 정보
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

            ReservationStatus reservationStatus = null;
            if (addReqDTO.getPaymentMethod().equals(PaymentMethod.BANK_TRANSFER.getValue())) {
                reservationStatus = ReservationStatus.CONFIRMED;
            }
            if (addReqDTO.getPaymentMethod().equals(PaymentMethod.NON_BANK_ACCOUNT.getValue())) {
                reservationStatus = ReservationStatus.PAYMENT_PENDING;
            }

            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            Reservation reservation = addReqDTO.toEntity(user, reservationStatus);
            reservationRepository.save(reservation);
            for (ReservationDetailDTO.AddReqDTO reqDTO : addReqDTO.getReservationList()) {
                Product product = productRepository.findById(reqDTO.getProductId()).orElseThrow(NoSuchElementException::new);
                ProductOption productOption = productOptionRepository.findById(reqDTO.getProductOptionId()).orElseThrow(NoSuchElementException::new);
                ReservationDetail reservationDetail = reqDTO.toEntity(reservation, product, productOption);
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
     * @param productOption 체크할 상품옵션
     * @param reservationNumber 예약 인원수
     */
    public boolean isPeopleLessThanMax(ProductOption productOption, int reservationNumber) {
        return (productOption.getMaxPeople() - productOption.getPresentPeopleNumber() >= reservationNumber) ? true : false;
    }

    /**
     * 예약 싱글룸개수가 예약가능 싱글룸개수를 넘지 않는지 체크
     *
     * @param productOption 체크할 상품옵션
     * @param reservationNumber 예약 싱글룸개수
     */
    public boolean isSingleRoomLessThanMax(ProductOption productOption, int reservationNumber) {
        return (productOption.getMaxSingleRoom() - productOption.getPresentSingleRoomNumber() >= reservationNumber) ? true : false;
    }
}
