package com.spring.javaclassS.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaclassS.service.StudyService;
import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.MailVO;
import com.spring.javaclassS.vo.UserVO;

@Controller
@RequestMapping("/study")
public class StudyController {
	
	@Autowired
	StudyService studyService;
	
	@Autowired
	JavaMailSender mailSender;
	
	@RequestMapping(value = "/ajax/ajaxForm", method = RequestMethod.GET)
	public String ajaxFormGet() {
		return "study/ajax/ajaxForm";
	}
	
	@ResponseBody  // 요청한걸 처리하고 받는 것  // 나를 보낸 곳으로 다시 값을 보냄
	@RequestMapping(value = "/ajax/ajaxTest1", method = RequestMethod.POST)
	public String ajaxTest1Post(int idx) {
		System.out.println("idx : " + idx);
		//return "study/ajax/ajaxForm";
		return idx + "";  // 넘기고 싶은 값이 있으면 넘기도록 유도만 해주는 정도(본인을 부르지 않음) => 현재는 404
	}
	
	@ResponseBody  // 요청한걸 처리하고 받는 것  // 나를 보낸 곳으로 다시 값을 보냄
	@RequestMapping(value = "/ajax/ajaxTest2", method = RequestMethod.POST, produces="application/text; charset=utf-8")
	public String ajaxTest2Post(String str) {
		System.out.println("str : " + str);
		//return "study/ajax/ajaxForm";
		return str;
	}
	
	@RequestMapping(value = "/ajax/ajaxTest3_1", method = RequestMethod.GET)
	public String ajaxTest3_1Get() {
		return "study/ajax/ajaxTest3_1";
	}
	
	@ResponseBody  // 이걸 붙이면서 무조건 문자가 아닌 객체로 가져갈 수 있게됨
	@RequestMapping(value = "/ajax/ajaxTest3_1", method = RequestMethod.POST)
	public String[] ajaxTest3_1Post(String dodo) {
		//String[] strArray = new String[100];
		//strArray = studyService.getCityStringArray();
		//return strArray;
		
		return studyService.getCityStringArray(dodo);  // 크기도 안주고 // 위에 타입만 잘 지정해주면 알아서 여기서는 스트링 배열 타입으로 넘겨줌(스프링에서는)
	}
	
	@RequestMapping(value = "/ajax/ajaxTest3_2", method = RequestMethod.GET)
	public String ajaxTest3_2Get() {
		return "study/ajax/ajaxTest3_2";
	}
	
	@ResponseBody  // 이걸 붙이면서 무조건 문자가 아닌 객체로 가져갈 수 있게됨
	@RequestMapping(value = "/ajax/ajaxTest3_2", method = RequestMethod.POST)
	public ArrayList<String> ajaxTest3_2Post(String dodo) {
		return studyService.getCityArrayList(dodo);
	}
	
