package com.googlecode.bootstrapx.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;

public class PageInfo {
	private String url;//
	private int total;//
	private int size;//
	private int previous;//
	private int current;//
	private int next;
	private int last;//
	private int offset;
	private List<Integer> pageList = new ArrayList<Integer>();

	private Map<String, String> paramsMap = new HashMap<String, String>();
	
	private boolean beforeDot = false;
	private boolean afterDot = false;
	
	public void parse(){
		pageList.clear();
		int startIndex = current - 3;	
		int endIndex = current + 3;

		if(startIndex<1){
			startIndex = 1;
			endIndex = NumberUtils.min(new int[]{startIndex+6, last});;
		}else{
			if(endIndex>last){
				endIndex = last;
				startIndex = NumberUtils.max(new int[]{1, last-6});
			}
		}
		
		for(int i=startIndex;i<=endIndex;i++){
			pageList.add(i);
		}
		
		offset = (current-1)*size;
		beforeDot = !pageList.contains(1);
		afterDot = !pageList.contains(last);
	}

	public static void main(String[] args) {
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrent(1);
		pageInfo.setLast(1);
		
		pageInfo.parse();
		
		System.out.println(pageInfo.pageList);
		
		pageInfo.setCurrent(1);
		pageInfo.setLast(20);
		
		pageInfo.parse();
		
		System.out.println(pageInfo.pageList);
		
		pageInfo.setCurrent(19);
		pageInfo.setLast(20);
		
		pageInfo.parse();
		
		System.out.println(pageInfo.pageList);
	}
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		
		for(Entry<String,String> entry:paramsMap.entrySet()){
			buffer.append(entry.getKey());
			buffer.append("=");
			buffer.append(entry.getValue());
			buffer.append("&");
		}
		if(buffer.length()>0)buffer.deleteCharAt(buffer.length()-1);
		
		return buffer.toString();
	}
	
	public void addParam(String key, Object value){
		if(key!=null&&value!=null){
			
		}
		paramsMap.put(key, value.toString());
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPrevious() {
		return previous;
	}
	public void setPrevious(int previous) {
		this.previous = previous;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getLast() {
		return last;
	}
	public void setLast(int last) {
		this.last = last;
	}

	public List<Integer> getPageList() {
		return pageList;
	}

	public void setPageList(List<Integer> pageList) {
		this.pageList = pageList;
	}

	public boolean isBeforeDot() {
		return beforeDot;
	}

	public void setBeforeDot(boolean beforeDot) {
		this.beforeDot = beforeDot;
	}

	public boolean isAfterDot() {
		return afterDot;
	}

	public void setAfterDot(boolean afterDot) {
		this.afterDot = afterDot;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
