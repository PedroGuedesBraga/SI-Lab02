package com.musicManager.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Usuario{
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column
	private String nome;
	
	@Column(unique = true)
	private String email;
	
	@Column
	private String senha;
	
	@OneToMany
	private List<Playlist> playlists = new LinkedList<Playlist>();
	
	
	
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
				p.adicionaMusica(musica);
				return true;
			}
		}
		return false;
	}
}
