package com.fronchak.movie_flix_spring3.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fronchak.movie_flix_spring3.dtos.genre.GenreInsertDTO;
import com.fronchak.movie_flix_spring3.dtos.genre.GenreOutputDTO;
import com.fronchak.movie_flix_spring3.dtos.genre.GenreUpdateDTO;
import com.fronchak.movie_flix_spring3.entities.Genre;
import com.fronchak.movie_flix_spring3.exceptions.DatabaseException;
import com.fronchak.movie_flix_spring3.exceptions.ResourceNotFoundException;
import com.fronchak.movie_flix_spring3.mappers.GenreMapper;
import com.fronchak.movie_flix_spring3.repositories.GenreRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GenreService {

	@Autowired
	private GenreRepository repository;
	
	@Autowired
	private GenreMapper mapper;
	
	@Transactional
	public GenreOutputDTO save(GenreInsertDTO inputDTO) {
		Genre entity = new Genre();
		mapper.copyDTOToEntity(inputDTO, entity);
		entity = repository.save(entity);
		return mapper.convertEntityToDTO(entity);
	}
	
	@Transactional
	public GenreOutputDTO update(GenreUpdateDTO inputDTO, Long id) {
		try {
			Genre entity = repository.getReferenceById(id);
			mapper.copyDTOToEntity(inputDTO, entity);
			entity = repository.save(entity);
			return mapper.convertEntityToDTO(entity);			
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Genre not found by ID: " + id);
		}
	}
	
	public GenreOutputDTO findById(Long id) {
		Genre entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Genre not found by ID: " + id));
		return mapper.convertEntityToDTO(entity);
	}
	
	public List<GenreOutputDTO> findAll() {
		List<Genre> entities = repository.findAll();
		return mapper.convertEntitiesToDTOs(entities);
	}
	
	public void deleteById(Long id) {
		try {
			repository.deleteById(id);			
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Genre not found by ID: " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("This category cannot be deleted");
		}
	}
}
