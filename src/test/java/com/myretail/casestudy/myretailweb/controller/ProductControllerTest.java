package com.myretail.casestudy.myretailweb.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.myretail.casestudy.myretailweb.util.MyRetailWebConstants;

/**
 * Integration end-to-end test that tests calls to the GET and PUT controller methods.
 * 
 * @author Shahriar Bourbour
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers=ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("local")
public class ProductControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void basicTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo("Test")));
  }
  
  @Test
  public void getProductInfo() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/products/13860428").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
  
  @Test
  public void getProductInfoUnhappyPath() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/products/999").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
  
  @Test
  public void updateProductPrice() throws Exception {	  
	  JSONObject product = new JSONObject();
	  JSONObject price = new JSONObject();		
	  product.put(MyRetailWebConstants.PRODUCT_ID, 13860428);
	  price.put(MyRetailWebConstants.PRODUCT_VALUE, 9.99);
	  price.put(MyRetailWebConstants.PRODUCT_CURRENCY_CODE, "USD");
	  product.put(MyRetailWebConstants.PRODUCT_CURRENT_PRICE, price);
	  
	  mvc.perform(MockMvcRequestBuilders.put("/products/13860428")
			  .contentType(MediaType.APPLICATION_JSON)
			  .accept(MediaType.APPLICATION_JSON)
			  .content(product.toString()))
        .andExpect(status().isOk());
  }
  
  @Test
  public void updateProductPriceUnhappyPath() throws Exception {	  
	  JSONObject product = new JSONObject();
	  JSONObject price = new JSONObject();		
	  product.put(MyRetailWebConstants.PRODUCT_ID, 13860428);	  
	  price.put("invalid_string", "FOO");
	  product.put(MyRetailWebConstants.PRODUCT_CURRENT_PRICE, price);
	  
	  mvc.perform(MockMvcRequestBuilders.put("/products/13860428")
			  .contentType(MediaType.APPLICATION_JSON)
			  .accept(MediaType.APPLICATION_JSON)
			  .content(product.toString()))
        .andExpect(status().isBadRequest());
  }
}