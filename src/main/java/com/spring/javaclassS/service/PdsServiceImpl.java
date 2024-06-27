package com.spring.javaclassS.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.dao.PdsDAO;
import com.spring.javaclassS.vo.PdsVO;

@Service  // component로 써도 ㄱㅊ
public class PdsServiceImpl implements PdsService {

	@Autowired
	PdsDAO pdsDAO;

	@Autowired
	JavaclassProvide javaclassProvide;
	
	@Override
	public int setPdsUpload(MultipartHttpServletRequest mFile, PdsVO vo) {
		// 파일 업로드
		try {
			List<MultipartFile> fileList = mFile.getFiles("file");  // 여러개가 들어오는 걸 한개 타입으로 받아내야 하는 것 MultipartFile 제너릭으로 사용  // jsp에서 값 보낼때 mFile로 보내지 않았음 fName으로 여러개 보냄
			String oFileNames = "";
			String sFileNames = "";
			int fileSizes = 0;
			
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				String sFileName = javaclassProvide.saveFileName(oFileName);  // 서버에 저장하는 saveFileName
			
				javaclassProvide.writeFile(file, sFileName, "pds");
				
				oFileNames += oFileName + "/";
				sFileNames += sFileName + "/";
				fileSizes += file.getSize();
			}
			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
			sFileNames = sFileNames.substring(0, sFileNames.length()-1);  // 여기까지 파일 업로드 끝났으면 db저장
			
			vo.setFName(oFileNames);
			vo.setFSName(sFileNames);
			vo.setFSize(fileSizes);
		} catch (IOException e) {e.printStackTrace();}
		
		// 파일업로드 작업완료후 모든자료의 정보를 DB에 담아준다.
		return pdsDAO.setPdsUpload(vo);
	}

	@Override
	public List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part) {
		return pdsDAO.getPdsList(startIndexNo, pageSize, part);
	}

	@Override
	public String setPdsDownNum(int idx) {
		return pdsDAO.setPdsDownNum(idx);
	}


	@Override
	public int setPdsDelete(int idx, String fSName, HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		String[] fSNames = fSName.split("/");
		
		// 서버에 저장된 실제 파일을 삭제처리
		for(int i=0; i<fSNames.length; i++) {
			new File(realPath + fSNames[i]).delete();
		}
		
		return pdsDAO.setPdsDelete(idx);
	}

	@Override
	public PdsVO getPdsContent(int idx) {
		return pdsDAO.getPdsContent(idx);
	}
	
}
