package edu.uces.ar.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uces.ar.model.Cart;
import edu.uces.ar.model.Product;
import edu.uces.ar.model.dto.CartProductDTO;
import edu.uces.ar.model.dto.ProductDTO;
import edu.uces.ar.repository.CartsRepository;
import edu.uces.ar.repository.ProductRepository;
import edu.uces.ar.service.business.exception.ProductServiceException;

@RestController
public class CartController {
	
	private final CartsRepository cartRepo;
	private final ProductRepository productRepo;
	
	public CartController(CartsRepository cartRepo, 
			ProductRepository productRepo) {
		super();
		this.cartRepo = cartRepo;
		this.productRepo = productRepo;
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
	public ResponseEntity<Cart> postCart(@RequestBody Cart cart) {
		cart.setCreationDate(LocalDate.now());
		cart.setStatus("NEW");
		Cart created = cartRepo.save(cart);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/carts/{id}/products")
    public ResponseEntity<Object> postProductToCart(@PathVariable long id, @RequestBody CartProductDTO cartProduct) {
        
        Optional<Cart> carrito = cartRepo.findById(id);
        Optional<Product> producto = productRepo.findById(cartProduct.getProductId());
        
        if (producto.get().getStock() >= cartProduct.getQuantity()){
        	carrito.get().getProducts().add(producto.get());
            cartRepo.save(carrito.get());
        } else {
        	throw new ProductServiceException("No hay stock suficiente de " + producto.get().getDescription() + 
        			" para agregar al carrito. Al momento solo tenemos: " + producto.get().getStock());
        }
        
        
        return new ResponseEntity<>("Cart created successfully", HttpStatus.CREATED);
    }

}
