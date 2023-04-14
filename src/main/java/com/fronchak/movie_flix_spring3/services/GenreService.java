package com.fronchak.movie_flix_spring3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fronchak.movie_flix_spring3.repositories.GenreRepository;

@Service
public class GenreService {

	@Autowired
	private GenreRepository repository;
}
