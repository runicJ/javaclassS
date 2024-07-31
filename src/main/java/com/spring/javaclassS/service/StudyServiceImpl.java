package com.spring.javaclassS.service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.dao.StudyDAO;
import com.spring.javaclassS.vo.BicycleVO;
import com.spring.javaclassS.vo.ChartVO;
import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.ExchangeRateVO;
import com.spring.javaclassS.vo.KakaoAddressVO;
import com.spring.javaclassS.vo.PetCafeVO;
import com.spring.javaclassS.vo.QrCodeVO;
import com.spring.javaclassS.vo.TagoExpressVO;
import com.spring.javaclassS.vo.TransactionVO;
import com.spring.javaclassS.vo.UserVO;

import net.coobird.thumbnailator.Thumbnailator;

@Service
public class StudyServiceImpl implements StudyService {

	@Autowired  // db값 얘가 안가져 오고 dao 시킴
	StudyDAO studyDAO;
	
	@Autowired
	JavaclassProvide javaclassProvide;  // 여기에 이름바꾸는것, 저장하는것, 지우는것
	
	@Override
	public String[] getCityStringArray(String dodo) {
		String[] strArray = new String[100];
			
		if(dodo.equals("서울")) {
			strArray[0] = "강남구";
			strArray[1] = "강북구";
			strArray[2] = "강서구";
			strArray[3] = "강동구";
			strArray[4] = "서초구";
			strArray[5] = "관악구";
			strArray[6] = "종로구";
			strArray[7] = "영등포구";
			strArray[8] = "마포구";
			strArray[9] = "동대문구";
		}
		else if(dodo.equals("경기")) {
			strArray[0] = "수원시";
			strArray[1] = "안양시";
			strArray[2] = "안성시";
			strArray[3] = "평택시";
			strArray[4] = "시흥시";
			strArray[5] = "용인시";
			strArray[6] = "성남시";
			strArray[7] = "광명시";
			strArray[8] = "김포시";
			strArray[9] = "안산시";
		}
		else if(dodo.equals("충북")) {
			strArray[0] = "청주시";
			strArray[1] = "충주시";
			strArray[2] = "제천시";
			strArray[3] = "단양군";
			strArray[4] = "음성군";
			strArray[5] = "진천군";
			strArray[6] = "괴산군";
			strArray[7] = "증평군";
			strArray[8] = "옥천군";
			strArray[9] = "영동군";
		}
		else if(dodo.equals("충남")) {
			strArray[0] = "천안시";
			strArray[1] = "아산시";
			strArray[2] = "논산시";
			strArray[3] = "공주시";
			strArray[4] = "당진시";
			strArray[5] = "서산시";
			strArray[6] = "홍성군";
			strArray[7] = "청양군";
			strArray[8] = "계룡시";
			strArray[9] = "예산군";
		}
		
//		for(String s : strArray) {
//			System.out.println("s : " + s);
//			if(s == null) break;
//		}
		
		return strArray;
	}

	@Override
	public ArrayList<String> getCityArrayList(String dodo) {
		ArrayList<String> vos = new ArrayList<String>();
		
		if(dodo.equals("서울")) {
			vos.add("강남구");
			vos.add("강북구");
			vos.add("강서구");
			vos.add("강동구");
			vos.add("서초구");
			vos.add("관악구");
			vos.add("종로구");
			vos.add("영등포");
			vos.add("마포구");
			vos.add("동대문구");
		}
		else if(dodo.equals("경기")) {
			vos.add("수원시");
			vos.add("안양시");
			vos.add("안성시");
			vos.add("평택시");
			vos.add("시흥시");
			vos.add("용인시");
			vos.add("성남시");
			vos.add("광명시");
			vos.add("김포시");
			vos.add("안산시");
		}
		else if(dodo.equals("충북")) {
			vos.add("청주시");
			vos.add("충주시");
			vos.add("제천시");
			vos.add("단양군");
			vos.add("음성군");
			vos.add("진천군");
			vos.add("괴산군");
			vos.add("증평군");
			vos.add("옥천군");
			vos.add("영동군");
		}
		else if(dodo.equals("충남")) {
			vos.add("천안시");
			vos.add("아산시");
			vos.add("논산시");
			vos.add("공주시");
			vos.add("당진시");
			vos.add("서산시");
			vos.add("홍성군");
			vos.add("청양군");
			vos.add("계룡시");
			vos.add("예산군");
		}
			
		return vos;
	}

//    HashMap<String, UserVO> userMidMap = new HashMap<>();
//    
//    @Override
//    public HashMap<String, UserVO> getUserMidMap() {
//        ArrayList<UserVO> users = studyDAO.getUserMidMap();
//        for (UserVO user : users) {
//            userMidMap.put(user.getMid(), user);
//        }
//        return userMidMap;
//    }

//    @Override
//    public UserVO getUserMidInfo(String mid) {
//        return userMidMap.get(mid);
//    }

//  	@Override
//  	public ArrayList<String> getDbtestMidList() {
//  		return studyDAO.getDbtestMidList();
//  	}
    
	@Override
	public UserVO getUserMidSearch(String mid) {
		return studyDAO.getUserMidSearch(mid);
	}

	@Override
	public ArrayList<UserVO> getUserMidList(String mid) {
		return studyDAO.getUserMidList(mid);
	}

	@Override
	public void setSaveCrimeDate(CrimeVO vo) {
		studyDAO.setSaveCrimeDate(vo);
	}

	@Override
	public void setDeleteCrimeDate(int year) {
		studyDAO.setDeleteCrimeDate(year);
	}

	@Override
	public ArrayList<CrimeVO> getListCrimeDate(int year) {
		return studyDAO.getListCrimeDate(year);
	}

	@Override
	public ArrayList<CrimeVO> getYearPoliceCheck(int year, String police, String yearOrder) {
		return studyDAO.getYearPoliceCheck(year, police, yearOrder);
	}

	@Override
	public CrimeVO getAnalyzeTotal(int year, String police) {
		return studyDAO.getAnalyzeTotal(year, police);
	}
	
