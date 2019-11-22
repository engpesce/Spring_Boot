package edu.uces.ar.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import edu.uces.ar.model.CartProduct;
import edu.uces.ar.model.CartProductKey;
import edu.uces.ar.model.Product;
import edu.uces.ar.model.dto.CartDTO;
import edu.uces.ar.model.dto.CartProductDTO;
import edu.uces.ar.model.dto.ProductDTO;
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
	public ResponseEntity<Optional<CartDTO>> getCart(@PathVariable long id) {
		
		Optional<CartDTO> response = Optional.of(new CartDTO());
		Optional<Cart> cart = cartRepo.findById(id);
		
		if(cart.isPresent()){
			BeanUtils.copyProperties(cart.get(), response.get());
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/carts")
	public ResponseEntity<CartDTO> createCart(@RequestBody CartDTO cartDTO) {
		
		Cart cart = new Cart();
		BeanUtils.copyProperties(cartDTO, cart);

		cart.setCreationDate(LocalDate.now());
		//cart.setProducts(new HashSet<>());
		cart.setStatus("NEW");
		cart.setTotal(BigDecimal.ZERO);
		
		Cart created = cartRepo.save(cart);
		BeanUtils.copyProperties(created, cartDTO);
		
		return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/carts/{cartId}/products")
    public ResponseEntity<Object> postProductToCart(@PathVariable long cartId, @RequestBody CartProductDTO cartProduct) {
        
        Optional<Cart> carrito = cartRepo.findById(cartId);
        Optional<Product> producto = productRepo.findById(cartProduct.getId());
        
        if (producto.get().getStock() >= cartProduct.getQuantity()){
        	carrito.get().addProduct(producto.get());
        	carrito.get().setTotal(carrito.get().getTotal().add(producto.get().getUnitPrice().multiply(new BigDecimal(cartProduct.getQuantity()))));
            cartRepo.save(carrito.get());
        } else {
        	throw new ProductServiceException("No hay stock suficiente de " + producto.get().getDescription() + 
        			" para agregar al carrito. Al momento solo tenemos: " + producto.get().getStock());
        }
        
        
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
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
        carrito.get().setStatus("READY");

        cartRepo.save(carrito.get());
        
		return new ResponseEntity<>("Cart move to READY successfully", HttpStatus.OK);
    }
	
	
	@DeleteMapping(value = "/carts/{cartId}/products/{productId}")
    public ResponseEntity<CartDTO> deleteProduct(@PathVariable long cartId, @PathVariable long productId) {
        
        //CartProductKey id = new CartProductKey(cartId, productId);
        cartProductRepo.deleteByIdCartIdAndProductId(cartId, productId);
        
        //Cart cartUpdated = cartRepo.save(carrito.get());
        CartDTO cartDTO = new CartDTO();
		BeanUtils.copyProperties(cartRepo.findById(cartId), cartDTO);
        
		return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
    }
}
