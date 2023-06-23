package com.market.repository;

import com.market.entity.Review;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT COUNT(a) FROM Review a WHERE a.item.id = :itemId")
    int getCountById(@Param("itemId") Long itemId);


    List<Review> findByItemIdOrderByRegDateDesc(Long itemId, Sort sort);
}
