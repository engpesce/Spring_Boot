package edu.uces.ar.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import edu.uces.ar.model.Product;
import edu.uces.ar.model.dto.ErrorDTO;
import edu.uces.ar.model.dto.ProductDTO;
import edu.uces.ar.repository.ProductRepository;
import edu.uces.ar.service.ProductService;

@RestController
@Validated
public class ProductController {
	
	private final ProductService service;
	private final ProductRepository repo;
	
	
	/*
	 	PRODUCT_ID_NOT_REQUIRED, PRODUCT_DESCRIPTION_REQUIRED,
		PRODUCT_STOCK_REQUIRED, PRODUCT_STOCK_INVALID,
		PRODUCT_UNITPRICE_REQUIRED, PRODUCT_UNITPRICE_INVALID
	 */
	
	public ProductController(ProductService service, ProductRepository repo) {
		super();
		this.service = service;
		this.repo = repo;
	}

	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDTO>> getProducts() {
		return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
	}

	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Object> getProduct(@PathVariable long id) {
		
		Optional<Product> product = repo.findById(id);
		if (product.isPresent()) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ErrorDTO("PRODUCT_NOT_PRESENT", "Product not present in repository"), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/products/{id}")
	public ResponseEntity<Object> putProduct(@Valid @RequestBody ProductDTO productDTO) {
		return new ResponseEntity<>(service.putProduct(productDTO), HttpStatus.CREATED);
	}
	
	@PostMapping(path = "/products")
	public ResponseEntity<Object> postProduct(@Valid @RequestBody ProductDTO productDTO) {
		return new ResponseEntity<>(service.putProduct(productDTO), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable long id) {
		service.deleteProductById(id);
		return new ResponseEntity<>("Product deleted successfully. Id: " + id, HttpStatus.OK);
	}

}
