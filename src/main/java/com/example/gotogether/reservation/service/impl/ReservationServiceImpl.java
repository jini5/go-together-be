package com.example.gotogether.reservation.service.impl;

import com.example.gotogether.global.response.PageResponseDTO;
import com.example.gotogether.reservation.dto.ReservationDTO;
import com.example.gotogether.reservation.entity.Reservation;
import com.example.gotogether.reservation.repository.ReservationRepository;
import com.example.gotogether.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.example.gotogether.global.config.PageSizeConfig.ADMIN_RESERVATION_LIST_SIZE;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

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
            Page<ReservationDTO.ListResDTO> reservationListResDTO = reservationPage.map(ReservationDTO.ListResDTO::new);

            return new ResponseEntity<>(new PageResponseDTO(reservationListResDTO), HttpStatus.OK);
        } catch (NullPointerException e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
