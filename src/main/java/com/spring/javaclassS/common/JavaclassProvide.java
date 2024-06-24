package com.spring.javaclassS.common;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service  // ioc 컨테이너로 들어감 @Autoweird로 사용 가능
public class JavaclassProvide {
	
	// urlPath에 파일 저장하는 메소드 : (업로드 파일명, 저장할 파일명, 저장할경로)
	public void writeFile(MultipartFile fName, String sFileName, String urlPath) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+urlPath+"/");  // 서버의 절대경로
		
		FileOutputStream fos = new FileOutputStream(realPath + sFileName);

		//fos.write(fName.getBytes())
		if(fName.getBytes().length != -1) {  // -1은 없단 얘기
			fos.write(fName.getBytes());
		}
		fos.flush();
		fos.getClass();
	}
	
}
