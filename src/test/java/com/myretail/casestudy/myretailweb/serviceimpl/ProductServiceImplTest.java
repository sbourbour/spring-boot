package com.myretail.casestudy.myretailweb.serviceimpl;


import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.myretail.casestudy.myretailweb.dao.ProductDao;
import com.myretail.casestudy.myretailweb.util.MyRetailWebConstants;
import com.myretail.casestudy.myretailweb.util.MyRetailWebUtil;
import com.myretail.casestudy.myretailweb.util.ProductHelper;
import com.myretail.casestudy.myretailweb.util.ProductTestUtil;
 
/**
 * JUnit Test that tests calls to the service tier.
 * 
 * @author Shahriar Bourbour
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceImplTest {

	@Mock
	ProductDao productDao;
	
	@Mock
	RestTemplate restTemplate;
	
	@InjectMocks
    ProductServiceImpl productServiceImpl = new ProductServiceImpl();
	
	@Value("${redskyendpoint}")
	private String redskyEndpoint;
	
	@Before
	public void setup() {
		restTemplate = new RestTemplate();
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(productServiceImpl, "redskyEndpoint", redskyEndpoint);
	}
	
	@Test
	public void getExternalProductInfo() throws Exception {
        String productId = ProductTestUtil.getSampleProductId();
		
		String redskyUrlWithProdId = String.format(redskyEndpoint, productId);	
		
		HttpEntity<?> requestEntity = MyRetailWebUtil.createJSONRequestEntity();
		ResponseEntity<String> response = new ResponseEntity<String>(ProductTestUtil.getSampleProductInfo(), HttpStatus.OK);
		
		// Mock the REST call
        Mockito.when(restTemplate.exchange(redskyUrlWithProdId, HttpMethod.GET, requestEntity, String.class))
                .thenReturn(response);
		
        String productInfo = productServiceImpl.getExternalProductInfo(productId);
        
        // Verify the REST and database were called once
        Mockito.verify(restTemplate, Mockito.times(1)).exchange(redskyUrlWithProdId, HttpMethod.GET, requestEntity, String.class);
        
        String title = ProductHelper.findProductTitle(productInfo);
        Assert.assertEquals(title, "Lord of the Rings");
	}
	
	@Test
    public void getProductById() throws Exception {
				
		String productId = ProductTestUtil.getSampleProductId();
		
		String redskyUrlWithProdId = String.format(redskyEndpoint, productId);
				
		HttpEntity<?> requestEntity = MyRetailWebUtil.createJSONRequestEntity();
		
		ResponseEntity<String> response = new ResponseEntity<String>(ProductTestUtil.getSampleProductInfo(), HttpStatus.OK);
		
		// Mock the REST call
        Mockito.when(restTemplate.exchange(redskyUrlWithProdId, HttpMethod.GET, requestEntity, String.class))
                .thenReturn(response);
		
        // Mock the database call
        Mockito.when(productDao.getProductById(productId))
                .thenReturn(ProductTestUtil.getSampleProductJSON());
        
        JSONObject productPriceInfo = productServiceImpl.getProductPriceInfo(productId);
        
        // Verify the REST and database were called once
        Mockito.verify(restTemplate, Mockito.times(1)).exchange(redskyUrlWithProdId, HttpMethod.GET, requestEntity, String.class);
        Mockito.verify(productDao, Mockito.times(1)).getProductById(productId);
               
        Assert.assertEquals(productPriceInfo.get(MyRetailWebConstants.PRODUCT_ID), Integer.parseInt(productId));        
        Assert.assertEquals(productPriceInfo
        		.getJSONObject(MyRetailWebConstants.PRODUCT_CURRENT_PRICE)
        		.get(MyRetailWebConstants.PRODUCT_VALUE), 9.99);        
    }	
}
