package edu.uces.ar.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uces.ar.model.Cart;
import edu.uces.ar.model.Product;
import edu.uces.ar.model.dto.CartDTO;
import edu.uces.ar.model.dto.CartProductDTO;
import edu.uces.ar.model.dto.ErrorDTO;
import edu.uces.ar.model.dto.ProductDTO;
import edu.uces.ar.model.dto.ProductInCartDTO;
import edu.uces.ar.repository.CartProductRepository;
import edu.uces.ar.repository.CartsRepository;
import edu.uces.ar.repository.ProductRepository;
import edu.uces.ar.service.business.exception.ProductServiceException;

@RestController
public class CartController {
	
	private final CartsRepository cartRepo;
	private final CartProductRepository cartProductRepo;
	private final ProductRepository productRepo;
	
	public CartController(CartsRepository cartRepo,
			CartProductRepository cartProductRepo,
			ProductRepository productRepo) {
		super();
		this.cartRepo = cartRepo;
		this.productRepo = productRepo;
		this.cartProductRepo = cartProductRepo;
	}

	@GetMapping(value = "/carts")
	public ResponseEntity<List<Cart>> gatAllCarts() {
		return new ResponseEntity<>(cartRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/carts/{id}")
	public ResponseEntity<Object> getCart(@PathVariable long id) {
		
		Optional<CartDTO> response = Optional.of(new CartDTO());
		Optional<Cart> cart = cartRepo.findById(id);
		
		if(cart.isPresent()){
			BeanUtils.copyProperties(cart.get(), response.get());
		} else {
			return new ResponseEntity<>(new ErrorDTO("CART_NOT_PRESENT", "Cart not present in repository"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/carts/{status}")
	public ResponseEntity<Object> getCart(@PathVariable Optional<String> status) {
		
		List<CartDTO> result = new ArrayList<CartDTO>();
		if (status.isPresent()) {
			
			if (status.get().equals("NEW") || status.get().equals("READY") || status.get().equals("PROCESSED") || status.get().equals("FAILED")) {
				BeanUtils.copyProperties(result, cartRepo.findByStatus(status.get()));
			} else {
				return new ResponseEntity<>(new ErrorDTO("CART_STATUS_NOT_SUPPORTED", "Cart status not supported"), HttpStatus.BAD_REQUEST);
			}
			
		} else {
			BeanUtils.copyProperties(result, cartRepo.findAll());
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping(value = "/carts")
	public ResponseEntity<Object> createCart(@RequestBody CartDTO cartDTO) {
		
		if (cartDTO.getFullName() == null || cartDTO.getFullName().isEmpty()) {
			return new ResponseEntity<>(new ErrorDTO("CART_FULLNAME_REQUIRED", "Cart not present in repository"), HttpStatus.BAD_REQUEST);
		} else if (cartDTO.getEmail() == null || cartDTO.getEmail().isEmpty()) {
			return new ResponseEntity<>(new ErrorDTO("CART_EMAIL_REQUIRED", "Cart not present in repository"), HttpStatus.BAD_REQUEST);
		}
		
		Cart cart = new Cart();
		BeanUtils.copyProperties(cartDTO, cart);

		cart.setCreationDate(LocalDate.now());
		cart.setStatus("NEW");
		cart.setTotal(BigDecimal.ZERO);
		
		Cart created = cartRepo.save(cart);
		BeanUtils.copyProperties(created, cartDTO);
		
		return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/carts/{cartId}/products")
    public ResponseEntity<Object> postProductToCart(@PathVariable long cartId, @RequestBody CartProductDTO cartProduct) {
        
		if (cartProduct.getQuantity() == null) {
			return new ResponseEntity<>(new ErrorDTO("PRODUCT_QUANTITY_REQUIRED", "Cart not present in repository"), HttpStatus.BAD_REQUEST);
		} else if (cartProduct.getQuantity() <= 0) {
			return new ResponseEntity<>(new ErrorDTO("PRODUCT_QUANTITY_INVALID", "Cart not present in repository"), HttpStatus.BAD_REQUEST);
		}
		
        Optional<Cart> carrito = cartRepo.findById(cartId);
        Optional<Product> producto = productRepo.findById(cartProduct.getId());
        
        if (!carrito.isPresent()) {
        	return new ResponseEntity<>(new ErrorDTO("CART_NOT_PRESENT", "Cart not present in repository"), HttpStatus.NOT_FOUND);
        } else if (!producto.isPresent()) {
        	return new ResponseEntity<>(new ErrorDTO("PRODUCT_NOT_PRESENT", "Cart not present in repository"), HttpStatus.NOT_FOUND);
        }
        
        if (producto.get().getStock() >= cartProduct.getQuantity()){
        	carrito.get().addProduct(producto.get());
        	carrito.get().setTotal(carrito.get().getTotal().add(producto.get().getUnitPrice().multiply(new BigDecimal(cartProduct.getQuantity()))));
            cartRepo.save(carrito.get());
        } else {
        	return new ResponseEntity<>(new ErrorDTO("PRODUCT_STOCK_INSUFFICIENT", "Product stock insufficient"), HttpStatus.CONFLICT);
        }
        
        return new ResponseEntity<>(new ProductInCartDTO(cartProduct.getId(), producto.get().getDescription(), producto.get().getUnitPrice(), cartProduct.getQuantity()) , HttpStatus.CREATED);
    }
	
	@GetMapping(value = "/carts/{cartId}/products")
    public ResponseEntity<List<ProductDTO>> getProductsFromCart(@PathVariable long cartId) {
        
        Optional<Cart> carrito = cartRepo.findById(cartId);
        
        List<Product> products = new ArrayList<Product>(carrito.get().getProducts());
		List<ProductDTO> dtos = new ArrayList<>(products.size());
		
		for (int i = 0; i < products.size(); i++) {
			ProductDTO productDTO = new ProductDTO();
			BeanUtils.copyProperties(products.get(i), productDTO);
			dtos.add(productDTO);
		}
        
        return new ResponseEntity<List<ProductDTO>>(dtos, HttpStatus.OK);
    }

	@PostMapping(value = "/carts/{cartId}/checkout")
    public ResponseEntity<Object> checkoutCart(@PathVariable long cartId) {
        
        Optional<Cart> carrito = cartRepo.findById(cartId);
        
        if (!carrito.isPresent()) {
        	return new ResponseEntity<>(new ErrorDTO("CART_NOT_PRESENT", "Cart not present in repository"), HttpStatus.NOT_FOUND);
        } else if (carrito.isPresent() && !carrito.get().getStatus().equals("NEW")) {
        	return new ResponseEntity<>(new ErrorDTO("CART_STATUS_NOT_ALLOW_CHECKOUT", "Cart status not allow checkout"), HttpStatus.CONFLICT);
        }
        
        carrito.get().setStatus("READY");

        cartRepo.save(carrito.get());
        
		return new ResponseEntity<>("Cart move to READY successfully", HttpStatus.OK);
    }
	
	
	@DeleteMapping(value = "/carts/{cartId}/products/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable long cartId, @PathVariable long productId) {
        
		Optional<Cart> carrito = cartRepo.findById(cartId);
        Optional<Product> producto = productRepo.findById(productId);
        
        if (!carrito.isPresent()) {
        	return new ResponseEntity<>(new ErrorDTO("CART_NOT_PRESENT", "Cart not present in repository"), HttpStatus.NOT_FOUND);
        } else if (!producto.isPresent()) {
        	return new ResponseEntity<>(new ErrorDTO("PRODUCT_NOT_PRESENT", "Cart not present in repository"), HttpStatus.NOT_FOUND);
        } else if (carrito.isPresent()) {
        	if (!carrito.get().getStatus().equals("NEW")) {
        		return new ResponseEntity<>(new ErrorDTO("CART_PROCESSING_NOT_ALLOW_DELETION", "Cart not present in repository"), HttpStatus.CONFLICT);
        	}
        }
		
        cartProductRepo.deleteByIdCartIdAndProductId(cartId, productId);
        CartDTO cartDTO = new CartDTO();
		BeanUtils.copyProperties(cartRepo.findById(cartId), cartDTO);
        
		return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
	
	
}
