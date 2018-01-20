package com.pt.ssm.service;

import java.util.List;
import java.util.Map;

import com.pt.ssm.po.CustomerData;
import com.pt.ssm.po.CustomerRight;
import com.pt.ssm.util.ModelBean;
import com.pt.ssm.util.ModelResults;

public interface CustomerService {

	ModelResults getCustomerList(long page, long size, String customerName,
			String danWei, String post);

	ModelBean addCustomerInfo(CustomerData cus);

	ModelBean getCustomerInfoById(Integer id);

	ModelBean updateCustomerInfo(CustomerData cus);

	ModelBean delCustomerInfoByIds(long[] ids);

	ModelResults getCustomerList1(long page, long size, String huXing,
			String louYu, String beginTime, String endTime);

	ModelBean addCustomer(Map<String, Object> paramMap);

	ModelBean updateCustomerInfo1(Map<String, Object> paramMap);

	ModelBean getCustomerInfoByLouHao(String louHao);
	
	ModelResults getExportExcel(Map<String, Object> paramMap);

	ModelBean addCustomerRight(CustomerRight right);

	ModelBean getCustomerRightById(Integer customerDataId);

	

}