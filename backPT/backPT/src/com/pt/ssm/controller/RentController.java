package com.pt.ssm.controller;



import java.io.IOException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pt.ssm.po.Rent;
import com.pt.ssm.util.Util;
import com.pt.ssm.service.RentService;
import com.pt.ssm.util.ModelBean;
import com.pt.ssm.util.ModelResults;


@Controller
@RequestMapping("/rent")
public class RentController {
	@Autowired
	private RentService rentService;
	

	@RequestMapping(value="/getAllRentList", method = RequestMethod.POST)
	@ResponseBody
	public ModelResults getAllRentList(
			@RequestParam(value ="rentName",required = false)String rentName,
			@RequestParam(value ="start",required = false,defaultValue="0")long page,
			@RequestParam(value ="end",required = false,defaultValue="20")long size
			) throws JSONException, IOException{
		
		ModelResults model = rentService.getAllRentList(page,size,rentName);
		return model;
	}
	
	
	@RequestMapping(value="/getRentList", method = RequestMethod.POST)
	@ResponseBody
	public ModelResults getRentList(
			@RequestParam(value ="rentName",required = false)String rentName,
			@RequestParam(value ="type",required = false)String type,
			@RequestParam(value ="isCome",required = false)String isCome,
			@RequestParam(value ="start",required = false,defaultValue="0")long page,
			@RequestParam(value ="end",required = false,defaultValue="20")long size
			) throws JSONException, IOException{
		
		ModelResults model = rentService.getRentList(page,size,rentName,type,isCome);
		return model;
	}
	
	
	
	
	
	@RequestMapping(value="/getrightInfoById")
	@ResponseBody
	public ModelBean getrightInfoById(
			@RequestParam(value="id",required = true)Integer id
			) throws JSONException, IOException{
		ModelBean model = rentService.getrightInfoById(id);
		return model;
	}
	
	
	
	@RequestMapping(value="/getRentList1", method = RequestMethod.POST)
	@ResponseBody
	public ModelResults getRentList1(
			@RequestParam(value ="rentName",required = false)String rentName,
			@RequestParam(value ="start",required = false,defaultValue="0")long page,
			@RequestParam(value ="end",required = false,defaultValue="20")long size
			) throws JSONException, IOException{
		
		ModelResults model = rentService.getRentList1(page,size,rentName);
		return model;
	}
	
	
	@RequestMapping(value="/addRentInfo")
	@ResponseBody
	public ModelBean addRentInfo(
			@RequestParam(value ="roomNumber",required = false)String roomNumber,
			@RequestParam(value ="rentName",required = false)String rentName,
			@RequestParam(value ="beginTime",required = false)String beginTime,
			@RequestParam(value ="endTime",required = false)String endTime,
			@RequestParam(value="area",required = false)Double area,
			@RequestParam(value="rentMoney",required = false)Double rentMoney,
			@RequestParam(value="wyf",required = false)Double wyf,
			@RequestParam(value="sdf",required = false)Double sdf,
			@RequestParam(value="tcf",required = false)Double tcf,
			@RequestParam(value="zzf",required = false)Double zzf,
			@RequestParam(value="total",required = false)Double total,
			@RequestParam(value="htong",required = false)String htong
			) throws JSONException, IOException{
		Rent rent = new Rent();
		if(Util.isNull(area)){
			area = 0.0;
		}
		if(Util.isNull(rentMoney)){
			rentMoney = 0.0;
		}
		
		if(Util.isNull(wyf)){
			wyf = 0.0;
		}
		
		if(Util.isNull(sdf)){
			sdf = 0.0;
		}
		
		if(Util.isNull(tcf)){
			tcf = 0.0;
		}
		
		if(Util.isNull(zzf)){
			zzf = 0.0;
		}
		
		rent.setWyf(wyf);
		rent.setSdf(sdf);
		rent.setTcf(tcf);
		rent.setZzf(zzf);
		rent.setTotal(total);
		rent.setArea(area);
		rent.setBeginTime(beginTime);
		rent.setEndTime(endTime);
		rent.setRentMoney(rentMoney);
		rent.setRentName(rentName);
		rent.setRoomNumber(roomNumber);
		rent.setHtong(htong);
		ModelBean model = rentService.addRentInfo(rent);
		return model;
	}
	
	
	@RequestMapping(value="/getRentInfoById")
	@ResponseBody
	public ModelBean getRentInfoById(
			@RequestParam(value="id",required = true)Integer id
			) throws JSONException, IOException{
		ModelBean model = rentService.getRentInfoById(id);
		return model;
	}
	
	
	
	@RequestMapping(value="/updateStatueById")
	@ResponseBody
	public ModelBean updateStatueById(
			@RequestParam(value="id",required = true)Integer id,
			@RequestParam(value ="ispay",required = true)String ispay,
			@RequestParam(value ="sfje",required = true)Double sfje,
			@RequestParam(value ="beizhu",required = true)String beizhu
			) throws JSONException, IOException{
		ModelBean model = rentService.updateStatueById(id,ispay,beizhu,sfje);
		return model;
	}
	
	
	
	@RequestMapping(value="/updateRentInfo")
	@ResponseBody
	public ModelBean updateRentInfo(
			@RequestParam(value="id",required = true)Integer id,
			@RequestParam(value ="roomNumber",required = false)String roomNumber,
			@RequestParam(value ="rentName",required = false)String rentName,
			@RequestParam(value ="beginTime",required = false)String beginTime,
			@RequestParam(value ="endTime",required = false)String endTime,
			@RequestParam(value="area",required = false)Double area,
			@RequestParam(value="rentMoney",required = false)Double rentMoney,
			@RequestParam(value="wyf",required = false)Double wyf,
			@RequestParam(value="sdf",required = false)Double sdf,
			@RequestParam(value="tcf",required = false)Double tcf,
			@RequestParam(value="zzf",required = false)Double zzf,
			@RequestParam(value="total",required = false)Double total,
			@RequestParam(value="htong",required = false)String htong
			) throws JSONException, IOException{
		Rent rent = new Rent();
		if(Util.isNull(area)){
			area = 0.0;
		}
		if(Util.isNull(rentMoney)){
			rentMoney = 0.0;
		}
		
		if(Util.isNull(wyf)){
			wyf = 0.0;
		}
		
		if(Util.isNull(sdf)){
			sdf = 0.0;
		}
		
		if(Util.isNull(tcf)){
			tcf = 0.0;
		}
		
		if(Util.isNull(zzf)){
			zzf = 0.0;
		}
		
		rent.setWyf(wyf);
		rent.setSdf(sdf);
		rent.setTcf(tcf);
		rent.setZzf(zzf);
		rent.setTotal(total);
		rent.setArea(area);
		rent.setBeginTime(beginTime);
		rent.setEndTime(endTime);
		rent.setRentMoney(rentMoney);
		rent.setRentName(rentName);
		rent.setRoomNumber(roomNumber);
		rent.setId(id);
		rent.setHtong(htong);
		ModelBean model = rentService.updateRentInfo(rent);
		return model;
	}
	
	
	
	/**
	 * 批量删除
	 * @param roleid[]
	 * */
	@RequestMapping(value = "delRentInfoByIds")
	@ResponseBody
	public ModelBean delRentInfoByIds(@RequestParam(value = "ids",required = true)long[] ids) throws Exception{
		return rentService.delRentInfoByIds(ids);
	}
}