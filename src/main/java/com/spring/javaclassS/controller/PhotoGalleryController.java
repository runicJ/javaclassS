package com.spring.javaclassS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.service.PhotoGalleryService;

@Controller
@RequestMapping("/photoGallery")
public class PhotoGalleryController {
	
	@Autowired
	PhotoGalleryService photoGalleryService;
	
	@Autowired
	JavaclassProvide javaclassProvide;
	
	@RequestMapping(value = "/photoGalleryList", method = RequestMethod.GET)
	public String photoGalleryListGet() {
		return "photoGallery/photoGalleryList";
	}
	
	@RequestMapping(value = "/photoGalleryInput", method = RequestMethod.GET)
	public String photoGalleryInputGet() {
		return "photoGallery/photoGalleryInput";
	}
	
	
	
}
