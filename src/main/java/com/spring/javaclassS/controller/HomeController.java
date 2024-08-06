package com.spring.javaclassS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaclassS.service.HomeService;
import com.spring.javaclassS.service.NotifyService;
import com.spring.javaclassS.vo.NotifyVO;
import com.spring.javaclassS.vo.WebChattingVO;

@Controller
public class HomeController {
	
	@Autowired
	HomeService homeService;
	
	@Autowired
	NotifyService notifyService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = {"/","/h"}, method = RequestMethod.GET)
	public String homeGet(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		// 첫화면에 공지사항 팝업으로 띄우기
		List<NotifyVO> popupVos = notifyService.getNotifyPopup();
		System.out.println(popupVos);
		model.addAttribute("popupVos", popupVos);
		
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
	
	@RequestMapping(value="/fileDownAction", method = RequestMethod.GET)
	public void fileDownAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getParameter("path");
		String file = request.getParameter("file");
		
		if(path.equals("pds")) path += "/temp/";  // 앞에서 pds가 넘어옴
		
		String realPathFile = request.getSession().getServletContext().getRealPath("/resources/data/" + path) + file;
		
		File downFile = new File(realPathFile);  // 서버에 들어있는 파일명 downFile
		String downFileName = new String(file.getBytes("utf-8"), "8859_1");  // 파일 이름만 보는 것
		response.setHeader("Content-Disposition", "attachment;fileName="+downFileName);
		
		FileInputStream fis = new FileInputStream(downFile);
		ServletOutputStream sos = response.getOutputStream();
		
		byte[] bytes = new byte[2048];
		int data;
		while((data = fis.read(bytes, 0, bytes.length)) != -1) {
			sos.write(bytes, 0, data);  // 전송
		}
		sos.flush(); // 전송이 끝나면 버퍼에 남아있는 찌꺼기까지 다 넘겨라
		sos.close();
		fis.close();
		
		// 클라이언트에 파일전송을 마치면, 서버에 존재하는 zip파일을 삭제처리한다.
		// 다운로드 완료 후에 서버에 저장된 zip파일을 삭제처리한다.
		downFile.delete();  // => zip파일 지우는 것
	}
	
	// 채팅창 띄우기
	@RequestMapping(value = "/webSocket/webSocket", method = RequestMethod.GET)
	public String webSocketGet() {
    return "webSocket/webSocket";
	}
	
	// 채팅메세지 DB에 저장하기
	@ResponseBody
	@RequestMapping(value = "/webSocket/msgInput", method = RequestMethod.POST)
	public String msgInputPost(WebChattingVO vo) {
		return homeService.setMsgInput(vo) + "";
	}
	
	// 1대1 채팅폼
	@RequestMapping(value = "/webSocket/endPoint", method = RequestMethod.GET)
	public String endPointGet() {
		return "webSocket/endPoint";
	}
	
}
