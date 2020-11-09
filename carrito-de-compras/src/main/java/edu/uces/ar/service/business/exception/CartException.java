package edu.uces.ar.service.business.exception;

import org.springframework.http.HttpStatus;

public class CartException extends RuntimeException {
	
	private static final long serialVersionUID = -4961625479507744127L;
	private String code;
	private HttpStatus status;
	
	public CartException(String code, String message, HttpStatus status) {
        super(message);
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		CartException other = (CartException) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
	
}
