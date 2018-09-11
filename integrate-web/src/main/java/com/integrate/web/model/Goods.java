package com.integrate.web.model;

public class Goods {

	private int goodsId;
	private String goods;
	
	public Goods() {
	}
	public Goods(int goodsId, String goods) {
		this.goodsId = goodsId;
		this.goods = goods;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	
	
}
