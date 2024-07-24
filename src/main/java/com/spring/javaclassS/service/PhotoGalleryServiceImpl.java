package com.spring.javaclassS.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.dao.PhotoGalleryDAO;
import com.spring.javaclassS.vo.PhotoGalleryVO;

@Service
public class PhotoGalleryServiceImpl implements PhotoGalleryService {
	
	@Autowired
	PhotoGalleryDAO photoGalleryDAO;
	
	@Autowired
	JavaclassProvide javaclassProvide;
	
	@Override
	public int imgCheck(PhotoGalleryVO vo, String realPath) {
		int res = 0;
		if(vo.getContent().indexOf("src=\"/") == -1) return res; // content안에 그림파일이 없으면 작업을 수행하지 않는다.
		//            0         1         2         3         4         5         6
		//            01234567890123456789012345678901234567890123456789012345678901234567890
		//<img alt="" src="/javaclassS/data/ckeditor/210201125255+0900_m13.jpg" style="height:400px; width:600px" />
		//<img alt="" src="/javaclassS/data/photoGallery/210201125255+0900_m13.jpg" style="height:400px; width:600px" />
		
		int position = 31, photoCount = 0;	// photoCount는 그림파일 개수
		boolean sw = true, firstSw = true;
		String firstFile = "";	// 첫번째 파일명을 저장하기위해 사용
		
		String nextImg = vo.getContent().substring(vo.getContent().indexOf("src=\"/")+position);
		
		while(sw) {
			photoCount++;
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));  // 순수한 그림파일만 가져온다.
			
			// 첫번째 그림을 thumbnail로 저장하기위해 파일명을 변수에 저장시켜두었다.
			if(firstSw) {
				firstFile = imgFile;
				vo.setThumbnail(firstFile);		// 첫번째파일명을 저장시켰다.
				firstSw = false;
			}
			
			// 아래로 그림파일을 photoGallery폴더로 복사하는 작업이다. 
			String oriFilePath = realPath + "ckeditor/" + imgFile;  // 원본파일이 있는 경로명과 파일명
			String copyFilePath = realPath + "photoGallery/" + imgFile;  // 복사될파일이 있는 경로명과 파일명
			
			 javaclassProvide.fileCopyCheck(oriFilePath, copyFilePath);  // data/ckeditor/폴더에서 data/photoGallery/폴더로 파일 복사작업처리
			
