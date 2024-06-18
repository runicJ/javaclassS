package com.spring.javaclassS.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/member")
public class MemberController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLogin(Model model) {
		System.out.println("이곳은 member컨트롤러의 login메소드 입니다.");
		model.addAttribute("data", "member컨트롤러의 login메소드에서 보냅니다.");
		return "member/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String postLogin(String mid, String pwd, int level,
			HttpSession session, Model model) {
		if(mid.substring(0,1).toLowerCase().equals("a")) {
			session.setAttribute("sMid", mid);
			session.setAttribute("sLevel", level);
			
			String strLevel = "";
			if(level == 0) strLevel = "관리자";
			else if(level == 1) strLevel = "우수회원";
			else if(level == 2) strLevel = "정회원";
			else if(level == 3) strLevel = "준회원";
			session.setAttribute("strLevel", strLevel);
			
			return "redirect:/message/loginOk?mid="+mid;
		}
		else {
			return "redirect:/message/loginNo";
		}
		
	}
	
//	@RequestMapping(value = "/loginOk", method = RequestMethod.GET)
//	public String getLoginOk(Model model) {
//		System.out.println("이곳은 member컨트롤러의 loginOk메소드 입니다.");
//		//model.addAttribute("data", "member컨트롤러의 login메소드에서 보냅니다.");
//		return "redirect:/message/loginOk?mid=admin";
//	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String getLogout(Model model, HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		session.invalidate();
		
		return "redirect:/message/logout?mid=" + mid;
	}
	
	@RequestMapping(value = "/idCheck", method = RequestMethod.GET)
	public String getIdCheck(Model model) {
		System.out.println("이곳은 member컨트롤러의 idCheck메소드 입니다.");
		model.addAttribute("data", "member컨트롤러의 idCheck메소드에서 보냅니다.");
		return "member/idCheck";
	}
	
	@RequestMapping(value = "/nickCheck", method = RequestMethod.GET)
	public String getNickCheck(Model model) {
		System.out.println("이곳은 member컨트롤러의 nickCheck메소드 입니다.");
		model.addAttribute("data", "member컨트롤러의 nickCheck메소드에서 보냅니다.");
		return "member/nickCheck";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String getJoin(Model model) {
		System.out.println("이곳은 member컨트롤러의 join메소드 입니다.");
		model.addAttribute("data", "member컨트롤러의 join메소드에서 보냅니다.");
		return "member/join";
	}
	
	@RequestMapping(value = "/joinOk", method = RequestMethod.GET)
	public String getJoinOk(Model model) {
		System.out.println("이곳은 member컨트롤러의 joinOk메소드 입니다.");
		//model.addAttribute("data", "member컨트롤러의 joinOk메소드에서 보냅니다.");
		//return "member/joinOk";
		return "redirect:/message/joinOk?mid=admin";
	}
	
	@RequestMapping(value = "/memberMain", method = RequestMethod.GET)
	public String getMemberMain(Model model) {
		System.out.println("이곳은 member컨트롤러의 memberMain메소드 입니다.");
		model.addAttribute("data", "member컨트롤러의 memberMain메소드에서 보냅니다.");
		return "member/memberMain";
	}
	
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.GET)
	public String getMemberUpdate(Model model) {
		System.out.println("이곳은 member컨트롤러의 memberUpdate메소드 입니다.");
		model.addAttribute("data", "member컨트롤러의 memberUpdate메소드에서 보냅니다.");
		return "member/memberUpdate";
	}
	
	@RequestMapping(value = "/memberUpdateOk", method = RequestMethod.GET)
	public String getMemberUpdateOk(Model model) {
		System.out.println("이곳은 member컨트롤러의 memberUpdateOk메소드 입니다.");
		model.addAttribute("data", "member컨트롤러의 memberUpdateOk메소드에서 보냅니다.");
		//return "member/memberUpdate";
		return "redirect:/message/memberUpdateOk?mid=admin";
	}
	
	@RequestMapping(value = "/memberDelete", method = RequestMethod.GET)
	public String getMemberDelete(Model model) {
		System.out.println("이곳은 member컨트롤러의 memberDelete메소드 입니다.");
		model.addAttribute("data", "member컨트롤러의 memberDelete메소드에서 보냅니다.");
		return "member/memberDelete";
	}
	
	@RequestMapping(value = "/memberList", method = RequestMethod.GET)
	public String getMemberList(Model model) {
		System.out.println("이곳은 member컨트롤러의 memberList메소드 입니다.");
		model.addAttribute("data", "member컨트롤러의 memberList메소드에서 보냅니다.");
		return "member/memberList";
	}
	
	@RequestMapping(value = "/memberSearch", method = RequestMethod.GET)
	public String getMemberSearch(Model model) {
		System.out.println("이곳은 member컨트롤러의 memberSearch메소드 입니다.");
		model.addAttribute("data", "member컨트롤러의 memberSearch메소드에서 보냅니다.");
		return "member/memberSearch";
	}
	
	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public String getMemberResources() {
		return "member/resources";
	}
	
}
