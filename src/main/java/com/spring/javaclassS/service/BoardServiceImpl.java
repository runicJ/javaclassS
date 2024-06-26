package com.spring.javaclassS.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.javaclassS.dao.BoardDAO;
import com.spring.javaclassS.vo.BoardReply2VO;
import com.spring.javaclassS.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardDAO boardDAO;

	@Override
	public ArrayList<BoardVO> getBoardList() {
		return boardDAO.getBoardList();
	}

	@Override
	public int setBoardInput(BoardVO vo) {
		return boardDAO.setBoardInput(vo);
	}

	@Override
	public BoardVO getBoardContent(int idx) {
		return boardDAO.getBoardContent(idx);
	}

	@Override
	public ArrayList<BoardVO> getBoardList(int startIndexNo, int pageSize) {
		return boardDAO.getBoardList(startIndexNo, pageSize);
	}

	@Override
	public void setReadNumPlus(int idx) {
		boardDAO.setReadNumPlus(idx);
	}

	@Override
	public BoardVO getPreNexSearch(int idx, String str) {
		return boardDAO.getPreNexSearch(idx, str);
	}

	// content에 이미지가 있다면 이미지를 'ckeditor'폴더에서 'board'폴더로 복사처리한다.
	@Override
	public void imgCheck(String content) {
		//        0         1         2         3
		//        0123456789012345678901234567890123456789
		//<p><img src="/javaclassS/data/ckeditor/240626093516_dadaepo-beach-2826172_1920.jpg" style="height:467px; width:700px" /></p>
		//<p><img src="/javaclassS/data/board/240626093516_dadaepo-beach-2826172_1920.jpg" style="height:467px; width:700px" /></p>
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();  // ServletRequestAttributes로 형변환 해줘야 .getRequest() 나옴  // realPath 사용하려고
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");  // ckeditor에 있는 것을 data로 가져가는 것이 기 때문에 경로르르 data/까지 줌
		
		int position = 31;  // 그림파일의 시작위치는 달라질 수 있음
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + "ckeditor/" + imgFile;
			String copyFilePath = realPath + "board/" + imgFile;
			
			fileCopyCheck(origFilePath, copyFilePath);  // ckeditor 폴더의 그림파일을 board 폴더 위치로 복사처리 하는 메소드. => 따로 빼서 공통으로 사용하도록 메소드 처리
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;  // -1 자료가 없는 것
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	// 파일 복사처리(이런건 공통폴더에 넣어놓고 쓰자)
	private void fileCopyCheck(String origFilePath, String copyFilePath) {  // 정상처리 됐는지 보려면 int나 boolean으로 // 지금은 그림이 있을때만 쓰기에 void로 타입 지정
		try {
			FileInputStream fis = new FileInputStream(new File(origFilePath));  // 껍데기가 아닌 실제자료 // 껍데기 서버에서 서버로 만들어줌
			FileOutputStream fos = new FileOutputStream(new File(copyFilePath));
			
			byte[] b = new byte[2048];  // 2048(2K)씩 읽어서 넣어서 cnt에 담음
			int cnt = 0;
			while((cnt = fis.read(b)) != -1) {  // 2K씩 읽은 것을 cnt에 담아
				fos.write(b, 0, cnt);  // b에 들어가 있는, cnt 0부터
			}
			fos.flush();
			fos.close();  // 앞부터 닫아줌
			fis.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void imgBackup(String content) {  // imgCheck 작업과 반대
		//        0         1         2         3
		//        0123456789012345678901234567890123456789
		//<p><img src="/javaclassS/data/board/240626093516_dadaepo-beach-2826172_1920.jpg" style="height:467px; width:700px" /></p>  // 원본 그림이 바뀜
		//<p><img src="/javaclassS/data/ckeditor/240626093516_dadaepo-beach-2826172_1920.jpg" style="height:467px; width:700px" /></p>
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();  // ServletRequestAttributes로 형변환 해줘야 .getRequest() 나옴  // realPath 사용하려고
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");  // ckeditor에 있는 것을 data로 가져가는 것이 기 때문에 경로르르 data/까지 줌
		
		int position = 28;  // 그림파일의 시작위치는 달라질 수 있음
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {  // 여러장 있을 수 있으니 반복문
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + "board/" + imgFile;  // 그림파일의 위치가 바뀐
			String copyFilePath = realPath + "ckeditor/" + imgFile;
			
			fileCopyCheck(origFilePath, copyFilePath);  // ckeditor 폴더의 그림파일을 board 폴더 위치로 복사처리 하는 메소드.
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	@Override
	public void imgDelete(String content) {  // content만 넘기지 않고, 삭제할 경로까지 같이 넘기면 영구적으로 공통적으로 사용할 수 있음
		//        0         1         2         3
		//        0123456789012345678901234567890123456789
		//<p><img src="/javaclassS/data/board/240626093516_dadaepo-beach-2826172_1920.jpg" style="height:467px; width:700px" /></p>  // board 지우고
		//<p><img src="/javaclassS/data/ckeditor/240626093516_dadaepo-beach-2826172_1920.jpg" style="height:467px; width:700px" /></p>
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();  // ServletRequestAttributes로 형변환 해줘야 .getRequest() 나옴  // realPath 사용하려고
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");  // ckeditor에 있는 것을 data로 가져가는 것이 기 때문에 경로르르 data/까지 줌
		
		int position = 28;  // 그림파일의 시작위치는 달라질 수 있음
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {  // 여러장 있을 수 있으니 반복문
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + "board/" + imgFile;
			
			fileDelete(origFilePath);  // board폴더의 폴더의 그림파일을 삭제한다.
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	// 서버에 존재하는 파일 삭제처리
	private void fileDelete(String origFilePath) {
		File delFile = new File(origFilePath);  // 앞에 넘어온 경로로 만들어줌
		if(delFile.exists()) delFile.delete();
	}

	@Override
	public int setBoardUpdate(BoardVO vo) {
		return boardDAO.setBoardUpdate(vo);
	}

	@Override
	public int setBoardDelete(int idx) {
		return boardDAO.setBoardDelete(idx);
	}

	@Override
	public BoardReply2VO getBoardParentReplyCheck(int boardIdx) {
		return boardDAO.getBoardParentReplyCheck(boardIdx);
	}

	@Override
	public int setBoardReplyInput(BoardReply2VO replyVO) {
		return boardDAO.setBoardReplyInput(replyVO);
	}

	@Override
	public List<BoardReply2VO> getBoardReply(int idx) {
		return boardDAO.getBoardReply(idx);
	}

	@Override
	public void setReplyOrderUpdate(int boardIdx, int re_order) {
		boardDAO.setReplyOrderUpdate(boardIdx, re_order);
	}

	@Override
	public List<BoardVO> getBoardSearchList(int startIndexNo, int pageSize, String search, String searchString) {
		return boardDAO.getBoardSearchList(startIndexNo, pageSize, search, searchString);
	}
	
}
