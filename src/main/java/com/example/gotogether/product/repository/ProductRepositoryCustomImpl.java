package com.example.gotogether.product.repository;

import com.example.gotogether.product.entity.Product;
import com.example.gotogether.reservation.entity.QReservationDetail;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.example.gotogether.product.entity.QProduct.product;
import static com.example.gotogether.reservation.entity.QReservationDetail.reservationDetail;
public class ProductRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl() {
        super(Product.class);
    }
    @Override
    public Page<Product> searchByKeywordAndSorting(Pageable pageable, String keyword, String sort) {
        JPQLQuery<Product> query = queryFactory.selectFrom(product)
                .where(containsName(keyword))
                .orderBy(sort(sort));
        List<Product> productList = this.getQuerydsl().applyPagination(pageable,query).fetch();
        return new PageImpl<Product>(productList,pageable, query.fetchCount());
    }

    @Override
    public List<Product> findPopular() {
        return queryFactory
                .select(product)
                .from(product)
                .leftJoin(product.reservationDetails, reservationDetail)
                .groupBy(product.productId)
                .limit(10)
                .orderBy(reservationDetail.count().desc())
                .fetch();
    }

    private BooleanExpression containsName(String keyword){
        if (keyword.isEmpty()){
            return null;
        }
        return product.name.contains(keyword).or(containsSummary(keyword)).or(containsArea(keyword)).or(containsFeature(keyword)).or(containsDetail(keyword));
    }
    private BooleanExpression containsSummary(String keyword){
        if (keyword.isEmpty()){
            return null;
        }
        return product.summary.contains(keyword);
    }

    private BooleanExpression containsArea(String keyword){
        if (keyword.isEmpty()){
            return null;
        }
        return product.area.contains(keyword);
    }

    private BooleanExpression containsFeature(String keyword){
        if (keyword.isEmpty()){
            return null;
        }
        return product.feature.contains(keyword);
    }
    private BooleanExpression containsDetail(String keyword){
        if (keyword.isEmpty()){
            return null;
        }
        return product.detail.contains(keyword);
    }
    private OrderSpecifier<?> sort(String sort) {
        switch (sort) {
            case "recent":
                return new OrderSpecifier<>(Order.DESC, product.productId);
            case "asc":
                return new OrderSpecifier<>(Order.ASC, product.price);
            case "desc":
                return new OrderSpecifier<>(Order.DESC, product.price);
        }
        return null;
    }

}
