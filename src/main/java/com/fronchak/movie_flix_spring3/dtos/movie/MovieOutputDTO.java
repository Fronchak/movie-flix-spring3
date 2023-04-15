package com.fronchak.movie_flix_spring3.dtos.movie;

import java.util.ArrayList;
import java.util.List;

import com.fronchak.movie_flix_spring3.dtos.genre.GenreOutputDTO;
import com.fronchak.movie_flix_spring3.entities.Movie;

public class MovieOutputDTO extends MovieSimpleOutputDTO {

	private static final long serialVersionUID = 1L;

	private String synopsis;
	private Integer lauchYear;
	private List<GenreOutputDTO> genres = new ArrayList<>();
	
	public MovieOutputDTO() {}
	
	public MovieOutputDTO(Movie entity) {
		super(entity);	
		this.synopsis = entity.getSynopsis();
		this.lauchYear = entity.getLauchYear();
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

	public List<GenreOutputDTO> getGenres() {
		return genres;
	}

	public void setGenres(List<GenreOutputDTO> genres) {
		this.genres = genres;
	}
	
	public void addGenre(GenreOutputDTO genre) {
		genres.add(genre);
	}
}
