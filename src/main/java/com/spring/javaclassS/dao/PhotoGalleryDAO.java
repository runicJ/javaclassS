package com.spring.javaclassS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.PhotoGalleryVO;

public interface PhotoGalleryDAO {

	public int photoGalleryInput(@Param("vo") PhotoGalleryVO vo);

	public List<PhotoGalleryVO> getPhotoGalleryList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("part") String part, @Param("choice") String choice);

}
