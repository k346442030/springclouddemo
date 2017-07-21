package com.platform.service.core.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class MD5Util {

	public static String encrypt(String str) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		return encoder.encodePassword(str, "");
	}

}
