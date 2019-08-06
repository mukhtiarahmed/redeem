package com.tenhawks.redeem;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages= {"com.tenhawks.redeem"})
public class RedeemApplication {

	public static void main(String[] args) {
		 SpringApplication.run(RedeemApplication.class, args);
	}

}
