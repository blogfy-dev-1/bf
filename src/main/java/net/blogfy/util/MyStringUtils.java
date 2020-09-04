package net.blogfy.util;

import java.security.SecureRandom;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class MyStringUtils {
	
	private static final char[] DIGITS = { // 数字和字母要按ASCII码顺序
			'0' , '1' , '2' , '3' , '4' , '5' , '6' , '7' , '8' , '9' ,
			'A' , 'B' , 'C' , 'D' , 'E' , 'F' ,
			'G' , 'H' , 'I' , 'J' , 'K' , 'L' ,
			'M' , 'N' , 'O' , 'P' , 'Q' , 'R' ,
			'S' , 'T' , 'U' , 'V' , 'W' , 'X' ,
			'Y' , 'Z' ,
			'a' , 'b' , 'c' , 'd' , 'e' , 'f' ,
			'g' , 'h' , 'i' , 'j' , 'k' , 'l' ,
			'm' , 'n' , 'o' , 'p' , 'q' , 'r' ,
			's' , 't' , 'u' , 'v' , 'w' , 'x' ,
			'y' , 'z' ,
			'*' , '-'
	};
	
	/**
	 * 10进制转成62进制
	 * @author ZHANGZHENWEI 2016-9-26
	 * @param number
	 * @return
	 */
	private static String To62String(long number) {
		int mask = 62;
		int bufLength = 11;
		int charPos = bufLength;
		char[] buf = new char[bufLength];
		do {
			buf[--charPos] = DIGITS[(int)(number % mask)];
			number = number / mask;
		} while (number > 0);
		return new String(buf, charPos, (bufLength - charPos));
	}
	
	/**
	 * 62进制转成Long类型的10进制
	 * @author ZHANGZHENWEI 2017-1-9
	 * @param str
	 * @return
	 */
	private static long Str62ToLong(String str) {
		long result = 0L;
		for (int i = 0; i < str.length(); i++) {
			int j = FindCharIndex(str.substring(i, i + 1).toCharArray()[0]);
			result += Math.pow(62, str.length() - i - 1) * j;
		}
		return result;
	}
	
	private static int FindCharIndex(char c) {
		for (int i = 0; i < DIGITS.length; i++) {
			if (DIGITS[i] == c) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * ID生成器（三位随机数+时间戳）
	 * @author ZHANGZHENWEI 2016-9-27
	 * @return
	 */
	public static synchronized String IdMarker() {
		try {
			int i = SecureRandom.getInstance("SHA1PRNG").nextInt(62 * 62 * 62 - 1);
			return To62String(System.currentTimeMillis()) + StringUtils.leftPad(To62String(i), 3, '0');
		} catch (Exception e) {
			throw new RuntimeException("ID生成失败");
		}
	}
	
	/**
	 * 生成32位UUID
	 * @return
	 */
	public static String UUID32() {
		return UUID.randomUUID().toString().replaceAll("-", StringUtils.EMPTY);
	}
	
}
