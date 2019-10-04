package edu.uces.ar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uces.ar.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	
}
