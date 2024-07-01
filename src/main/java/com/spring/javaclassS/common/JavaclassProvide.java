package com.spring.javaclassS.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class JavaclassProvide {
	
	@Autowired
	JavaMailSender mailSender;

	// urlPath에 파일 저장하는 메소드 : (업로드파일명, 저장할파일명, 저장할경로)
	public void writeFile(MultipartFile fName, String sFileName, String urlPath) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+urlPath+"/");
		
		FileOutputStream fos = new FileOutputStream(realPath + sFileName);
		
		if(fName.getBytes().length != -1) {
			fos.write(fName.getBytes());
		}
		fos.flush();
		fos.close();
	}
	
	public void deleteFile(String photo, String urlPath) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+urlPath+"/");
		
		File file = new File(realPath + photo);
		if(file.exists()) file.delete();
	}

	// 파일 이름 변경하기(중복방지를 위한 작업)
	public String saveFileName(String oFileName) {
		String fileName = "";
		
		Calendar cal = Calendar.getInstance();  // 싱글톤 객체랑 같은 형식, 객체를 생성하지 않음(Date는 객체 생성해서 사용), 열거형 상수 사용(singleDateFormat 필요 X(Date는 필요)) // Date객체 Calendar는 기억하기
		fileName += cal.get(Calendar.YEAR);
		fileName += cal.get(Calendar.MONTH)+1;
		fileName += cal.get(Calendar.DATE);
		fileName += cal.get(Calendar.HOUR_OF_DAY);
		fileName += cal.get(Calendar.MINUTE);
		fileName += cal.get(Calendar.SECOND);
		fileName += cal.get(Calendar.MILLISECOND);
		fileName += "_" + oFileName;  // 파일명은 주로 _ 사용, 경로명은 -
		
		return fileName;
	}
	
	// 서버의 파일을 클라이너트로 다운로드하기
	
//	public void fileDownActionGet(HttpServletResponse response, String downFilePath, 
//			String fileName) throws UnsupportedEncodingException {
//		//String realPath = 
//		//File downFile = new File(downFilePath);
//		
//		String downFileName = new String(fileName.getBytes("utf-8"), "8859_1");
//		response.setHeader("Content-Disposition", "attachment;fileName="+downFileName);
//		
//	}
	
	//메일 전송하기(아이디찾기, 비밀번호 찾기)
	public String mailSend(String toMail, String title, String pwd) throws MessagingException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();  // 그냥 못씀 강제 형변환 해서 request 써야함
		String content = "";
			
 		// 메일 전송을 위한 객체 : MimeMessage(), MimeMessageHelper()  // 보내고, 뒤에 중간중간 작업한걸 저장하는 저장소
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");  // 인코딩해서 항상 저장
 		
 		// 메일보관함에 작성한 메시지들의 정보를 모두 저장시킨 후 작업처리...(3개 필요한 것 밑에서 하나로 처리함(공부하라고 써놓음)
 		messageHelper.setTo(toMail);  // 받는 사람 메일 주소 // 앞에서 받은 메일 주소로 보낼거야
 		messageHelper.setSubject(title);  // 메일 제목  // 다 setter에 넣는 것
 		messageHelper.setText(content);
 		
 		// 메시지 보관함의 내용(content)에, 발신자의 필요한 정보를 추가로 담아서 전송처리한다.
		content = content.replace("\n", "<br>");  // 우리는 textarea에 내용을 담지만 보내면 웹에서 text로 봄(한줄로 출력되기에 줄바꿈 처리 위해서 첫줄 '=' 사용)
		content += "<br><hr><h3> 임시비밀번호 : "+pwd+"</h3><hr><br>";
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>javaclass</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);  // 기존 것 무시하고 깨끗하게 갈아치워줘(위에거 3개 안써도됨)
 		
 		// 본문에 기재될 그림파일의 경로를 별도로 표시시켜준다. 그런후 다시 보관함에 저장한다.
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		messageHelper.addInline("main.jpg", file);
 		
 		// 메일 전송하기
		mailSender.send(message);
		
		return "1";
	}
}
