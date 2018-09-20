package com.integrate.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrate.model.Image;
import com.integrate.model.Product;
import com.integrate.web.dao.ImageDao;
import com.integrate.web.dao.ProductDao;

@Service
public class ProductService {

	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private ProductDao productDao;

	public List<Map<String,Object>> getProducts() {
		return productDao.getProducts();
	}
	
	public List<Map<String,Object>> searchProducts(String keyword) {
		return productDao.getProducts(keyword);
	}
	
	public Product getProduct(Long id) {
		Product product =  productDao.getProduct(id);
		if(product != null){
			List<Image> images = imageDao.getImages(product.getId());
			product.setImages(images);
		}
		return product;
	}
}
