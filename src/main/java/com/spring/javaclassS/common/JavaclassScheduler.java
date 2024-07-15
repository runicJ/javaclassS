package com.spring.javaclassS.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
  - cron 사용예
  매달 10일 14시에 실행 : cron = "0 0 14 10 * ?"
  매달 10일, 20일 14시에 실행 : cron = "0 0 14 10,20 * ?"
  매달 마지막날 23시에 실행 : cron = "0 0 23 L * ?"
  매일 9시00분~9시55분, 18시00분~18시55분에 5분 간격으로 실행
  				cron = "0 0/5 9,18 * * *"
  매일 9시00분 ~ 18시00분에 5분 간격으로 실행
  				cron = "0 0/5 9-18 * * *"
  매년 7월달내 월~금 10시30분에 실행  : 요일(월:1, 화:2, 수:3~~~)
          cron = "0 30 10 ? 7 1-5"
  매달 마지막 토요일 10시30분에 실행
          cron = "0 30 10 ? * 6L"
*/

//@Component  // 서비스 우두머리
//@Service
//@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JavaclassScheduler {
	
	@Autowired
	JavaMailSender mailSender;
	
	//@Autowired
	//HttpServletRequest request;
	
	// 10초에 한번  // 시간 라이브러리(클론 방식) // * 나 신경쓰지 말고 정해진대로 해
	// 정해진 시간에 자동으로 실행 (cron = 초 분 시 일 월 요일) 기본값으로 '*'을 입력시켜준다.
	//@Scheduled(cron = "0/10 * * * * *")  // 10초에 한번씩 자동 실행 // 분 시 일 월 요일 필요없음
	//@Scheduled(cron = "10 * * * * *")
	public void scheduleRun1() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("10초에 한번씩 메시지가 출력됩니다." + strToday);
	}
	
	//@Scheduled(cron = "0 0/1 * * * *")  // 1분에 한번씩 자동 실행
	public void scheduleRun2() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("1분에 한번씩 메시지가 출력됩니다." + strToday);
	}
	
	//@Scheduled(cron = "5 * * * * *")  // 5초에 한번씩 자동 실행  // 5 매분 5초마다 출력(5초일때만 출력) / 0/5 매번실행
	public void scheduleRun3() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("5초에 한번씩 메시지가 출력됩니다." + strToday);
	}
	
	//@Scheduled(cron = "0 14 10 * * *")  // 매일 10시 14분에 한번씩 자동실행
	public void scheduleRun4() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("매일 10시 14분에 메시지가 출력됩니다." + strToday);
	}
	
	//@Scheduled(cron = "0 8 11 * * *")  // 매일 11시 5분에 메일 전송
	public void scheduleRun5() throws MessagingException {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("매일 11시 5분에 메일을 전송합니다." + strToday);
		
		String email = "dodobiwa0270@gmail.com";
		String title = "신제품 안내 메일";
		String content = "여름 신상품 안내 메일 입니다.";
		
		//HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();  // 그냥 못씀 강제 형변환 해서 request 써야함
			
 		// 메일 전송을 위한 객체 : MimeMessage(), MimeMessageHelper()  // 보내고, 뒤에 중간중간 작업한걸 저장하는 저장소
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");  // 인코딩해서 항상 저장
 		
 		// 메일보관함에 작성한 메시지들의 정보를 모두 저장시킨 후 작업처리...(3개 필요한 것 밑에서 하나로 처리함(공부하라고 써놓음)
 		messageHelper.setTo(email);  // 받는 사람 메일 주소 // 앞에서 받은 메일 주소로 보낼거야
 		messageHelper.setSubject(title);  // 메일 제목  // 다 setter에 넣는 것
 		messageHelper.setText(content);
 		
 		// 메시지 보관함의 내용(content)에, 발신자의 필요한 정보를 추가로 담아서 전송처리한다.
		content = content.replace("\n", "<br>");  // 우리는 textarea에 내용을 담지만 보내면 웹에서 text로 봄(한줄로 출력되기에 줄바꿈 처리 위해서 첫줄 '=' 사용)
//		content += "<br><hr><h3> 임시비밀번호 : "+pwd+"</h3><hr><br>";
		content += "<br><hr><h3>"+content+"</h3><hr><br>";
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>javaclass</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);  // 기존 것 무시하고 깨끗하게 갈아치워줘(위에거 3개 안써도됨)
 		
 		// 본문에 기재될 그림파일의 경로를 별도로 표시시켜준다. 그런후 다시 보관함에 저장한다.
		//FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		//messageHelper.addInline("main.jpg", file);
 		
 		// 메일 전송하기
		mailSender.send(message);
	}
	
	//@Scheduled(cron = "0 44 10 * * *")	// 매일 10시 44분에 한번씩 자동 실행
	public void scheduleRun6() throws MessagingException {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("매일 10시 44분에 메일을 전송합니다." + strToday);
		
		String email = "cjsk1126@naver.com";
		String title = "신제품 안내 메일";
		String content = "여름 신상품 안내 메일 입니다.";
		
		//HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		// 메일 전송을 위한 객체 : MimeMessage(), MimeMessageHelper()
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		// 메일보관함에 작성한 메세지들의 정보를 모두 저장시킨후 작업처리...
		messageHelper.setTo(email);			// 받는 사람 메일 주소
		messageHelper.setSubject(title);	// 메일 제목
		messageHelper.setText(content);		// 메일 내용
		
		// 메세지 보관함의 내용(content)에 , 발신자의 필요한 정보를 추가로 담아서 전송처리한다.
		content = content.replace("\n", "<br>");
		//content += "<br><hr><h3> 임시비밀번호 : "+pwd+"</h3><hr><br>";
		content += "<br><hr><h3>신상품</h3><hr><br>";
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>javaclass</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);
		
		// 본문에 기재될 그림파일의 경로를 별도로 표시시켜준다. 그런후 다시 보관함에 저장한다.
		//FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		FileSystemResource file = new FileSystemResource("D:\\javaclass\\springframework\\works\\javaclassS\\src\\main\\webapp\\resources\\images\\main.jpg");
		messageHelper.addInline("main.jpg", file);
		
		// 메일 전송하기
		mailSender.send(message);
	}
	
	
}
