package com.musicManager.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Playlist {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column
	private String nome;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Musica> musicas = new LinkedList<Musica>();
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Musica> getMusicas() {
		return musicas;
	}
	public void setMusicas(List<Musica> musicas) {
		this.musicas = musicas;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	public boolean adicionaMusica(Musica musica){
		for (Musica m : musicas) {
			if(m.getNome().equals(musica.getNome()) && m.getAlbum().equals(musica.getAlbum())) {
				return false;
			}
		}
		musicas.add(musica);
		return true;
	}
}
