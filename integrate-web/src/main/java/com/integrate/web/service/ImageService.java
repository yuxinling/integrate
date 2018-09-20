package com.integrate.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrate.model.Image;
import com.integrate.web.dao.ImageDao;

@Service
public class ImageService {

	@Autowired
	private ImageDao imageDao;

	public List<Image> getImages(Long rid) {
		return imageDao.getImages(rid);
	}
}
