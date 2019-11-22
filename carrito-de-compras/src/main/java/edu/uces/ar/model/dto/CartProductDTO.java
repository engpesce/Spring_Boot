package edu.uces.ar.model.dto;

import javax.validation.constraints.Min;

public class CartProductDTO {

	private Long id;
	@Min(1)
	private Integer quantity;
	
	public CartProductDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
