package com.myretail.casestudy.myretailweb.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myretail.casestudy.myretailweb.service.ProductService;
import com.myretail.casestudy.myretailweb.util.ProductHelper;

/**
 * Rest controller for retrieving and updating product info.
 *  
 * @author Shahriar Bourbour
 */
@RestController
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	ResponseEntity<String> test() {
		
		ResponseEntity<String> response = new ResponseEntity<String>("Test", HttpStatus.OK);
	
		return response;
	}
		
	@GetMapping("/products/{id}")
	ResponseEntity<String> getProductInfo(@PathVariable("id") String productId) {
			
		ResponseEntity<String> response = null;
		
		try {		
			JSONObject productPriceInfo  = productService.getProductPriceInfo(productId);
			
			response = new ResponseEntity<String>(productPriceInfo.toString(), HttpStatus.OK);			
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response = new ResponseEntity<String>(ProductHelper.createErrorProduct(productId).toString(), HttpStatus.NOT_FOUND);					
		}	
		return response;
	}
	
	@PutMapping("/products/{id}")
	ResponseEntity<String> updateProductPrice(@RequestBody String productPriceInfo) {
		
		ResponseEntity<String> response = null;
		boolean rowUpdated = productService.updateProductPrice(productPriceInfo);
		
		if(rowUpdated) {
			response = new ResponseEntity<String>("Update successful", HttpStatus.OK);
		}
		else {
			response = new ResponseEntity<String>("Update failed", HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}

}
