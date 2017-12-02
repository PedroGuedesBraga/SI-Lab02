package com.musicManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.musicManager.model.Usuario;
import com.musicManager.service.UsuarioService;

@RestController
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/usuarios/cadastro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario){
		Usuario usuarioSalvo = usuarioService.cadastrar(usuario);
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/usuarios/login", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Usuario> loginUsuario(@RequestBody Usuario usuario){
		Usuario usuarioLogado = usuarioService.login(usuario.getEmail(), usuario.getSenha());
		//Se o usuario retornado pelo login nao for um "usuario null", retorna o usuario logado 
		if(usuarioLogado.getEmail() != null && usuarioLogado.getSenha() != null) {
			return new ResponseEntity<>(usuarioLogado, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	

}
