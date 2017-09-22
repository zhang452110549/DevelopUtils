package com.linkchain.DevelopUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static String getMD5(String md5Str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(md5Str.getBytes());
			StringBuffer sb = new StringBuffer();
			String temp = "";
			for (byte b : bytes) {
				temp = Integer.toHexString(b & 0XFF);
				sb.append(temp.length() == 1 ? "0" + temp : temp);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

}
