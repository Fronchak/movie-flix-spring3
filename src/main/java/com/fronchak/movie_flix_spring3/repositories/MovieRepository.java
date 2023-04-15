package com.fronchak.movie_flix_spring3.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fronchak.movie_flix_spring3.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	Movie findByTitle(String title);
	
	@Query("SELECT DISTINCT obj FROM Movie obj INNER JOIN obj.genres generos WHERE " +
			"(obj.rating >= :rating) " +
			"AND " +
			"(UPPER(obj.title) LIKE UPPER( CONCAT('%', :title , '%')))")
	Page<Movie> findAllFiltered(Double rating, String title, Pageable pageable);
}
