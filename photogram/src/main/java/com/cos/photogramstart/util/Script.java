package com.cos.photogramstart.util;

public class Script {
	
	public static String back(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('"+msg+"');"); // 알림창 띄우기 
		sb.append("history.back();"); // 되돌아가기 
		sb.append("</script>");
		return sb.toString();
	}
}
