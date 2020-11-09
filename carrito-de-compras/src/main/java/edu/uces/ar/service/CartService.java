package edu.uces.ar.service;

import org.springframework.stereotype.Service;

import edu.uces.ar.model.dto.CartDTO;

@Service
public interface CartService {

	CartDTO getCartByEmail(String email);
	
	CartDTO checkoutCart(Long id);

}
