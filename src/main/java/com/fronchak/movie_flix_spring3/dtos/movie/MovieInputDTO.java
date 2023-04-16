package com.fronchak.movie_flix_spring3.dtos.movie;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class MovieInputDTO {

	@NotBlank(message = "Movie's title is required and must be non blank")
	private String title;
	
	@NotBlank(message = "Movie's synopsis is required and must be non blank")
	private String synopsis;
	
	@NotNull(message = "Lauch year is required")
	@Min(value = 1900, message = "Lauch year must be greater than 1900")
	@Max(value = 2024, message = "Lauch year cannot be greater than 2024")
	private Integer lauchYear;
	
	@NotNull(message = "Movie's rating is required")
	@Min(value = 0, message = "Movie's rating cannot be lower than 0")
	@Max(value = 10, message = "Movie's rating cannot be greater than 10")
	private Double rating;

	//@Pattern(regexp = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)", message = "Image must be a valid url")
	@NotBlank(message = "Movie's image is required and must be non blank")
	private String imageUrl;
	
	@NotEmpty(message = "Movie should have at least one genre")
	private List<Long> idGenres;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public Integer getLauchYear() {
		return lauchYear;
	}

	public void setLauchYear(Integer lauchYear) {
		this.lauchYear = lauchYear;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Long> getIdGenres() {
		return idGenres;
	}

	public void setIdGenres(List<Long> idGenres) {
		this.idGenres = idGenres;
	}
}
