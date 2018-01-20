package com.pt.ssm.service;

import java.io.UnsupportedEncodingException;

import com.pt.ssm.util.ModelBean;
import com.pt.ssm.util.ModelResults;

public interface WarrantyService {

	
	//获取Property集合
	ModelResults getWarrantyList(String id, String repairsname, long page, long pageSize) throws UnsupportedEncodingException;
	//添加用户信息
	ModelBean addWarranty(String repairsname,String repairsphone,String applycontent,String applyaddress,String repairsdate,String applysource,String fj);
	//查询单个信息
	ModelBean getWarrantyID(Integer id);
	//修改数据
	ModelBean upWarranty(String id,String repairsname,String repairsphone,String applycontent,String applyaddress,String repairsdate,String applysource,String fj);
	//删除数据
	ModelBean deWarranty(String id);
	//提交数据
	ModelBean subWarranty(String id,String name,String departid,String tijiaodate);




}