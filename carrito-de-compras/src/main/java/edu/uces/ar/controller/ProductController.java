package edu.uces.ar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uces.ar.model.Product;
import edu.uces.ar.repository.ProductRepository;

@RestController
public class ProductController {
	
	private final ProductRepository productRepo;
	
	public ProductController(ProductRepository productRepo) {
		super();
		this.productRepo = productRepo;
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProducts() {
		return new ResponseEntity<>(productRepo.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Product>> getProduct(@PathVariable long id) {
		return new ResponseEntity<>(productRepo.findById(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ResponseEntity<Object> postProduct(@RequestBody Product product) {
		productRepo.save(product);
		return new ResponseEntity<>("Product created successfully", HttpStatus.CREATED);
	}
}
