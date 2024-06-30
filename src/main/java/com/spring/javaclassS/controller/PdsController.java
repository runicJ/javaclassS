package com.spring.javaclassS.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.pagination.PageProcess;
import com.spring.javaclassS.service.PdsService;
import com.spring.javaclassS.vo.BoardReply2VO;
import com.spring.javaclassS.vo.PageVO;
import com.spring.javaclassS.vo.PdsReplyVO;
import com.spring.javaclassS.vo.PdsVO;

@Controller
@RequestMapping("/pds")
public class PdsController {
	
	@Autowired
	PdsService pdsService;
	
	@Autowired
	PageProcess pageProcess;
		
	@Autowired
	JavaclassProvide javaclassProvide;
	
	@RequestMapping(value = "/pdsList", method = RequestMethod.GET)
	public String pdsListGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
		) {
		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "pds", part, "");
		
		List<PdsVO> vos = pdsService.getPdsList(pageVO.getStartIndexNo(),pageSize,part);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		
		return "pds/pdsList";
	}
	
	@RequestMapping(value = "/pdsInput", method = RequestMethod.GET)
	public String pdsInputGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part) {
		return "pds/pdsInput";
	}
	
	@RequestMapping(value = "/pdsInput", method = RequestMethod.POST)
	public String pdsInputPost(MultipartHttpServletRequest mFName, PdsVO vo) {
		int res = pdsService.setPdsUpload(mFName, vo);
		
		if(res != 0) return "redirect:/message/pdsUploadOk";
		else return "redirect:/message/pdsUploadNo";
	}
	
//	@RequestMapping(value = "/pdsInput", method = RequestMethod.POST)
//	public String pdsInputPost(MultipartHttpServletRequest mFile, PdsVO vo, HttpServletRequest request) {
//		try {
//			MultipartHttpServletRequest mFile =  (MultipartHttpServletRequest) request;  
//			List<MultipartFile> fileList = mFile.getFiles("fName");
//         Iterator fileNameIterator = mpRequest.getFileNames();
//         List boardFileList = new ArrayList();
//         while (fileNameIterator.hasNext()) {
//             MultipartFile multiFile = mpRequest
//                     .getFile((String) fileNameIterator.next());
//             if (multiFile.getSize() > 0) {
//                 BoardFile boardFile = FileUploadUtil.uploadFormFile(multiFile,
//                         realUploadPath);
//                 boardFileList.add(boardFile);
//             }
//         }
//			String oFileNames = "";
//			String sFileNames = "";
//			int fileSizes = 0;  // 누적할 것이므로 0
//			
//			for(MultipartFile file : fileList) {
//				//System.out.println("원본화일 : " + file.getOriginalFilename());
//				String oFileName = file.getOriginalFilename();
//				String sFileName = javaclassProvide.saveFileName(oFileName);
//				
//				javaclassProvide.writeFile(file, sFileName, "pds");
//				
//				oFileNames += oFileName + "/";
//				sFileNames += sFileName + "/";
//				fileSizes += file.getSize();
//			}
//			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
//			sFileNames = sFileNames.substring(0, sFileNames.length()-1);
//			
//			vo.setFName(oFileNames);
//			vo.setFSName(sFileNames);
//			vo.setFSize(fileSizes);
//			
//		} catch (IOException e) {e.printStackTrace();}
//		
//		int res =  pdsService.setPdsUpload(mFile, vo);
//		
//		if(res != 0) return "redirect:/message/pdsUploadOk";
//		else return "redirect:/message/pdsUploadNo";
//	}
	
	@ResponseBody
	@RequestMapping(value = "/pdsDownNumCheck", method = RequestMethod.POST)
	public String pdsDownNumCheckPost(int idx) {
		return pdsService.setPdsDownNumPlus(idx) + "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/pdsDeleteCheck", method = RequestMethod.POST)
	public String pdsDeleteCheckPost(int idx, String fSName, HttpServletRequest request) {
		return pdsService.setPdsDelete(idx, fSName, request) + "";
	}

	@RequestMapping(value = "/pdsContent", method = RequestMethod.GET)
	public String pdsContentGet(Model model, int idx,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
		) {
		PdsVO vo = pdsService.getPdsContent(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("part", part);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		List<PdsReplyVO> rVos = pdsService.getPdsReplyList(idx);
		
		int reviewTot = 0;
		for(PdsReplyVO r : rVos) {
			reviewTot += r.getStar();
		}
		double starAvg = 0.0;
		if(rVos.size() != 0) starAvg = (double) reviewTot / rVos.size();
		
		model.addAttribute("rVos", rVos);
		model.addAttribute("starAvg", starAvg);
		
		return "pds/pdsContent";
	}
	
	@ResponseBody
	@RequestMapping(value = "/pdsReplyInput", method = RequestMethod.POST)
	public String PdsReplyInputPost(PdsReplyVO replyVO) {
	    
	    if (replyVO.getMid() == null) {
	        System.out.println("Error: mid is null");
	        return "0"; // 오류 메시지 반환
	    }
		
	    int res = 0;
	    // 별점 리뷰 작성인 경우
	    if (replyVO.getParentId() == null) {
	        PdsReplyVO existReply = pdsService.getPdsReplyCheck(replyVO.getPdsIdx(), replyVO.getMid());
	        if (existReply == null) {
	            res = pdsService.setPdsReplyInput(replyVO);
	        }
	    }
	    // 대댓글 작성인 경우
	    else { 
	        res = pdsService.setPdsReplyInput(replyVO);
	    }
	    return res + "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/reviewDelete", method = RequestMethod.POST)
	public String PdsReplyInputPost(int idx) {
		int res = pdsService.setPdsReplyDelete(idx);
		
		return res + "";
	}
}
