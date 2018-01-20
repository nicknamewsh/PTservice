package com.pt.ssm.controller;



import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pt.ssm.po.CustomerData;
import com.pt.ssm.po.CustomerRight;
import com.pt.ssm.util.Util;
import com.pt.ssm.service.CustomerService;
import com.pt.ssm.util.ModelBean;
import com.pt.ssm.util.ModelResults;
import com.pt.ssm.util.ToJson;


@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	

	@RequestMapping(value="/getCustomerList")
	@ResponseBody
	public ModelResults getCustomerList(
			@RequestParam(value ="customerName",required = false)String customerName,
			@RequestParam(value ="danWei",required = false)String danWei,
			@RequestParam(value ="post",required = false)String post,
			@RequestParam(value ="start",required = false,defaultValue="0")long page,
			@RequestParam(value ="end",required = false,defaultValue="20")long size
			) throws JSONException, IOException{
		
		ModelResults model = customerService.getCustomerList(page,size,customerName,danWei,post);
		return model;
	}
	
	@RequestMapping(value="/getCustomerList1")
	@ResponseBody
	public ModelResults getCustomerList1(
			@RequestParam(value ="huXing",required = false)String huXing,
			@RequestParam(value ="louYu",required = false)String louYu,
			@RequestParam(value ="beginTime",required = false)String beginTime,
			@RequestParam(value ="endTime",required = false)String endTime,
			@RequestParam(value ="start",required = false,defaultValue="0")long page,
			@RequestParam(value ="end",required = false,defaultValue="20")long size
			) throws JSONException, IOException{
		
		ModelResults model = customerService.getCustomerList1(page,size,huXing,louYu,beginTime,endTime);
		return model;
	}
	
	
	@RequestMapping(value="/addCustomerRight")
	@ResponseBody
	public ModelBean addCustomerRight(
			@RequestParam(value ="customerDataId",required = false)Integer customerDataId,
			@RequestParam(value ="fzje",required = false)Double fzje,
			@RequestParam(value ="wyje",required = false)Double wyje,
			@RequestParam(value ="rentTime",required = false)String rentTime,
			@RequestParam(value ="rentType",required = false)Integer rentType,
			@RequestParam(value ="jieShu",required = false)String jieShu,
			@RequestParam(value ="rentincrea",required = false)Double rentincrea,
			@RequestParam(value ="monthNum",required = false)Integer monthNum
			) throws JSONException, IOException, ParseException{
		
		ModelBean model = new ModelBean();
		
		if(monthNum == 0){
			monthNum = 1;
		}
		
		//yearNum  计算一共多少年
		int yearNum = monthNum/12;
		int yearYu = monthNum%12;
		if(yearYu != 0){
			yearNum = yearNum + 1;
		}
		//房租递增
		List<Double> fzList = new ArrayList<Double>();
		Double fangzu = 0.0;
		//根据年份来计算每年增长的房租 存入fzList中
		for(int i = 0;i<yearNum;i++){
			if(i == 0){
				fangzu = fzje;
			}else{
				fangzu = fangzu + fangzu*rentincrea/100;
			}
			fzList.add(fangzu);
		}
		
		
		//用总月数/chushu  算出循环插入的次数
		int chushu = 3;
		if(rentType == 0){
			chushu = 3;//季
		}else if(rentType == 1){
			chushu = 6;//半年
		}else if(rentType == 2){
			chushu = 12;//年
		}
		
		//循环的次数
		int count = monthNum/chushu;
		int yu = monthNum%chushu;
		if(yu != 0){
			count = count + 1;
		}
		
		//存放开始时间和结束时间的时间段
		List<String> dateList = new ArrayList<String>();
		dateList.add(rentTime);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc1=new GregorianCalendar(); 
		//gc1.add(filed,value); 
		//filed: 1 一年   2  一个月   3 一周   4 一周   5 一天
		//value:相加的 次数   季：2,3     半年:2,6   年：1,1 || 2,12
		for(int i = 0;i<count;i++){
			gc1.setTime(sdf.parse(rentTime)); 
			if(rentType == 0){//季
				gc1.add(2,3);
			}else if(rentType == 1){//半年
				gc1.add(2,6);
			}else if(rentType == 2){//年
				gc1.add(1,1);
			}
			Date time = gc1.getTime();
			rentTime = sdf.format(time);
			dateList.add(rentTime);
		}
		
		dateList.remove(dateList.size()-1);
		dateList.add(jieShu);
		
		
		int size = 4;
		if(rentType == 0){
			size = 4;//季
		}else if(rentType == 1){
			size = 2;//半年
		}else if(rentType == 2){
			size = 1;//年
		}
		
		
		for(int j = 0;j<yearNum;j++){
			if(dateList.size()>size){
				for(int i = size*j;i<size*(j+1);i++){
					CustomerRight right = new CustomerRight();
					right.setFanzhu(fzList.get(j));
					right.setCustomerDataId(customerDataId);
					right.setEndTime(dateList.get(i+1));
					right.setStartTime(dateList.get(i));
					model = customerService.addCustomerRight(right);
					//System.out.println(dateList.get(i)+"~"+dateList.get(i+1)+"----"+right.getFanzhu());
					
				}
			}else{
				for(int i = 0;i<dateList.size();i++){
					if(i != dateList.size() -1){
						CustomerRight right = new CustomerRight();
						right.setFanzhu(fzList.get(j));
						right.setCustomerDataId(customerDataId);
						right.setEndTime(dateList.get(i+1));
						right.setStartTime(dateList.get(i));
						model = customerService.addCustomerRight(right);
						//System.out.println(dateList.get(i)+"~"+dateList.get(i+1)+"----"+right.getFanzhu());
					}
				}
			}
		}
		return model;
	}
	
	
	
	@RequestMapping(value="/addCustomerInfo")
	@ResponseBody
	public ModelBean addCustomerInfo(
			@RequestParam(value ="customerName",required = false)String customerName,
			@RequestParam(value ="customerGender",required = false)Integer customerGender,
			@RequestParam(value ="danWei",required = false)String danWei,
			@RequestParam(value ="post",required = false)String post,
			@RequestParam(value ="email",required = false)String email,
			@RequestParam(value ="wangzhan",required = false)String wangzhan,
			@RequestParam(value ="mobilePhone",required = false)String mobilePhone,
			@RequestParam(value ="telePhone",required = false)String telePhone,
			@RequestParam(value ="companyPhone",required = false)String companyPhone,
			@RequestParam(value ="fax",required = false)String fax,
			@RequestParam(value ="QQHao",required = false)String QQHao
			) throws JSONException, IOException{
		
		CustomerData cus = new CustomerData();
		if(Util.isNull(customerGender)){
			customerGender = 0;
		}
		cus.setCompanyPhone(companyPhone);
		cus.setCustomerGender(customerGender);
		cus.setDanWei(danWei);
		cus.setEmail(email);
		cus.setFax(fax);
		cus.setMobilePhone(mobilePhone);
		cus.setPost(post);
		cus.setQQHao(QQHao);
		cus.setTelePhone(telePhone);
		cus.setCustomerName(customerName);
		cus.setWangzhan(wangzhan);
		ModelBean model = customerService.addCustomerInfo(cus);
		return model;
	}
	
	
	@RequestMapping(value="/addCustomer")
	@ResponseBody
	public ModelBean addCustomer(HttpServletRequest request) throws JSONException, IOException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerName",  request.getParameter("customerName")==null?"":request.getParameter("customerName"));
		paramMap.put("unit", request.getParameter("unit")==null?"":request.getParameter("unit"));
		paramMap.put("registerAdress", request.getParameter("registerAdress")==null?"":request.getParameter("registerAdress"));
		paramMap.put("registerNumber", request.getParameter("registerNumber")==null?"":request.getParameter("registerNumber"));
		paramMap.put("registerMoney", request.getParameter("registerMoney")==null?"":request.getParameter("registerMoney"));
		paramMap.put("enterprisexz", request.getParameter("enterprisexz")==null?"":request.getParameter("enterprisexz"));
		paramMap.put("enterprisess", request.getParameter("enterprisess")==null?"":request.getParameter("enterprisess"));
		paramMap.put("mobilePhone", request.getParameter("mobilePhone")==null?"":request.getParameter("mobilePhone"));
		paramMap.put("kaiShi", request.getParameter("kaiShi")==null?"":request.getParameter("kaiShi"));
		paramMap.put("jieShu", request.getParameter("jieShu")==null?"":request.getParameter("jieShu"));
		paramMap.put("loudong", request.getParameter("loudong")==null?"":request.getParameter("loudong"));
		paramMap.put("louhao", request.getParameter("louhao")==null?"":request.getParameter("louhao"));
		paramMap.put("contractno", request.getParameter("contractno")==null?"":request.getParameter("contractno"));
		paramMap.put("rentincrea", request.getParameter("rentincrea")==null?"":request.getParameter("rentincrea"));
		paramMap.put("rent", request.getParameter("rent")==null?"":request.getParameter("rent"));
		paramMap.put("property", request.getParameter("property")==null?"":request.getParameter("property"));
		paramMap.put("cars", request.getParameter("cars")==null?"":request.getParameter("cars"));
		paramMap.put("carssta", request.getParameter("carssta")==null?"":request.getParameter("carssta"));
		paramMap.put("carsend", request.getParameter("carsend")==null?"":request.getParameter("carsend"));
		paramMap.put("carsmoney", request.getParameter("carsmoney")==null?"":request.getParameter("carsmoney"));
		paramMap.put("deposit", request.getParameter("deposit")==null?"":request.getParameter("deposit"));
		paramMap.put("sfzx", request.getParameter("sfzx")==null?"":request.getParameter("sfzx"));
		paramMap.put("zxbzj", request.getParameter("zxbzj")==null?"":request.getParameter("zxbzj"));
		paramMap.put("area", request.getParameter("area")==null?"":request.getParameter("area"));
		paramMap.put("tc", request.getParameter("tc")==null?"0":request.getParameter("tc"));
		paramMap.put("hys", request.getParameter("hys")==null?"0":request.getParameter("hys"));
		paramMap.put("nbbj", request.getParameter("nbbj")==null?"0":request.getParameter("nbbj"));
		paramMap.put("zwzl", request.getParameter("zwzl")==null?"0":request.getParameter("zwzl"));
		paramMap.put("dk", request.getParameter("dk")==null?"0":request.getParameter("dk"));
		paramMap.put("cyfw", request.getParameter("cyfw")==null?"0":request.getParameter("cyfw"));
		paramMap.put("wxfw", request.getParameter("wxfw")==null?"0":request.getParameter("wxfw"));
		paramMap.put("frzm", request.getParameter("frzm")==null?"":request.getParameter("frzm"));
		paramMap.put("nsrzm", request.getParameter("nsrzm")==null?"":request.getParameter("nsrzm"));
		paramMap.put("imginfo", request.getParameter("imginfo")==null?"":request.getParameter("imginfo"));
		paramMap.put("hturl", request.getParameter("hturl")==null?"":request.getParameter("hturl"));
		paramMap.put("zxurl", request.getParameter("zxurl")==null?"":request.getParameter("zxurl"));
		paramMap.put("huXing", request.getParameter("huXing")==null?"":request.getParameter("huXing"));
		paramMap.put("fzje", request.getParameter("fzje")==null?"":request.getParameter("fzje"));
		paramMap.put("wyje", request.getParameter("wyje")==null?"":request.getParameter("wyje"));
		paramMap.put("renttime", request.getParameter("renttime")==null?"":request.getParameter("renttime"));
		paramMap.put("renttype", request.getParameter("renttype")==null?"":request.getParameter("renttype"));
		
		ModelBean model = customerService.addCustomer(paramMap);
		return model;
	}
	
	
	
	@RequestMapping(value="/getCustomerInfoByLouHao")
	@ResponseBody
	public ModelBean getCustomerInfoByLouHao(
			@RequestParam(value="loudong",required = true)String loudong
			) throws JSONException, IOException{
		ModelBean model = customerService.getCustomerInfoByLouHao(loudong);
		String json = ToJson.objectToJson(model);
		System.out.println(json);
		return model;
	}
	
	
	@RequestMapping(value="/getCustomerInfoById")
	@ResponseBody
	public ModelBean getCustomerInfoById(
			@RequestParam(value="id",required = true)Integer id
			) throws JSONException, IOException{
		ModelBean model = customerService.getCustomerInfoById(id);
		return model;
	}
	
	
	@RequestMapping(value="/getCustomerRightById")
	@ResponseBody
	public ModelBean getCustomerRightById(
			@RequestParam(value="customerDataId",required = true)Integer customerDataId
			) throws JSONException, IOException{
		ModelBean model = customerService.getCustomerRightById(customerDataId);
		return model;
	}
	
	
	@RequestMapping(value="/updateCustomerInfo")
	@ResponseBody
	public ModelBean updateCustomerInfo(
			@RequestParam(value="id",required = true)Integer id,
			@RequestParam(value ="customerName",required = false)String customerName,
			@RequestParam(value ="customerGender",required = false)Integer customerGender,
			@RequestParam(value ="danWei",required = false)String danWei,
			@RequestParam(value ="post",required = false)String post,
			@RequestParam(value ="email",required = false)String email,
			@RequestParam(value ="wangzhan",required = false)String wangzhan,
			@RequestParam(value ="mobilePhone",required = false)String mobilePhone,
			@RequestParam(value ="telePhone",required = false)String telePhone,
			@RequestParam(value ="companyPhone",required = false)String companyPhone,
			@RequestParam(value ="fax",required = false)String fax,
			@RequestParam(value ="QQHao",required = false)String QQHao
			) throws JSONException, IOException{
		CustomerData cus = new CustomerData();
		if(Util.isNull(customerGender)){
			customerGender = 0;
		}
		cus.setCompanyPhone(companyPhone);
		cus.setCustomerGender(customerGender);
		cus.setDanWei(danWei);
		cus.setEmail(email);
		cus.setFax(fax);
		cus.setMobilePhone(mobilePhone);
		cus.setPost(post);
		cus.setQQHao(QQHao);
		cus.setTelePhone(telePhone);
		cus.setWangzhan(wangzhan);
		cus.setCustomerName(customerName);
		cus.setId(id);
		ModelBean model = customerService.updateCustomerInfo(cus);
		return model;
	}
	
	
	@RequestMapping(value="/updateCustomerInfo1")
	@ResponseBody
	public ModelBean updateCustomerInfo1(HttpServletRequest request) throws JSONException, IOException{
		
		
		System.out.print("------------------"+request.getParameter("enterprisess"));
		

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id",  request.getParameter("id")==null?"":request.getParameter("id"));
		paramMap.put("customerName",  request.getParameter("customerName")==null?"":request.getParameter("customerName"));
		paramMap.put("unit", request.getParameter("unit")==null?"":request.getParameter("unit"));
		paramMap.put("registerAdress", request.getParameter("registerAdress")==null?"":request.getParameter("registerAdress"));
		paramMap.put("registerNumber", request.getParameter("registerNumber")==null?"":request.getParameter("registerNumber"));
		paramMap.put("registerMoney", request.getParameter("registerMoney")==null?"":request.getParameter("registerMoney"));
		paramMap.put("enterprisexz", request.getParameter("enterprisexz")==null?"":request.getParameter("enterprisexz"));
		paramMap.put("enterprisess", request.getParameter("enterprisess")==null?"":request.getParameter("enterprisess"));
		paramMap.put("mobilePhone", request.getParameter("mobilePhone")==null?"":request.getParameter("mobilePhone"));
		paramMap.put("kaiShi", request.getParameter("kaiShi")==null?"":request.getParameter("kaiShi"));
		paramMap.put("jieShu", request.getParameter("jieShu")==null?"":request.getParameter("jieShu"));
		paramMap.put("loudong", request.getParameter("loudong")==null?"":request.getParameter("loudong"));
		paramMap.put("louhao", request.getParameter("louhao")==null?"":request.getParameter("louhao"));
		paramMap.put("contractno", request.getParameter("contractno")==null?"":request.getParameter("contractno"));
		paramMap.put("rentincrea", request.getParameter("rentincrea")==null?"":request.getParameter("rentincrea"));
		paramMap.put("rent", request.getParameter("rent")==null?"":request.getParameter("rent"));
		paramMap.put("property", request.getParameter("property")==null?"":request.getParameter("property"));
		paramMap.put("cars", request.getParameter("cars")==null?"":request.getParameter("cars"));
		paramMap.put("carssta", request.getParameter("carssta")==null?"":request.getParameter("carssta"));
		paramMap.put("carsend", request.getParameter("carsend")==null?"":request.getParameter("carsend"));
		paramMap.put("carsmoney", request.getParameter("carsmoney")==null?"":request.getParameter("carsmoney"));
		paramMap.put("deposit", request.getParameter("deposit")==null?"":request.getParameter("deposit"));
		paramMap.put("sfzx", request.getParameter("sfzx")==null?"":request.getParameter("sfzx"));
		paramMap.put("zxbzj", request.getParameter("zxbzj")==null?"":request.getParameter("zxbzj"));
		paramMap.put("area", request.getParameter("area")==null?"":request.getParameter("area"));
		paramMap.put("tc", request.getParameter("tc")==null?"0":request.getParameter("tc"));
		paramMap.put("hys", request.getParameter("hys")==null?"0":request.getParameter("hys"));
		paramMap.put("nbbj", request.getParameter("nbbj")==null?"0":request.getParameter("nbbj"));
		paramMap.put("zwzl", request.getParameter("zwzl")==null?"0":request.getParameter("zwzl"));
		paramMap.put("dk", request.getParameter("dk")==null?"0":request.getParameter("dk"));
		paramMap.put("cyfw", request.getParameter("cyfw")==null?"0":request.getParameter("cyfw"));
		paramMap.put("wxfw", request.getParameter("wxfw")==null?"0":request.getParameter("wxfw"));
		paramMap.put("frzm", request.getParameter("frzm")==null?"":request.getParameter("frzm"));
		paramMap.put("nsrzm", request.getParameter("nsrzm")==null?"":request.getParameter("nsrzm"));
		paramMap.put("imginfo", request.getParameter("imginfo")==null?"":request.getParameter("imginfo"));
		paramMap.put("hturl", request.getParameter("hturl")==null?"":request.getParameter("hturl"));
		paramMap.put("zxurl", request.getParameter("zxurl")==null?"":request.getParameter("zxurl"));
		paramMap.put("huXing", request.getParameter("huXing")==null?"":request.getParameter("huXing"));
		paramMap.put("fzje", request.getParameter("fzje")==null?"":request.getParameter("fzje"));
		paramMap.put("wyje", request.getParameter("wyje")==null?"":request.getParameter("wyje"));
		paramMap.put("renttime", request.getParameter("renttime")==null?"":request.getParameter("renttime"));
		paramMap.put("renttype", request.getParameter("renttype")==null?"":request.getParameter("renttype"));
		ModelBean model = customerService.updateCustomerInfo1(paramMap);
		return model;
	}
	
	
	
	@RequestMapping(value="/ExportExcel")
	@ResponseBody
	public ModelResults ExportExcel(HttpServletRequest request) throws JSONException, IOException{

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("rentName",  request.getParameter("rentName")==null?"":request.getParameter("rentName"));
		paramMap.put("isCome",  request.getParameter("isCome")==null?"":request.getParameter("isCome"));
		ModelResults model = customerService.getExportExcel(paramMap);
		return model;
	}
	
	
	
	/**
	 * 批量删除
	 * @param roleid[]
	 * */
	@RequestMapping(value = "delCustomerInfoByIds")
	@ResponseBody
	public ModelBean delCustomerInfoByIds(@RequestParam(value = "ids",required = true)long[] ids) throws Exception{
		return customerService.delCustomerInfoByIds(ids);
	}
}