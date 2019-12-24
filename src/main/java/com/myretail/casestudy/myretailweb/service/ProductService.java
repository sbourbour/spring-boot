package com.myretail.casestudy.myretailweb.service;

import org.json.JSONObject;

public interface ProductService {
	
    String getExternalProductInfo(String productId);
    
    JSONObject getProductPriceInfo(String productId);
    
    boolean updateProductPrice(String productId);
}
