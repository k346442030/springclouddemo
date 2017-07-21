package com.platform.service.business.cache;

public interface CacheKeys {

	public static final String NAMESPACE = CacheKeys.class.getName();

	public static final String SEPARATOR = ".";

	public static final String ACCESS_TOKEN_PREFIX = NAMESPACE + ".accessToken.";
	public static final String CLIENT_ID_PREFIX = NAMESPACE + ".clientid.";
	public static final String VERIFY_CODE_PREFIX = NAMESPACE + ".verifycode.";

	public static final String IMG_VERIFYCODE_PREFIX = NAMESPACE + ".imgverifycode.";

}
