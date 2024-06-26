package com.spring.javaclassS.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaclassS.pagination.PageProcess;
import com.spring.javaclassS.service.BoardService;
import com.spring.javaclassS.vo.BoardReply2VO;
import com.spring.javaclassS.vo.BoardVO;
import com.spring.javaclassS.vo.PageVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	PageProcess pageProcess;
	
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String boardListGet(Model model,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "board", "", "");
		
		ArrayList<BoardVO> vos = boardService.getBoardList(pageVO.getStartIndexNo(), pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		
		return "board/boardList";
	}
	
	@RequestMapping(value = "/boardInput", method = RequestMethod.GET)
	public String boardInputGet(Model model) {
		return "board/boardInput";
	}
	
	@RequestMapping(value = "/boardInput", method = RequestMethod.POST)
	public String boardInputPost(BoardVO vo) {
		// 1.만약 cotent에 이미지가 저장되어 있다면, 저장된 이미지만 골라서 board 폴더에 따로 보관시켜준다.('/data/ckeditor' 폴더에서 '/data/board' 폴더로 복사처리)  // content에 이미지가 저장되어 있는지(src에 /가 있는지 확인), 폴더이름을 board로 바꿔준다.
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgCheck(vo.getContent());
		
		// 2.이미지 작업(복사작업)을 모두 마치면, ckeditor 폴더 경로를 board 폴더 경로로 변경처리한다.
		vo.setContent(vo.getContent().replace("/data/ckeditor","/data/board/"));  // 서버에서 서버로 가는 것이기 때문에 inputStream과 outputStream 둘다 필요함
		
		// 3.content 안의 그림에 대한 정리와 내용 정리가 끝나면 변경된 내용을 vo에 담은 후 DB에 저장한다.
		int res = boardService.setBoardInput(vo);
		
		if(res != 0) return "redirect:/message/boardInputOk";
		else  return "redirect:/message/boardInputNo";
	}
	
	@RequestMapping(value = "/boardContent", method = RequestMethod.GET)
	public String boardContenGet(int idx, Model model, HttpServletRequest request,
			@RequestParam(name="flag", defaultValue="", required=false) String flag,
			@RequestParam(name="search", defaultValue="", required=false) String search,
			@RequestParam(name="searchString", defaultValue="", required=false) String searchString,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		// 조회수 증가하기
		//boardService.setReadNumPlus(idx);
		// 게시글 조회수 1씩 증가시키기(중복방지)
		HttpSession session = request.getSession();
		ArrayList<String> contentReadNum = (ArrayList<String>) session.getAttribute("sContentIdx");
		if(contentReadNum == null) contentReadNum = new ArrayList<String>();
		String imsiContentReadNum = "board" + idx;
		if(!contentReadNum.contains(imsiContentReadNum)) {
			boardService.setReadNumPlus(idx);
			contentReadNum.add(imsiContentReadNum);
		}
		session.setAttribute("sContentIdx", contentReadNum);
		
		BoardVO vo = boardService.getBoardContent(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		// 이전글/다음글 가져오기
		BoardVO preVO =  boardService.getPreNexSearch(idx, "preVO");
		BoardVO nextVO = boardService.getPreNexSearch(idx, "nextVO");
		model.addAttribute("preVO",preVO);
		model.addAttribute("nextVO",nextVO);

		// 댓글(대댓글) 추가 입력처리
		List<BoardReply2VO> replyVos = boardService.getBoardReply(idx);
		model.addAttribute("replyVos", replyVos);
		model.addAttribute("flag", flag);
		
		return "board/boardContent";
	}
	
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.GET)
	public String boardUpdateGet(int idx, Model model,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		// 수정화면으로 이동할시에는 기존 원본파일의 그림파일이 존재한다면, 현재폴더(board)의 그림파일을 ckeditor폴더로 복사시켜준다.
		BoardVO vo = boardService.getBoardContent(idx);  // idx로 그림이 있는지 정보를 찾아와야 함(선행)
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgBackup(vo.getContent());
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("vo", vo);
		return "board/boardUpdate";
	}
	
