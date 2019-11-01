package edu.uces.ar.controller;

import java.util.List;

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

import edu.uces.ar.model.dto.ProductDTO;
import edu.uces.ar.service.ProductService;

@RestController
@Validated
public class ProductController {
	
	private final ProductService service;
	
	public ProductController(ProductService service) {
		super();
		this.service = service;
	}

	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDTO>> getProducts() {
		return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
	}

	@GetMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable long id) {
		return new ResponseEntity<>(service.getProductByProductId(id), HttpStatus.OK);
	}

	@PutMapping(value = "/products/{id}")
	public ResponseEntity<Object> putProduct(@Valid @RequestBody ProductDTO productDTO) {
		Long id = service.putProduct(productDTO);
		return new ResponseEntity<>("Product created successfully. Id: " + id, HttpStatus.CREATED);
	}
	
	@PostMapping(path = "/products")
	public ResponseEntity<Object> postProduct(@Valid @RequestBody ProductDTO productDTO) {
		Long id = service.putProduct(productDTO);
		return new ResponseEntity<>("Product updated successfully. Id: " + id, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable long id) {
		service.deleteProductById(id);
		return new ResponseEntity<>("Product deleted successfully. Id: " + id, HttpStatus.OK);
	}

}
