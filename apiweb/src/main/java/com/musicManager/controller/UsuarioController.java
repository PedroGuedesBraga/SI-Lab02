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

import com.musicManager.model.Artista;
import com.musicManager.model.Musica;
import com.musicManager.model.Playlist;
import com.musicManager.model.Usuario;
import com.musicManager.service.ArtistaService;
import com.musicManager.service.UsuarioService;

@RestController
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ArtistaService artistaService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/usuarios/cadastro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario){
		boolean foiCadastrado = usuarioService.cadastrar(usuario);
		if(foiCadastrado) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/usuarios/login", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Usuario> loginUsuario(@RequestBody Usuario usuario){
		Usuario usuarioLogado = usuarioService.login(usuario.getNome(), usuario.getEmail(), usuario.getSenha());
		//Se o usuario retornado pelo login nao for um "usuario null", retorna o usuario logado 
		if(usuarioLogado.getNome() != null && usuarioLogado.getEmail() != null && usuarioLogado.getSenha() != null) {
			return new ResponseEntity<>(usuarioLogado, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	//Adiciona uma nova playlist (%20 para tratar espacos)
	@RequestMapping(value="usuarios/playlist/{nomeDoUsuario}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Playlist> adicionarPlaylist(@RequestBody Playlist playlist, @PathVariable String nomeDoUsuario){
		
		if(usuarioService.adicionarPlaylist(playlist, nomeDoUsuario)) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);  
	}
	
	
	//Adiciona uma musica em uma playlist ja existente de um usuario - //A MUSICA RECEBIDA JA DEVE TER UM ID!!!!!!!!!!!!!!
	@RequestMapping(value="/usuarios/playlist/{nomeDoUsuario}/{nomeDaPlaylist}",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Musica> adicionarMusicaEmPlaylist(@PathVariable String nomeDoUsuario, @PathVariable String nomeDaPlaylist, @RequestBody Musica musica){
		if(usuarioService.adicionarMusicaEmPlaylist(nomeDoUsuario, nomeDaPlaylist, musica)) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	//Retorna todas as playlists de um usuario
	@RequestMapping(value="/usuarios/playlist/{nomeDoUsuario}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Playlist>> getPlaylists(@PathVariable String nomeDoUsuario){
		return new ResponseEntity<List<Playlist>>(usuarioService.getPlaylists(nomeDoUsuario), HttpStatus.OK);
	}
	
	//Receber um artista ja existente no sistema
	@RequestMapping(value="/usuarios/favoritos/{id}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artista> adicionarArtistaFavorito(@RequestBody Artista favorito, @PathVariable Integer id){
		if(usuarioService.adicionaArtistaFavorito(favorito, id)) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	//Recebe os artistas favoritos de um usuario de determinado id
	@RequestMapping(value="/usuarios/favoritos/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Artista>> getFavoritos(@PathVariable Integer id){
		List<Artista> result = usuarioService.getFavoritos(id);
		if(!result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	//Recebe o id do usuario e o id do artista favorito e deleta esse artista da colecao de artistas favoritos do usuario
	@RequestMapping(value="/usuarios/favoritos/{idUsuario}/{idFavorito}", method=RequestMethod.DELETE)
	public ResponseEntity<Artista> deletarArtistaDosFavoritos(@PathVariable Integer idUsuario, @PathVariable Integer idFavorito){
		if(usuarioService.deletarArtistaDosFavoritos(idUsuario, idFavorito)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	

}
