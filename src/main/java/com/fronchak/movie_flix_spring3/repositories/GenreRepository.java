package com.fronchak.movie_flix_spring3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fronchak.movie_flix_spring3.entities.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

	Genre findByName(String name);
}
