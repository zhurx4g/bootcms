package com.googlecode.bootstrapx.util;

import org.apache.commons.lang.math.NumberUtils;

public class PageInfoBuilder {
	public static PageInfo build(String url, int total, int page, int size){
		PageInfo pageInfo = new PageInfo();
		pageInfo.setUrl(url);
		pageInfo.setTotal(NumberUtils.max(new int[]{0,total}));
		//size
		size = NumberUtils.max(new int[]{1,size});
		size = NumberUtils.min(new int[]{100,size});
		pageInfo.setSize(size);
		
		//current
		pageInfo.setCurrent(NumberUtils.max(new int[]{1,page}));
		
		//previous
		page=page<=0?1:page;
		if((page-1)>0){
			pageInfo.setPrevious(page-1);
		}else{
			pageInfo.setPrevious(0);
		}
		
		//last page
		int pageCount = total/size;
		if(total%size>0){
			pageCount++;
		}
		pageInfo.setLast(pageCount);
		
		//next page
		if(page+1<=pageCount){
			pageInfo.setNext(page+1);
		}else{
			pageInfo.setNext(0);
		}
		pageInfo.parse();
		return pageInfo;
	}
}
