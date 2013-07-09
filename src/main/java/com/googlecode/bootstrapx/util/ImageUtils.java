package com.googlecode.bootstrapx.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

    public static BufferedImage  zoom(BufferedImage  originalImage, int width,int height){
    	if(width<=0||height<=0){
    		return originalImage;
    	}
    	
    	//大于1000像素的不做缩放
    	if(width>1000||height>1000){
    		return originalImage;
    	}
        BufferedImage newImage = new BufferedImage(width,height,originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage.getScaledInstance(width, height,Image.SCALE_DEFAULT), 0,0,null);
        g.dispose();
        return newImage;
    }
    public static BufferedImage zoomImage(BufferedImage bufferedImage, int width, int height){
        if(bufferedImage != null){
        	return zoom(bufferedImage,width, height);
        }
        return bufferedImage;
    }
    public static void main(String[] args) throws IOException {
    	BufferedImage image = zoomImage(ImageIO.read(new File("/home/zhuruixian/aaaaa.jpg")),166,212);
        
    	ImageIO.write(image, "png", new File("/home/zhuruixian/test.png"));
    }
 
}
