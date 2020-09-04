package net.blogfy.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * AES加解密工具类，替换之前的DES加解密方式。
 * @author ZHANGZHENWEI 2019/10/16
 */
public class AESCoder {

	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	public static final String KEY = "1e45qb3kdj079thr"; // 随机生成的

	private static Key getKey(String strKey) {
		SecretKeySpec secretKey = new SecretKeySpec(strKey.getBytes(StandardCharsets.UTF_8), KEY_ALGORITHM);
		return secretKey;
	}
	
	public static String encrypt(String data) {
		try {
			Key key = getKey(KEY);
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return Base64.encodeBase64URLSafeString(result);
		} catch (Exception e) {
			throw new RuntimeException("AES加密异常");
		}
	}
	
	public static String decrypt(String data) {
		try {
			Key key = getKey(KEY);
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(Base64.decodeBase64(data));
			return new String(result, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException("AES解密异常");
		}
	}
	
}
