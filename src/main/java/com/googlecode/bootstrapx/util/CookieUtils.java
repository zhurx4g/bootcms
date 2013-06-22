package com.googlecode.bootstrapx.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
    public static Cookie getCookieByName(HttpServletRequest request, String name)
    {
        Cookie cookies[] = request.getCookies();
        if(null != cookies)
        {
            Cookie arr[] = cookies;
            int len = arr.length;
            for(int i = 0; i < len; i++)
            {
                Cookie cookie = arr[i];
                if(cookie.getName().equals(name))
                    return cookie;
            }

        }
        return null;
    }
}
