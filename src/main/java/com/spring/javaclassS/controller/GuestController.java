package com.spring.javaclassS.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaclassS.service.GuestService;
import com.spring.javaclassS.vo.GuestVO;

@Controller
@RequestMapping("/guest")
public class GuestController {

	@Autowired
	private GuestService guestService;
	
	@RequestMapping(value = "/guestList", method = RequestMethod.GET)
	public String guestListGet(Model model) {
		ArrayList<GuestVO> vos = guestService.getGuestList();
		model.addAttribute("vos", vos);
		
		return "guest/guestList";
	}
	
	@RequestMapping(value = "/guestInput", method = RequestMethod.GET)
	public String guestInputGet(Model model) {
		return "guest/guestInput";
	}
	
	@RequestMapping(value = "/guestInput", method = RequestMethod.POST)
	public String guestInputPost(GuestVO vo) {
		int res = guestService.setGuestInput(vo);
		
		if(res != 0) return "redirect:/message/guestInputOk";
		else return "redirect:/message/guestInputNo";
	}
	
}
