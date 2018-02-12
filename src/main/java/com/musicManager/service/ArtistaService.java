package com.musicManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicManager.model.Artista;
import com.musicManager.repository.ArtistaRepository;

@Service
public class ArtistaService {
	
	@Autowired
	private ArtistaRepository artistaRepository;
	
	public Artista adicionarArtista(Artista artista) {
		if(!existeArtista(artista)){
			return artistaRepository.save(artista);
			
		}
		return new Artista(); //Retorna uma flag
		
	}
	
	private boolean existeArtista(Artista artista) {
		List<Artista> artistas = artistaRepository.findAll();
		for (Artista a : artistas) {
			if(a.getNome().equals(artista.getNome())) {
				return true;
			}
		}
		return false;
	}
	
	public List<Artista> getArtistas(){
		return artistaRepository.findAll();
	}
	
	
}
