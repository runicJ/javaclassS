package com.spring.javaclassS.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = {"/","/h"}, method = RequestMethod.GET)
	public String homeGet(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/imageUpload")
	public void imageUploadGet(MultipartFile upload, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");  // 서버로 들어오는 거니까 request는 안해도 됨, 이건 내보내는 것
		response.setContentType("text/html; charset=utf-8");  // 응답 객체에 인코딩을 다시 해주는게 좋음
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");  // 파일명을 주기에 마지막 /
		String oFileName = upload.getOriginalFilename();
		
		// 파일명 중복방지를 위한 이름 설정하기(날짜로 분류처리...)
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		oFileName = sdf.format(date) + "_" + oFileName;  // 경로명엔 _ 비추, 파일명엔 _, $ 사용
		
		FileOutputStream fos = new FileOutputStream(new File(realPath + oFileName));
		fos.write(upload.getBytes());  // out => 보내는 거
		
		PrintWriter out = response.getWriter();  // 웹 화면으로 불러옴
		String fileUrl = request.getContextPath() + "/data/ckeditor/" + oFileName;  // ctp는 jsp에서 쓰는 것이므로 여기선 이렇게 씀  // ""resources/data/cheditof/" 이렇게 써도 됨
		out.println("{\"originalFilename\":\""+oFileName+"\",\"uploaded\":1,\"url\":\""+fileUrl+"\"}");  // (xml이나) json 형식으로 출력 {ckeditor의 변수명(예약어)-키값 : 내가 지정한 파일명(따당)}, 업로드(1) 숫자는 "" 안줘도 됨
		
		out.flush();
		fos.close();
	}
	
}
