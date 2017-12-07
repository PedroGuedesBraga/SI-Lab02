package com.musicManager.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicManager.model.Musica;
import com.musicManager.model.Playlist;
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
	
	//TIRAR TRATAMENTO POR COLUMN(UNIQUE=TRUE)
	//Add playlist a um usuario(email dele) passado no parametro, retorna true se foi add ou false se n foi
	public boolean adicionarPlaylist(Playlist playlist, String nomeDoUsuario) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		for(Usuario u : usuarios) {
			if(u.getNome().equals(nomeDoUsuario)) {
				boolean retorno = u.adicionaPlaylist(playlist);
				usuarioRepository.save(u);
				return retorno;
			}
		}
		return false; //Nenhum usuario com o nome igual o da passado no parametro foi encontrado
	}
	
	//Adiciona uma musica em uma playlist de um determinado usuario
	public boolean adicionarMusicaEmPlaylist(String emailDoUsuario, String nomeDaPlaylist, Musica musica) {
		for(Usuario u : usuarioRepository.findAll()) {
			if (u.getEmail().equals(emailDoUsuario)) {
				return u.adicionaMusicaEmPlaylist(nomeDaPlaylist, musica);
			}
		}
		return false; //Se o usuario nao foi achado
	}
	
	//Retorna todas as playlists de um usuario pelo nome dele - Se ele nao existir, retorna uma colecao de playlists vazia
	public List<Playlist> getPlaylists(String emailDoUsuario){
		for (Usuario u : usuarioRepository.findAll()) {
			if(u.getEmail().equals(emailDoUsuario)) {
				return u.getPlaylists();
			}
		}
		return new LinkedList<Playlist>();
	}
	
	
	
}