	@RequestMapping(value = "/ajax/ajaxTest3_3", method = RequestMethod.GET)
	public String ajaxTest3_3Get() {
		return "study/ajax/ajaxTest3_3";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_3", method = RequestMethod.POST)
	public HashMap<Object, Object> ajaxTest3_3Post(String dodo) {
		//return studyService.getCityHashMap(dodo);
		ArrayList<String> vos = studyService.getCityArrayList(dodo);
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("city", vos);
		
		return map;
	}
	
    @RequestMapping(value = "/ajax/ajaxTest3_4", method = RequestMethod.GET)
    public String ajaxTest3_4Get(Model model) {
    	HashMap<String, UserVO> userMap = studyService.getUserMidMap();
        model.addAttribute("userMap", userMap);
        return "study/ajax/ajaxTest3_4";
    }

    @ResponseBody
    @RequestMapping(value = "/ajax/ajaxTest3_4/{mid}", method = RequestMethod.GET)
    public UserVO ajaxTest3_4Post(@PathVariable String mid) {
        return studyService.getUserMidInfo(mid);
    }
    
  	@RequestMapping(value = "/ajax/ajaxTest3_4_2", method = RequestMethod.GET)
  	public String ajaxTest3_4_2Get(Model model) {
  		ArrayList<String> midVos = studyService.getDbtestMidList();
  		model.addAttribute("midVos", midVos);
  		
  		ArrayList<String> addressVos = studyService.getDbtestAddressList();
  		model.addAttribute("addressVos", addressVos);
  		return "study/ajax/ajaxTest3_4_2";
  	}
  	
  	@ResponseBody
  	@RequestMapping(value = "/ajax/ajaxTest3_4_2", method = RequestMethod.POST, produces="application/text; charset=utf-8")
  	public String ajaxTest3_4_2Post(String mid) {
  		UserVO vo = studyService.getUserIdCheck(mid);
  		String str = "<h3>회원정보</h3>";
  		str += "아이디 : " + vo.getMid() + "<br>";
  		str += "성명 : " + vo.getName() + "<br>";
  		str += "나이 : " + vo.getAge() + "<br>";
  		str += "주소 : " + vo.getAddress();
  		return str;
  	}
    
    @ResponseBody
    @RequestMapping(value = "/ajax/ajaxTest4-1", method = RequestMethod.POST)
    public UserVO ajaxTest4_1Post(String mid) {
    	return studyService.getUserMidSearch(mid);
    }
    
    @ResponseBody
    @RequestMapping(value = "/ajax/ajaxTest4-2", method = RequestMethod.POST)
    public ArrayList<UserVO> ajaxTest4_2Post(String mid) {
    	return studyService.getUserMidList(mid);
    }
    
    @RequestMapping(value = "/restapi/restapi", method = RequestMethod.GET)
    public String restapiGet() {
    	return "study/restapi/restapi";
    }
    
    @RequestMapping(value = "/restapi/restapiTest1/{message}", method = RequestMethod.GET)
    public String restapiTest1Get(@PathVariable String message) {
    	System.out.println("message : " + message);
    	return "message : " + message;  // /views/message : Hello Springframework.jsp 404 발생
    }
    
  	@RequestMapping(value = "/restapi/restapiTest4", method = RequestMethod.GET)
  	public String restapiTest4Get() {
  		return "study/restapi/restapiTest4";
  	}
  	
  	@ResponseBody  // ajax
  	@RequestMapping(value = "/restapi/saveCrimeData", method = RequestMethod.POST)
  	public void saveCrimeDataPost(CrimeVO vo) {
  		studyService.setSaveCrimeData(vo);
  	}
  	
  	/*
    @ResponseBody
    @RequestMapping(value = "/restapi/deleteCrimeData", method = RequestMethod.GET)
    public int deleteCrimeDataGet(@RequestParam int year) {
        return studyService.setDeleteCrimeData(year);
    }

    @ResponseBody
    @RequestMapping(value = "/restapi/listCrimeData", method = RequestMethod.POST)
    public String listCrimeDataPost(@RequestParam int year) {
        return studyService.getListCrimeData(year);
    }

    @ResponseBody
    @RequestMapping(value = "/restapi/policeCrimeData", method = RequestMethod.POST)
    public String policeCrimeDatePost(@RequestParam String police, @RequestParam int year) {
        return studyService.getPoliceCrimeDate(police, year);
    }
    
    @ResponseBody
    @RequestMapping(value = "/restapi/policeCheck", method = RequestMethod.POST)
    public String policeCheckPost(@RequestParam String police) {
        return studyService.getPoliceCheck(police);
    }

     @ResponseBody
     @RequestMapping(value = "/restapi/yearPoliceCheck", method = RequestMethod.POST)
     public String yearSortPoliceCheckPOST(@RequestParam String police, @RequestParam int year, @RequestParam String sort) {
         return studyService.getYearPoliceCheck(police, year, sort);
     }
     */
  	
  	@ResponseBody
  	@RequestMapping(value = "/restapi/deleteCrimeDate", method = RequestMethod.POST)
  	public void deleteCrimeDatePost(int year) {
  		studyService.setDeleteCrimeDate(year);
  	}
  	
  	@ResponseBody
  	@RequestMapping(value = "/restapi/listCrimeDate", method = RequestMethod.POST)
  	public ArrayList<CrimeVO> listCrimeDatePost(int year) {
  		return studyService.getListCrimeDate(year);
  	}
  	
  	@RequestMapping(value = "/restapi/yearPoliceCheck", method = RequestMethod.POST)
  	public String yearPoliceCheckPost(int year, String police, String yearOrder, Model model) {
  		ArrayList<CrimeVO> vos = studyService.getYearPoliceCheck(year, police, yearOrder);
  		model.addAttribute("vos", vos);
  		
  		CrimeVO analyzeVo = studyService.getAnalyzeTotal(year, police);
  		model.addAttribute("analyzeVo", analyzeVo);
  		
  		model.addAttribute("year", year);
  		model.addAttribute("police", police);
  		model.addAttribute("totalCnt", analyzeVo.getTotMurder()+analyzeVo.getTotRobbery()+analyzeVo.getTotTheft()+analyzeVo.getTotViolence());
  		
  		return "study/restapi/restapiTest4";
  	}
  	
  	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.GET)
  	public String mailFormGet() {
  		return "study/mail/mailForm";
  	}
  	
