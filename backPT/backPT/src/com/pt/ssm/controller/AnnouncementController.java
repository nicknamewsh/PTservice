package com.pt.ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pt.ssm.po.Announcement;
import com.pt.ssm.service.AnnouncementService;
import com.pt.ssm.util.ModelBean;
import com.pt.ssm.util.ModelResults;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {
	@Autowired
	private AnnouncementService service;
	@RequestMapping("getAnnouncementList")
	@ResponseBody
	public ModelResults getModelResultsList(
			@RequestParam(required = true,value = "noticetitle")String noticetitle,
			@RequestParam(required = true,value = "start")Integer page,
			@RequestParam(required = true,value = "end")Integer size,
			@RequestParam(required = true,value = "departid")String departid
			) throws Exception{
		return service.getDateList(noticetitle, page, size,departid);
	}
	
	
	@RequestMapping("getAnnouncementList1")
	@ResponseBody
	public ModelBean getAnnouncementList1(
			@RequestParam(required = true,value = "statues")Integer statues
			) throws Exception{
		return service.getDateList1(statues);
	}
	
	@RequestMapping("getdepa")
	@ResponseBody
	public ModelBean getdepa(
			@RequestParam(required = true,value = "id")String id
			) throws Exception{
		return service.getdepa(id);
	}
	
	
	@ResponseBody
	@RequestMapping("/addAnnounment")
	public ModelBean addAnnounment(
			@RequestParam("noticeperson")String noticeperson,
			@RequestParam("noticeconent")String noticeconent,
			@RequestParam("noticestatues")Integer noticestatues,
			@RequestParam("noticetime")String noticetime,
			@RequestParam("statues")Integer statues,
			@RequestParam("noticetitle") String noticetitle,
			@RequestParam("noticename") String noticename
			) throws Exception{
		Announcement an = new Announcement();
		an.setNoticeconent(noticeconent);
		an.setNoticeperson(noticeperson);
		an.setNoticestatues(noticestatues);
		an.setNoticetime(noticetime);
		an.setStatues(statues);
		an.setNoticetitle(noticetitle);
		an.setNoticetitle(noticetitle);
		an.setNoticename(noticename);
		return service.addAnnouncement(an);
	}
	
	
	@ResponseBody
	@RequestMapping("deleteAnnounment")
	public ModelBean deleteAnnounment(@RequestParam("id")long[] id) throws Exception{
		return service.deleteAnnouncement(id);
	}
	
	
	@ResponseBody
	@RequestMapping("updateAnnouncement")
	public ModelBean updateAnnouncement(
			@RequestParam("noticeperson")String noticeperson,
			@RequestParam("noticeconent")String noticeconent,
			@RequestParam("noticestatues")Integer noticestatues,
			@RequestParam("noticetime")String noticetime,
			@RequestParam("statues")Integer statues,
			@RequestParam("id") Integer id,
			@RequestParam("noticetitle") String noticetitle
			) throws Exception{
		Announcement an = new Announcement();
		an.setId(id);
		an.setNoticeconent(noticeconent);
		an.setNoticeperson(noticeperson);
		an.setNoticestatues(noticestatues);
		an.setNoticetime(noticetime);
		an.setStatues(statues);
		an.setNoticetitle(noticetitle);
		return service.updateAnnouncement(an);
	}
	
	
	@ResponseBody
	@RequestMapping("/getOneById")
	public ModelBean getOneById(@RequestParam("id")long id) throws Exception{
		
		return service.getAnnouncementById(id);
	}
	
	
	@RequestMapping("/updateOne")
	@ResponseBody
	public ModelBean updateOne(
			@RequestParam("id")long id,
			@RequestParam("noticestatues")long statues
			) throws Exception{
		return service.updateStatues(id, statues);
	}
	
	
	
	
	
	
	
	
	@RequestMapping("getAnnouncementListsh")
	@ResponseBody
	public ModelResults getAnnouncementListsh(
			@RequestParam(required = true,value = "Noticetitle")String Noticetitle,
			@RequestParam(required = true,value = "start")Integer page,
			@RequestParam(required = true,value = "end")Integer size,
			@RequestParam(required = true,value = "departid")String departid
			) throws Exception{
		return service.getAnnomentListsh(Noticetitle, page, size,departid);
	}
	
	
	@RequestMapping("getAnnouncementListfb")
	@ResponseBody
	public ModelResults getAnnouncementListfb(
			@RequestParam(required = true,value = "Noticetitle")String Noticetitle,
			@RequestParam(required = true,value = "start")Integer page,
			@RequestParam(required = true,value = "end")Integer size
			) throws Exception{
		return service.getAnnomentListfb(Noticetitle, page, size);
	}
	
	
	@RequestMapping("/updateshenhe")
	@ResponseBody
	public ModelBean updateshenhe(
			@RequestParam("id")long id,
			@RequestParam("noticestatues")long noticestatues,
			@RequestParam(value = "htstatues",required = true)String htstatues,
			@RequestParam(value = "htreason")String htreason
			) throws Exception{
		return service.updateshenhe(id, noticestatues,htstatues,htreason);
	}
	
	
	
	


}