package com.integrate.admin.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.integrate.core.db.AbstractDbService;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 主业务数据库连接服务 
 *
 */
@Component
public class AppDbService extends AbstractDbService {
	
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbc = new JdbcTemplate(dataSource);
	}
	
	
	/**
	 * 根据ID查找数据库的SQL分页按需求封装的方法;
	 * 
	 * @param hql 要查询的SQL语句;
	 * @param offId  当前查询的ID: 获取下标ID索引值;
	 *             (初始时传0,pageType传 1,获取最新数据;
	 *              1.刷新 传最大ID, 
	 *              3.上一页也是传获取最大ID,
	 *              2.下一页,传本页的最小ID)
	 * @param type 前端传过来的按那种类型查询
	 *  分页获取方向: 1.刷新(最新) 2.下一页(更多) 3.上一页(比当前页ID要新的)
	 * @return
	 */
	public String getPageById( String hql, int offId, int type,String orderId) {
		StringBuffer sb = new StringBuffer("");
		int hqlstate = hql.toLowerCase().indexOf(" where ");
		if (hqlstate > 0) {
			sb.append(" and ");
		} else {
			sb.append(" where ");
		}
		switch (type) {
		case 1: // 刷新调用
			sb.append(orderId).append(" > ").append(offId).append(" order by ").append(orderId).append(" desc");
			break;
		case 2: // 上一页调用
			sb.append(orderId).append(" > ").append(offId).append(" order by ").append(orderId).append(" asc");
			break;
		case 3: // 更多时调用
			sb.append(orderId).append(" < ").append(offId).append(" order by ").append(orderId).append(" desc");
			break;
		default:
			
			break;
		}
		sb.append(" LIMIT ? ");
		hql += sb.toString();
		return hql;
	}


}
