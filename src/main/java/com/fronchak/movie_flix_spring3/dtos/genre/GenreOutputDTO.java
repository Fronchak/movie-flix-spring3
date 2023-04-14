package com.fronchak.movie_flix_spring3.dtos.genre;

import com.fronchak.movie_flix_spring3.entities.Genre;

public class GenreOutputDTO {

	private Long id;
	private String name;
	private String imageUrl;
	
	public GenreOutputDTO() {}
	
	public GenreOutputDTO(Long id, String name, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
	}

	public GenreOutputDTO(Genre genre) {
		this(genre.getId(), genre.getName(), genre.getImageUrl());
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
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
