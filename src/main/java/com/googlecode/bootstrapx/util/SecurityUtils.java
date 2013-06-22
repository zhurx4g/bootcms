package com.googlecode.bootstrapx.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class SecurityUtils {

	public static String password(String password){
		if(password==null)
			return StringUtils.EMPTY;
		return DigestUtils.md5Hex(password)		;
	}
	
	public static void main(String[] args) {
		System.out.println(password("miuiadmin4rd"));//81dc9bdb52d04dc20036dbd8313ed055
	}
}
