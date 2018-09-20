package com.integrate.admin.module.app.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.integrate.admin.dao.DaoSupport;
import com.integrate.model.Image;

@Service("imageService")
public class ImageService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public Image insertImage(Image image) throws Exception{
		if(image != null){
			if(image.getCreateTime() == null){
				image.setCreateTime(System.currentTimeMillis());
				image.setUpdateTime(image.getCreateTime());
			}
			
			int count = (int) dao.save("ImageMapper.insertImage", image);
			if(count == 1){
				return image;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Image> getImages(Long rid) throws Exception{
		if(rid != null){
			return (List<Image>) dao.findForList("ImageMapper.getImages", rid);
		}
		return Collections.EMPTY_LIST;
	}

}
