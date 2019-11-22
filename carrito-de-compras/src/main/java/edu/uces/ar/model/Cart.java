package edu.uces.ar.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cart {

	private @Id @GeneratedValue Long id;
	private String fullName;
	private String email;
	private LocalDate creationDate;
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<CartProduct> cartProduct;
	private BigDecimal total;
	private String status;
	
	public Cart() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public Set<Product> getProducts() {
		
		Set<Product> products = new HashSet<>();
		
		if (getCartProduct() != null){
			for (CartProduct cartProduct : this.getCartProduct()) {
				products.add(cartProduct.getProduct());
			}
		}
		return products;
	}
	
	public void setProducts(Set<Product> products) {
		
		if (products != null){
			
			this.setCartProduct(new HashSet<CartProduct>());
			for (Product product : products) {
				addProduct(product);
			}
		}
	}

	public void addProduct(Product product) {
		this.getCartProduct().add(new CartProduct(new CartProductKey(this.getId(), product.getId()), this, product));
		
	}
	
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<CartProduct> getCartProduct() {
		return cartProduct;
	}

	public void setCartProduct(Set<CartProduct> cartProduct) {
		this.cartProduct = cartProduct;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