//	Document doc = Jsoup.parse(content); // content에 뽑아낼 html 문자열을 넣는다
//	 
//	List<String> imagesName = new ArrayList<>();
//	        Elements imgElements = doc.select("img"); // html 코드에서 img만 추출
//	        for (Element imgElement : imgElements) {
//	            String src = imgElement.attr("src"); // img 코드에서 src만 추출
//	            String fileName = "";
//	            // 이미지를 경로로 넣었나 직접 올렸나 확인
//	            if(src.indexOf("http") == -1) fileName = src.substring(src.lastIndexOf("/") + 1);
//	            else fileName = src;
//	            imagesName.add(fileName); // images 리스트에 추가
//	        }
//	        String imageFileName = String.join("|", imagesName); // 파일명들만 가진 문자열이 필요하면 join으로 결합
	
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.POST)
	public String boardUpdatePost(BoardVO vo, Model model,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		// 수정된 자료가 원본자료와 완전히 동일하다면 수정할 필요가 없다. 즉 DB에 저장된 원본자료를 불러와서 현재 vo에 담긴 내용(content)과 비교해본다.
		BoardVO origVo = boardService.getBoardContent(vo.getIdx());  // db에 있는 내용과 비교해서 변경된 것이 없다면 변화없음
		System.out.println("pag:" + pageSize);
		// content의 내용이 조금이라도 변경이 되었다면 내용을 수정한 것이기에, 그림파일 처리유무를 결정한다.
		if(!origVo.getContent().equals(vo.getContent())) {
			// 1.기존 board 폴더에 그림이 존재했다면 원본그림을 모두 삭제처리한다.(원본 그림은 수정창에 들어오기 전에 ckeditor폴더에 저장시켜두었다.)
			if(origVo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(origVo.getContent());
			
			// 2.앞의 삭제 작업이 끝나면 'board'폴더를 'ckeditor'로 경로 변경한다.
			vo.setContent(vo.getContent().replace("/data/board/", "/data/ckeditor/"));
			
			// 1,2 작업을 마치면 파일을 처음 업로드한 것과 같은 작업처리를 해준다.
			// 즉, content에 이미지가 저장되어 있다면, 저장된 이미지만 골라서 '/data/board/' 폴더에 복사 저장처리한다.
			if(origVo.getContent().indexOf("src=\"/") != -1) boardService.imgCheck(vo.getContent());
			
			// 이미지들의 모든 복사작업을 마치면, 다시 폴더명을 'ckeditor'에서 'board'폴더로 변경처리한다.
			vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
			
		}
		// content안의 내용과 그림파일까지, 잘 정비된 vo를 DB에 Update 시켜준다.
		int res = boardService.setBoardUpdate(vo);
		model.addAttribute("idx", vo.getIdx());  // ?쿼리 스트링과 같음
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		if(res != 0) return "redirect:/message/boardUpdateOk";
		else return "redirect:/message/boardUpdateNo";
	}
	
	@RequestMapping(value = "/boardDelete", method = RequestMethod.GET)
	public String boardDeleteGet(int idx,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		// 게시글에 사진이 존재한다면 서버에 저장된 사진을 삭제처리한다.
		BoardVO vo = boardService.getBoardContent(idx);  // idx로 그림이 있는지 정보를 찾아와야 함(선행)
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(vo.getContent());
		
		// 사진작업이 끝나면 DB에 저장된 실제 정보 레코드르 삭제처리한다.
		int res = boardService.setBoardDelete(idx);
		
		if(res != 0) return "redirect:/message/boardDeleteOk";
		else return "redirect:/message/boardDeleteNo?idx="+idx+"&pag="+pag+"&pageSize="+pageSize;
	}
	
//	// 부모댓글 입력처리(원본글에 대한 댓글)
//	@ResponseBody
//	@RequestMapping(value = "/boardReplyInput", method = RequestMethod.POST)
//	public String boardReplyInputPost(BoardReply2VO replyVO) {
//		// 부모 댓글의 경우는 re_step = 0, re_order = 1 로 처리.(단, 원본글의 첫번째 부모 댓글은 re_order=1 이지만, 2번 이상은 마지막 부모 댓글의 re_order보다 +1 처리 시켜준다.)
//		BoardReply2VO replyParentVO = boardService.getBoardParentReplyCheck(replyVO.getBoardIdx());  // 부모 댓글 첫번째 댓글이 있느냐
//		
//		if(replyParentVO == null) {
//			replyVO.setRe_order(1);
//		}
//		else {
//			replyVO.setRe_order(replyParentVO.getRe_order() + 1);
//		}
//		replyVO.setRe_step(0);
//		
//		int res = boardService.setBoardReplyInput(replyVO);
//		
//		return res + "";  // ajax 처리
//	}
//	
//	// 대댓글 입력처리(부모댓글에 대한 댓글)
//	@ResponseBody
//	@RequestMapping(value = "/boardReplyInputRe", method = RequestMethod.POST)
//	public String boardReplyInputRePost(BoardReply2VO replyVO) {
//		// 대댓글(답변글)의 경우는 1.re_step은 부모댓글의 re_step+1, 2.re_order는 부모의 re_order보다 큰 댓글은 모두 re_order+1 시켜준다.
//
//		replyVO.setRe_step(replyVO.getRe_step()+1);  // 1번처리
//		
//		boardService.setReplyOrderUpdate(replyVO.getBoardIdx(), replyVO.getRe_order());  // 2번처리
//		
//		replyVO.setRe_order(replyVO.getRe_order() + 1);
//		
//		int res = boardService.setBoardReplyInput(replyVO);
//		
//		return res + "";  // ajax 처리
//	}
	// 부모댓글 입력처리(원본글에 대한 댓글)
	@ResponseBody
	@RequestMapping(value = "/boardReplyInput", method = RequestMethod.POST)
	public String boardReplyInputPost(BoardReply2VO replyVO) {
		// 부모댓글의 경우는 re_step=0, re_order=1로 처리.(단, 원본글의 첫번째 부모댓글은 re_order=1이지만, 2번이상은 마지막부모댓글의 re_order보다 +1처리 시켜준다.
		BoardReply2VO replyParentVO = boardService.getBoardParentReplyCheck(replyVO.getBoardIdx());
		
		if(replyParentVO == null) {
			replyVO.setRe_order(1);
		}
		else {
			replyVO.setRe_order(replyParentVO.getRe_order() + 1);
		}
		replyVO.setRe_step(0);
		
		int res = boardService.setBoardReplyInput(replyVO);
		
		return res + "";
	}
	
	// 대댓글 입력처리(부모댓글에 대한 댓글)
	@ResponseBody
	@RequestMapping(value = "/boardReplyInputRe", method = RequestMethod.POST)
	public String boardReplyInputRePost(BoardReply2VO replyVO) {
		// 대댓글(답변글)의 1.re_step은 부모댓글의 re_step+1, 2.re_order는 부모의 re_order보다 큰 댓글은 모두 +1처리후, 3.자신의 re_order+1시켜준다.
		
		replyVO.setRe_step(replyVO.getRe_step() + 1);		// 1번처리
		
		boardService.setReplyOrderUpdate(replyVO.getBoardIdx(), replyVO.getRe_order());  // 2번 처리
		
		replyVO.setRe_order(replyVO.getRe_order() + 1);
		
		int res = boardService.setBoardReplyInput(replyVO);
		
		return res + "";
	}
	
	// 대댓글 입력처리(부모댓글에 대한 댓글)
	//@RequestMapping(value = "/boardSearch", method = {RequestMethod.POST, RequestMethod.GET})
	@RequestMapping(value = "/boardSearch")  // 빼면 post, get 둘다 처리됨
	public String boardSearch(Model model, String search,  // search는 안넘어올 일이 없음
			@RequestParam(name="searchString", defaultValue="", required=false) String searchString,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "board", search, searchString);
		
		List<BoardVO> vos = boardService.getBoardSearchList(pageVO.getStartIndexNo(), pageSize, search, searchString);

		String searchTitle = "";
		if(pageVO.getSearch().equals("title")) searchTitle = "글제목";
		else if(pageVO.getSearch().equals("nickName")) searchTitle = "글쓴이";
		else searchTitle = "글내용";
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("searchTitle", searchTitle);
		model.addAttribute("search", search);
		model.addAttribute("searchString", searchString);
		model.addAttribute("searchCount", vos.size());
		
		return "board/boardSearchList";
	}
	
}

