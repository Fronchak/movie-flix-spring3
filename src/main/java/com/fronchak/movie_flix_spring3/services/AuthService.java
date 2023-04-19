package com.fronchak.movie_flix_spring3.services;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fronchak.movie_flix_spring3.config.JwtService;
import com.fronchak.movie_flix_spring3.dtos.user.TokenOutputDTO;
import com.fronchak.movie_flix_spring3.dtos.user.UserInputAuxDTO;
import com.fronchak.movie_flix_spring3.dtos.user.UserInputDTO;
import com.fronchak.movie_flix_spring3.dtos.user.UserLoginDTO;
import com.fronchak.movie_flix_spring3.entities.User;
import com.fronchak.movie_flix_spring3.repositories.RoleRepository;
import com.fronchak.movie_flix_spring3.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public TokenOutputDTO register(UserInputDTO inputDTO) {
		User user = new User();
		user.setEmail(inputDTO.getEmail());
		user.setPassword(passwordEncoder.encode(inputDTO.getPassword()));
		user.addRole(roleRepository.getReferenceById(1L));
		user = userRepository.save(user);
		HashMap<String, Object> map = new HashMap<>();
		map.put("roles", user.getRoles().stream().map((role) -> role.getAuthority()).collect(Collectors.toList()));
		String token = jwtService.generateToken(map, user);
		return new TokenOutputDTO(token);
	}
	
	@Transactional
	public TokenOutputDTO registerAux(UserInputAuxDTO inputDTO) {
		User user = convertDTOToEntity(inputDTO);
		user = userRepository.save(user);
		HashMap<String, Object> map = new HashMap<>();
		map.put("roles", user.getRoles().stream().map((role) -> role.getAuthority()).collect(Collectors.toList()));
		String token = jwtService.generateToken(map, user);
		return new TokenOutputDTO(token);
	}
	
	private User convertDTOToEntity(UserInputAuxDTO inputDTO) {
		User user = new User();
		user.setEmail(inputDTO.getEmail());
		user.setPassword(passwordEncoder.encode(inputDTO.getPassword()));
		inputDTO.getIdRoles().forEach((id) -> user.addRole(roleRepository.getReferenceById(id)));
		return user;
	}
	
	@Transactional(readOnly = true)
	public TokenOutputDTO login(UserLoginDTO dto) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
		User user = userRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		HashMap<String, Object> map = new HashMap<>();
		map.put("roles", user.getRoles().stream().map((role) -> role.getAuthority()).collect(Collectors.toList()));
		String token = jwtService.generateToken(map, user);
		return new TokenOutputDTO(token);
	}
}
