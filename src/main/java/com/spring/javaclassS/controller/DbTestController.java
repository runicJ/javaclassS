package com.spring.javaclassS.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaclassS.service.DbTestService;
import com.spring.javaclassS.service.UserService;
import com.spring.javaclassS.vo.UserVO;

@Controller
@RequestMapping("/dbTest")
public class DbTestController {
	
	@Autowired  // service 객체 interface
	DbTestService dbTestService;  // interface 만들기

	//getMapping 해도 관계없음
	@RequestMapping(value = "/dbTestList", method = RequestMethod.GET)
	public String dbTestListGet(Model model) {
		ArrayList<UserVO> vos = dbTestService.getDbTestList();
		model.addAttribute("vos", vos);
		
		return "user/dbTestList";
	}
	
	@RequestMapping(value = "/dbTestSearch/{mid}", method = RequestMethod.GET)
	public String dbTestSearchGet(Model model, @PathVariable String mid) {
		ArrayList<UserVO> vos = dbTestService.getDbTestList();
		model.addAttribute("vos", vos);

		ArrayList<UserVO> searchVos = dbTestService.getDbTestSearch(mid);
		model.addAttribute("searchVos", searchVos);
		
		return "user/dbTestList";
	}
	
	@RequestMapping(value = "/dbTestDelete", method = RequestMethod.GET)
	public String dbTestDeleteGet(int idx) {
		int res = dbTestService.setDbTestDelete(idx);

		if(res != 0) return "redirect:/message/dbTestDeleteOk";
		else return "redirect:/message/dbTestDeleteNo";
	}
	
	@RequestMapping(value = "/dbTestInputOk", method = RequestMethod.POST)
	public String dbTestInputOkGet(UserVO vo) {
		int res = dbTestService.setDbTestInputOk(vo);
		
		if(res != 0) return "redirect:/message/dbTestInputOk";
		else return "redirect:/message/dbTestInputNo";
	}

	@RequestMapping(value = "/dbTestUpdateOk", method = RequestMethod.POST)
	public String dbTestUpdateOkGet(UserVO vo) {
		int res = dbTestService.setDbTestUpdateOk(vo);
		
		if(res != 0) return "redirect:/message/dbTestUpdateOk";
		else return "redirect:/message/dbTestUpdateNo";
	}
	
	@RequestMapping(value = "/dbTestWindow", method = RequestMethod.GET)
	public String dbTestWindowGet(String mid, Model model) {
		UserVO vo = dbTestService.getUserIdCheck(mid);
		
		String idCheck = "";

		if(vo != null) idCheck = "NO";
		else idCheck = "OK";
		
		model.addAttribute("mid", mid);
		model.addAttribute("idCheck", idCheck);
		
		return "user/dbTestWindow";
	}
	
}
