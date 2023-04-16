package com.fronchak.movie_flix_spring3.dtos.genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class GenreInputDTO {

	@NotBlank(message = "Genre's name is required and must be non blank")
	private String name;
	
	//@Pattern(regexp = "(http(s?):)(\\.*)", message = "Image must be a valid url")
	@NotBlank(message = "Genre's image is required and must be non blank")
	private String imageUrl;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
