package com.spring.javaclassS.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.dao.StudyDAO;
import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.UserVO;

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

}