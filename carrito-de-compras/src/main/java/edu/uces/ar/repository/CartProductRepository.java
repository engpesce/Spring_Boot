package edu.uces.ar.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.uces.ar.model.CartProduct;
import edu.uces.ar.model.CartProductKey;

public interface CartProductRepository extends JpaRepository<CartProduct, CartProductKey> {

	@Modifying
	@Transactional
	@Query("delete from CartProduct cp where cp.cart.id=:cartId and cp.product.id=:productId")
	void deleteByIdCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);
}
