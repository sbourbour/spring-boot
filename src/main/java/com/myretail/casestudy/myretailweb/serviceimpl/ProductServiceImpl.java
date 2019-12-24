package com.myretail.casestudy.myretailweb.serviceimpl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myretail.casestudy.myretailweb.dao.ProductDao;
import com.myretail.casestudy.myretailweb.service.ProductService;
import com.myretail.casestudy.myretailweb.util.MyRetailWebConstants;
import com.myretail.casestudy.myretailweb.util.MyRetailWebUtil;
import com.myretail.casestudy.myretailweb.util.ProductHelper;

/**
 * Service to connect to internal as well as external data resources.
 * 
 * @author Shahriar Bourbour
 */
@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${redskyendpoint}")
	private String redskyEndpoint;	
	
	public String getExternalProductInfo(String productId) {
		
		String redskyUrlWithProdId = String.format(redskyEndpoint, productId);
				
		HttpEntity<?> requestEntity = MyRetailWebUtil.createJSONRequestEntity();
		ResponseEntity<String> response = restTemplate.exchange(redskyUrlWithProdId, HttpMethod.GET, requestEntity, String.class);
		
		return response.getBody();
	}
	
	
	public JSONObject getProductPriceInfo(String productId) {		
		JSONObject product = productDao.getProductById(productId);
		
		String externalProductInfo = getExternalProductInfo(productId);
		product.put(MyRetailWebConstants.PRODUCT_NAME, ProductHelper.findProductTitle(externalProductInfo));
		return product;
	}
	
	public boolean updateProductPrice(String productPriceInfo) {
		return productDao.updateProductPrice(productPriceInfo);
	}
}
