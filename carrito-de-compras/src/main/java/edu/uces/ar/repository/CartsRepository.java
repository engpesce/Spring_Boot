package edu.uces.ar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uces.ar.model.Cart;

public interface CartsRepository extends JpaRepository<Cart, Long> {

	Optional<Cart> findByEmail(String email);
}
