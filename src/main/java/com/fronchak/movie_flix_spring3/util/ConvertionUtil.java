package com.fronchak.movie_flix_spring3.util;

import com.fronchak.movie_flix_spring3.exceptions.BadRequestException;

public class ConvertionUtil {

	public static Long convertIdParam(String id) {
		try {
			return Long.parseLong(id);			
		}
		catch(NumberFormatException e) {
			throw new BadRequestException("ID param must be a number");
		}

	}
}
