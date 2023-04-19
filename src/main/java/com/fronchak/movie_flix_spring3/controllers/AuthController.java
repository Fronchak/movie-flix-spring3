package com.fronchak.movie_flix_spring3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fronchak.movie_flix_spring3.dtos.user.TokenOutputDTO;
import com.fronchak.movie_flix_spring3.dtos.user.UserInputAuxDTO;
import com.fronchak.movie_flix_spring3.dtos.user.UserInputDTO;
import com.fronchak.movie_flix_spring3.dtos.user.UserLoginDTO;
import com.fronchak.movie_flix_spring3.services.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private AuthService service;
	
	@PostMapping(value = "/register")
	public ResponseEntity<TokenOutputDTO> register(@RequestBody UserInputDTO inputDTO) {
		TokenOutputDTO token = service.register(inputDTO);
		return ResponseEntity.ok().body(token);
	}
	
	@PostMapping(value = "/registerAux")
	public ResponseEntity<TokenOutputDTO> registerAux(@RequestBody UserInputAuxDTO inputDTO) {
		TokenOutputDTO token = service.registerAux(inputDTO);
		return ResponseEntity.ok().body(token);
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<TokenOutputDTO> login(@RequestBody UserLoginDTO inputDTO) {
		TokenOutputDTO token = service.login(inputDTO);
		return ResponseEntity.ok().body(token);
	}
}
