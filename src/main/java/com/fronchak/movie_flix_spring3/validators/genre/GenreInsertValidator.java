package com.fronchak.movie_flix_spring3.validators.genre;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fronchak.movie_flix_spring3.dtos.genre.GenreInsertDTO;
import com.fronchak.movie_flix_spring3.entities.Genre;
import com.fronchak.movie_flix_spring3.exceptions.FieldMessage;
import com.fronchak.movie_flix_spring3.repositories.GenreRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenreInsertValidator implements ConstraintValidator<GenreInsertValid, GenreInsertDTO> {

	@Autowired
	private GenreRepository repository;
	
	@Override
	public boolean isValid(GenreInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> errors = new ArrayList<>();
		
		String name = dto.getName();
		if(name != null && !name.isBlank()) {
			Genre entity = repository.findByName(name);
			if(entity != null) {
				errors.add(new FieldMessage("name", "This name is already register"));
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
