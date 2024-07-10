package com.spring.javaclassS.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizer.WhiteSpaceWordTokenizer;
import com.kennycason.kumo.palette.ColorPalette;
import com.spring.javaclassS.common.ARIAUtil;
import com.spring.javaclassS.common.SecurityUtil;
import com.spring.javaclassS.service.DbtestService;
import com.spring.javaclassS.service.StudyService;
import com.spring.javaclassS.vo.CrawlingVO;
import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.KakaoAddressVO;
import com.spring.javaclassS.vo.MailVO;
import com.spring.javaclassS.vo.QrCodeVO;
import com.spring.javaclassS.vo.UserVO;

@Controller
@RequestMapping("/study")
public class StudyController {
	
	@Autowired
	StudyService studyService;
	
	@Autowired
	DbtestService dbtestService;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
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
		ArrayList<String> vos = studyService.getCityArrayList(dodo);
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("city", vos);
		
		return map;
	}
	
//    @RequestMapping(value = "/ajax/ajaxTest3_4", method = RequestMethod.GET)
//    public String ajaxTest3_4Get(Model model) {
//    	HashMap<String, UserVO> userMap = studyService.getUserMidMap();
//        model.addAttribute("userMap", userMap);
//        return "study/ajax/ajaxTest3_4";
//    }

//    @ResponseBody
//    @RequestMapping(value = "/ajax/ajaxTest3_4/{mid}", method = RequestMethod.GET)
//    public UserVO ajaxTest3_4Post(@PathVariable String mid) {
//        return studyService.getUserMidInfo(mid);
//    }
    
	@RequestMapping(value = "/ajax/ajaxTest3_4", method = RequestMethod.GET)
	public String ajaxTest3_4Get(Model model) {
		ArrayList<String> midVos = dbtestService.getDbtestMidList();
		model.addAttribute("midVos", midVos);
		
		ArrayList<String> addressVos = dbtestService.getDbtestAddressList();
		model.addAttribute("addressVos", addressVos);
		return "study/ajax/ajaxTest3_4";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_4", method = RequestMethod.POST, produces="application/text; charset=utf-8")
	public String ajaxTest3_4Post(String mid) {
		UserVO vo = dbtestService.getUserIdCheck(mid);
		String str = "<h3>회원정보</h3>";
		str += "아이디 : " + vo.getMid() + "<br>";
		str += "성명 : " + vo.getName() + "<br>";
		str += "나이 : " + vo.getAge() + "<br>";
		str += "주소 : " + vo.getAddress();
		return str;
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_5", method = RequestMethod.POST)
	public ArrayList<UserVO> ajaxTest3_5Post(String address) {
		return dbtestService.getUserAddressCheck(address);
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
	@RequestMapping(value = "/restapi/saveCrimeDate", method = RequestMethod.POST)
	public void saveCrimeDatePost(CrimeVO vo) {
		studyService.setSaveCrimeDate(vo);
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
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>javaclass</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);  // 기존 것 무시하고 깨끗하게 갈아치워줘(위에거 3개 안써도됨)
  	  
  		// 본문에 기재될 그림파일의 경로를 별도로 표시시켜준다. 그 후 다시 보관함에 저장한다.(항상 편집했으면 다시 저장해야함)
  		//FileSystemResource file = new FileSystemResource("D:\\javaclass\\springframework\\works\\javaclassS\\src\\main\\webapp\\resources\\images\\main.png");
  		
  		//request.getSession().getServletContext().getRealPath("/resources/images/main.png");  // '/'는 webapp
  		FileSystemResource file =  new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));  // 그림이 이름이 아래와 같아야 함.
  		messageHelper.addInline("main.png", file);  // 첨부가 아니라 본문 내용에 추가를 하겠다는 것  // 그림이면 그림파일 이름을 적어주고 다른건 파일
  		
		// 첨부파일 보내기
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/chicago.jpg"));
		messageHelper.addAttachment("chicago.jpg", file);
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/sanfran.zip"));
		messageHelper.addAttachment("sanfran.zip", file);
  		
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
		
		if(res != 0) return "redirect:/message/fileUploadOk";
		else  return "redirect:/message/fileUploadNo";
	}
  	
	@ResponseBody
	@RequestMapping(value = "/fileUpload/fileDelete", method = RequestMethod.POST)
	public String fileDeletePost(HttpServletRequest request, String file) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/"); // 파일명으로 지우니까 마지막에 /가 있어야 함

  		String res = "0";
  		File fName = new File(realPath + file);  // 껍데기 만들어서 지움

  		if(fName.exists()) {
  			fName.delete();
  			res = "1";
  		}
  		
  		return res;
  	}
	
	@ResponseBody
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
  	
	@RequestMapping(value = "/fileUpload/multiFile", method = RequestMethod.GET)
	public String multiFileGet() {
		return "study/fileUpload/multiFile";
	}
	
	@RequestMapping(value = "/fileUpload/multiFile", method = RequestMethod.POST)
	public String multiFilePost(MultipartHttpServletRequest mFile/* , HttpServletRequest request */) {  // MultipartHttpServletRequests는 여러개 파일 가져오는 것 / HttpServletRequest request 객체로 파일을 뽑아오고싶음 // 파일을 하나만 올릴때 MultipartFile 객체 사용(MultipartFile fName)
		
		/* String[] imgNames = request.getParameter("imgNames").split("/"); */
		
		int res = studyService.multiFileUpload(mFile/* , imgNames */);
		
		if(res != 0) return "redirect:/message/multiFileUploadOk";
		else return "redirect:/message/multiFileUploadNo";
	}
	
	@RequestMapping(value = "/fileUpload/multiFile2", method = RequestMethod.GET)
	public String multiFile2Get() {
		return "study/fileUpload/multiFile2";
	}

	@RequestMapping(value = "/fileUpload/multiFile2", method = RequestMethod.POST)
	public String multiFile2Post(MultipartHttpServletRequest mFile, HttpServletRequest request, String imgNames) {  // MultipartHttpServletRequests는 여러개 파일 가져오는 것 / HttpServletRequest request 객체로 파일을 뽑아오고싶음 // 파일을 하나만 올릴때 MultipartFile 객체 사용(MultipartFile fName)
		//String[] imgNames = request.getParameter("imgNames").split("/");
		
		int res = studyService.multiFileUpload(mFile);
		
		if(res != 0) return "redirect:/message/multiFileUploadOk";
		else return "redirect:/message/multiFileUploadNo";
	}
	
	// 크롤링 연습(jsoup)
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.GET)
	public String jsoupGet() {
		return "study/crawling/jsoup";
	}
	
	// 크롤링 연습(jsoup) 처리
