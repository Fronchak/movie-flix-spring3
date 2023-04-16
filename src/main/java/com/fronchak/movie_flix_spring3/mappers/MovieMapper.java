package com.fronchak.movie_flix_spring3.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fronchak.movie_flix_spring3.dtos.movie.MovieInputDTO;
import com.fronchak.movie_flix_spring3.dtos.movie.MovieOutputDTO;
import com.fronchak.movie_flix_spring3.dtos.movie.MovieSimpleOutputDTO;
import com.fronchak.movie_flix_spring3.entities.Movie;
import com.fronchak.movie_flix_spring3.util.StringUtil;

@Service
public class MovieMapper {

	@Autowired
	private GenreMapper genreMapper;
	
	public void copyDTOToEntity(MovieInputDTO dto, Movie entity) {
		entity.setTitle(StringUtil.cleanString(dto.getTitle()));
		entity.setSynopsis(StringUtil.cleanString(dto.getSynopsis()));
		entity.setLaunchYear(dto.getLaunchYear());
		entity.setRating(dto.getRating());
		entity.setImageUrl(dto.getImageUrl());
	}
	
	public MovieSimpleOutputDTO convertEntityToSimpleDTO(Movie entity) {
		return new MovieSimpleOutputDTO(entity);	
	}
	
	public Page<MovieSimpleOutputDTO> convertEntitiesToSimpleDTOs(Page<Movie> entities) {
		return entities.map((entity) -> convertEntityToSimpleDTO(entity));
	}
	
	public MovieOutputDTO convertEntityToDTO(Movie entity) {
		MovieOutputDTO dto = new MovieOutputDTO(entity);
		dto.setGenres(genreMapper.convertEntitiesToDTOs(entity.getGenres()));
		return dto;
	}
}
