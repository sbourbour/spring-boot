package com.myretail.casestudy.myretailweb.util;

import org.json.JSONObject;

public class ProductTestUtil {
	
	public static String getSampleProductInfo() {
		
		return "{\"product\": {\"item\": {\"product_description\": {\"title\": \"Lord of the Rings\"}}}}";
	}

	public static JSONObject getSampleProductJSON() {
		JSONObject product = new JSONObject();
		  JSONObject price = new JSONObject();		
		  JSONObject item = new JSONObject();
		  JSONObject description = new JSONObject();
		  product.put(MyRetailWebConstants.PRODUCT_ID, 13860428);
		  
		  price.put(MyRetailWebConstants.PRODUCT_VALUE, 9.99);
		  price.put(MyRetailWebConstants.PRODUCT_CURRENCY_CODE, "USD");
		  
		  description.put("title", "Lord of the Rings");
		  item.put(MyRetailWebConstants.PRODUCT_DESC, description);
		  
		  product.put(MyRetailWebConstants.PRODUCT_ITEM, item);
		  product.put(MyRetailWebConstants.PRODUCT_CURRENT_PRICE, price);
		  
		  return product;
	}
	
	public static String getSampleProductId() {
		return "13860428";
	}	
}
