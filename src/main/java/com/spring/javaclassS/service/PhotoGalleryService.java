package com.spring.javaclassS.service;

import java.util.List;

import com.spring.javaclassS.vo.PhotoGalleryVO;

public interface PhotoGalleryService {

	public int imgCheck(PhotoGalleryVO vo, String realPath);

	public List<PhotoGalleryVO> getPhotoGalleryList(int startIndexNo, int pageSize, String part, String choice);

}
