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
import org.springframework.web.servlet.ModelAndView;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.pagination.PageProcess;
import com.spring.javaclassS.service.PhotoGalleryService;
import com.spring.javaclassS.vo.PhotoGalleryVO;

@Controller
@RequestMapping("/photoGallery")
public class PhotoGalleryController {
	
	@Autowired
	PhotoGalleryService photoGalleryService;
	
	@Autowired
	JavaclassProvide javaclassProvide;
	
	@Autowired
	PageProcess pageProcess;
	
	// 사진 여러장 보기 List
	@RequestMapping(value = "/photoGalleryList", method = RequestMethod.GET)
	public String photoGalleryListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag, 
			@RequestParam(name="pageSize", defaultValue = "12", required = false) int pageSize,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="choice", defaultValue = "최신순", required = false) String choice
		) {
		int startIndexNo = (pag - 1) * pageSize;
		
		String imsiChoice = "";
		if(choice.equals("최신순")) imsiChoice = "idx";
		else if(choice.equals("추천순")) imsiChoice = "goodCount";
		else if(choice.equals("조회순")) imsiChoice = "readNum";
		else if(choice.equals("댓글순")) imsiChoice = "replyCnt";	
		else imsiChoice = choice;
		
		//PageVO pageVo = pageProcess.totRecCnt(pag, pageSize, "photoGallery", part, choice);
		List<PhotoGalleryVO> vos = photoGalleryService.getPhotoGalleryList(startIndexNo, pageSize, part, imsiChoice);
		model.addAttribute("vos", vos);
		model.addAttribute("part", part);
		model.addAttribute("choice", choice);
		return "photoGallery/photoGalleryList";
	}
	
	// 사진 여러장보기에서, 한화면 마지막으로 이동했을때 다음 페이지 스크롤하기
	@ResponseBody
	@RequestMapping(value = "/photoGalleryListPaging", method = RequestMethod.POST)
	public ModelAndView photoGalleryPagingPost(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag, 
			@RequestParam(name="pageSize", defaultValue = "12", required = false) int pageSize,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="choice", defaultValue = "최신순", required = false) String choice
		) {
		int startIndexNo = (pag - 1) * pageSize;
		
		String imsiChoice = "";
		if(choice.equals("최신순")) imsiChoice = "idx";
		else if(choice.equals("추천순")) imsiChoice = "goodCount";
		else if(choice.equals("조회순")) imsiChoice = "readNum";
		else if(choice.equals("댓글순")) imsiChoice = "replyCnt";	
		else imsiChoice = choice;
		
		//PageVO pageVo = pageProcess.totRecCnt(pag, pageSize, "photoGallery", part, choice);
		List<PhotoGalleryVO> vos = photoGalleryService.getPhotoGalleryList(startIndexNo, pageSize, part, imsiChoice);
		model.addAttribute("vos", vos);
		model.addAttribute("part", part);
		model.addAttribute("choice", choice);
		
		// ModelAndView에 담아서 return
		ModelAndView mv = new ModelAndView();
		mv.setViewName("photoGallery/photoGalleryListPaging");
		return mv;
	}
	
	// 사진 한장씩 전체 보기(나중에 올린순으로 보기)
	@RequestMapping(value = "/photoGallerySingle", method = RequestMethod.GET)
	public String photoGallerySingleGet(Model modelModel, Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag, 
			@RequestParam(name="pageSize", defaultValue = "1", required = false) int pageSize
		) {
		int startIndexNo = (pag - 1) * pageSize;
		List<PhotoGalleryVO> vos = photoGalleryService.setPhotoGallerySingle(startIndexNo, pageSize);
		model.addAttribute("vos", vos);
		return "photoGallery/photoGallerySingle";
	}
	
	// 사진 한장씩 전체 보기(나중에 올린순으로 보기) - 한화면 마지막으로 이동했을때 다음 페이지 스크롤하기
	@ResponseBody
	@RequestMapping(value = "/photoGallerySinglePaging", method = RequestMethod.POST)
	public ModelAndView photoGallerySinglePagingPost(Model modelModel, Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag, 
			@RequestParam(name="pageSize", defaultValue = "1", required = false) int pageSize
			) {
		int startIndexNo = (pag - 1) * pageSize;
		List<PhotoGalleryVO> vos = photoGalleryService.setPhotoGallerySingle(startIndexNo, pageSize);
		model.addAttribute("vos", vos);
		
	  // ModelAndView에 담아서 return
		ModelAndView mv = new ModelAndView();
		mv.setViewName("photoGallery/photoGallerySinglePaging");
		return mv;
	}
	
	// 포토갤러리 사진 등록하기 폼보기
	@RequestMapping(value = "/photoGalleryInput", method = RequestMethod.GET)
	public String photoGalleryInputGet() {
		return "photoGallery/photoGalleryInput";
	}
	
	// 포토갤러리 사진 등록처리
	@RequestMapping(value = "/photoGalleryInput", method = RequestMethod.POST)
	public String photoGalleryInputPost(PhotoGalleryVO vo, HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		int res = photoGalleryService.imgCheck(vo, realPath);
		if(res != 0) return "redirect:/message/photoGalleryInputOk";
		else return "redirect:/message/photoGalleryInputNo";
	}
	
	// 개별항목 상세보기
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/photoGalleryContent", method = RequestMethod.GET)
	public String photoGalleryContentGet(HttpSession session, int idx, Model model, HttpServletRequest request) {
		// 게시글 조회수 1씩 증가시키기(중복방지)
		ArrayList<String> contentReadNum = (ArrayList<String>) session.getAttribute("sContentIdx");
		if(contentReadNum == null) contentReadNum = new ArrayList<String>();
		String imsiContentReadNum = "photoGallery" + idx;
		if(!contentReadNum.contains(imsiContentReadNum)) {
			photoGalleryService.setPhotoGalleryReadNumPlus(idx);
			contentReadNum.add(imsiContentReadNum);
		}
		session.setAttribute("sContentIdx", contentReadNum);

		// 조회자료 1건 담아서 내용보기로 보낼 준비
		PhotoGalleryVO vo = photoGalleryService.getPhotoGalleryIdxSearch(idx);
		model.addAttribute("vo", vo);
		
		// ckeditor의 사진정보만 뽑아서 넘겨주기(content화면에서 여러장의 사진을 보이고자 함)
		List<String> photoList = photoGalleryService.getPhotoGalleryPhotoList(vo.getContent());
		//model.addAttribute("photoList", photoList);
		request.setAttribute("photoList", photoList);
		
		// 댓글 처리
		ArrayList<PhotoGalleryVO> replyVos = photoGalleryService.getPhotoGalleryReply(idx);
		model.addAttribute("replyVos", replyVos);
		
		return "photoGallery/photoGalleryContent";
	}

	// 댓글달기
	@ResponseBody
	@RequestMapping(value = "/photoGalleryReplyInput", method = RequestMethod.POST)
	public String photoGalleryReplyInputPost(PhotoGalleryVO vo) {
		return photoGalleryService.setPhotoGalleryReplyInput(vo) + "";
	}
	
	// 댓글 삭제
	@ResponseBody
	@RequestMapping(value = "/photoGalleryReplyDelete", method = RequestMethod.POST)
	public String photoGalleryReplyDeletePost(int idx) {
		return photoGalleryService.setPhotoGalleryReplyDelete(idx) + "";
	}
	
	// 좋아요수 증가
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/photoGalleryGoodCheck", method = RequestMethod.POST)
	public String photoGalleryGoodCheckPost(HttpSession session, int idx) {
		String res = "0";
		// 좋아요 클릭수 1씩 증가시키기(중복방지)
		ArrayList<String> contentReadNum = (ArrayList<String>) session.getAttribute("sContentGood");
		if(contentReadNum == null) contentReadNum = new ArrayList<String>();
		String imsiContentReadNum = "photoGallery" + idx;
		if(!contentReadNum.contains(imsiContentReadNum)) {
			photoGalleryService.setPhotoGalleryGoodPlus(idx);
			contentReadNum.add(imsiContentReadNum);
			res = "1";
		}
		session.setAttribute("sContentGood", contentReadNum);
		return res;
	}

	// 내용 삭제하기
	@RequestMapping(value = "/photoGalleryDelete", method = RequestMethod.GET)
	public String photoGalleryDeleteGet(int idx) {
		int res = photoGalleryService.setPhotoGalleryDelete(idx);
	  if(res != 0) return "redirect:/message/photoGalleryDeleteOk";
	  else return "redirect:/message/photoGalleryDeleteNo";
	}

	
}
