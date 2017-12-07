package com.musicManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.musicManager.model.Musica;
import com.musicManager.model.Playlist;
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
	
	//Adiciona uma nova playlist
	@RequestMapping(value="usuarios/playlist/{nomeDoUsuario}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Playlist> adicionarPlaylist(@RequestBody Playlist playlist, @PathVariable String nomeDoUsuario){
		
		if(usuarioService.adicionarPlaylist(playlist, nomeDoUsuario)) {
			return new ResponseEntity<>(playlist, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE); 
	}
	
	
	//Adiciona uma musica em uma playlist ja existente de um usuario
	@RequestMapping(value="/usuarios/playlist/{emailDoUsuario}/{nomeDaPlaylist}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Musica> adicionarMusicaEmPlaylist(@PathVariable String emailDoUsuario, @PathVariable String nomeDaPlaylist, @RequestBody Musica musica){
		if(usuarioService.adicionarMusicaEmPlaylist(emailDoUsuario, nomeDaPlaylist, musica)) {
			return new ResponseEntity<>(musica, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	//Retorna todas as playlists de um usuario
	@RequestMapping(value="/usuarios/playlist/{emailDoUsuario}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Playlist>> getPlaylists(@PathVariable String emailDoUsuario){
		return new ResponseEntity<List<Playlist>>(usuarioService.getPlaylists(emailDoUsuario), HttpStatus.OK);
	}
	
	
	

}
