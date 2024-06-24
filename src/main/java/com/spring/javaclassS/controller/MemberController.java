package com.spring.javaclassS.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.service.MemberService;
import com.spring.javaclassS.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender mailSender;
	
	@RequestMapping(value = "/memberLogin", method = RequestMethod.GET)
	public String memberLoginGet(HttpServletRequest request) {
		// 로그인창에 아이디 체크 유무에 대한 처리
		// 쿠키를 검색해서 cMid가 있을때 가져와서 아이디입력창에 뿌릴수 있게 한다.
		Cookie[] cookies = request.getCookies();

		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("cMid")) {
					request.setAttribute("mid", cookies[i].getValue());
					break;
				}
			}
		}
		return "member/memberLogin";
	}
	
	@RequestMapping(value = "/memberLogin", method = RequestMethod.POST)
	public String memberLoginPost(HttpServletRequest request, HttpServletResponse response, HttpSession session,  // 세션처리하려면 request 넣어야함
		@RequestParam(name="mid", defaultValue = "hkd1234", required = false) String mid,
		@RequestParam(name="pwd", defaultValue = "1234", required = false) String pwd,  // name은 앞에서 넘기는 변수 뒤는 사용할 변수
		@RequestParam(name="idSave", defaultValue = "1234", required = false) String idSave
	) {
		// 로그인 인증처리(스프링 시큐리티의 BCrypPasswordEncoder객체를 이용한 암호화되어 있는 비밀번호 비교하기)
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(vo != null && vo.getUserDel().equals("NO") && passwordEncoder.matches(pwd, vo.getPwd())) {  // encoder와 matches만 사용함
			// 로그인 인증 완료시 처리할 부분(세션, 쿠키, 기타 설정값...)
			// 1.세션처리
			String strLevel = "";
			if(vo.getLevel() == 0) strLevel = "관리자";
			else if(vo.getLevel() == 1) strLevel = "우수회원";
			else if(vo.getLevel() == 2) strLevel = "정회원";
			else if(vo.getLevel() == 3) strLevel = "준회원";  // 초기값 주는 것 정보는 컨트롤러에서(서비스에서 일이 과중)
			
			session.setAttribute("sMid", mid);
			session.setAttribute("sNickName", vo.getNickName());
			session.setAttribute("sLevel", vo.getLevel());
			session.setAttribute("strLevel", strLevel);
			
			// 2.쿠키 저장/삭제
			if(idSave.equals("on")) {  // 저장할래 하면 on이 넘어오면 저장, 아니면 찾아서 지워야 함
				Cookie cookieMid = new Cookie("cMid", mid);  // 읽어올때는 cookies 여러개(배열로), 저장할 때는 하나씩
				cookieMid.setPath("/");  // 검색을 root부터 시킴(쿠키를 안놓침)
				cookieMid.setMaxAge(60*60*24*7);  // 쿠키의 만료시간을 7일로 지정
				response.addCookie(cookieMid);  // 쿠키는 사용자의 컴퓨터에 저장
			}
			else {
				Cookie[] cookies = request.getCookies();  // 쿠키 찾을때 이렇게 씀 // 읽을 때는 여려개 있을 수 있으니 배열로 저장, 읽을 때는 하나씩
				if(cookies != null) {
					for(int i=0; i<cookies.length; i++) {
						if(cookies[i].getName().equals("cMid")) {   // 해당 아이디로 저장해 놓은 쿠키가 있다면 0으로 주고 새로
							cookies[i].setMaxAge(0);  // 쿠키의 만료시간을 0으로 주면 사라짐
							response.addCookie(cookies[i]);
							break;
						}
					}
				}
			}
			
			// 3.기타처리(DB에 처리해야할 것들(방문카운트, 포인트, ... 등)
			// 방문포인트 : 1회 방문시 point 10점, 1일 최대 50점까지 할당가능(!숙제!)
			int point = 10;
			
			// 방문카운트(DB에 바로 넣음)
			memberService.setMemberInforUpdate(mid, point);
			
			return "redirect:/message/memberLoginOk?mid="+mid;
		}
		else {
			return "redirect:/message/memberLoginNo";
		}
	}
	
	@RequestMapping(value = "/memberLogout", method = RequestMethod.GET)
	public String memberMainGet(HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		session.invalidate();
		
		return "redirect:/message/memberLogout?mid="+mid;
	}
	
	@RequestMapping(value = "/memberMain", method = RequestMethod.GET)
	public String memberMainGet(HttpSession session, Model model) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO mVo = memberService.getMemberIdCheck(mid);
		model.addAttribute("mVo", mVo);
		
		return "member/memberMain";
	}
	
	@RequestMapping(value = "/memberJoin", method = RequestMethod.GET)
	public String memberJoinGet() {
		return "member/memberJoin";
	}
	
	@RequestMapping(value = "/memberJoin", method = RequestMethod.POST)
	public String memberJoinPost(MultipartFile fName, MemberVO vo) throws IOException {
		// 아이디/닉네임 중복체크
		if(memberService.getMemberIdCheck(vo.getMid()) != null) return "redirect:/message/idCheckNo";
		if(memberService.getMemberNickCheck(vo.getNickName()) != null) return "redirect:/message/nickCheckNo";
		
		// 비밀번호 암호화
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		// 회원 사진 처리(service객체에서 처리후 DB에 저장한다.)
		if(fName == null || fName.toString().equals("")) {
			UUID uid = UUID.randomUUID();
			String oFileName = fName.getOriginalFilename();
			String sFileName = vo.getMid() + "_" + uid.toString().substring(0,8) + "_" + oFileName;
			vo.setPhoto(sFileName);	
		}
		else vo.setPhoto("noImage.jpg");
		
//		if(!fName.getOriginalFilename().equals("")) vo.setPhoto(memberService.fileUpload(fName, vo.getMid()));
//		else vo.setPhoto("noimage.jpg");

		int res = memberService.setMemberJoinOk(fName, vo);
		
		if(res != 0) return "redirect:/message/memberJoinOk";
		else return "redirect:/message/memberJoinNo";
	}
	
	@ResponseBody
	@RequestMapping(value = "/memberIdCheck", method = RequestMethod.GET)
	public String memberIdCheckGet(String mid) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(vo != null) return "1";
		else return "0";
	}
	
	@ResponseBody
	@RequestMapping(value = "/memberNickCheck", method = RequestMethod.GET)
	public String memberNickCheckGet(String nickName) {
		MemberVO vo = memberService.getMemberNickCheck(nickName);
		if(vo != null) return "1";
		else return "0";
	}
	
	@ResponseBody
	@RequestMapping(value = "/memberNewPassword", method = RequestMethod.POST)
	public String memberNewPasswordPost(String mid, String email) throws MessagingException {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(vo != null && vo.getEmail().equals(email)) {
			// 정보 확인 후 정보가 맞으면 임시 비밀번호를 발급 받아서 메일로 전송 처리한다.
			UUID uid = UUID.randomUUID();  // 다른방법 6월 23일에 (월요일)
			String pwd = uid.toString().substring(0,8);
			
			// 새로 발급받은 비밀번호를 암호화 한후, DB에 저장한다.
			memberService.setMemberPasswordUpdate(mid, passwordEncoder.encode(pwd));
			
			// 발급받은 비밀번호를 메일로 전송처리한다.
			String title = "임시 비밀번호를 발급하셨습니다.";
			String mailFlag = "임시 비밀번호 : " + pwd;
			String res = mailSend(email, title, mailFlag);
			
			// 새 비밀번호를 발급하였을 시에 sLogin이란 세션을 발생시키고, 2분 안에 새 비밀번호로 로그인 후 비밀번호를 변경 처리할 수 있도록 한다(sLogin값이 없을 경우에 (처음과 끝 차이?
			// (!숙제!)
			
			//session.setAttribute("sLogin", "OK");  // 새비밀번호 왔을때만 세션이 생성 -> 다 끝나고 세션 지워버림
			
			if(res == "1") return "1";
		}
		return "0";
	}

	// 메일 전송하기(아이디찾기, 비밀번호 찾기)
	private String mailSend(String toMail, String title, String mailFlag) throws MessagingException {
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
  		content += "<br><hr><h3>"+mailFlag+"</h3><hr><br>";
  		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
  		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>javaclass</a></p>";
  		content += "<hr>";
  		messageHelper.setText(content, true);  // 기존 것 무시하고 깨끗하게 갈아치워줘(위에거 3개 안써도됨)
  		
  		//FileSystemResource file = new FileSystemResource("D:\\javaclass\\springframework\\works\\javaclassS\\src\\main\\webapp\\resources\\images\\main.jpg");
  		
  		//request.getSession().getServletContext().getRealPath("/resources/images/main.jpg");
  		
  		// 본문에 기재될 그림파일의 경로를 별도로 표시시켜준다. 그런후 다시 보관함에 저장한다.
  		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
  		messageHelper.addInline("main.jpg", file);
  		
  		// 메일 전송하기
  		mailSender.send(message);
  		
  		return "1";
	}
	
	@RequestMapping(value = "/memberPwdCheck/{pwdFlag}", method = RequestMethod.GET)
	public String memberPwdCheckGet(@PathVariable String pwdFlag, Model model) {
		model.addAttribute("pwdFlag", pwdFlag);
		return "member/memberPwdCheck";
	}
	
	@ResponseBody
	@RequestMapping(value = "/memberPwdCheck", method = RequestMethod.POST)
	public String memberPwdCheckPost(String mid, String pwd) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(passwordEncoder.matches(pwd, vo.getPwd())) return "1";
		return "0";
	}
	
	@ResponseBody  // ajax
	@RequestMapping(value = "/memberPwdChangeOk", method = RequestMethod.POST)
	public String memberPwdChangeOkPost(String mid, String pwd) {
		return memberService.setPwdChangeOk(mid, passwordEncoder.encode(pwd)) + "";
	}
	
	@RequestMapping(value = "/memberUpdate/{mid}", method = RequestMethod.GET)
	public String memberUpdateGet(@PathVariable String mid, String pwd, Model model) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(passwordEncoder.matches(pwd, vo.getPwd())) {
			String[] tel = vo.getTel().split("-");
			if(tel[1].equals(" ")) tel[1] = "";
			if(tel[2].equals(" ")) tel[2] = "";		
			
			model.addAttribute("tel1", tel[0]);
			model.addAttribute("tel2", tel[1]);
			model.addAttribute("tel3", tel[2]);
			
			// 주소분리(/)
			String[] address = vo.getAddress().split("/");
			if(address[0].equals(" ")) address[0] = "";
			if(address[1].equals(" ")) address[1] = "";
			if(address[2].equals(" ")) address[2] = "";
			if(address[3].equals(" ")) address[3] = "";
			model.addAttribute("postcode", address[0]);
			model.addAttribute("roadAddress", address[1]);
			model.addAttribute("detailAddress", address[2]);
			model.addAttribute("extraAddress", address[3]);
			
			model.addAttribute("vo", vo);
			return "member/memberUpdate";
		}
		else return "member/memberPwdCheck";
	}
	
	@ResponseBody
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.POST)
	public String memberUpdatePost(MultipartFile fName, MemberVO vo) {
		// 아이디/닉네임 중복체크
		if(memberService.getMemberNickCheck(vo.getNickName()) != null) return "redirect:/message/nickCheckNo";
		
		// 비밀번호 암호화
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		// 회원 사진 처리(service객체에서 처리후 DB에 저장한다.)
		if(fName == null || fName.toString().equals("")) {
			UUID uid = UUID.randomUUID();
			String oFileName = fName.getOriginalFilename();
			String sFileName = vo.getMid() + "_" + uid.toString().substring(0,8) + "_" + oFileName;
			vo.setPhoto(sFileName);	
		}
		else vo.setPhoto("noImage.jpg");
		
		int res = memberService.setMemberUpdateOk(fName, vo);
		
		if(res != 0) return "redirect:/message/memberUpdateOk";
		else return "redirect:/message/memberUpdateNo";
	}
	
	@RequestMapping(value = "/memberList", method = RequestMethod.GET)
	public String memberListGet(Model model, HttpSession session) {
		int level = (int) session.getAttribute("sLevel");
		ArrayList<MemberVO> vos = memberService.getMemberList(level);
		model.addAttribute("vos", vos);
		return "member/memberList";
	}
	
	@RequestMapping(value = "/memberDelete", method = RequestMethod.GET)
	public String memberPwdCheckGet(String mid, String pwd) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		int res = 0;
		if(passwordEncoder.matches(pwd, vo.getPwd())) {
			res = memberService.setMemberDelete(mid);
		}
		else return "redirect:/message/memberPwdNo";
		
		if(res != 0) return "redirect:/message/memberDeleteOk";
		else return "redirect:/message/memberDeleteNo";
	}
}
