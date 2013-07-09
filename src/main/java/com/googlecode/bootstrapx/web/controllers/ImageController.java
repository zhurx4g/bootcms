package com.googlecode.bootstrapx.web.controllers;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.bootstrapx.util.ImageUtils;

@Path("")
public class ImageController extends CommonController {

	private static final String IMAGE_HOME = "images/";
	
	@Get("/image.do")
	public ModelAndView image(String name, int width, int height,Invocation inv) throws Exception {
		if(StringUtils.isEmpty(name)){
			return null;
		}
		if(name.contains("..")){
			return null;
		}
		String imageHome = inv.getRequest().getServletPath() + "/" + IMAGE_HOME;
		
		BufferedImage image = null;
		try{
			File imageFile = new File(imageHome + name);
			image = ImageUtils.zoom(ImageIO.read(imageFile), width, height);
			ImageIO.write(image, "png", inv.getResponse().getOutputStream());
			return null;
		}catch(Exception e){
			LOGGER.error("", e);
		}
		return null;
	}
}
