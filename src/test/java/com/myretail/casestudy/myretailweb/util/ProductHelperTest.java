package com.myretail.casestudy.myretailweb.util;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class ProductHelperTest {
	
	@Test
	public void findProductTitle() {	
		String title = ProductHelper.findProductTitle(ProductTestUtil.getSampleProductInfo());
		Assert.assertTrue(title.equalsIgnoreCase("Lord of the Rings"));
	}

	@Test
	public void createErrorProduct() {
		String id = "12345";
		JSONObject errorProduct = ProductHelper.createErrorProduct(id);
		Assert.assertTrue(errorProduct.getString(MyRetailWebConstants.PRODUCT_ID).equals(id));
		Assert.assertTrue(errorProduct.getString(MyRetailWebConstants.PRODUCT_NAME).equals(MyRetailWebConstants.NOT_FOUND));
	}
}
