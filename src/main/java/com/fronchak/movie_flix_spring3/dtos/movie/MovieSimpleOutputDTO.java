package com.fronchak.movie_flix_spring3.dtos.movie;

import java.io.Serializable;

import com.fronchak.movie_flix_spring3.entities.Movie;

public class MovieSimpleOutputDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private Double rating;
	private String imageUrl;
	
	public MovieSimpleOutputDTO() {}
	
	public MovieSimpleOutputDTO(Long id, String title, Double rating, String imageUrl) {
		this.id = id;
		this.title = title;
		this.rating = rating;
		this.imageUrl = imageUrl;
	}

	public MovieSimpleOutputDTO(Movie entity) {
		this(entity.getId(), entity.getTitle(), entity.getRating(), entity.getImageUrl());
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
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
}
