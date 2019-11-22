package edu.uces.ar.model;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class CartProduct {
	 
	@EmbeddedId
    CartProductKey id;
 
    @ManyToOne
    @MapsId("cart_id")
    @JoinColumn(name = "cart_id")
    Cart cart;
 
    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    Product product;
 
    Integer quantity;
    BigDecimal totalProduct;
	
    public CartProduct() {
		super();
	}

	public CartProduct(CartProductKey id, Cart cart, Product product) {
		super();
		this.id = id;
		this.cart = cart;
		this.product = product;
	}

	public CartProductKey getId() {
		return id;
	}

	public void setId(CartProductKey id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(BigDecimal totalProduct) {
		this.totalProduct = totalProduct;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cart == null) ? 0 : cart.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		CartProduct other = (CartProduct) obj;
		if (cart == null) {
			if (other.cart != null)
				return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} 
		
		if (product.getId().equals(other.product.getId()) && cart.getId().equals(other.cart.getId()))
			return false;
		}
		
		return true;
	}
     
	
}
