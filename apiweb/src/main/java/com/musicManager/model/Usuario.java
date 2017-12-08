package com.musicManager.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Usuario{
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column
	private String nome;
	
	@Column
	private String email;
	
	@Column
	private String senha;
	
	@OneToMany(cascade = CascadeType.ALL) //OneToMany - Relacionamento Usuario para muitas playlists. Em cascata (Cascade - Usado para persistir tambem as entidades relacionadas)
	private List<Playlist> playlists = new ArrayList<Playlist>();
	
	@ManyToMany(cascade = CascadeType.ALL) //Relacionamento de muitos para muitos. Um usuario se relaciona com varios artistas assim como um artista se relaciona com varios usuarios
	private List<Artista> artistasFavoritos = new ArrayList<Artista>();
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Playlist> getPlaylists() {
		return playlists;
	}
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
	public List<Artista> getArtistasFavoritos() {
		return artistasFavoritos;
	}
	public void setArtistasFavoritos(List<Artista> artistasFavoritos) {
		this.artistasFavoritos = artistasFavoritos;
	}
	
	
	
	//Add playlist no usuario
	public boolean adicionaPlaylist(Playlist playlist) {
		for(Playlist p : this.getPlaylists()) {
			if(p.getNome().equals(playlist.getNome())){
				return false; //Duas playlists nao podem ter o mesmo nome
			}
		}
		this.getPlaylists().add(playlist);
		return true;
	}
	
	//Add musica em uma playlist q o usuario ja tem
	public boolean adicionaMusicaEmPlaylist(String nomeDaPlaylist, Musica musica) {
		for(Playlist p : this.getPlaylists()) {
			if(p.getNome().equals(nomeDaPlaylist)) {
				return p.adicionaMusica(musica);
			}
		}
		return false;
	}
	
	
	public boolean adicionaArtistaFavorito(Artista favorito) {
		for(Artista a : this.getArtistasFavoritos()) {
			if(a.getNome().equals(favorito.getNome())) {
				return false;
			}
		}
		this.getArtistasFavoritos().add(favorito);
		return true;
	}
	
	public boolean removeArtistaFavorito(Integer idFavorito) {
		List<Artista> favoritos = this.getArtistasFavoritos();
		for(int i = 0; i < favoritos.size(); i++) {
			if(favoritos.get(i).getId().equals(idFavorito)) {
				favoritos.remove(i);
				return true;
			}
		}
		return false; //Nao foi achado artista com esse id.
	}
	
}
