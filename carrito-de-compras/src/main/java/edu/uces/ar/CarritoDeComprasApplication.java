package edu.uces.ar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;

@SpringBootApplication
public class CarritoDeComprasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarritoDeComprasApplication.class, args);
	}

}
