package com.integrate.admin.module.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.integrate.admin.dao.DaoSupport;
import com.integrate.model.Product;

@Service("productService")
public class ProductService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public Product insertProduct(Product product) throws Exception {
		if (product != null) {
			int count = (int) dao.save("ProductMapper.insertProduct", product);
			if (count == 1) {
				return product;
			}
		}
		return null;
	}
	
	public Product updateProduct(Product product) throws Exception {
		if (product != null) {
			int count = (int) dao.update("ProductMapper.updateProduct", product);
			if (count == 1) {
				return product;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Product> getProducts() throws Exception {
		return (List<Product>) dao.findForList("ProductMapper.getProducts", null);
	}
	
	public Product getProduct(Long id) throws Exception {
		return (Product) dao.findForObject("ProductMapper.getProduct", id);
	}
	
	public int deleteProduct(Long id) throws Exception {
		return (int) dao.delete("ProductMapper.deleteProduct", id);
	}

}