    /*
    @Override
    public int setDeleteCrimeData(int year) {
        return studyDAO.setDeleteCrimeData(year);
    }

    @Override
    public String getListCrimeData(int year) {
        List<CrimeVO> vos = studyDAO.getListCrimeData(year);
        StringBuilder str = new StringBuilder("<table class='table table-bordered table-hover text-center'>"
                + "<tr class='table-info'><th colspan='5' style='font-size:24pt'>" + year + "년도 강력범죄 자료</th></tr>"
                + "<tr class='table-secondary'><th>경찰서</th><th>살인</th><th>강도</th><th>절도</th><th>폭력</th></tr>");
        if (vos.isEmpty()) str.append("<tr><td colspan='5'>해당 년도의 자료가 없습니다.</td></tr>");
        else {
            for (CrimeVO v : vos) {
                str.append("<tr><td>").append(v.getPolice()).append("</td>")
                        .append("<td>").append(v.getMurder()).append("건</td>")
                        .append("<td>").append(v.getRobbery()).append("건</td>")
                        .append("<td>").append(v.getTheft()).append("건</td>")
                        .append("<td>").append(v.getViolence()).append("건</td></tr>");
            }
        }
        str.append("</table>");
        return str.toString();
    }

    @Override
    public String getPoliceCrimeDate(String police, int year) {
        List<CrimeVO> vos = studyDAO.getPoliceCrimeDate(police, year);
        StringBuilder str = new StringBuilder("<table class='table table-bordered table-hover text-center'>"
                + "<tr class='table-info'><th colspan='5' style='font-size:24pt'>" + year + "년도 " + police + " 경찰서 강력범죄 자료</th></tr>"
                + "<tr class='table-secondary'><th>경찰서</th><th>살인</th><th>강도</th><th>절도</th><th>폭력</th></tr>");
        if (vos.isEmpty()) str.append("<tr><td colspan='5'>해당 년도의 자료가 없습니다.</td></tr>");
        else {
            for (CrimeVO v : vos) {
                str.append("<tr><td>").append(v.getPolice()).append("</td>")
                        .append("<td>").append(v.getMurder()).append("건</td>")
                        .append("<td>").append(v.getRobbery()).append("건</td>")
                        .append("<td>").append(v.getTheft()).append("건</td>")
                        .append("<td>").append(v.getViolence()).append("건</td></tr>");
            }
        }
        str.append("</table>");
        return str.toString();
    }

    @Override
    public String getYearPoliceCheck(String police, int year, String sort) {
        List<CrimeVO> vos;
        if ("a".equals(sort)) {
            vos = studyDAO.getYearPoliceCheckA(police, year);
        } else {
            vos = studyDAO.getYearPoliceCheckD(police, year);
        }
        StringBuilder str = new StringBuilder("<table class='table table-bordered table-hover text-center'>"
                + "<tr class='table-info'><th colspan='5' style='font-size:24pt'>" + year + "년도 " + police + "지역 강력범죄 자료</th></tr>"
                + "<tr class='table-secondary'><th>경찰서</th><th>살인</th><th>강도</th><th>절도</th><th>폭력</th></tr>");
        if (vos.isEmpty()) str.append("<tr><td colspan='5'>해당 년도의 자료가 없습니다.</td></tr>");
        else {
            for (CrimeVO v : vos) {
                str.append("<tr><td>").append(v.getPolice()).append("</td>")
                        .append("<td>").append(v.getMurder()).append("건</td>")
                        .append("<td>").append(v.getRobbery()).append("건</td>")
                        .append("<td>").append(v.getTheft()).append("건</td>")
                        .append("<td>").append(v.getViolence()).append("건</td></tr>");
            }
        }
        str.append("</table>");
        return str.toString();
    }
	
    @Override
    public String getPoliceCheck(String police) {
        List<CrimeVO> vos = studyDAO.getPoliceCheck(police);
        StringBuilder str = new StringBuilder("<table class='table table-bordered table-hover text-center'>"
                + "<tr class='table-info'><th colspan='5' style='font-size:24pt'>" + police + "지역 강력범죄 자료</th></tr>"
                + "<tr class='table-secondary'><th>경찰서</th><th>살인</th><th>강도</th><th>절도</th><th>폭력</th></tr>");
        if (vos.isEmpty()) str.append("<tr><td colspan='5'>해당 지역의 자료가 없습니다.</td></tr>");
        else {
            for (CrimeVO v : vos) {
                str.append("<tr><td>").append(v.getPolice()).append("</td>")
                        .append("<td>").append(v.getMurder()).append("건</td>")
                        .append("<td>").append(v.getRobbery()).append("건</td>")
                        .append("<td>").append(v.getTheft()).append("건</td>")
                        .append("<td>").append(v.getViolence()).append("건</td></tr>");
            }
        }
        str.append("</table>");
        return str.toString();
    }
    */

	@Override
	public int fileUpload(MultipartFile fName, String mid) {
		int res = 0;
		
		// 파일이름 중복처리를 위해 UUID객체 활용 // 파일중복체크 안해 주기에 강제로 중복됐는지 확인해야함 => 중복되면 파일이름을 붙여서 저장할 파일 만들어 넣어줌
		UUID uid = UUID.randomUUID();
		String oFileName = fName.getOriginalFilename();
		String sFileName = mid + "_" + uid.toString().substring(0,8) + "_" + oFileName;
		
		// 서버에 파일 올리기
		try {
			writeFile(fName, sFileName);  // 성공적으로 파일을 올린 것
			res = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}

	private void writeFile(MultipartFile fName, String sFileName) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");  // 서버의 절대경로
		
		FileOutputStream fos = new FileOutputStream(realPath + sFileName);

		//fos.write(fName.getBytes())
		if(fName.getBytes().length != -1) {  // -1은 없단 얘기
			fos.write(fName.getBytes());
		}
		fos.flush();
		fos.close();
	}

	@Override
	public int multiFileUpload(MultipartHttpServletRequest mFile) {
		int res = 0;
		
		try {
			List<MultipartFile> fileList = mFile.getFiles("fName");  // 여러개가 들어오는 걸 한개 타입으로 받아내야 하는 것 MultipartFile 제너릭으로 사용  // jsp에서 값 보낼때 mFile로 보내지 않았음 fName으로 여러개 보냄
			String oFileNames = "";
			String sFileNames = "";
			int fileSizes = 0;  // 누적할 것이므로 0
			
			for(MultipartFile file : fileList) {
				//System.out.println("원본화일 : " + file.getOriginalFilename());
				String oFileName = file.getOriginalFilename();
				String sFileName = javaclassProvide.saveFileName(oFileName);  // 서버에 저장하는 saveFileName
				
				javaclassProvide.writeFile(file, sFileName, "fileUpload");
				
				oFileNames += oFileName + "/";
				sFileNames += sFileName + "/";
				fileSizes += file.getSize();
			}
			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
			sFileNames = sFileNames.substring(0, sFileNames.length()-1);
			
//			System.out.println("원본파일 : " + oFileNames);
//			System.out.println("서버저장파일 : " + sFileNames);
//			System.out.println("파일총사이즈 : " + fileSizes);
			
			res = 1;  // 정상적으로 통과됨
		} catch (IOException e) {e.printStackTrace();}
		return res;
		
	}

