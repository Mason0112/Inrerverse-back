package com.interverse.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, CartId>{

	@Query("from Cart c where c.cartId.usersId = :uuu and c.cartId.productsId = :ppp")
	Cart findByUsersIdAndProductsId(@Param("uuu") Integer usersId,@Param("ppp") Integer productsId);
	
}