  	// 메일 전송하기
  	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.POST)
  	public String mailFormPost(MailVO vo, HttpServletRequest request) throws MessagingException {
  		String toMail = vo.getToMail();  // gmail에 넣는건 처리했으니 글 넣는 것 처리
  		String title = vo.getTitle();
  		String content = vo.getContent();
  		
  		// 메일 전송을 위한 객체 : MimeMessage(), MimeMessageHelper()  // 보내고, 뒤에 중간중간 작업한걸 저장하는 저장소
  		MimeMessage message = mailSender.createMimeMessage();
  		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");  // 인코딩해서 항상 저장
  		
  		// 메일보관함에 작성한 메시지들의 정보를 모두 저장시킨 후 작업처리...(3개 필요한 것 밑에서 하나로 처리함(공부하라고 써놓음)
  		messageHelper.setTo(toMail);  // 받는 사람 메일 주소 // 앞에서 받은 메일 주소로 보낼거야
  		messageHelper.setSubject(title);  // 메일 제목  // 다 setter에 넣는 것
  		messageHelper.setText(content);
  		
  		// 메시지 보관함의 내용(content)에, 발신자의 필요한 정보를 추가로 담아서 전송처리한다.
  	  content = content.replace("\n", "<br>");  // 우리는 textarea에 내용을 담지만 보내면 웹에서 text로 봄(한줄로 출력되기에 줄바꿈 처리 위해서 첫줄 '=' 사용)
  	  content += "<br><hr><h3>javaclass 에서 보냅니다.</h3><hr><br>";
  	  content += "<p><img src=\"cid:main.png\" width='500px'></p>";
  	  content += "<p>방문하기 : <a href='http://49.142.157.251:9090/javaclassJ14/Main'>javaclass</a></p>";
  	  content += "<hr>";
  		messageHelper.setText(content, true);  // 기존 것 무시하고 깨끗하게 갈아치워줘(위에거 3개 안써도됨)
  	  
  		// 본문에 기재될 그림파일의 경로를 별도로 표시시켜준다. 그 후 다시 보관함에 저장한다.(항상 편집했으면 다시 저장해야함)
  		//FileSystemResource file = new FileSystemResource("D:\\javaclass\\springframework\\works\\javaclassS\\src\\main\\webapp\\resources\\images\\main.png");
  		
  		//request.getSession().getServletContext().getRealPath("/resources/images/main.png");  // '/'는 webapp
  		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.png"));  // 그림이 이름이 아래와 같아야 함.
  		messageHelper.addInline("main.png", file);  // 첨부가 아니라 본문 내용에 추가를 하겠다는 것  // 그림이면 그림파일 이름을 적어주고 다른건 파일
  		
  		// 첨부파일 보내기
  		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/paris.jpg"));
  		messageHelper.addAttachment("paris.jpg", file);

  		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/favorite1.zip"));
  		messageHelper.addAttachment("favorite1.zip", file);
  		
  		// 메일 전송하기
  		mailSender.send(message);
  		
  		return "redirect:/message/mailSendOk";  // 위에서 실패하면 그냥 안감
  	}
  	
  	// 파일 업로드 연습폼 호출하기
  	@RequestMapping(value = "/fileUpload/fileUpload", method = RequestMethod.GET)
  	public String fileUploadGet(HttpServletRequest request, Model model) {
  		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload");  // 정보를 꺼내는 것 끝에 / 지우기
  		
  		String[] files = new File(realPath).list();  // realPath의 위치를 보겠다는 것
  		
  		model.addAttribute("files", files);
  		model.addAttribute("fileCount", files.length);
  		
  		return "study/fileUpload/fileUpload";
  	}
  	
  	@RequestMapping(value = "/fileUpload/fileUpload", method = RequestMethod.POST)
  	public String fileUploadPost(MultipartFile fName, String mid) {
  		
  		int res = studyService.fileUpload(fName, mid);
  		
  		if(res != 0) return "redirect:/message/filUploadOk";
  		return "redirect:/message/filUploadNo";
  	}
  	
  	@RequestMapping(value = "/fileUpload/fileDelete", method = RequestMethod.POST)
  	public String fileDeletePost(HttpServletRequest request, String file) {
  		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");  // 파일명으로 지우니까 마지막에 /가 있어야 함

  		String res = "0";
  		File fName = new File(realPath + file);  // 껍데기 만들어서 지움

  		if(fName.exists()) {
  			fName.delete();
  			res = "1";
  		}
  		
  		return res;
  	}
  	
  	@RequestMapping(value = "/fileUpload/fileDeleteAll", method = RequestMethod.POST)
  	public String fileDeleteAllPost(HttpServletRequest request, String file) {
  		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");  // 파일명으로 지우니까 마지막에 /가 있어야 함
  		
  		String res = "0";
  		File targetFolder = new File(realPath);
  		if(!targetFolder.exists()) return "0";
  		
  		File[] files = targetFolder.listFiles();  // 각각의 파일에 대한 속성을 배열에 담는다. // 폴더의 파일을 하나씩 속성으로 꺼내ㅐ서 객체로 꺼냄
  		
  		if(files.length != 0) {
  			for(File f : files) {
  				if(!f.isDirectory()) f.delete();  // 파일이냐 물어보고 파일이 아니면 전부 지움 여기서 안하면 폴더까지 지움// 있냐 물어볼 필요 없음. 있으니까 들어온 것.
  			}
  			res = "1";
  		}
  		
  		return res;
  	}
  	
}
