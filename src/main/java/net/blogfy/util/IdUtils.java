package net.blogfy.util;

import java.util.Random;

public class IdUtils {
	
	public static final int USER_ID_LENGTH = 8;
	public static final int POST_ID_LENGTH = 8;
	
	private static final char[] DIGITS = {
			'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
	};
	
	public static int newRandomInt(int length) {
		return Integer.parseInt(newRandomStr(length));
	}
	
	public static String newRandomStr(int length) {
		Random random = new Random();
		char[] arr = new char[length];
		for (int i = 0; i < length; i++) {
			arr[i] = DIGITS[random.nextInt(i == 0 ? (DIGITS.length - 1) : DIGITS.length)];
		}
		return String.valueOf(arr);
	}
	
	public static int newRandomUserId() {
		int id;
		do {
			id = newRandomInt(USER_ID_LENGTH);
		} while ("9".equals(String.valueOf(id).substring(0, 1)));
		return id;
	}
	
	public static int newRandomGroupId() {
		int id;
		do {
			id = newRandomInt(USER_ID_LENGTH);
		} while (!"9".equals(String.valueOf(id).substring(0, 1)));
		return id;
	}
	
	public static int newRandomPostId() {
		return newRandomInt(POST_ID_LENGTH);
	}
}
