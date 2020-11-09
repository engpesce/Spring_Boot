package edu.uces.ar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import edu.uces.ar.model.Cart;
import edu.uces.ar.model.dto.CartDTO;
import edu.uces.ar.repository.CartsRepository;
import edu.uces.ar.service.CartService;
import edu.uces.ar.service.business.exception.CartException;

@Service
public class CartServiceImpl implements CartService{
	
	private final CartsRepository cartsRepo;
	
	public CartServiceImpl(CartsRepository cartsRepo) {
		super();
		this.cartsRepo = cartsRepo;
	}

	@Override
	public CartDTO getCartByEmail(String email) {

		CartDTO result = null;
		List<Cart> carts = cartsRepo.findByEmail(email);
		if ( !carts.isEmpty() ) {
			
			Optional<Cart> found = carts.stream().filter( carrito -> carrito.getStatus() == "NEW").findAny();
			
			if (found.isPresent()) {
				result = new CartDTO();
				BeanUtils.copyProperties(found.get(), result);
			}
		}
		
		return result;
	}

	@Override
	public CartDTO checkoutCart(Long id) {

		CartDTO result = null;
		Optional<Cart> cart = cartsRepo.findById(id);

		if(cart.isPresent()) {
			Cart found = cart.get();
			if (found.getStatus().equalsIgnoreCase("NEW")) { 
				found.setStatus("READY");
				cartsRepo.save(found);
				BeanUtils.copyProperties(cart.get(), result);
			} else {
				throw new CartException("CART_STATUS_NOT_ALLOW_CHECKOUT","Cart status not allow checkout", HttpStatus.CONFLICT);
			}
		} else {
			throw new CartException("CART_NOT_PRESENT","Cart not present in repository", HttpStatus.NOT_FOUND);
		}

		return result;
	}

	
}