	@Override
	public Map<String, Integer> analyzer(String content) {
		int wordFrequenciesToReturn = 10;  // 빈도수
		int minWordLength = 2;  // 최소 글자수
		
		Map<String, Integer> frequencyMap = new HashMap<>();  // wrapper 타입으로 적어야 함(int 못옴)  // 구현객체로 써야함 hashMap  // 뒤에는 앞에 따라가므로 안써도 됨
		
		String[] words = content.split("\\s+");   // 공백을 정규식으로 넣어야함(" " 안됨)  // s 공백 + 최소 한개이상 s만 쓰면 문자로 보기에 \\ 붙임
		
		for(String word : words) {
			if(word.length() >= minWordLength) {  // 이상이 되야 작업대상으로 봄
				word = word.toLowerCase();  // 소문자로 통일
				frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);  // 형태소 분석  // object key, integer default  // 첫번째는 없으니까 0이고 같은게 들어오면 하나를 더해주세요  // 담은 것을 각각의 키에 담아줌  // word 키 , 뒤에 value
			}
		}
		
    return frequencyMap.entrySet().stream()
        .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
        .limit(wordFrequenciesToReturn)
        .collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);
	}

	@Override
	public KakaoAddressVO getKakaoAddressSearch(String address) {
		return studyDAO.getKakaoAddressSearch(address);
	}

	@Override
	public void setKakaoAddressInput(KakaoAddressVO vo) {
		studyDAO.setKakaoAddressInput(vo);
	}

	@Override
	public List<KakaoAddressVO> getKakaoAddressList() {
		return studyDAO.getKakaoAddressList();
	}

	@Override
	public int setKakaoAddressDelete(String address) {
		return studyDAO.setKakaoAddressDelete(address);
	}

	@Override
	public String fileCsvToMysql(MultipartFile fName) {
		String str = "";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
		
		// 작업할 원본 csv파일 업로드(원본파일이 utf-8파일이 아니라면 메모장에서 utf-8로 다시 저장해서 업로드시켜준다.
		try {
			FileOutputStream fos;
			fos = new FileOutputStream(realPath + fName.getOriginalFilename());
			if(fName.getBytes().length != -1) {
				fos.write(fName.getBytes());
			}
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 업로드된 파일을 Line단위로 읽어와서 ','로 분리후 DB에 저장하기
		realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/"+fName.getOriginalFilename());
		try {
			BufferedReader br = new BufferedReader(new FileReader(realPath));
			String line;
			int cnt = 0;
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				cnt++;
				str += cnt + " : " + line + "<br>";
				String[] pet_cafe = line.split(",");
				PetCafeVO vo = new PetCafeVO();
				int k = 0;
				vo.setPlaceName(pet_cafe[k]); k++;
				vo.setCategory(pet_cafe[k]); k++;
				vo.setSido(pet_cafe[k]); k++;
				vo.setSigungu(pet_cafe[k]); k++;
				vo.setDong(pet_cafe[k]); k++;
				vo.setLatitude(Double.parseDouble(pet_cafe[k])); k++;
				vo.setLongitude(Double.parseDouble(pet_cafe[k])); k++;
				vo.setZipNum(Integer.parseInt(pet_cafe[k])); k++;
				vo.setRdnmAddress(pet_cafe[k]); k++;
				vo.setLnmAddress(pet_cafe[k]); k++;
				vo.setHomePage(pet_cafe[k]); k++;
				vo.setClosedDay(pet_cafe[k]); k++;
				vo.setOpenTime(pet_cafe[k]); k++;
				vo.setParking(pet_cafe[k]); k++;
				vo.setPlayPrice(pet_cafe[k]); k++;
				vo.setPetOK(pet_cafe[k]); k++;
				vo.setPetSize(pet_cafe[k]); k++;
				vo.setPetLimit(pet_cafe[k]); k++;
				vo.setInPlaceOK(pet_cafe[k]); k++;
				vo.setOutPlaceOK(pet_cafe[k]); k++;
				vo.setPlaceInfo(pet_cafe[k]); k++;
				vo.setPetExtraFee(pet_cafe[k]); k=0;
				
				// DB에 저장하기
				studyDAO.setPetCafe(vo);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public String setCsvTableDelete(String csvTable) {
		return studyDAO.setCsvTableDelete(csvTable);
	}

	@Override
	public String setQrCodeCreate(String realPath) {
		String qrCodeName = javaclassProvide.newNameCreate(2);  // 외부꺼 가져오면 예외처리 하라고 함
		String qrCodeImage = "";  // qrCode 모양을 만듦
		try {  // io에 관련된 것이므로 예외처리
			// QRCode 안의 한글 인코딩, 안하면 이상하게 나옴
			qrCodeImage = "생성된 QR코드명 : " + qrCodeName;
			qrCodeImage = new String(qrCodeImage.getBytes("UTF-8"), "ISO-8859-1");  // 인코딩 하는 방법 여러개 이게 제일 편함(String 이용)
			
			// qr 코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeImage, BarcodeFormat.QR_CODE, 200, 200); // 점의 밀도 형식  // 바코드 만드는 형식과 똑같이 만듦(구글에서 제공 BarcodeFormat)  // 크기(폭, 높이)
			
			//MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();  // 기본컬러(글자색:검정,배경색:흰색) => 색을 바꾸려면 여기서 바꾸면 됨 // 점을 그림으로 바꾸는 class
			//int qrCodeColor = 0xFF0000FF;  // 16진수 여기선 0x에서 FF까지 붙여줘야함
			int qrCodeColor = 0xFF000000;
			int qrCodeBackColor = 0xFFFFFFFF;
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			BufferedImage buffredImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);  // 랜더링 처리한 qr코드 이미지를 실제 그림으로 꺼냄 // 이미지로 만들어 주겠다 // buffer 이용하는 것이 안정적이라 함
			
			// 랜더링된 QR코드 이미지를 실제 그림파일로 만들어낸다.(캡챠에서 함)  // 이미지로 형상화해서 만듦
			ImageIO.write(buffredImage, "png", new File(realPath + qrCodeName + ".png") );  // 두번째 확장자, 세번째 파일객체
		} catch (IOException e) {  // Exception으로 하면 모든 에러를 받아서 어디서 문제가 생겼는지 알 수 없는 경우가 있음(각각 해야함)
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public String setQrCodeCreate1(String realPath, QrCodeVO vo) {
		String qrCodeName = javaclassProvide.newNameCreate(2);
		String qrCodeImage = "";
		try {
			// QRCode 안의 한글 인코딩
			qrCodeName += vo.getMid() + "_" + vo.getName() + "_" + vo.getEmail();  // 파일명의 개념, qr코드 이름
			qrCodeImage = "생성날짜 : " + qrCodeName.substring(0,4) + "년, " + qrCodeName.substring(4,6) + "월, " + qrCodeName.substring(6,8) + "일\n";  // <br>태그가 아님(br은 웹에서 보는 것) \n은 모바일로 보는 것
			qrCodeImage += "아이디	: " + vo.getMid() + "\n";  // qr코드에 들어가는 것
			qrCodeImage += "성명	: " + vo.getName() + "\n";
			qrCodeImage += "이메일	: " + vo.getEmail();
			qrCodeImage = new String(qrCodeImage.getBytes("UTF-8"), "ISO-8859-1"); // 한글을 넣기에 꼭 인코딩 해줘야 함 하나로 묶어서 씀
			
			// qr 코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeImage, BarcodeFormat.QR_CODE, 200, 200); // 점의 밀도 형식  // 바코드 만드는 형식과 똑같이 만듦(구글에서 제공 BarcodeFormat)  // 크기(폭, 높이)
			
			//MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();  // 기본컬러(글자색:검정,배경색:흰색) => 색을 바꾸려면 여기서 바꾸면 됨 // 점을 그림으로 바꾸는 class
			int qrCodeColor = 0xFF000000;
			int qrCodeBackColor = 0xFFFFFFFF;
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			BufferedImage buffredImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);  // 랜더링 처리한 qr코드 이미지를 실제 그림으로 꺼냄 // 이미지로 만들어 주겠다 // buffer 이용하는 것이 안정적이라 함
			
			// 랜더링된 QR코드 이미지를 실제 그림파일로 만들어낸다.
			ImageIO.write(buffredImage, "png", new File(realPath + qrCodeName + ".png") );
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public String setQrCodeCreate2(String realPath, QrCodeVO vo) {
		String qrCodeName = javaclassProvide.newNameCreate(2);
		String qrCodeImage = "";
		try {
			// QRCode 안의 한글 인코딩
			qrCodeName += vo.getMoveUrl();
			qrCodeImage = vo.getMoveUrl();
			qrCodeImage = new String(qrCodeImage.getBytes("UTF-8"), "ISO-8859-1"); // 한글을 넣기에 꼭 인코딩 해줘야 함 하나로 묶어서 씀
			
			// qr 코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeImage, BarcodeFormat.QR_CODE, 200, 200); // 점의 밀도 형식  // 바코드 만드는 형식과 똑같이 만듦(구글에서 제공 BarcodeFormat)  // 크기(폭, 높이)
			
			//MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();  // 기본컬러(글자색:검정,배경색:흰색) => 색을 바꾸려면 여기서 바꾸면 됨 // 점을 그림으로 바꾸는 class
			int qrCodeColor = 0xFF000000;
			int qrCodeBackColor = 0xFFFFFFFF;
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			BufferedImage buffredImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);  // 랜더링 처리한 qr코드 이미지를 실제 그림으로 꺼냄 // 이미지로 만들어 주겠다 // buffer 이용하는 것이 안정적이라 함
			
			// 랜더링된 QR코드 이미지를 실제 그림파일로 만들어낸다.
			ImageIO.write(buffredImage, "png", new File(realPath + qrCodeName + ".png") );
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public String setQrCodeCreate3(String realPath, QrCodeVO vo) {
		String qrCodeName = javaclassProvide.newNameCreate(2);
		String qrCodeImage = "";
		try {
			// QRCode 안의 한글 인코딩
			qrCodeName += vo.getMid() + "_" + vo.getMovieName() + "_" + vo.getMovieDate() + "_" + vo.getMovieTime().replace(":", "") + "_" + vo.getMovieAdult() + "_" + vo.getMovieChild();  // ':' 안먹음 시간 주의하기
			qrCodeImage = "구매자 ID : " + vo.getMid();
			qrCodeImage += "영화명 : " + vo.getMovieName() + "\n";
			qrCodeImage += "상영일자 : " + vo.getMovieDate() + "\n";
			qrCodeImage += "상영시간 : " + vo.getMovieTime() + "\n";
			qrCodeImage += "성인 : " + vo.getMovieAdult() + "\n";
			qrCodeImage += "소인 : " + vo.getMovieChild();  // => 메일로 보내기
			qrCodeImage = new String(qrCodeImage.getBytes("UTF-8"), "ISO-8859-1"); // 한글을 넣기에 꼭 인코딩 해줘야 함 하나로 묶어서 씀
			
			// qr 코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeImage, BarcodeFormat.QR_CODE, 200, 200); // 점의 밀도 형식  // 바코드 만드는 형식과 똑같이 만듦(구글에서 제공 BarcodeFormat)  // 크기(폭, 높이)
			
			//MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();  // 기본컬러(글자색:검정,배경색:흰색) => 색을 바꾸려면 여기서 바꾸면 됨 // 점을 그림으로 바꾸는 class
			int qrCodeColor = 0xFF000000;
			int qrCodeBackColor = 0xFFFFFFFF;
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			BufferedImage buffredImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);  // 랜더링 처리한 qr코드 이미지를 실제 그림으로 꺼냄 // 이미지로 만들어 주겠다 // buffer 이용하는 것이 안정적이라 함
			
			// 랜더링된 QR코드 이미지를 실제 그림파일로 만들어낸다.
			ImageIO.write(buffredImage, "png", new File(realPath + qrCodeName + ".png") );
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public String setQrCodeCreate4(String realPath, QrCodeVO vo) {
		String qrCodeName = javaclassProvide.newNameCreate(2);
		String qrCodeImage = "";
		try {
			String strToday = qrCodeName.substring(0,qrCodeName.length()-3);
			
			// QRCode 안의 한글 인코딩
			qrCodeName += vo.getMid() + "_" + vo.getMovieName() + "_" + vo.getMovieDate() + "_" + vo.getMovieTime().replace(":", "") + "_" + vo.getMovieAdult() + "_" + vo.getMovieChild();  // ':' 안먹음 시간 주의하기
			qrCodeImage = "구매자 ID : " + vo.getMid() + "\n";
			qrCodeImage += "영화명 : " + vo.getMovieName() + "\n";
			qrCodeImage += "상영일자 : " + vo.getMovieDate() + "\n";
			qrCodeImage += "상영시간 : " + vo.getMovieTime() + "\n";
			qrCodeImage += "성인 : " + vo.getMovieAdult() + "\n";
			qrCodeImage += "소인 : " + vo.getMovieChild();  // => 메일로 보내기
			qrCodeImage = new String(qrCodeImage.getBytes("UTF-8"), "ISO-8859-1");
			
			// qr 코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeImage, BarcodeFormat.QR_CODE, 200, 200); // 점의 밀도 형식  // 바코드 만드는 형식과 똑같이 만듦(구글에서 제공 BarcodeFormat)  // 크기(폭, 높이)
			
			//MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();  // 기본컬러(글자색:검정,배경색:흰색) => 색을 바꾸려면 여기서 바꾸면 됨 // 점을 그림으로 바꾸는 class
			int qrCodeColor = 0xFF000000;
			int qrCodeBackColor = 0xFFFFFFFF;
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			BufferedImage buffredImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);  // 랜더링 처리한 qr코드 이미지를 실제 그림으로 꺼냄 // 이미지로 만들어 주겠다 // buffer 이용하는 것이 안정적이라 함
			
			// 랜더링된 QR코드 이미지를 실제 그림파일로 만들어낸다.
			ImageIO.write(buffredImage, "png", new File(realPath + qrCodeName + ".png") );
			
			// QR코드 생성후, 생성된 정보를 DB에 저장시켜준다.
			vo.setPublishDate(strToday);
			vo.setQrCodeName(qrCodeName);
			studyDAO.setQrCodeCreate(vo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		return qrCodeName;
	}

	@Override
	public QrCodeVO getQrCodeSearch(String qrCode) {
		return studyDAO.getQrCodeSearch(qrCode);
	}

	@Override
	public String setThumbnailCreate(MultipartFile file) {  // 오버라이드 되어 있기 때문에 던지지 못하고 try~catch
		String res = "";
		try {
			System.out.println("getOriginalFilename : " + file.getOriginalFilename());
			String sFileName = javaclassProvide.newNameCreate(2) + "_" + file.getOriginalFilename();  // 만든이름__오리지널이름
			
			// 썸네일 파일이 저장될 경로설정
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
			File realFileName = new File(realPath + sFileName);  // 껍데기 객체 만들어 놓고 알맹이를 채움  // getBytes
			file.transferTo(realFileName);  // 이경로에 이파일로 주겠다  // 원본파일로 저장
			
			// 썸네일 이미지 생성 저장하기
			String thumbnailSaveName = realPath + "s_" + sFileName;
			File thumbnailFile = new File(thumbnailSaveName);
			
			int width = 160;  // 썸네일 크기 저장
			int height = 120;
			Thumbnailator.createThumbnail(realFileName,thumbnailFile,width,height);  // realFileName 원본파일, thumbnailFile 썸네일, 폭, 높이
			
			res = sFileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	@Override
	public List<ChartVO> getRecentlyVisitCount(int i) {
		return studyDAO.getRecentlyVisitCount(i);
	}

	@Override
	public List<TransactionVO> getTransactionList() {
		return studyDAO.getTransactionList();
	}

	@Override
	public int setTransactionUserInput(TransactionVO vo) {
		return studyDAO.setTransactionUserInput(vo);
	}
	
	@Override
	public List<TransactionVO> getTransactionList2() {
		return studyDAO.getTransactionList2();
	}

	@Override
	public void setTransactionUser1Input(TransactionVO vo) {
		studyDAO.setTransactionUser1Input(vo);
	}

	@Override
	public void setTransactionUser2Input(TransactionVO vo) {
		studyDAO.setTransactionUser2Input(vo);
	}

	@Transactional
	@Override
	public int setTransactionUserTotalInput(TransactionVO vo) {
		return studyDAO.setTransactionUserTotalInput(vo);
	}
	
	/*
	@Override
	public String getCurrencyRate(String receiveCountry) {
    Date today = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String strToday = sdf.format(today);
    String json = "";
    
		URL url;
		try {
			url = new URL("https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=9p1ZRWn6RsyGLKlXhdbYl1ZQtbs7KoMS&searchdate=2024-07-23&data=AP01");
			url = new URL("https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=9p1ZRWn6RsyGLKlXhdbYl1ZQtbs7KoMS&searchdate="+strToday+"&data=AP01");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDefaultUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			if ((output = br.readLine()) != null) {
				//System.out.println("output : " + output);
			  json += output;
			}
			conn.disconnect();
			
			// api로 담아온 자료는 json형식의 문자열이기에 json객체로 바꿔준후 각각의 자료를 변수에 담아서 사용(DB의 저장등..)할수 있게한다.
			System.out.println("json : " + json);
			
			
	    // (방법1) JSON 배열로 변환
	    JSONArray jsonArray = new JSONArray(json);
	
	    // 배열 내의 각 JSON 객체 처리
	    for (int i = 0; i < jsonArray.length(); i++) {
	      JSONObject jsonObject = jsonArray.getJSONObject(i);
	      int result = jsonObject.getInt("result");
	      String cur_nm = jsonObject.getString("cur_nm");
	      String ttb = jsonObject.getString("ttb");
	      String deal_bas_r = jsonObject.getString("deal_bas_r");
	
	      // 출력
	      System.out.println("result " + result);
	      System.out.println("cur_nm: " + cur_nm);
	      System.out.println("ttb: " + ttb);
	      System.out.println("deal_bas_r: " + deal_bas_r);
	      System.out.println();
	    }
	    
	    // (방법2) DTO(VO)를 이용하는 방법
			Gson gson = new Gson();
      // JSON 문자열을 List<Person> 객체로 변환
      Type type = new TypeToken<List<ExchangeRateVO>>() {}.getType();
      List<ExchangeRateVO> vos = gson.fromJson(json, type);

      // 리스트 내의 각 객체 처리
      for (ExchangeRateVO vo : vos) {
      	System.out.println("cur_unit: " + vo.getCur_unit());
				System.out.println("cur_nm: " + vo.getCur_nm());
				System.out.println("ttb: " + vo.getTtb());
				System.out.println("tts: " + vo.getTts());
				System.out.println("deal_bas_r: " + vo.getDeal_bas_r());
				System.out.println();
      }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
    return json;
	}
  */
	
	private String authkey = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=9p1ZRWn6RsyGLKlXhdbYl1ZQtbs7KoMS&data=AP01&searchdate=";
	
	@Override
	public List<ExchangeRateVO> getExchangeRateUnit(String searchdate) {
		List<ExchangeRateVO> unitVos = new ArrayList<ExchangeRateVO>();
//		Date today = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String strToday = sdf.format(today);
//		System.out.println("searchdate : " + searchdate);
		String json = "";
		URL url;
		try {
			url = new URL(authkey+searchdate);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDefaultUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			if ((output = br.readLine()) != null) {
				json += output;
			}
			conn.disconnect();
			//System.out.println("json : " + json);
			// (방법2) DTO(VO)를 이용하는 방법
			Gson gson = new Gson();
			// JSON 문자열을 List<Person> 객체로 변환
			Type type = new TypeToken<List<ExchangeRateVO>>() {}.getType();
			List<ExchangeRateVO> vos = gson.fromJson(json, type);
			
			// 리스트 내의 각 객체 처리
			for (ExchangeRateVO vo : vos) {
//      	System.out.println("cur_unit: " + vo.getCur_unit());
//				System.out.println("cur_nm: " + vo.getCur_nm());
//				System.out.println();
				
				ExchangeRateVO resVo = new ExchangeRateVO();
				resVo.setCur_nm(vo.getCur_nm());
				resVo.setCur_unit(vo.getCur_unit());
				//resVo.setTts(vo.getTts());
				unitVos.add(resVo);
			}
			//System.out.println("unitVos :" + unitVos);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return unitVos;
	}
	
	@Override
	public String getCurrencyRate(String receiveCountry, String searchdate) {
//		if(searchdate.equals("")) {
//	    Date today = new Date();
//	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	    String strToday = sdf.format(today);
//		}
    String json = "";
    String tts = "";
    
		URL url;
		try {
			url = new URL(authkey+searchdate);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDefaultUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			if ((output = br.readLine()) != null) {
			  json += output;
			}
			conn.disconnect();
			
			// api로 담아온 자료는 json형식의 문자열이기에 json객체로 바꿔준후 각각의 자료를 변수에 담아서 사용(DB의 저장등..)할수 있게한다.
			//System.out.println("json : " + json);
			
	    // (방법1) JSON 배열로 변환
	    JSONArray jsonArray = new JSONArray(json);
	
	    // 배열 내의 각 JSON 객체 처리
	    for (int i = 0; i < jsonArray.length(); i++) {
	      JSONObject jsonObject = jsonArray.getJSONObject(i);
//	      int result = jsonObject.getInt("result");
//	      String cur_nm = jsonObject.getString("cur_nm");
	      String cur_unit = jsonObject.getString("cur_unit");
	      tts = jsonObject.getString("tts");
//	      String deal_bas_r = jsonObject.getString("deal_bas_r");
	
	      // 출력
//	      System.out.println("result " + result);
//	      System.out.println("cur_nm: " + cur_nm);
//	      System.out.println("ttb: " + ttb);
//	      System.out.println("deal_bas_r: " + deal_bas_r);
//	      System.out.println();
	      
	      if(cur_unit.equals(receiveCountry)) {
	      	//System.out.println("tts : " + tts);
	      	break; 
	      }
	    }
	    
	    // (방법2) DTO(VO)를 이용하는 방법
//			Gson gson = new Gson();
      // JSON 문자열을 List<Person> 객체로 변환
//      Type type = new TypeToken<List<ExchangeRateVO>>() {}.getType();
//      List<ExchangeRateVO> vos = gson.fromJson(json, type);

      // 리스트 내의 각 객체 처리
//      for (ExchangeRateVO vo : vos) {
//      	System.out.println("cur_unit: " + vo.getCur_unit());
//				System.out.println("cur_nm: " + vo.getCur_nm());
//				System.out.println("ttb: " + vo.getTtb());
//				System.out.println("tts: " + vo.getTts());
//				System.out.println("deal_bas_r: " + vo.getDeal_bas_r());
//				System.out.println();
//      }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
    return json + "@" + tts;
	}

	@Override
	public String getCurrencyRateCompute(String receiveCountry, String sendAmount, String searchdate) {
//    Date today = new Date();
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    String strToday = sdf.format(today);
//		System.out.println("searchdate..: " + searchdate);
    String json = "";
    String tts = "";
		URL url;
		try {
			url = new URL(authkey+searchdate);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDefaultUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			if ((output = br.readLine()) != null) {
			  json += output;
			}
			conn.disconnect();
			
	    JSONArray jsonArray = new JSONArray(json);
	
	    for (int i = 0; i < jsonArray.length(); i++) {
	      JSONObject jsonObject = jsonArray.getJSONObject(i);
	      String cur_unit = jsonObject.getString("cur_unit");
	      tts = jsonObject.getString("tts");
	      
	      if(cur_unit.equals(receiveCountry)) break; 
	    }
	    
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("tts : " + tts);
		//System.out.println("receiveCountry : " + receiveCountry);
		// 환율계산공식 = (송금액 / 그나라 환율)  , 단, 100원단위로 환율을 제공하는 나라는 ((송금액*100)/그나라환율) 로 계산한다.
		double doubleTts = 0.0;
		if(receiveCountry.indexOf("(100)") != -1)	doubleTts = (100.0 / Double.parseDouble(tts.replaceAll(",", ""))) * Double.parseDouble(sendAmount);
		else doubleTts = Double.parseDouble(sendAmount) / Double.parseDouble(tts.replaceAll(",", ""));
		//System.out.println("doubleTts : " + doubleTts);
		return doubleTts + "";
	}
	// 전국 자전거 대여소 현황 처리
	@Override
	public List<BicycleVO> getBicycleData(int page) {
		List<BicycleVO> vos = new ArrayList<BicycleVO>();
		StringBuilder sb = new StringBuilder();	// JSON데이터로 들어오는 값들을 vos객체로 담아주기위한 준비
		try {
	    StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/tn_pubr_public_bcycl_lend_api"); /*URL*/
	    //urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=서비스키"); /*Service Key*/
	    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=7WMGwpEENfXvFnxY1efwZ4263gPHczyuehE7RyufhGeO4SZPOKxDisyWglB%2BjylPIXZJu8Xxs8BCWVbLqr9PdA%3D%3D"); /*Service Key*/
	    //urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("2", "UTF-8")); /*페이지 번호*/
	    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + page); /*페이지 번호*/
	    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("300", "UTF-8")); /*한 페이지 결과 수*/
	    urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML/JSON 여부*/
//	    urlBuilder.append("&" + URLEncoder.encode("bcyclLendNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*자전거대여소명*/
//	    urlBuilder.append("&" + URLEncoder.encode("bcyclLendSe","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*자전거대여소구분*/
//	    urlBuilder.append("&" + URLEncoder.encode("rdnmadr","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*소재지도로명주소*/
//	    urlBuilder.append("&" + URLEncoder.encode("lnmadr","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*소재지지번주소*/
//	    urlBuilder.append("&" + URLEncoder.encode("latitude","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*위도*/
//	    urlBuilder.append("&" + URLEncoder.encode("longitude","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*경도*/
//	    urlBuilder.append("&" + URLEncoder.encode("operOpenHm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*운영시작시각*/
//	    urlBuilder.append("&" + URLEncoder.encode("operCloseHm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*운영종료시각*/
//	    urlBuilder.append("&" + URLEncoder.encode("rstde","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*휴무일*/
//	    urlBuilder.append("&" + URLEncoder.encode("chrgeSe","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*요금구분*/
//	    urlBuilder.append("&" + URLEncoder.encode("bcyclUseCharge","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*자전거이용요금*/
//	    urlBuilder.append("&" + URLEncoder.encode("bcyclHoldCharge","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*자전거보유대수*/
//	    urlBuilder.append("&" + URLEncoder.encode("holderCo","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*거치대수*/
//	    urlBuilder.append("&" + URLEncoder.encode("airInjectorYn","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공기주입기비치여부*/
//	    urlBuilder.append("&" + URLEncoder.encode("airInjectorType","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*공기주입기유형*/
//	    urlBuilder.append("&" + URLEncoder.encode("repairStandYn","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*수리대설치여부*/
//	    urlBuilder.append("&" + URLEncoder.encode("phoneNumber","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*관리기관전화번호*/
//	    urlBuilder.append("&" + URLEncoder.encode("institutionNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*관리기관명*/
//	    urlBuilder.append("&" + URLEncoder.encode("referenceDate","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*데이터기준일자*/
//	    urlBuilder.append("&" + URLEncoder.encode("instt_code","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*제공기관코드*/
	    URL url = new URL(urlBuilder.toString());
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-type", "application/json");
	    //System.out.println("Response code: " + conn.getResponseCode());
	    BufferedReader rd;
	    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    } else {
	        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	    }
	    
	    String line;
	    while ((line = rd.readLine()) != null) {
	        sb.append(line);
	    }
	    rd.close();
	    conn.disconnect();
	    //System.out.println("응답 : " + sb.toString());
	    
	    // 배열 내의 각 JSON 객체 처리
	    // (방법2) vo객체 활용
			
			String str = sb.substring(sb.indexOf("["));		// 실제 JSON형식의 값만 추출([])
			
	    JSONArray jsonArray = new JSONArray(str);			// 넘어오는 값들을 JSON배열로 담기위해 선언
	    for (int i = 0; i < jsonArray.length(); i++) {// 배열안의 개수만큼 vos객체에 담아준다.
	      JSONObject jsonObject = jsonArray.getJSONObject(i);	// json자료를 개별객체로 변환
	      BicycleVO vo = new BicycleVO();
	      vo.setBcyclLendNm(jsonObject.getString("bcyclLendNm"));
	      vo.setBcyclLendSe(jsonObject.getString("bcyclLendSe"));
	      vo.setRdnmadr(jsonObject.getString("rdnmadr"));
	      vo.setLnmadr(jsonObject.getString("lnmadr"));
	      vo.setLatitude(jsonObject.getString("latitude"));
	      vo.setLongitude(jsonObject.getString("longitude"));
	      vo.setOperOpenHm(jsonObject.getString("operOpenHm"));
	      vo.setOperCloseHm(jsonObject.getString("operCloseHm"));
	      vo.setRstde(jsonObject.getString("rstde"));
	      vo.setChrgeSe(jsonObject.getString("chrgeSe"));
	      vo.setBcyclUseCharge(jsonObject.getString("bcyclUseCharge"));
	      vo.setBcyclHoldCharge(jsonObject.getString("bcyclHoldCharge"));
	      vo.setHolderCo(jsonObject.getString("holderCo"));
	      vo.setAirInjectorYn(jsonObject.getString("airInjectorYn"));
	      vo.setAirInjectorType(jsonObject.getString("airInjectorType"));
	      vo.setRepairStandYn(jsonObject.getString("repairStandYn"));
	      vo.setPhoneNumber(jsonObject.getString("phoneNumber"));
	      vo.setInstitutionNm(jsonObject.getString("institutionNm"));
	      vo.setReferenceDate(jsonObject.getString("referenceDate"));
	      vo.setInsttCode(jsonObject.getString("insttCode"));
	      //if(vo.getStationName() == null) vo.setStationName("등록정보없음");
	      vos.add(vo);
	    }
		} catch (UnsupportedEncodingException e) {e.printStackTrace();
		} catch (MalformedURLException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();} catch (ParseException e) {e.printStackTrace();}
		return vos;
	}

	@Override
	public List<BicycleVO> getBicycleData2() {
		List<BicycleVO> vos = new ArrayList<BicycleVO>();
		StringBuilder sb = new StringBuilder();	// JSON데이터로 들어오는 값들을 vos객체로 담아주기위한 준비
		try {
			// 샘플URL http://openapi.seoul.go.kr:8088/인증키/json/bikeList/1/5/
			StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
			//urlBuilder.append("/" +  URLEncoder.encode("sample","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
			urlBuilder.append("/" +  URLEncoder.encode("6c4a54744d636a7332354d6e6a6164","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
			urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
			urlBuilder.append("/" + URLEncoder.encode("bikeList","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
			urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
			urlBuilder.append("/" + URLEncoder.encode("200","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
			// 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
			
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			//System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
			BufferedReader rd;
	
			// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
					rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			String line;
			while ((line = rd.readLine()) != null) {
					sb.append(line);
			}
			rd.close();
			conn.disconnect();
			//System.out.println(sb.toString());
			
	    // 배열 내의 각 JSON 객체 처리
	    // (방법2) vo객체 활용
			
			String str = sb.substring(sb.indexOf("["));		// 실제 JSON형식의 값만 추출([])
			
	    JSONArray jsonArray = new JSONArray(str);			// 넘어오는 값들을 JSON배열로 담기위해 선언
	    for (int i = 0; i < jsonArray.length(); i++) {// 배열안의 개수만큼 vos객체에 담아준다.
	      JSONObject jsonObject = jsonArray.getJSONObject(i);	// json자료를 개별객체로 변환
	      BicycleVO vo = new BicycleVO();
	      vo.setRackTotCnt(jsonObject.getString("rackTotCnt"));
	      vo.setStationId(jsonObject.getString("stationName"));
	      vo.setParkingBikeTotCnt(jsonObject.getString("parkingBikeTotCnt"));
	      vo.setShared(jsonObject.getString("shared"));
	      vo.setStationLatitude(jsonObject.getString("stationLatitude"));
	      vo.setStationLongitude(jsonObject.getString("stationLongitude"));
	      vo.setStationId(jsonObject.getString("stationId"));
	      if(vo.getStationName() == null) vo.setStationName("등록정보없음");
	      vos.add(vo);
	    }
	    //System.out.println("vos: " + vos);
		} catch (UnsupportedEncodingException e) {e.printStackTrace();
		} catch (MalformedURLException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();
		} catch (ParseException e) {e.printStackTrace();}
		return vos;
	}

	@Override
	public List<TagoExpressVO> getTagoExpressData(int page) {
		List<TagoExpressVO> vos = new ArrayList<TagoExpressVO>();
		StringBuilder sb = new StringBuilder();	// JSON데이터로 들어오는 값들을 vos객체로 담아주기위한 준비
		try {
			 StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/ExpBusInfoService/getStrtpntAlocFndExpbusInfo"); /*URL*/
       //urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=서비스키"); /*Service Key*/
       urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=7WMGwpEENfXvFnxY1efwZ4263gPHczyuehE7RyufhGeO4SZPOKxDisyWglB%2BjylPIXZJu8Xxs8BCWVbLqr9PdA%3D%3D"); /*Service Key*/
       //urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
       urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + page); /*페이지번호*/
       urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
       urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
       urlBuilder.append("&" + URLEncoder.encode("depTerminalId","UTF-8") + "=" + URLEncoder.encode("NAEK010", "UTF-8")); /*출발터미널ID*/
       urlBuilder.append("&" + URLEncoder.encode("arrTerminalId","UTF-8") + "=" + URLEncoder.encode("NAEK300", "UTF-8")); /*도착터미널ID*/
       urlBuilder.append("&" + URLEncoder.encode("depPlandTime","UTF-8") + "=" + URLEncoder.encode("20230401", "UTF-8")); /*출발일(YYYYMMDD)*/
       urlBuilder.append("&" + URLEncoder.encode("busGradeId","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*버스등급*/
       URL url = new URL(urlBuilder.toString());
       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
       conn.setRequestMethod("GET");
       conn.setRequestProperty("Content-type", "application/json");
       System.out.println("Response code: " + conn.getResponseCode());
       BufferedReader rd;
       if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
           rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
       } else {
           rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
       }
       String line;
       while ((line = rd.readLine()) != null) {
           sb.append(line);
       }
       rd.close();
       conn.disconnect();
       System.out.println(sb.toString());
	    //System.out.println("응답 : " + sb.toString());
	    
	    // 배열 내의 각 JSON 객체 처리
	    // (방법2) vo객체 활용
			
			String str = sb.substring(sb.indexOf("["));		// 실제 JSON형식의 값만 추출([])
			
	    JSONArray jsonArray = new JSONArray(str);			// 넘어오는 값들을 JSON배열로 담기위해 선언
	    for (int i = 0; i < jsonArray.length(); i++) {// 배열안의 개수만큼 vos객체에 담아준다.
	      JSONObject jsonObject = jsonArray.getJSONObject(i);	// json자료를 개별객체로 변환
	      TagoExpressVO vo = new TagoExpressVO();
	      vo.setRouteId(jsonObject.getString("routeId"));
	      vo.setGradeNm(jsonObject.getString("gradeNm"));
	      vo.setDepPlandTime(jsonObject.getInt("depPlandTime"));
	      vo.setArrPlandTime(jsonObject.getInt("arrPlandTime"));
	      vo.setDepPlaceNm(jsonObject.getString("depPlaceNm"));
	      vo.setArrPlaceNm(jsonObject.getString("arrPlaceNm"));
	      vo.setCharge(jsonObject.getInt("charge"));
	      //if(vo.getStationName() == null) vo.setStationName("등록정보없음");
	      vos.add(vo);
	    }
		} catch (UnsupportedEncodingException e) {e.printStackTrace();
		} catch (MalformedURLException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();} catch (ParseException e) {e.printStackTrace();}
		return vos;
	}

	@Override
	public void getCalendar() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		// 오늘 날짜 저장시켜둔다.(calToday변수, 년(toYear), 월(toMonth), 일(toDay))
		Calendar calToday = Calendar.getInstance();
		int toYear = calToday.get(Calendar.YEAR);
		int toMonth = calToday.get(Calendar.MONTH);
		int toDay = calToday.get(Calendar.DATE);
				
		// 화면에 보여줄 해당 '년(yy)/월(mm)'을 셋팅하는 부분(처음에는 오늘 년도와 월을 가져오지만, '이전/다음'버튼 클릭하면 해당 년과 월을 가져오도록 한다.
		Calendar calView = Calendar.getInstance();
		int yy = request.getParameter("yy")==null ? calView.get(Calendar.YEAR) : Integer.parseInt(request.getParameter("yy"));
	  int mm = request.getParameter("mm")==null ? calView.get(Calendar.MONTH) : Integer.parseInt(request.getParameter("mm"));
	  
	  if(mm < 0) { // 1월에서 전월 버튼을 클릭시에 실행
	  	yy--;
	  	mm = 11;
	  }
	  if(mm > 11) { // 12월에서 다음월 버튼을 클릭시에 실행
	  	yy++;
	  	mm = 0;
	  }
	  calView.set(yy, mm, 1);		// 현재 '년/월'의 1일을 달력의 날짜로 셋팅한다.
	  
	  int startWeek = calView.get(Calendar.DAY_OF_WEEK);  						// 해당 '년/월'의 1일에 해당하는 요일값을 숫자로 가져온다.
	  int lastDay = calView.getActualMaximum(Calendar.DAY_OF_MONTH);  // 해당월의 마지막일자(getActualMaxximum메소드사용)를 구한다.
	  
	  // 화면에 보여줄 년월기준 전년도/다음년도를 구하기 위한 부분
	  int prevYear = yy;  			// 전년도
	  int prevMonth = (mm) - 1; // 이전월
	  int nextYear = yy;  			// 다음년도
	  int nextMonth = (mm) + 1; // 다음월
	  
	  if(prevMonth == -1) {  // 1월에서 전월 버튼을 클릭시에 실행..
	  	prevYear--;
	  	prevMonth = 11;
	  }
	  
	  if(nextMonth == 12) {  // 12월에서 다음월 버튼을 클릭시에 실행..
	  	nextYear++;
	  	nextMonth = 0;
	  }
	  
	  // 현재달력에서 앞쪽의 빈공간은 '이전달력'을 보여주고, 뒷쪽의 남은공간은 '다음달력'을 보여주기위한 처리부분(아래 6줄)
	  Calendar calPre = Calendar.getInstance(); // 이전달력
	  calPre.set(prevYear, prevMonth, 1);  			// 이전 달력 셋팅
	  int preLastDay = calPre.getActualMaximum(Calendar.DAY_OF_MONTH);  // 해당월의 마지막일자를 구한다.
	  
	  Calendar calNext = Calendar.getInstance();// 다음달력
	  calNext.set(nextYear, nextMonth, 1);  		// 다음 달력 셋팅
	  int nextStartWeek = calNext.get(Calendar.DAY_OF_WEEK);  // 다음달의 1일에 해당하는 요일값을 가져온다.
	  
	  /* ---------  아래는  앞에서 처리된 값들을 모두 request객체에 담는다.  -----------------  */
	  
	  // 오늘기준 달력...
	  request.setAttribute("toYear", toYear);
	  request.setAttribute("toMonth", toMonth);
	  request.setAttribute("toDay", toDay);
	  
	  // 화면에 보여줄 해당 달력...
	  request.setAttribute("yy", yy);
	  request.setAttribute("mm", mm);
	  request.setAttribute("startWeek", startWeek);
	  request.setAttribute("lastDay", lastDay);
	  
	  // 화면에 보여줄 해당 달력 기준의 전년도, 전월, 다음년도, 다음월 ...
	  request.setAttribute("prevYear", prevYear);
		request.setAttribute("prevMonth", prevMonth);
		request.setAttribute("nextYear", nextYear);
		request.setAttribute("nextMonth", nextMonth);
		
		// 현재 달력의 '앞/뒤' 빈공간을 채울, 이전달의 뒷부분과 다음달의 앞부분을 보여주기위해 넘겨주는 변수
		request.setAttribute("preLastDay", preLastDay);				// 이전달의 마지막일자를 기억하고 있는 변수
		request.setAttribute("nextStartWeek", nextStartWeek);	// 다음달의 1일에 해당하는 요일을 기억하고있는 변수
	}
	
}