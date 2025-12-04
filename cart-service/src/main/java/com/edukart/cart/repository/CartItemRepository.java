package com.edukart.cart.repository;

import com.edukart.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.productID = :productId AND ci.cart.userID = :userId")
    int deleteByUserIdAndProductId(
            @Param("userId") String userId,
            @Param("productId") String productId
    );
}