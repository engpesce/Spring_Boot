package edu.uces.ar.model.dto;

import java.math.BigDecimal;

public class ProductInCartDTO {

	private Long id;
	private String description;
	private BigDecimal unitPrice;
	private Integer quantity;
	
	public ProductInCartDTO() {
		super();
	}
	
	public ProductInCartDTO(Long id, String description, BigDecimal unitPrice, Integer quantity) {
		super();
		this.id = id;
		this.description = description;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
