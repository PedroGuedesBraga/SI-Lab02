package com.musicManager.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicManager.model.Musica;
import com.musicManager.repository.MusicaRepository;

@Service
public class MusicaService {
	
	@Autowired
	private MusicaRepository musicaRepository;
	
	//true se a musica foi add, false CC.
	public boolean adicionarMusica(Musica musica){
		if(!anAlbumContainsMusic(musica)) {
			musicaRepository.save(musica);
			return true;
		}
		return false;
	}
	
	
	//Um album nao pode ter duas musicas com o mesmo nome, este metodo recebe uma musica e ve se existe um album com uma musica com o mesmo nome	
	private boolean anAlbumContainsMusic(Musica musica){
		List<Musica> musicas = musicaRepository.findAll();
		for (Musica m : musicas){
			if((m.getNome().equals(musica.getNome())) &&  m.getAlbum().equals(musica.getAlbum())){
				return true;
			}
		}
		return false;
	}
	
	public List<Musica> getMusicas(){
		return musicaRepository.findAll();
	}

	
}
