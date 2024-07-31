package com.spring.javaclassS.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaclassS.service.ScheduleService;
import com.spring.javaclassS.vo.ScheduleVO;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	
	// 스케줄 달력 띄우기
	@RequestMapping(value = "/schedule", method=RequestMethod.GET)
	public String scheduleGet() {
		scheduleService.getSchedule();
		return "schedule/schedule";
	}
	
	// 일정내역들어가서 메뉴보기
	@RequestMapping(value = "/scheduleMenu", method=RequestMethod.GET)
	public String scheduleMenuGet(HttpSession session, String ymd, Model model) {
		String mid = (String) session.getAttribute("sMid");
		
		String mm = "", dd = "";
		String[] ymdArr = ymd.split("-");
		// 2024-8-5/2024-8-15/2024-10-5  ==> 2024-08-05
		if(ymd.length() != 10) {
			if(ymdArr[1].length() == 1) mm = "0" + ymdArr[1];
			else mm = ymdArr[1];
			if(ymdArr[2].length() == 1) dd = "0" + ymdArr[2];
			else dd = ymdArr[2];
			ymd = ymdArr[0] + "-" + mm + "-" + dd;
		}
		
		List<ScheduleVO> vos = scheduleService.getScheduleMenu(mid, ymd);
		
		model.addAttribute("vos",vos);
		model.addAttribute("ymd", ymd);
		model.addAttribute("scheduleCnt", vos.size());

		return "schedule/scheduleMenu";
	}
	
	// 스케줄 등록하기
	@ResponseBody
	@RequestMapping(value = "/scheduleInputOk", method=RequestMethod.POST)
	public String scheduleInputOkPost(ScheduleVO vo) {
		return scheduleService.setScheduleInputOk(vo) + "";
	}
	
	// 스케줄 수정하기
	@ResponseBody
	@RequestMapping(value = "/scheduleUpdateOk", method=RequestMethod.POST)
	public String scheduleUpdateOkPost(ScheduleVO vo) {
		return scheduleService.setScheduleUpdateOk(vo) + "";
	}
	
	// 스케줄 삭제하기
	@ResponseBody
	@RequestMapping(value = "/scheduleDeleteOk", method=RequestMethod.POST)
	public String scheduleDeleteOkPost(int idx) {
		return scheduleService.setScheduleDeleteOk(idx) + "";
	}
	
	@RequestMapping(value = "/adminSchedule", method=RequestMethod.GET)
	public String adminScheduleGet() {
		scheduleService.getSchedule();
		return "admin/schedule/adminSchedule";
	}
		
}
