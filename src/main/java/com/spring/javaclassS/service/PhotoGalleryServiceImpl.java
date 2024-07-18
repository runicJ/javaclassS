package com.spring.javaclassS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaclassS.dao.PhotoGalleryDAO;

@Service
public class PhotoGalleryServiceImpl implements PhotoGalleryService {
	
	@Autowired
	PhotoGalleryDAO photoGalleryDAO;
	
}
