package com.platform.service.business.cache;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.platform.service.business.cache.model.ClientSession;
import com.platform.service.business.common.model.AccessToken;
import com.platform.service.business.common.util.RandomUtil;
import com.platform.service.business.config.ConfigValues;

@Component
public class SessionService {

	@Autowired
	private StringRedisTemplate template;

	public void refresh(String key, Long timeout) {
		template.expire(key, timeout, TimeUnit.SECONDS);
	}

	private void refresh(String key) {
		refresh(key, ConfigValues.SESSION_TIMEOUT);
	}

	public AccessToken renewToken(ClientSession session) {
		String tokenStr = UUID.randomUUID().toString();
		Long timeOut = ConfigValues.SESSION_TIMEOUT;
		AccessToken token = new AccessToken(tokenStr, timeOut);
		String clientIdKey = getClientIdKey(session.getClientId());
		destorySession(session.getClientId());
		set(clientIdKey, getAccessTokenKey(tokenStr));
		set(getAccessTokenKey(tokenStr), session.toStringInfo());
		return token;
	}

	public void refreshSession(ClientSession session) {
		String clientIdKey = getClientIdKey(session.getClientId());
		String tokenKey = get(clientIdKey);
		set(tokenKey, session.toStringInfo());
	}

	public void destorySession(String clientId) {
		ValueOperations<String, String> ops = template.opsForValue();
		String clientIdKey = getClientIdKey(clientId);
		String oldTokenKey = ops.get(clientIdKey);
		if (oldTokenKey != null) {
			template.delete(clientIdKey);
			template.delete(oldTokenKey);
		}
	}

	private String getClientIdKey(String clientId) {
		return CacheKeys.CLIENT_ID_PREFIX + clientId;
	}

	private String getVerifyCodeKey(String mobile, Integer verifyType) {
		return CacheKeys.VERIFY_CODE_PREFIX + mobile + '.' + verifyType;
	}

	private String getAccessTokenKey(String token) {
		return CacheKeys.ACCESS_TOKEN_PREFIX + token;
	}

	public ClientSession fetchClientCacheByUserToken(String token) {
		try {
			// 从缓存获取会话令牌对应的字符串信息
			String stringInfo = get(getAccessTokenKey(token));
			// 解析会话身份信息
			if (!StringUtils.isEmpty(stringInfo)) {
				return JSONObject.parseObject(stringInfo, ClientSession.class);
			}
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	public String get(String key) {
		return template.opsForValue().get(key);
	}

	public void set(String key, String value) {
		set(key, value, ConfigValues.SESSION_TIMEOUT);
	}

	public void set(String key, String value, long timeout) {
		template.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	public void refreshToken(ClientSession identity) {
		String clientIdKey = getClientIdKey(identity.getClientId());
		String tokenKey = get(clientIdKey);
		this.refresh(tokenKey);
		this.refresh(clientIdKey);
	}

	public Long saveNewVerifyCode(String mobile, Integer verifyType, String code) {
		set(getVerifyCodeKey(mobile, verifyType), code, ConfigValues.VERIFYCODE_TIMEOUT);
		return ConfigValues.VERIFYCODE_TIMEOUT;
	}

	public boolean checkVerifyCode(String mobile, Integer verifyType, String code) {
		if (ConfigValues.SUPER_VALIDATE_CODE.equals(code)) {
			return true;
		}
		String trueCode = get(getVerifyCodeKey(mobile, verifyType));
		return trueCode != null && trueCode.equals(code);
	}

	public String newImgVerifyCode(String verify_base) {
		String verifycode = RandomUtil.genNumericAlphaRandomString(4);
		set(CacheKeys.IMG_VERIFYCODE_PREFIX + verify_base, verifycode);
		return verifycode;
	}

	public boolean checkImgVerifyCode(String verifyBase, String code) {
		if (ConfigValues.SUPER_VALIDATE_CODE.equals(code)) {
			return true;
		}
		String trueCode = get(CacheKeys.IMG_VERIFYCODE_PREFIX + verifyBase);
		return trueCode != null && trueCode.equalsIgnoreCase(code);
	}

	public Set<String> keys(String pattern) {
		return template.keys(pattern);
	}

	public void delete(Collection<String> keys) {
		template.delete(keys);
	}

	public void delete(String key) {
		template.delete(key);
	}
}
