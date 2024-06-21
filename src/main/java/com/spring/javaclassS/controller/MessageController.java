package com.spring.javaclassS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

	@RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
	public String getMessage(Model model,
			@PathVariable String msgFlag,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid
			) {
		
		if(msgFlag.equals("userDeleteOk")) {
			model.addAttribute("msg", "회원 자료가 삭제 되었습니다.");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userDeleteNo")) {
			model.addAttribute("msg", "회원 자료가 삭제 실패~");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("dbTestDeleteOk")) {
			model.addAttribute("msg", "회원 자료가 삭제 되었습니다.");
			model.addAttribute("url", "/dbTest/dbTestList");
		}
		else if(msgFlag.equals("dbTestDeleteNo")) {
			model.addAttribute("msg", "회원 자료가 삭제 실패~");
			model.addAttribute("url", "/dbTest/dbTestList");
		}
		else if(msgFlag.equals("userInputOk")) {
			model.addAttribute("msg", "회원 정보가 등록되었습니다.~");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userDeleteNo")) {
			model.addAttribute("msg", "회원 자료 등록 실패~");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userSearchNo")) {
			model.addAttribute("msg", "검색어와 관련된 회원이 존재하지 않습니다.");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userUpdateOk")) {
			model.addAttribute("msg", "회원 정보를 수정하였습니다.");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userUpdateNo")) {
			model.addAttribute("msg", "회원 정보 실패~~");
			model.addAttribute("url", "/user/userList");
		}		
		else if(msgFlag.equals("dbTestInputOk")) {
			model.addAttribute("msg", "회원 가입 성공~");
			model.addAttribute("url", "/dbTest/dbTestList");
		}
		else if(msgFlag.equals("dbTestInputNo")) {
			model.addAttribute("msg", "회원 가입 실패~");
			model.addAttribute("url", "/dbTest/dbTestList");
		}
		else if(msgFlag.equals("dbTestUpdateOk")) {
			model.addAttribute("msg", "회원 정보를 수정하였습니다.");
			model.addAttribute("url", "/dbTest/dbTestList");
		}
		else if(msgFlag.equals("dbTestUpdateNo")) {
			model.addAttribute("msg", "회원 정보 수정 실패~~");
			model.addAttribute("url", "/dbTest/dbTestList");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("msg", "메일이 성공적으로 발송되었습니다!");
			model.addAttribute("url", "/study/mail/mailForm");
		}
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("msg", "방명록에 글이 등록되었습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("guestInputNo")) {
			model.addAttribute("msg", "방명륵 글 등록 실패!");
			model.addAttribute("url", "/guest/guestInput");
		}
		
		return "include/message";
	}
	
}
