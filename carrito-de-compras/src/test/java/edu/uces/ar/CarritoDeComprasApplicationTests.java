package edu.uces.ar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uces.ar.model.Cart;
import edu.uces.ar.model.Product;
import edu.uces.ar.repository.CartsRepository;
import edu.uces.ar.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarritoDeComprasApplicationTests {

	@Autowired
	private CartsRepository repo;
	@Autowired
	private ProductRepository prod;
		
	
	public CarritoDeComprasApplicationTests() {
	}

	@Test
	public void contextLoads() {
		
		//con los ids de cada producto agregado al carrito, voy a buscarlo a la BD.
		//Lo agrego al cart a grabar y lo persisto
		
		Set<Product> products = new HashSet<Product>();
		Optional<Product> producto1 = prod.findById(1L);
		products.add(producto1.get());
		Optional<Product> producto2 = prod.findById(2L);
		products.add(producto2.get());
		
		Cart carrito = new Cart();
		carrito.setFullName("Compras ebay");		
		carrito.setEmail("lalala@gmail.com");
		carrito.setCreationDate(LocalDate.now());
		carrito.setStatus("Pendiente");
		carrito.setTotal(new BigDecimal("1350.50"));
		
		carrito.setProducts(products);
		
		this.repo.save(carrito);
	}
	
	@Test
	public void saveWithOutSelect() {
		
		//con los ids de cada producto agregado al carrito, voy a buscarlo a la BD.
		//Lo agrego al cart a grabar y lo persisto
		
		Set<Product> products = new HashSet<Product>();
		Product producto1 = new Product();
		producto1.setId(1L);
		products.add(producto1);
		
		Product producto2 = new Product();
		producto2.setId(2L);
		products.add(producto2);
		
		Cart carrito = new Cart();
		carrito.setFullName("Compras ebay");		
		carrito.setEmail("lalala@gmail.com");
		carrito.setCreationDate(LocalDate.now());
		carrito.setStatus("Pendiente");
		carrito.setTotal(new BigDecimal("1350.50"));
		
		carrito.setProducts(products);
		
		this.repo.saveAndFlush(carrito);
		
		
		List<Cart> carritos = this.repo.findAll();
		System.out.println(carritos);
	}

}
