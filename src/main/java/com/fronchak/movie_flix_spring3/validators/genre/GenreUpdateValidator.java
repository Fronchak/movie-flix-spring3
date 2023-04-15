package com.fronchak.movie_flix_spring3.validators.genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.fronchak.movie_flix_spring3.dtos.genre.GenreUpdateDTO;
import com.fronchak.movie_flix_spring3.entities.Genre;
import com.fronchak.movie_flix_spring3.exceptions.FieldMessage;
import com.fronchak.movie_flix_spring3.repositories.GenreRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenreUpdateValidator implements ConstraintValidator<GenreUpdateValid, GenreUpdateDTO> {

	@Autowired
	private GenreRepository repository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public boolean isValid(GenreUpdateDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> errors = new ArrayList<>();
		
		Map<String, String> uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long id;
		try {
			id = Long.parseLong(uriVars.get("id"));
		}
		catch(NumberFormatException e) {
			return true;
		}
		
		String name = dto.getName();
		if(name != null && !name.isBlank()) {
			Genre entity = repository.findByName(name);
			if(entity != null && !entity.getId().equals(id)) {
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
