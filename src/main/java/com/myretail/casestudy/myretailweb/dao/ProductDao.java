package com.myretail.casestudy.myretailweb.dao;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.myretail.casestudy.myretailweb.util.MyRetailWebConstants;

/**
 * Data Access Object to perform CRUD operations on the product table in the database.
 * 
 * @author Shahriar Bourbour
 */
@Repository
public class ProductDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductDao.class);
	
	@Autowired
    @Qualifier("CassandraSession")
	Session cassandraSession;
	
	private final String SELECT_PRODUCT_BY_ID = "SELECT id, price, currency FROM shahriar.product WHERE id = %s"; 
	
	private final String UPDATE_PRODUCT_BY_ID = "UPDATE shahriar.product SET price = %s, currency = '%s' WHERE id = %s";	
	
	private final String PRODUCT_ID_COLUMN = "id";
	private final String PRICE_COLUMN = "price";
	private final String CURRENCY_COLUMN = "currency";
	
	public JSONObject getProductById(String productId) {
		
		String query = String.format(SELECT_PRODUCT_BY_ID, productId);
		
		ResultSet resultSet = cassandraSession.execute(query);
		
		List<Row> rows = resultSet.all();
		
		JSONObject product = new JSONObject();
		JSONObject price = new JSONObject();		
		product.put(PRODUCT_ID_COLUMN, Integer.parseInt(productId));
		// id is the primary key. There will be only one row for this Id.
		for(Row row : rows) {
			price.put(MyRetailWebConstants.PRODUCT_VALUE, row.getDouble(PRICE_COLUMN));
			price.put(MyRetailWebConstants.PRODUCT_CURRENCY_CODE, row.getString(CURRENCY_COLUMN));
			product.put(MyRetailWebConstants.PRODUCT_CURRENT_PRICE, price);
		}
	
		return product;
	}
	
	public boolean updateProductPrice(String productPriceInfo) {
			 
		try {			
			JSONObject productInfoJO = new JSONObject(productPriceInfo);
			int id = productInfoJO.getInt(PRODUCT_ID_COLUMN);
			JSONObject currentPrice = productInfoJO.getJSONObject(MyRetailWebConstants.PRODUCT_CURRENT_PRICE); 
			double price = 	currentPrice.getDouble(MyRetailWebConstants.PRODUCT_VALUE);
			String currency = currentPrice.getString(MyRetailWebConstants.PRODUCT_CURRENCY_CODE);
			
			String query = String.format(UPDATE_PRODUCT_BY_ID, price, currency, id);
			
			cassandraSession.execute(query);
									
		} catch (JSONException e) {
			logger.error(e.getMessage());
			return false;
		}
		
		return true;
	}
}
