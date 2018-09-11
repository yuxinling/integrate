package com.integrate.enums;

public enum PriceEnum {
	/*价格筛选范围  */
	range_one("0-200", " and price between 0  and  200  "), 
	range_two("200-1000", " and price between 200  and  1000  "),
	range_three("1000以上", " and price > 1000   "),;
	String range;
	String sql;

	PriceEnum(String range, String sql) {
		this.range = range;
		this.sql = sql;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

}