//	@ResponseBody
//	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.POST, produces="application/text; charset=utf-8")
//	public String jsoupPost(String url, String selector) throws IOException {
//		Connection conn = Jsoup.connect(url);  // sql로 했었음
//		
//		Document document = conn.get();
//		//System.out.println("document : " + document);
//		
//		Elements selects = document.select(selector);
//		//System.out.println("selects : " + selects);
//		//System.out.println("selects : " + selects.text());
//		
//		String str = "";
//		int i = 0;
//		for(Element select : selects) {
//			i++;
//			System.out.println(i + " : " + select);
//			str += i + " : " + select + "<br>";
//		}
//		
//		return str;
//	}
	
	// 크롤링 연습(jsoup) 처리
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.POST)  // ajax로 하면 객체로 받아올 수 있음, 한글처리 필요x
	public ArrayList<String> jsoupPost(String url, String selector) throws IOException {
		Connection conn = Jsoup.connect(url);  // sql로 했었음
		
		Document document = conn.get();
		//System.out.println("document : " + document);
		
		Elements selects = document.select(selector);
		System.out.println("selects : " + selects);
		//System.out.println("selects : " + selects.text());
		
		ArrayList<String> vos = new ArrayList<String>();  // 값이 여러개면 vo(제작사, 타이틀, 내용 등)
		int i = 0;
		for(Element select : selects) {
			i++;
			System.out.println(i + " : " + select);
			vos.add(i + " : " + select.toString().replace("data-onshow-",""));
			//vos.add(i + " : " + select.html().replace("data-onshow-",""));
		}
		
		return vos;
	}
	
	// 크롤링 연습(jsoup) 처리
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup2", method = RequestMethod.POST)  // ajax로 하면 객체로 받아올 수 있음, 한글처리 필요x
	public ArrayList<CrawlingVO> jsoup2Post() throws IOException {
		Connection conn = Jsoup.connect("https://news.naver.com/");
		
		Document document = conn.get();
		
		Elements selects = null;

		ArrayList<String> titleVos = new ArrayList<String>();
		selects = document.select("div.cjs_t");
		for(Element select : selects) {
			titleVos.add(select.html());
		}
		
		ArrayList<String> imageVos = new ArrayList<String>();
		selects = document.select("div.cjs_news_mw");
		for(Element select : selects) {
			imageVos.add(select.html().replace("data-onshow-",""));
		}
		
		ArrayList<String> broadcastVos = new ArrayList<String>();
		selects = document.select("h4.channel");
		for(Element select : selects) {
			broadcastVos.add(select.html());
		}
		
		ArrayList<CrawlingVO> vos = new ArrayList<CrawlingVO>();
		CrawlingVO vo = null;
		for(int i=0; i<titleVos.size(); i++) {
			vo = new CrawlingVO();
			vo.setItem1(titleVos.get(i));
			vo.setItem2(imageVos.get(i));
			vo.setItem3(broadcastVos.get(i));
			vos.add(vo);
		}
		
		return vos;  // 이렇게 안쓰면 HashMap으로 쓰면 됨.
	}
	
	// 크롤링 연습(jsoup) 처리
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup3", method = RequestMethod.POST)
	public ArrayList<CrawlingVO> jsoup3Post() throws IOException {
		Connection conn = Jsoup.connect("https://entertain.daum.net/");
		
		Document document = conn.get();
		
		Elements selects = null;
		
		ArrayList<String> titleVos = new ArrayList<String>();
		selects = document.select("ul.list_news a.link_txt");
		for(Element select : selects) {
			titleVos.add(select.html());
		}
		
		ArrayList<String> imageVos = new ArrayList<String>();
		selects = document.select("ul.list_news a.link_thumb");
		for(Element select : selects) {
			imageVos.add(select.html().replace("data-onshow-",""));
		}
		
		ArrayList<String> broadcastVos = new ArrayList<String>();
		selects = document.select("ul.list_news span.info_thumb");
		for(Element select : selects) {
			broadcastVos.add(select.html());
		}
		
		ArrayList<CrawlingVO> vos = new ArrayList<CrawlingVO>();
		CrawlingVO vo = null;
		for(int i=0; i<broadcastVos.size(); i++) {
			vo = new CrawlingVO();
			vo.setItem1(titleVos.get(i));
			vo.setItem2(imageVos.get(i));
			vo.setItem3(broadcastVos.get(i));
			vos.add(vo);
		}
		
		return vos;
	}
	
	// 크롤링연습 처리4(jsoup) - 네이버 검색어를 이용한 검색처리
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup4", method = RequestMethod.POST)
	public ArrayList<String> jsoup4Post(String search, String searchSelector) throws IOException {
		Connection conn = Jsoup.connect(search);
		
		Document document = conn.get();
		
		// 하나만 넘긴다고 가정
		Elements selects = document.select(searchSelector);
		
		ArrayList<String> vos = new ArrayList<String>();
		
		int i = 0;
		for(Element select : selects) {
			i++;
			System.out.println(i + " : " + select.html());  // html 안 써도 됨
			vos.add(i + " : " + select.html().replace("data-lazy", ""));
		}
		
		return vos;
	}
	
	// jsoup를 이용한 웹크롤링 처리하기(멜론 차트 검색하기)
	@ResponseBody
	@RequestMapping(value = "/crawling/melonCrawling", method = RequestMethod.POST)
	public ArrayList<CrawlingVO> melonCrawlingPost() throws Exception {
		Connection conn = Jsoup.connect("https://www.melon.com/chart/index.htm");
		
		Document document = conn.get();
		
		Elements selects = null;
		
		selects = document.select("span.rank");
		ArrayList<String> vos1 = new ArrayList<String>();
		for(Element select : selects) {
			vos1.add(select.html());
		}
		
		selects = document.select("div.wrap > a.image_typeAll");
		ArrayList<String> vos2 = new ArrayList<String>();
		for(Element select : selects) {
			vos2.add(select.html());
		}
		
		selects = document.select("div.wrap_song_info > div.ellipsis.rank01 a");
		ArrayList<String> vos3 = new ArrayList<String>();
		for(Element select : selects) {
			vos3.add(select.html());
		}
		
		selects = document.select("span.checkEllipsis > a");
		ArrayList<String> vos4 = new ArrayList<String>();
		for(Element select : selects) {
			vos4.add(select.html());
		}
		
		ArrayList<CrawlingVO> vos = new ArrayList<CrawlingVO>();
		CrawlingVO vo = null;
		for(int i=0; i<100; i++) {
			vo = new CrawlingVO();
			vo.setItem1(vos1.get(i+1));
			vo.setItem2(vos2.get(i));
			vo.setItem3(vos3.get(i));
			vo.setItem4(vos4.get(i));
			System.out.println("vo : " + vo);
			vos.add(vo);
		}
		return vos;
	}
	
	// 셀레니움 연습(selenium)
	@RequestMapping(value = "/crawling/selenium", method = RequestMethod.GET)
	public String seleniumGet() {
		return "study/crawling/selenium";
	}
	
	// 크롤링연습 처리(selenium) - CGV 상영작 크롤링
	@ResponseBody  // ajax는 넣어야함
	@RequestMapping(value = "/crawling/selenium", method = RequestMethod.POST)
	public List<HashMap<String, Object>> seleniumPost(HttpServletRequest request) {
		List<HashMap<String, Object>> vos = new ArrayList<HashMap<String,Object>>();
		
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/resources/crawling/");
			System.setProperty("webdriver.chrome.driver", realPath + "chromedriver.exe");
			
			WebDriver driver = new ChromeDriver();
			driver.get("http://www.cgv.co.kr/movies/");
			
			// 현재 상영작만 보기
			WebElement btnMore = driver.findElement(By.id("chk_nowshow"));
			btnMore.click();
			
			// 더보기 버튼을 클릭한다.
			btnMore = driver.findElement(By.className("link-more"));
			btnMore.click();
			
			// 화면이 더 열리는 동안 시간을 지연시켜준다.
			try { Thread.sleep(2000); } catch (Exception e) {}
			
			// 낱개의 vos객체(elements)를 HashMap에 등록후 List객체로 처리해서 프론트로 넘겨준다.
			List<WebElement> elements = driver.findElements(By.cssSelector("div.sect-movie-chart ol li"));
			for(WebElement  element : elements) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				String image = "<img src='"+element.findElement(By.tagName("img")).getAttribute("src")+"' width='200px' />";  // 그림에 접근해서 src 속성을 가져옴
				String link = element.findElement(By.tagName("a")).getAttribute("href");
				String title = "<a href='"+link+"' target='_blank'>" +element.findElement(By.className("title")).getText() + "</a>";  // jsoup에서는 text()
				String percent = element.findElement(By.className("percent")).getText();
				map.put("image", image);
				map.put("link", link);
				map.put("title", title);
				map.put("percent", percent);
				vos.add(map);
			}
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("vos : " + vos);
		return vos;
	}
	
	// 크롤링연습 처리(selenium) - SRT 열차 조회하기
	@ResponseBody
	@RequestMapping(value = "/crawling/train", method = RequestMethod.POST)
	public List<HashMap<String, Object>> trainPost(HttpServletRequest request, String stationStart, String stationStop) {
		List<HashMap<String, Object>> array = new ArrayList<HashMap<String,Object>>();
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/resources/crawling/");
			System.setProperty("webdriver.chrome.driver", realPath + "chromedriver.exe");
			
			WebDriver driver = new ChromeDriver();
			driver.get("http://srtplay.com/train/schedule");  // f12 element 숨겨진 곳 마우스 오른쪽 Copy XPath
			
			WebElement btnMore = driver.findElement(By.xpath("//*[@id=\"station-start\"]/span"));  // 출발역 버튼 출력
			btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}  // 2초간 쉬어(처음엔 쉬어주는 것이 좋음)
      
      btnMore = driver.findElement(By.xpath("//*[@id=\"station-pos-input\"]"));  // input창을 클릭함
      btnMore.sendKeys(stationStart);  // 출발역 입력함
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationListArea\"]/li/label/div/div[2]"));
      btnMore.click();
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationDiv\"]/div/div[3]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      btnMore = driver.findElement(By.xpath("//*[@id=\"station-arrive\"]/span"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}  // 뜨는동아 2초전도 머물음
      btnMore = driver.findElement(By.id("station-pos-input"));
      
      btnMore.sendKeys(stationStop);  // 입력창 클릭해서 출발역 입력
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationListArea\"]/li/label/div/div[2]"));
      btnMore.click();
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationDiv\"]/div/div[3]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}  // 출발과 도착을 다 눌러서 2초간 쉼
      
      btnMore = driver.findElement(By.xpath("//*[@id=\"sr-train-schedule-btn\"]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      List<WebElement> timeElements = driver.findElements(By.cssSelector(".table-body ul.time-list li"));
 			
      HashMap<String, Object> map = null;
      
			for(WebElement element : timeElements){
				map = new HashMap<String, Object>();
				String train=element.findElement(By.className("train")).getText();
				String start=element.findElement(By.className("start")).getText();
				String arrive=element.findElement(By.className("arrive")).getText();
				String time=element.findElement(By.className("time")).getText();
				String price=element.findElement(By.className("price")).getText();
				map.put("train", train);
				map.put("start", start);
				map.put("arrive", arrive);
				map.put("time", time);
				map.put("price", price);
				array.add(map);
			}
			
	    // 요금조회하기 버튼을 클릭한다.(처리 안됨 - 스크린샷으로 대체)
      btnMore = driver.findElement(By.xpath("//*[@id=\"scheduleDiv\"]/div[2]/div/ul/li[1]/div/div[5]/button"));
      //System.out.println("요금 조회버튼클릭");
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      // 지정경로에 브라우저 화면 스크린샷 저장처리
  		realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");
      File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(scrFile, new File(realPath + "screenshot.png"));
			
      driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	
	// 크롤링연습 처리(selenium) - 네이버 게임 목록 조회하기
	@ResponseBody
	@RequestMapping(value = "/crawling/naverGameSearch", method = RequestMethod.POST)
	public List<CrawlingVO> naverGameSearchPost(HttpServletRequest request, int page) {
		List<CrawlingVO> vos = new ArrayList<CrawlingVO>();
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/resources/crawling/");
			System.setProperty("webdriver.chrome.driver", realPath + "chromedriver.exe");
			
			WebDriver driver = new ChromeDriver();
			driver.get("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=게임");
			
			WebElement btnMore = null;
			
			Connection conn = Jsoup.connect("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=게임");
			Document document = conn.get();
			
			Elements selects = null;
			ArrayList<String> titleVos = new ArrayList<String>();
			ArrayList<String> jangreVos = new ArrayList<String>();
			ArrayList<String> platformVos = new ArrayList<String>();
			ArrayList<String> chulsiilVos = new ArrayList<String>();
			ArrayList<String> thumbnailVos = new ArrayList<String>();
			
			for(int i=0; i<page; i++) {
				// 페이지마다 새로고침된 HTML을 가져와서 Jsoup으로 파싱
				document = Jsoup.parse(driver.getPageSource());
				
				//selects =	document.select("a.title");
				selects =	document.selectXpath("//*[@id=\"mflick\"]/div/div/div/div/strong/a");
				for(Element select : selects) {
					//titleVos.add(select.html());
					//titleVos.add("<a href='"+select.tagName("a").attribute("href")+"' target='_blank'>"+select.text()+"</a>");
					titleVos.add("<a href='https://search.naver.com/search.naver?"+select.tagName("a").attribute("href").toString().substring(select.tagName("a").attribute("href").toString().indexOf("?")+1)+"' target='_blank'>"+select.text()+"</a>");
				}
				//System.out.println();
				
				selects =	document.selectXpath("//*[@id=\"mflick\"]/div/div/div/div/dl/dd[1]");
				for(Element select : selects) {
					jangreVos.add(select.text());
				}
				
				selects =	document.selectXpath("//*[@id=\"mflick\"]/div/div/div/div/dl/dd[2]");
				for(Element select : selects) {
					platformVos.add(select.text());
				}
				
				selects =	document.selectXpath("//*[@id=\"mflick\"]/div/div/div/div/dl/dd[3]");
				for(Element select : selects) {
					chulsiilVos.add(select.text());
				}
				
				selects =	document.selectXpath("//*[@id=\"mflick\"]/div/div/div/div/div/a");
				for(Element select : selects) {
					thumbnailVos.add(select.html());
				}
				
				btnMore = driver.findElement(By.xpath("//*[@id=\"main_pack\"]/section[5]/div[2]/div/div/div[4]/div/a[2]"));
	      btnMore.click();
	      try { Thread.sleep(2000);} catch (InterruptedException e) {}
			}
			driver.close();
			
			for(int i=0; i<jangreVos.size(); i++) {
				CrawlingVO vo = new CrawlingVO();
				vo.setItem1(titleVos.get(i));
				vo.setItem2(jangreVos.get(i));
				vo.setItem3(platformVos.get(i));
				vo.setItem4(chulsiilVos.get(i));
				vo.setItem5(thumbnailVos.get(i));
				vos.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("vos : " + vos);
		return vos;
	}
	
	@RequestMapping(value = "/password/password", method = RequestMethod.GET)
	public String passwordGet() {
		return "study/password/password";
	}
	
	@ResponseBody
	@RequestMapping(value = "/password/sha256", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String sha256Post(String pwd) {
		UUID uid = UUID.randomUUID();
		String salt = uid.toString().substring(0,8);
		
		SecurityUtil security = new SecurityUtil();
		String encPwd = security.encryptSHA256(salt + pwd);
		
		pwd = "salt키 : " + salt + " / 암호화된 비밀번호 : " + encPwd;
		
		return pwd;
	}
	
	@ResponseBody
	@RequestMapping(value = "/password/aria", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String ariaPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		UUID uid = UUID.randomUUID();
		String salt = uid.toString().substring(0,8);
		
		String encPwd = "";
		String decPwd = "";
		
		encPwd = ARIAUtil.ariaEncrypt(salt + pwd);
		decPwd = ARIAUtil.ariaDecrypt(encPwd);
		
		pwd = "salt키 : " + salt + " / 암호화비번 : " + encPwd + " / 복호화비번 : " + decPwd.substring(8);
		
		return pwd;
	}
	
	@ResponseBody
	@RequestMapping(value = "/password/bCryptPassword", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String bCryptPasswordPost(String pwd) {
		String encPwd = "";
		encPwd = passwordEncoder.encode(pwd);
		
		pwd = "암호화된 비밀번호 : " + encPwd;
		
		return pwd;
	}
	
	// wordcloud 연습
	@RequestMapping(value = "/wordcloud/wordcloudForm", method = RequestMethod.GET)
	public String wordcloudGet() {
		return "study/wordcloud/wordcloudForm";
	}
	
	// wordcloud 연습정리1
	@ResponseBody
	@RequestMapping(value = "/wordcloud/analyzer1", method = RequestMethod.POST)
	public Map<String, Integer> analyzer1Post(String content) {
		return studyService.analyzer(content);
	}
	
	// wordcloud 연습정리2
	@ResponseBody
	@RequestMapping(value = "/wordcloud/analyzer2", method = RequestMethod.POST)
	public Map<String, Integer> analyzer2Post(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/sample.txt");  // 여기서 study부터 들어가면 x
		String content = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader(realPath))) {  // 웹에서 가져오는 건 많이 했으니 자바에서 자료에 줄단위로 가져오는 방법 씀 => io => 예외처리  // 자바 io  // 폴더명, 파일명 같이 넣야함 => realPath에 넣어놓음  // 알아서 파일단위로 읽어옴
			String line;
			while ((line = br.readLine()) != null) {  // 어디 쓰는게 아니라 읽어오는 것  // null이 아닌동안 한줄씩 읽어주세요
				content += line + " ";
				//System.out.println("line" + line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return studyService.analyzer(content);
	}
	
	// wordcloud 연습정리3
	@ResponseBody
	@RequestMapping(value = "/wordcloud/analyzer3", method = RequestMethod.POST)
	public Map<String, Integer> analyzer3Post(HttpServletRequest request,
			String url, String selector, 
			String excludeWord
		) throws IOException {
		Connection conn = Jsoup.connect(url);  // 연결된것
		
		Document document = conn.get();// 사이트 접속해서 실행  // 화면에 보이는걸 변수에 담음
		Elements selectors = document.select(selector);  // selector로 하나씩 검색해서 꺼냄
		
		int i = 0;
		String str = "";
		for(Element select : selectors) {
			i++;
			System.out.println(i + " : " + select.html());
			str += select.html() + "\n";
		}
		
		// 제외할 문자 처리하기
		String[] tempStrs = excludeWord.split("/");  // [속보]/[특종]/[단독]
		for(int k=0; k<tempStrs.length; k++) {
			str = str.replace(tempStrs[k], "");  // str에 들어있는 문자 중에서 특정 단어 넣은 것을 찾아서 빈 것으로 대체함
		}
		
		// 파일로 저장하기
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/sample2.txt");
		try (FileWriter writer = new FileWriter(realPath)) {
			writer.write(str);
			System.out.println("파일 생성 Ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studyService.analyzer(str);
	}
	
	// 워드클라우드 생성하여 이미지로 보관하기
	@RequestMapping(value = "/wordcloud/wordcloudShow", method = RequestMethod.GET)  // 주소로 보냈으므로 당연히 GET
	public String wordcloudShowGet(HttpServletRequest request, Model model) throws IOException, FontFormatException {  // 그림을 담아야 하니 model로 보냄
		FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();  // 생성 안하고 위에 autowired 걸어도 됨 // 이건 자바에서 쓰는방식 여긴 스프링
		frequencyAnalyzer.setWordFrequenciesToReturn(300);  // 반환개수 // 200~300이 적당(지정한 것 보다 적으면 안된것 만큼 가져옴)
		frequencyAnalyzer.setMinWordLength(2);  // 형태소
		frequencyAnalyzer.setWordTokenizer(new WhiteSpaceWordTokenizer());  // 토큰단위로 분석해서 만들어 주세요
		
		List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream(request.getSession().getServletContext().getRealPath("/resources/data/study/sample2.txt")));  // 그림으로 저장.. 일정 기간마다 저장해서 보여줘도 되고 지금은 덮어쓰는 것
		
		Dimension dimension = new Dimension(500, 500);  // 워드클라우드 크기(픽셀)  // 자바 그래픽 기술  // 백터형식으로 만들어짐 평면좌표 주면 됨  // 500에 500짜리 백터 형식으로 만듬
		WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);  // 단어 사이의 충돌을 감지해서 최대한 조밀하게 처리할 것 // 밀도를 꽉 차게 만들어란 명령어  // 충돌하는걸 최대한 완벽하게 꽉차게
		wordCloud.setPadding(2);  // 단어 사이의 약간의 여백
		wordCloud.setBackground(new CircleBackground(250));  // 워드클라우드으ㅢ 배경모양을 결정(반지름이 250인 원형)  // 원의 반지름을 줘서 원의 크기 지정  // 여기서 지원해주는 모양이 틀림(사각, 물고기 등)
		wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1),new Color(0x408DF1),new Color(0x40AAF1),new Color(0x40C5F1),new Color(0x40D3F1),new Color(0x4055F1),new Color(0xcccccc),new Color(0xd1b2c3),new Color(0xe1e2e3)));  // 16진수 0x 영어 못씀 6자리  // 5개는 기본적으로 줌 처음 3개가 메인이고 뒤로 갈수록 은은하게 작은
		wordCloud.setFontScalar(new LinearFontScalar(10, 60));  // 최소, 최대
		
		// 한글 폰트 설정
		Font font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("fonts/NanumGothic-Bold.ttf"));  // 나를 지칭해 주는 resources // 외부 폰트 사용했기에 무조건 예외처리 //한글 깨짐, 한글 폰트 넣어줘야함(윈도우 폰트 안먹음)  // 한글 안쓰면 없어도 됨
		wordCloud.setKumoFont(new KumoFont(font));  // 기본 폰트를 내가 올린 폰트로 쓰겠다 지정
		
		wordCloud.build(wordFrequencies);  // 분석한 걸로 만들어 주세요
		wordCloud.writeToFile(request.getSession().getServletContext().getRealPath("resources/data/study/wordcloud.png"));  // 그림 파일로 만들어 주세요
		
		model.addAttribute("imagePath", "resources/data/study/wordcloud.png");
		return "study/wordcloud/wordcloudShow";
	}
	
	private InputStream getInputStream(String path) throws IOException {
		return new FileInputStream(new File(path));
	}
	
	@RequestMapping(value = "/random/randomForm", method = RequestMethod.GET)
	public String randomFormGet() {
		return "study/random/randomForm";
	}
	
	// randomNumeric : 숫자를 랜덤하게 처리(숫자 8자리)..
	@ResponseBody
	@RequestMapping(value = "/random/randomNumeric", method = RequestMethod.POST)
	public String randomNumericPost() {
		// (int) ((Math.random()*(최대값-최소값+1)) + 최소값
		return ((int) (Math.random()*(99999999-10000000+1)) + 10000000) + "";  // 1000만 ~ 9999만
	}
	
	// randomUUID : 숫자와 문자를 소문자형식으로 랜덤하게 처리(16진수 32자리 문자)...
	@ResponseBody
	@RequestMapping(value = "/random/randomUUID", method = RequestMethod.POST)
	public String randomUUIDPost() {
		return (UUID.randomUUID()) + "";
	}
	
	// randomAlphaNumericCheck : 숫자와 문자를 대/소문자 섞어서 랜덤하게 처리(일반 영숫자 xx자리 문자)...
	@ResponseBody
	@RequestMapping(value = "/random/randomAlphaNumeric", method = RequestMethod.POST)
	public String randomAlphaNumericPost() {
		//String res = RandomStringUtils.randomAlphanumeric(64);  // 원하는만큼 가능
		return RandomStringUtils.randomAlphanumeric(64);
	}

	// 카카오맵 화면보기
	@RequestMapping(value = "/kakao/kakaomap", method = RequestMethod.GET)
	public String kakaomapGet() {
		return "study/kakao/kakaomap";
	}
	
	// 카카오맵 마커표시/저장 폼보기
	@RequestMapping(value = "/kakao/kakaoEx1", method = RequestMethod.GET)
	public String kakaoEx1Get() {
		return "study/kakao/kakaoEx1";
	}
	
	// 카카오맵 마커표시/저장 처리
	@ResponseBody
	@RequestMapping(value = "/kakao/kakaoEx1", method = RequestMethod.POST)
	public String kakaoEx1Post(KakaoAddressVO vo) {
		KakaoAddressVO searchVO = studyService.getKakaoAddressSearch(vo.getAddress());
		
		if(searchVO != null) return "0";
		
		studyService.setKakaoAddressInput(vo);
		
		return "1";
	}

//	// 카카오맵 MyDB에 저장된 지명검색
//	@RequestMapping(value = "/kakao/kakaoEx2", method = RequestMethod.GET)
//	public String kakaoEx2Get(Model model) {
//		List<KakaoAddressVO> addressVos = studyService.getKakaoAddressList();
//		
//		model.addAttribute("addressVos", addressVos);
//	
//		return "study/kakao/kakaoEx2";
//	}
	
	// 카카오맵 연습2(MyDB에 저장된 주소목록 가져오기 / 지점검색하기 추가)
	@RequestMapping(value = "/kakao/kakaoEx2", method = RequestMethod.GET)
	public String kakaoEx2Get(Model model,
			@RequestParam(name="address", defaultValue = "", required = false) String address) {
		KakaoAddressVO vo = new KakaoAddressVO();
		List<KakaoAddressVO> addressVos = studyService.getKakaoAddressList();
		
		if(address.equals("")) {
			vo.setAddress("청주 그린컴퓨터");  // 여기를 홈으로 설정
			vo.setLatitude(36.63518067764835);
			vo.setLongitude(127.45950765979747);
		}
		else {
		  vo = studyService.getKakaoAddressSearch(address);
		}
		model.addAttribute("addressVos", addressVos);
		model.addAttribute("vo", vo);
		
		return "study/kakao/kakaoEx2";
	}
	
	// 카카오맵 MyDb에 저장된 검색위치 삭제하기
	@ResponseBody
	@RequestMapping(value = "/kakao/kakaoAddressDelete", method = RequestMethod.POST)
	public String kakaoAddressDeletePost(String address) {
		return studyService.setKakaoAddressDelete(address) + "";
	}
	
	// 카카오맵 : KakaoDB에 저장된 키워드검색후 MyDB에 저장하기
	@RequestMapping(value = "/kakao/kakaoEx3", method = RequestMethod.GET)
	public String kakaoEx3Get(Model model,
			@RequestParam(name="address", defaultValue = "", required = false) String address
		) {
		model.addAttribute("address", address);
		return "study/kakao/kakaoEx3";
	}
	
	// 카카오맵 : 지명으로 위치검색후 카카오DB에 저장된 위치를 검색하여 주변정보 보여주기
	@RequestMapping(value = "/kakao/kakaoEx4", method = RequestMethod.GET)
	public String kakaoEx4Get(Model model,
			@RequestParam(name="address", defaultValue = "", required = false) String address
			) {
		model.addAttribute("address", address);
		return "study/kakao/kakaoEx4";
	}
	
	// CSV파일을 MySQL파일로 변환하기폼보기
	@RequestMapping(value = "/csv/csvForm", method = RequestMethod.GET)
	public String csvFormGet() {
		return "study/csv/csvForm";
	}
	
	// CSV파일을 MySQL파일로 변환하기
	@ResponseBody
	@RequestMapping(value = "/csv/csvForm", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String csvFormPost(MultipartFile fName, HttpServletRequest request) throws IOException {
		return studyService.fileCsvToMysql(fName);
	}
	
	// CSV파일을 MySQL파일로 삭제하기
	@ResponseBody
	@RequestMapping(value = "/csv/csvDeleteTable", method = RequestMethod.POST)
	public String csvDeleteTablePost(String csvTable) throws IOException {
		return studyService.setCsvTableDelete(csvTable) + "";
	}
	
	// 날씨 API 폼
	@RequestMapping(value = "/weather/weatherForm", method = RequestMethod.GET)
	public String weatherFormGet(Model model) {
		List<KakaoAddressVO> jiyukVos = studyService.getKakaoAddressList();
		model.addAttribute("jiyukVos", jiyukVos);
		return "study/weather/weatherForm";
	}
	
	// 캡차 연습하기 폼
	@RequestMapping(value = "/captcha/captchaForm", method = RequestMethod.GET)
	public String captchaFormGet() {
		return "redirect:/study/captcha/captchaImage";
	}
	
	// 캡차 연습하기 처리
	@RequestMapping(value = "/captcha/captchaImage", method = RequestMethod.GET)
	public String captchaImageGet(HttpSession session, HttpServletRequest request, Model model) {
		// 시스템에 설정된 폰트 출력해보기
		/*
		Font[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		for(Font f : fontList) {
			System.out.println(f.getName());
		}
		*/
		
		try {
			// 알파뉴메릭문자 5개를 가져온다.
			String randomString = RandomStringUtils.randomAlphanumeric(5);
			System.out.println("randomString : " + randomString);
			session.setAttribute("sCaptcha", randomString);
			
			Font font = new Font("Jokerman", Font.ITALIC, 30);
			FontRenderContext frc = new FontRenderContext(null, true, true);
			Rectangle2D bounds = font.getStringBounds(randomString, frc);
			int w = (int) bounds.getWidth();
			int h = (int) bounds.getHeight();
			
			// 이미지로 생성
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
			Graphics2D g = image.createGraphics();
			
			g.fillRect(0, 0, w, h);
			g.setColor(new Color(0, 156, 240));
			g.setFont(font);
			// 각종 랜더링 명령어에 의한 chptcha문자 작업.....
			g.drawString(randomString, (float)bounds.getX(), (float)-bounds.getY());
			g.dispose();
			
			String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
			int temp = (int) (Math.random()*5) + 1;
			String captchaImage = "captcha" + temp + ".png";
			
			ImageIO.write(image, "png", new File(realPath + captchaImage));
			model.addAttribute("captchaImage", captchaImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "study/captcha/captchaForm";
	}
	
	// 캡차 문자 확인하기
	@ResponseBody
	@RequestMapping(value = "/captcha/captcha", method = RequestMethod.POST)
	public String captchaPost(HttpSession session, String strCaptcha) {
		if(strCaptcha.equals(session.getAttribute("sCaptcha").toString())) return "1";
		else return "0";
	}
	
	// QR Code 연습 폼
	@RequestMapping(value = "/qrCode/qrCodeForm", method = RequestMethod.GET)
	public String qrCodeFormGet() {
		return "study/qrCode/qrCodeForm";
	}
	
	// QR Code 연습 폼
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeCreate", method = RequestMethod.POST)
	public String qrCodeCreatePost(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		return studyService.setQrCodeCreate(realPath);
	}
	
	// QR Code 개인정보 코드로 생성하기 폼 보기
	@RequestMapping(value = "/qrCode/qrCodeEx1", method = RequestMethod.GET)
	public String qrCodeEx1Get() {
		return "study/qrCode/qrCodeEx1";
	}

	// QR Code 개인정보 코드로 생성하기
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeCreate1", method = RequestMethod.POST, produces="application/text; charset=utf8")  // , produces="application/text; charset=utf8" 한글처리(닉네임)
	public String qrCodeCreate1Post(HttpServletRequest request, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		return studyService.setQrCodeCreate1(realPath, vo);  // 오버로딩해도 되는데(Mybatis에서 id가 같으므로 사용못함)
	}
	
	// QR Code 소개사이트 주소 생성하기 폼 보기
	@RequestMapping(value = "/qrCode/qrCodeEx2", method = RequestMethod.GET)
	public String qrCodeEx2Get() {
		return "study/qrCode/qrCodeEx2";
	}
	
	// QR Code 소개사이트 주소 생성하기
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeCreate2", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String qrCodeCreate2Post(HttpServletRequest request, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		return studyService.setQrCodeCreate2(realPath, vo);
	}
	
	// QR Code 티켓예매 폼보기
	@RequestMapping(value = "/qrCode/qrCodeEx3", method = RequestMethod.GET)
	public String qrCodeEx3Get() {
		return "study/qrCode/qrCodeEx3";
	}
	
	// QR Code 티켓예매 생성하기
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeCreate3", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String qrCodeCreate3Post(HttpServletRequest request, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		return studyService.setQrCodeCreate3(realPath, vo);
	}
	
	// QR Code 티켓예매 폼보기(DB 저장/검색)
	@RequestMapping(value = "/qrCode/qrCodeEx4", method = RequestMethod.GET)
	public String qrCodeEx4Get() {
		return "study/qrCode/qrCodeEx4";
	}
	
	// QR Code 티켓예매 생성하기(DB 저장/검색)
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeCreate4", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String qrCodeCreate4Post(HttpServletRequest request, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		return studyService.setQrCodeCreate4(realPath, vo);
	}
	
	// QR Code명 검색하기(DB 저장/검색)
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeSearch", method = RequestMethod.POST)
	public QrCodeVO qrCodeSearchPost(String qrCode) {
		return studyService.getQrCodeSearch(qrCode);  // 생성 x db만 검색
	}
	
	// 썸네일 연습 폼보기
	@RequestMapping(value = "/thumbnail/thumbnailForm", method = RequestMethod.GET)
	public String thumbnailFormGet() {
		return "study/thumbnail/thumbnailForm";
	}
	
	// 썸네일 연습 사진처리
	@ResponseBody
	@RequestMapping(value = "/thumbnail/thumbnailForm", method = RequestMethod.POST)
	public String thumbnailFormPost(MultipartFile file) {
		return studyService.setThumbnailCreate(file);
	}
	
	// 썸네일 연습 폼보기
	@RequestMapping(value = "/thumbnail/thumbnailResult", method = RequestMethod.GET)
	public String thumbnailResultGet(HttpServletRequest request, Model model) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
		String[] files = new File(realPath).list();
		
		model.addAttribute("files",files);
		model.addAttribute("fileCnt",(files.length/2)); // 같은 파일이 2개씩 가므로
		
		return "study/thumbnail/thumbnailResult";
	}
	
	@RequestMapping(value = "/chart/chartForm", method = RequestMethod.GET)
	public String chartForm(Model model,
			@RequestParam(name="part", defaultValue="barVChart", required=false) String part) {  // 안넘어올때 대비해서 requestparam 사용
		model.addAttribute("part", part);
		return "study/chart/chartForm";
	}
}