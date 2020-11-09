package edu.uces.ar;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.uces.ar.model.Cart;
import edu.uces.ar.repository.CartsRepository;
import edu.uces.ar.service.CartService;
import edu.uces.ar.service.business.exception.CartException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CarritoDeComprasApplicationTests {
	

	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartsRepository cartRepo;

	
	public CarritoDeComprasApplicationTests() {
	}


	
	
    @Before
    public void seEjecutaAntesDeCadaTest() {
    }

    @BeforeClass
    public static void seEjecutaUnaSolaVezAlPrincipio() {
    }
    
    @Rule
	public ExpectedException exceptionRule = ExpectedException.none();
    
    @Test()
    public void testCartSearch() {
    	
    	Cart carrito = new Cart();
    	carrito.setEmail("gp@gmail.com");
    	carrito.setFullName("German Pesce");
    	carrito.setStatus("NEW");
    	carrito.setTotal(BigDecimal.ZERO);
    	
    	List<Cart> carritosBefore = cartRepo.findAll();
    	
    	cartRepo.save(carrito);
    	
    	List<Cart> carritosAfter = cartRepo.findAll();
    	
    	assertNotNull(cartService.getCartByEmail("gp@gmail.com"));
    }
    
    
    @Test
	public void cartCheckoutWithInvalidIdThrowException() {
    	
	    exceptionRule.expect(CartException.class);
	    exceptionRule.expectMessage("Cart not present in repository");
    	cartService.checkoutCart(7777L);
	}
    
    @After
    public void seEjecutaDespuesDeCadaTest() {
    }
 
    @AfterClass
    public static void seEjecutaUnaSolaVezAlFinal() {
    }
	
}