			if(nextImg.indexOf("src=\"/") == -1) {	// nextImg변수안에 또다른 'src="/'문구가 있는지를 검색하여, 있다면 다시 앞의 작업을 반복수행한다.
				sw = false;
			}
			else {
				nextImg = nextImg.substring(nextImg.indexOf("src=\"/")+position);
			}
		}
		vo.setPhotoCount(photoCount);
		
		// content필드에 'style=~~~~~' 을 찾아서 모두 삭제처리한다.
		String tempContent = "";
		sw = true;
		String nextContent = vo.getContent();
		while(nextContent.indexOf("style=\"") != -1) {
			sw = false;
			tempContent += nextContent.substring(0,nextContent.indexOf("style=\""));
			tempContent += nextContent.substring(nextContent.indexOf("px\"")+4);
			if(tempContent.indexOf("style=\"")==-1) {
				break;
			}
			else {
				nextContent = tempContent;
				tempContent = "";
			}
		}
		// content필드안에 있는 'style=~~~~'문구들을 모두 삭제시켰다. 그후 다시 content에 set시켜준다.
		if(!sw)	vo.setContent(tempContent);
		
  	// 파일이 정상적으로 src폴더에 복사되었으면 DB에 저장되는 실제경로를 src폴더로 변경시켜준다.
		vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/photoGallery/")); // 실제로 서버에 저장되는 경로
		
		// 아래로, 일반파일의 모든 복사작업이 끝나면 /photoGallery/폴더의 첫번째 그림파일을 썸네일파일로 복사작업처리한다.
		try {
			String fileExt = firstFile.substring(firstFile.lastIndexOf(".")+1); // 확장자 구하기
			
			// 썸네일 이미지 만들어 저장하기(이미지를 필요한 크기로 잘라서 저장한다.)
			String uploadPath = realPath + "/photoGallery/" + firstFile;
			BufferedImage srcImg = ImageIO.read(new File(uploadPath));
			
			int ow = srcImg.getWidth();
			int oh = srcImg.getHeight();
			
			int tw = 200, th = 150;
			
			int rw = ow;
			int rh = (ow * th) / tw;
			
			if(rh > oh) {
				rh = oh;
				rw =(oh * tw) / th;
			}
			Scalr.crop(srcImg, (ow-rw)/2, (oh-rh)/2, rw, rh);
			
			// 잘라낸 그림파일의 정보를 다시 읽어온다.
			BufferedImage resizeImg = Scalr.resize(srcImg, tw, th);
			
			// 새롭게 재 계산처리되어 잘라진 썸네일을 저장한다.
			String thumbnail = firstFile.substring(0,firstFile.lastIndexOf(".")) + "_S." + fileExt;
			File tFileName = new File(realPath + "photoGallery/" + thumbnail);
			ImageIO.write(resizeImg, fileExt, tFileName);  // 썸네일 이미지 저장처리
			
			// 이미지 잘라서 저장작업을 마친후 DB에 저장하기 위해서 VO에 작업된 내용을 담는다.
			//System.out.println("thumbnail : " + thumbnail);
			vo.setThumbnail(thumbnail);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
  	// 잘 정비된 vo를 DB에 저장한다.
		//System.out.println("vo : " + vo);
		res = photoGalleryDAO.photoGalleryInput(vo);
		return res;
	}

	@Override
	public List<PhotoGalleryVO> getPhotoGalleryList(int startIndexNo, int pageSize, String part, String choice) {
		return photoGalleryDAO.getPhotoGalleryList(startIndexNo, pageSize, part, choice);
	}

	@Override
	public void setPhotoGalleryReadNumPlus(int idx) {
		photoGalleryDAO.setPhotoGalleryReadNumPlus(idx);
	}

	@Override
	public PhotoGalleryVO getPhotoGalleryIdxSearch(int idx) {
		return photoGalleryDAO.getPhotoGalleryIdxSearch(idx);
	}

	@Override
	public ArrayList<PhotoGalleryVO> getPhotoGalleryReply(int idx) {
		return photoGalleryDAO.getPhotoGalleryReply(idx);
	}

	@Override
	public List<String> getPhotoGalleryPhotoList(String content) {
		List<String> photoList = new ArrayList<String>();
		
		if(content.indexOf("src=\"/") == -1) return photoList; // content안에 그림파일이 없으면 작업을 수행하지 않는다.
		//            0         1         2         3         4         5         6
		//            01234567890123456789012345678901234567890123456789012345678901234567890
		//<img alt="" src="/javaclassS/data/photoGallery/210201125255+0900_m13.jpg" style="height:400px; width:600px" />
		
		int position = 35;
		
		String nextImg = content.substring(content.indexOf("src=\"/")+position);
		
		while(true) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));  // 순수한 그림파일만 가져온다.
			photoList.add(imgFile);
			if(nextImg.indexOf("src=\"/") == -1) break;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/")+position);
		}
		return photoList;
	}

	@Override
	public int setPhotoGalleryReplyInput(PhotoGalleryVO vo) {
		return photoGalleryDAO.setPhotoGalleryReplyInput(vo);
	}

	@Override
	public int setPhotoGalleryReplyDelete(int idx) {
		return photoGalleryDAO.setPhotoGalleryReplyDelete(idx);
	}

	@Override
	public void setPhotoGalleryGoodPlus(int idx) {
		photoGalleryDAO.setPhotoGalleryGoodPlus(idx);
	}

	@Transactional
	@Override
	public int setPhotoGalleryDelete(int idx) {
		PhotoGalleryVO vo = photoGalleryDAO.getPhotoGalleryIdxSearch(idx);
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/photoGallery/");

		//            0         1         2         3         4         5         6
		//            01234567890123456789012345678901234567890123456789012345678901234567890
		//<img alt="" src="/javaclassS/data/photoGallery/210201125255+0900_m13.jpg" style="height:400px; width:600px" />
		
		int position = 35;	// photoCount는 그림파일 개수
		
		String nextImg = vo.getContent().substring(vo.getContent().indexOf("src=\"/")+position);
		
		while(true) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));  // 순수한 그림파일만 가져온다.
			
			// 서버에 존재하는 파일을 삭제한다.
			new File(realPath + imgFile).delete();
			
			if(nextImg.indexOf("src=\"/") == -1) break;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/")+position);
		}
		// 서버의 그림파일을 모두 삭제하였으면 현재 내역을 DB에서 포토갤러리의 정보를 삭제한다.
		return photoGalleryDAO.setPhotoGalleryDelete(idx);
	}

	@Override
	public List<PhotoGalleryVO> setPhotoGallerySingle(int startIndexNo, int pageSize) {
		List<PhotoGalleryVO> vos = new ArrayList<PhotoGalleryVO>();
		int[] idxs = photoGalleryDAO.getPhotoGalleryIdxList(startIndexNo, pageSize);
		
		PhotoGalleryVO photoVo = null;
		PhotoGalleryVO vo = null;
		for(int idx : idxs) {
			photoVo = photoGalleryDAO.setPhotoGallerySingle(idx);
			//System.out.println(idx + ".photoVo" + photoVo);
			
			vo = new PhotoGalleryVO();
			vo.setIdx(photoVo.getIdx());
			vo.setPart(photoVo.getPart());
			vo.setTitle(photoVo.getTitle());
			vo.setPhotoCount(photoVo.getPhotoCount());
			vo.setContent(photoVo.getContent());
			vos.add(vo);
		}
		//System.out.println("vos : " + vos.get(0));
		return vos;
	}
	
}
