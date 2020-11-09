package edu.uces.ar.service.business.exception;

public class ProductInvalidException extends RuntimeException {
	
	private static final long serialVersionUID = -4961625479507744127L;

	public ProductInvalidException(String message) {
        super(message);
    }

}
