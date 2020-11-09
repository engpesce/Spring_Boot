package edu.uces.ar;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uces.ar.model.dto.ProductDTO;
import edu.uces.ar.service.ProductService;
import edu.uces.ar.service.business.exception.ProductInvalidException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductosApplicationTests {
	
	@Autowired
	private ProductService productService;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	public ProductosApplicationTests() {
	}
	    
	@Test
	public void createProductOk() {
		
		ProductDTO product = new ProductDTO();
		product.setDescription("Raqueta de tenis");
		product.setStock(10);
		product.setUnitPrice(new BigDecimal("1500.50"));
		
		List<ProductDTO> p = productService.getAllProducts();
		ProductDTO dto = productService.putProduct(product);
		List<ProductDTO> p2 = productService.getAllProducts();
		productService.deleteProductById(dto.getId());
		List<ProductDTO> p3 = productService.getAllProducts();
		assertNotNull(dto.getId());
	}
	
	@Test
	public void createProductWithDTONull() {
		
		exceptionRule.expect(ProductInvalidException.class);
		exceptionRule.expectMessage("");
		productService.putProduct(null);
		
	}
	   
	@Test
	public void createProductWithoutDescription () {
		
		exceptionRule.expect(ProductInvalidException.class);
		exceptionRule.expectMessage("Product description is required");
		ProductDTO product = new ProductDTO();
		product.setStock(10);
		product.setUnitPrice(new BigDecimal("1500.50"));
		
		productService.putProduct(product);
		
	}
	
	@Test
	public void createProductWithEmptyDescription () {
		
		exceptionRule.expect(ProductInvalidException.class);
		exceptionRule.expectMessage("Product description is required");
		ProductDTO product = new ProductDTO();
		product.setDescription("");
		product.setStock(10);
		product.setUnitPrice(new BigDecimal("1500.50"));
		
		productService.putProduct(product);
		
	}
	
	@Test
	public void createProductWithInvalidStock() {
		
		exceptionRule.expect(ProductInvalidException.class);
		exceptionRule.expectMessage("Product stock must be greater than 0");
		ProductDTO product = new ProductDTO();
		product.setDescription("Raqueta de tenis");
		product.setStock(-1);
		product.setUnitPrice(new BigDecimal("1500.50"));
		
		productService.putProduct(product);
		
	}
	
	@Test
	public void createProductWithInvalidPrice() {
		
		exceptionRule.expect(ProductInvalidException.class);
		exceptionRule.expectMessage("Product unit price must be greater than 0");
		ProductDTO product = new ProductDTO();
		product.setDescription("Raqueta de tenis");
		product.setStock(10);
		product.setUnitPrice(new BigDecimal("-1500.50"));
		
		productService.putProduct(product);
		
	}
}
