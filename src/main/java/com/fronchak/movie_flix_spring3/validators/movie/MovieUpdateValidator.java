package com.fronchak.movie_flix_spring3.validators.movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.fronchak.movie_flix_spring3.dtos.movie.MovieUpdateDTO;
import com.fronchak.movie_flix_spring3.entities.Movie;
import com.fronchak.movie_flix_spring3.exceptions.FieldMessage;
import com.fronchak.movie_flix_spring3.repositories.MovieRepository;
import com.fronchak.movie_flix_spring3.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MovieUpdateValidator implements ConstraintValidator<MovieUpdateValid, MovieUpdateDTO> {

	@Autowired
	private MovieRepository repository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public boolean isValid(MovieUpdateDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> errors = new ArrayList<>();
		
		Map<String, String> uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long id;
		try {
			id = Long.parseLong(uriVars.get("id"));
		}
		catch(NumberFormatException e) {
			return true;
		}
		
		String title = dto.getTitle();
		if(title != null && !title.isBlank()) {
			Movie entity = repository.findByTitle(StringUtil.cleanString(title));
			if(entity != null && !id.equals(entity.getId())) {
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
