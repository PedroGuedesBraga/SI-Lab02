package com.musicManager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Musica {
	
	@Id
	@GeneratedValue
	private Integer id;
	@Column
	private String nome;
	@Column
	private String artista;
	@Column
	private String album;
	@Column
	private String ano;
	@Column
	private String tempoDeDuracao;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getTempoDeDuracao() {
		return tempoDeDuracao;
	}
	public void setTempoDeDuracao(String tempoDeDuracao) {
		this.tempoDeDuracao = tempoDeDuracao;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	

}
