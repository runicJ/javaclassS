package com.spring.javaclassS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.pagination.PageProcess;
import com.spring.javaclassS.service.PdsService;
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
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/pdsTotalDown", method = RequestMethod.GET)
	public String pdsTotalDownGet(HttpServletRequest request, int idx) throws IOException {  // 파일이 없으면 예외처리 시켜라
		// 다운로드 수 증가하기
		pdsService.setPdsDownNumPlus(idx);
		
		// 여러개의 파일을 하나의 파일(zip)로 압축(통합)하여 다운로드 시켜준다. 압축파일의 이름은 '제목.zip'으로 처리한다.
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		PdsVO vo = pdsService.getPdsContent(idx);
		
		String[] fNames = vo.getFName().split("/");
		String[] fSNames = vo.getFSName().split("/");  // 잘라서 fSNames에 넣어놓음
		
		String zipPath = realPath + "temp/";
		String zipName = vo.getTitle() + ".zip";  // 제목.zip
		
		FileInputStream fis = null;  // 서버(pds) -> 서버(temp) input output 둘다 필요
		FileOutputStream fos = null;
		
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath + zipName));  // io에 관계된 거니까 무조건 예외처리 해야함  // temp에 하나하나 넣고 outputStream을 넣어야 함 하나로 제목.zip을 만듦
		
		byte[] bytes = new byte[2048];
		
		for(int i=0; i<fNames.length; i++) {
			fis = new FileInputStream(realPath + fSNames[i]);
			fos = new FileOutputStream(zipPath + fNames[i]);
			File copyFile = new File(zipPath + fNames[i]);
			
			// pds 폴더의 파일을 temp 폴더로 복사..(fis)
			int data = 0;
			while((data = fis.read(bytes, 0, bytes.length)) != -1) {  // 존재할 때까지 읽어옮
				fos.write(bytes, 0, data);
			}
			fos.flush();  // 찌꺼기 남으면 보냄 여기선 사실 안써도 됨(길이만큼 돌리니까)
			fos.close();
			fis.close();
			
			// temp폴더로 복사된 파일을 zip파일에 담는다.
			fis = new FileInputStream(copyFile);
			zout.putNextEntry(new ZipEntry(fNames[i]));  // 지금 temp임 // 생성해서 넣어야 함
			while((data = fis.read(bytes, 0, bytes.length)) != -1) {
				zout.write(bytes, 0, data);
			}
			zout.flush();
			zout.closeEntry();  // ZipEntry를 닫는 것, 엔트리 닫고 다시 열고 다시 닫고
			fis.close();  // zout을 여기서 닫으면 하나 넣고 멈춤
		}
		zout.close();
		
		// home controller에 호출
		// 작업완료 후 다운카운트 증가하기
		return "redirect:/fileDownAction?path=pds&file="+java.net.URLEncoder.encode(zipName);  // zipName을 encode 해라 // 재사용 위해서
	}
}
