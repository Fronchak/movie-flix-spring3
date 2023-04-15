package com.fronchak.movie_flix_spring3.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fronchak.movie_flix_spring3.dtos.genre.GenreInsertDTO;
import com.fronchak.movie_flix_spring3.dtos.genre.GenreOutputDTO;
import com.fronchak.movie_flix_spring3.dtos.genre.GenreUpdateDTO;
import com.fronchak.movie_flix_spring3.services.GenreService;
import com.fronchak.movie_flix_spring3.util.ConvertionUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/genres")
public class GenreController {

	@Autowired
	private GenreService service;
	
	@PostMapping
	public ResponseEntity<GenreOutputDTO> save(@RequestBody @Valid GenreInsertDTO inputDTO) {
		GenreOutputDTO outputDTO = service.save(inputDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(outputDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(outputDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenreOutputDTO> update(@RequestBody @Valid GenreUpdateDTO inputDTO, @PathVariable String id) {
		GenreOutputDTO outputDTO = service.update(inputDTO, ConvertionUtil.convertIdParam(id));
		return ResponseEntity.ok().body(outputDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<GenreOutputDTO> findById(@PathVariable String id) {
		GenreOutputDTO outputDTO = service.findById(ConvertionUtil.convertIdParam(id));
		return ResponseEntity.ok().body(outputDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<GenreOutputDTO>> findAll() {
		List<GenreOutputDTO> dtos = service.findAll();
		return ResponseEntity.ok().body(dtos);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String id) {
		service.deleteById(ConvertionUtil.convertIdParam(id));
		return ResponseEntity.noContent().build();
	}
}
