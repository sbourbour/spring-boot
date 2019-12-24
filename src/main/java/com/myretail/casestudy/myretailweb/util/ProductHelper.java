package com.myretail.casestudy.myretailweb.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductHelper.class);
	
	public static String findProductTitle(String productInfo) {
		
		String productTitle = null;
		
		try {			
			JSONObject productInfoJO = new JSONObject(productInfo);
			productTitle = productInfoJO.getJSONObject(MyRetailWebConstants.PRODUCT)
					.getJSONObject(MyRetailWebConstants.PRODUCT_ITEM)
					.getJSONObject(MyRetailWebConstants.PRODUCT_DESC)
					.getString(MyRetailWebConstants.PRODUCT_TITLE);
						
		} catch (JSONException e) {
			productTitle = MyRetailWebConstants.NOT_FOUND;
			logger.error(e.getMessage());
		}
		
		return productTitle;
	}	
		

	public static JSONObject createErrorProduct(String productId) {
		JSONObject product = new JSONObject();
		product.put(MyRetailWebConstants.PRODUCT_ID, productId);
		product.put(MyRetailWebConstants.PRODUCT_NAME, MyRetailWebConstants.NOT_FOUND);
		
		return product;
	}
}
