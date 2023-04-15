package com.fronchak.movie_flix_spring3.validators.movie;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fronchak.movie_flix_spring3.dtos.movie.MovieInsertDTO;
import com.fronchak.movie_flix_spring3.entities.Movie;
import com.fronchak.movie_flix_spring3.exceptions.FieldMessage;
import com.fronchak.movie_flix_spring3.repositories.MovieRepository;
import com.fronchak.movie_flix_spring3.util.StringUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MovieInsertValidator implements ConstraintValidator<MovieInsertValid, MovieInsertDTO> {

	@Autowired
	private MovieRepository repository;
	
	@Override
	public boolean isValid(MovieInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> errors = new ArrayList<>();
		
		String title = dto.getTitle();
		if(title != null && !title.isBlank()) {
			Movie entity = repository.findByTitle(StringUtil.cleanString(title));
			if(entity != null) {
				errors.add(new FieldMessage("title", "This title is already register"));
			}			
		}

		for (FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		return errors.isEmpty();
	}

}
