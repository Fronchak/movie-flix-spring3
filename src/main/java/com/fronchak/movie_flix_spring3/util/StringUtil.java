package com.fronchak.movie_flix_spring3.util;

public class StringUtil {

	public static String cleanString(String text) {
		return text == null ? null : text.trim().replaceAll("\s{2,}", " ");
	}
}
