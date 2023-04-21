package com.fronchak.movie_flix_spring3.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fronchak.movie_flix_spring3.dtos.movie.MovieInsertDTO;
import com.fronchak.movie_flix_spring3.dtos.movie.MovieOutputDTO;
import com.fronchak.movie_flix_spring3.dtos.movie.MovieSimpleOutputDTO;
import com.fronchak.movie_flix_spring3.dtos.movie.MovieUpdateDTO;
import com.fronchak.movie_flix_spring3.services.MovieService;
import com.fronchak.movie_flix_spring3.util.ConvertionUtil;
import com.fronchak.movie_flix_spring3.util.StringUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

	@Autowired
	private MovieService service;
	
	@PostMapping
	public ResponseEntity<MovieOutputDTO> save(@RequestBody @Valid MovieInsertDTO inputDTO) {
		MovieOutputDTO outputDTO = service.save(inputDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(outputDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(outputDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<MovieOutputDTO> update(@RequestBody @Valid MovieUpdateDTO inputDTO, @PathVariable String id) {
		MovieOutputDTO outputDTO = service.update(inputDTO, ConvertionUtil.convertIdParam(id));
		return ResponseEntity.ok().body(outputDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieOutputDTO> findById(@PathVariable String id) {
		MovieOutputDTO outputDTO = service.findById(ConvertionUtil.convertIdParam(id));
		return ResponseEntity.ok().body(outputDTO);
	}
	
	@GetMapping	
	public ResponseEntity<Page<MovieSimpleOutputDTO>> findAll(
			@RequestParam(value = "title", defaultValue = "") String title,
			@RequestParam(value = "idGenre", defaultValue = "0L") Long idGenre,
			@RequestParam(value = "rating", defaultValue = "0.0") Double rating,
			Pageable pageable) {
		Page<MovieSimpleOutputDTO> dtos = service.findAll(StringUtil.cleanString(title), idGenre, rating, pageable);
		return ResponseEntity.ok().body(dtos);
	}
	
	@GetMapping(value = "/genre")
	public ResponseEntity<Page<MovieSimpleOutputDTO>> findAllByGenre(
			@RequestParam(value = "idGenre", defaultValue = "0") Long idGenre,
			Pageable pageable) {
		Page<MovieSimpleOutputDTO> dtos = service.findAllByGenre(idGenre, pageable);
		return ResponseEntity.ok().body(dtos);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.deleteById(ConvertionUtil.convertIdParam(id));
		return ResponseEntity.noContent().build();
	}
}
