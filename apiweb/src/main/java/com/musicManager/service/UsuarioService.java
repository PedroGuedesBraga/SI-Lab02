package com.musicManager.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicManager.model.Usuario;
import com.musicManager.repository.UsuarioRepository;

@Service
public class UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//Salva o usuario e retorna ele. //**Ver como tratar o fato de existir uma column que é unica no BD!!!!
	public Usuario cadastrar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	//Retorna o usuario achado no BD a partir do email e senha dados
	public Usuario login(String email, String senha) {
		List<Usuario> todosUsuarios = usuarioRepository.findAll();
		for (Usuario u : todosUsuarios) {
			if(u.getEmail().equals(email) && u.getSenha().equals(senha)) {
				return u;	
			}
		}
		return new Usuario(); //Retorna uma flag
	}
	
}
