package edu.uces.ar.model.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;

public class ProductDTO {

	private Long id;
	private String description;
	private BigDecimal unitPrice;
	@Min(1)
	private Integer stock;
	
	public ProductDTO() {
		super();
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}
