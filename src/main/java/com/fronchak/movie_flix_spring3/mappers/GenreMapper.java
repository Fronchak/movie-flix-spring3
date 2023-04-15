package com.fronchak.movie_flix_spring3.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fronchak.movie_flix_spring3.dtos.genre.GenreInputDTO;
import com.fronchak.movie_flix_spring3.dtos.genre.GenreOutputDTO;
import com.fronchak.movie_flix_spring3.entities.Genre;
import com.fronchak.movie_flix_spring3.util.StringUtil;

@Service
public class GenreMapper {

	public void copyDTOToEntity(GenreInputDTO dto, Genre entity) {
		entity.setName(StringUtil.cleanString(dto.getName()));
		entity.setImageUrl(dto.getImageUrl());
	}
	
	public GenreOutputDTO convertEntityToDTO(Genre entity) {
		return new GenreOutputDTO(entity);
	}
	
	public List<GenreOutputDTO> convertEntitiesToDTOs(List<Genre> entities) {
		return entities.stream()
				.map((entity) -> convertEntityToDTO(entity))
				.collect(Collectors.toList());
	}
}
