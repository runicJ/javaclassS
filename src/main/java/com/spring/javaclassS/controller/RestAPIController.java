package com.spring.javaclassS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController  // REST Api의 규칙을 따르려면 이걸 적어주기 => ajax 처리하는 걸 묶어놓고 처리해야 함 // postMapping getMapping 할때 사용
@RequestMapping("/restapi")
public class RestAPIController {

	@ResponseBody
	@RequestMapping(value = "/restapiTest2/{message}", method = RequestMethod.GET)
	public String restapiTest2Get(@PathVariable String message) {
		System.out.println("message : " + message);
		return "message : " + message;
	}
	
	@RequestMapping(value = "/restapiTest3/{message}", method = RequestMethod.GET)
	public String restapiTest3Get(@PathVariable String message) {
		System.out.println("message : " + message);
		return "message : " + message;
	}
	
}