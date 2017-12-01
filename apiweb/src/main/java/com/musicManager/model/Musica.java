package com.musicManager.model;

public class Musica {
	
	String nome;
	String artista;
	String album;
	String ano;
	String tempoDeDuracao;
	
	
	public Musica(String nome, String artista, String album, String ano, String tempoDeDuracao) {
		this.nome = nome;
		this.artista = artista;
		this.album = album;
		this.ano = ano;
		this.tempoDeDuracao = tempoDeDuracao;
	}
	
	
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
	

}
