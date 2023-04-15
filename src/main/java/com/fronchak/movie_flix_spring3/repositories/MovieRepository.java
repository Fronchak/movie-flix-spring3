package com.fronchak.movie_flix_spring3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fronchak.movie_flix_spring3.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	Movie findByTitle(String title);
}
