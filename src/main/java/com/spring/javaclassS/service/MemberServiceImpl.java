package com.spring.javaclassS.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.dao.MemberDAO;
import com.spring.javaclassS.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	JavaclassProvide javaclassProvide;

	@Override
	public MemberVO getMemberIdCheck(String mid) {
		return memberDAO.getMemberIdCheck(mid);
	}

	@Override
	public MemberVO getMemberNickCheck(String nickName) {
		return memberDAO.getMemberNickCheck(nickName);
	}

	@Override
	public int setMemberJoinOk(MemberVO vo) {
		return memberDAO.setMemberJoinOk(vo);
	}

	@Override
	public void setMemberPasswordUpdate(String mid, String pwd) {
		memberDAO.setMemberPasswordUpdate(mid, pwd);
	}

	@Override
	public void setMemberInforUpdate(String mid, int point) {
		memberDAO.setMemberInforUpdate(mid, point);
	}

	@Override
	public int setPwdChangeOk(String mid, String pwd) {
		return memberDAO.setPwdChangeOk(mid, pwd);
	}

	@Override
	public String fileUpload(MultipartFile fName, String mid) {
		// 파일이름 중복처리를 위해 UUID객체 활용
		UUID uid = UUID.randomUUID();
		String oFileName = fName.getOriginalFilename();
		String sFileName = mid + "_" + uid.toString().substring(0,8) + "_" + oFileName;
		
		// 서버에 파일 올리기
		try {
			javaclassProvide.writeFile(fName, sFileName, "member");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sFileName;
	}

	@Override
	public ArrayList<MemberVO> getMemberList(int level) {
		return memberDAO.getMemberList(level);
	}

	@Override
	public int setMemberUpdateOk(String currentPhoto, MultipartFile fName, MemberVO vo, HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/member/");
		
		// 파일이름 중복처리를 위해 UUID객체 활용
        UUID uid = UUID.randomUUID();
        String oFileName = fName.getOriginalFilename();
        String sFileName = vo.getMid() + "_" + uid.toString().substring(0, 8) + "_" + oFileName;
		
		// 서버에 파일 올리기
        try {
            if (fName != null && !fName.isEmpty()) {
                javaclassProvide.writeFile(fName, sFileName, "member");
                vo.setPhoto(sFileName);

                File oldFile = new File(realPath + currentPhoto);
                
                if (oldFile.exists() && !currentPhoto.equals("noImage.jpg")) {
                    oldFile.delete();
                }
            } 
            else {
                vo.setPhoto(currentPhoto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return memberDAO.setMemberUpdateOk(vo);
	}

	@Override
	public int setMemberDelete(String mid) {
		return memberDAO.setMemberDelete(mid);
	}

}
