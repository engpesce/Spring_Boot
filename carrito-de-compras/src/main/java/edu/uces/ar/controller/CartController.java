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

import edu.uces.ar.model.Cart;
import edu.uces.ar.repository.CartsRepository;

@RestController
public class CartController {
	
	private final CartsRepository cartRepo;
	
	public CartController(CartsRepository cartRepo) {
		super();
		this.cartRepo = cartRepo;
	}

	@RequestMapping(value = "/carts", method = RequestMethod.GET)
	public ResponseEntity<List<Cart>> getCarts() {
		return new ResponseEntity<>(cartRepo.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/carts/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Cart>> getCart(@PathVariable long id) {
		return new ResponseEntity<>(cartRepo.findById(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/carts", method = RequestMethod.POST)
	public ResponseEntity<Object> postCart(@RequestBody Cart cart) {
		cartRepo.save(cart);
		return new ResponseEntity<>("Cart created successfully", HttpStatus.CREATED);
	}
}
