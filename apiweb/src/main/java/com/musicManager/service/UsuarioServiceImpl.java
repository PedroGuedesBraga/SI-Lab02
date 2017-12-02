package com.musicManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicManager.model.Usuario;
import com.musicManager.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public void cadastrar(Usuario usuario) {
		usuarioRepository.save(usuario);
		
	}
	
}
