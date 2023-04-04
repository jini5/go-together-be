package com.example.gotogether.reservation.repository;

import com.example.gotogether.reservation.entity.Reservation;
import com.example.gotogether.reservation.entity.ReservationDetail;
import com.example.gotogether.reservation.entity.ReservationStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.gotogether.reservation.entity.QReservationDetail.reservationDetail;

public class ReservationDetailRepositoryImpl extends QuerydslRepositorySupport implements ReservationDetailRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ReservationDetailRepositoryImpl(EntityManager em) {
        super(ReservationDetail.class);
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ReservationDetail> findByUserAndReservationStatus(List<Reservation> reservationList, ReservationStatus reservationStatus, Pageable pageable) {
        JPQLQuery<ReservationDetail> query = queryFactory
                .selectFrom(reservationDetail)
                .where(
                        reservationIn(reservationList),
                        reservationStatusEq(reservationStatus)
                );
        List<ReservationDetail> reservationDetailList = getQuerydsl().applyPagination(pageable, query).fetch();
        return PageableExecutionUtils.getPage(reservationDetailList, pageable, query::fetchCount);
    }

    @Override
    public Page<ReservationDetail> findByUserAndNotReservationStatus(List<Reservation> reservationList, ReservationStatus reservationStatus, Pageable pageable) {
        JPQLQuery<ReservationDetail> query = queryFactory
                .selectFrom(reservationDetail)
                .where(
                        reservationIn(reservationList),
                        reservationStatusNe(reservationStatus)
                );
        List<ReservationDetail> reservationDetailList = getQuerydsl().applyPagination(pageable, query).fetch();
        return PageableExecutionUtils.getPage(reservationDetailList, pageable, query::fetchCount);
    }

    private BooleanExpression reservationIn(List<Reservation> reservationList) {
        return reservationDetail.reservation.in(reservationList);
    }

    private BooleanExpression reservationStatusEq(ReservationStatus reservationStatus) {
        return reservationDetail.reservationStatus.eq(reservationStatus);
    }

    private BooleanExpression reservationStatusNe(ReservationStatus reservationStatus) {
        return reservationDetail.reservationStatus.ne(reservationStatus);
    }
}
