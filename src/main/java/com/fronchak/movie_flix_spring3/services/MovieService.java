package com.fronchak.movie_flix_spring3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fronchak.movie_flix_spring3.dtos.movie.MovieInputDTO;
import com.fronchak.movie_flix_spring3.dtos.movie.MovieInsertDTO;
import com.fronchak.movie_flix_spring3.dtos.movie.MovieOutputDTO;
import com.fronchak.movie_flix_spring3.dtos.movie.MovieSimpleOutputDTO;
import com.fronchak.movie_flix_spring3.dtos.movie.MovieUpdateDTO;
import com.fronchak.movie_flix_spring3.entities.Movie;
import com.fronchak.movie_flix_spring3.exceptions.DatabaseException;
import com.fronchak.movie_flix_spring3.exceptions.ResourceNotFoundException;
import com.fronchak.movie_flix_spring3.mappers.MovieMapper;
import com.fronchak.movie_flix_spring3.repositories.GenreRepository;
import com.fronchak.movie_flix_spring3.repositories.MovieRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private MovieMapper mapper;
	
	@Transactional
	public MovieOutputDTO save(MovieInsertDTO inputDTO) {
		Movie entity = new Movie();
		copyDTOToEntity(inputDTO, entity);
		entity = movieRepository.save(entity);
		return mapper.convertEntityToDTO(entity);
	}
	
	private void copyDTOToEntity(MovieInputDTO dto, Movie entity) {
		mapper.copyDTOToEntity(dto, entity);
		entity.getGenres().clear();
		dto.getIdGenres().forEach((idGenre) -> entity.addGenre(genreRepository.getReferenceById(idGenre)));
	}
	
	@Transactional
	public MovieOutputDTO update(MovieUpdateDTO inputDTO, Long id) {
		try {
			Movie entity = movieRepository.getReferenceById(id);
			copyDTOToEntity(inputDTO, entity);
			entity = movieRepository.save(entity);
			return mapper.convertEntityToDTO(entity);	
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Movie not found by ID: " + id);
		}
	}
	
	@Transactional(readOnly = true)
	public MovieOutputDTO findById(Long id) {
		Movie entity = movieRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Movie not found by ID: " + id));
		return mapper.convertEntityToDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public Page<MovieSimpleOutputDTO> findAll(String title, Integer idGenre, Double rating, Pageable pageable) {
		Page<Movie> entities = movieRepository.findAll(pageable);
		return mapper.convertEntitiesToSimpleDTOs(entities);
	}
	
	public void deleteById(Long id) {
		try {
			movieRepository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("This movie cannot be deleted");
		}
	}
}
