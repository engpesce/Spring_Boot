package edu.uces.ar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uces.ar.model.Cart;

public interface CartsRepository extends JpaRepository<Cart, Long> {

	List<Cart> findByEmail(String email);
	List<Cart> findByStatus(String status);
}
