package com.spring.javaclassS.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaclassS.dao.DbTestDAO;
import com.spring.javaclassS.dao.StudyDAO;
import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.UserVO;

@Service
public class StudyServiceImpl implements StudyService {

	@Autowired  // db값 얘가 안가져 오고 dao 시킴
	StudyDAO studyDAO;
	
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
			strArray[9] = "동대문국";
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
			strArray[7] = "청양시";
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
			vos.add("영등포구");
			vos.add("마포구");
			vos.add("동대문국");
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
			vos.add("청양시");
			vos.add("계룡시");
			vos.add("예산군");
		}
		
		return vos;
	}

    HashMap<String, UserVO> userMidMap = new HashMap<>();
    
    @Override
    public HashMap<String, UserVO> getUserMidMap() {
        ArrayList<UserVO> users = studyDAO.getUserMidMap();
        for (UserVO user : users) {
            userMidMap.put(user.getMid(), user);
        }
        return userMidMap;
    }

    @Override
    public UserVO getUserMidInfo(String mid) {
        return userMidMap.get(mid);
    }

  	@Override
  	public ArrayList<String> getDbtestMidList() {
  		return studyDAO.getDbtestMidList();
  	}
    
    @Override
    public UserVO getUserIdCheck(String mid) {
    	// TODO Auto-generated method stub
    	return null;
    }

  	@Override
  	public ArrayList<String> getDbtestAddressList() {
  		return studyDAO.getDbtestAddressList();
  	}

  	@Override
  	public ArrayList<UserVO> getUserAddressCheck(String address) {
  		return studyDAO.getUserAddressCheck(address);
  	}

	@Override
	public UserVO getUserMidSearch(String mid) {
		return studyDAO.getUserMidSearch(mid);
	}

	@Override
	public ArrayList<UserVO> getUserMidList(String mid) {
		return studyDAO.getUserMidList(mid);
	}

	@Override
	public void setSaveCrimeData(CrimeVO vo) {
		studyDAO.setSaveCrimeData(vo);
	}
    
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
}