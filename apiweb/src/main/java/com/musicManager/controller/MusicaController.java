package com.musicManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.musicManager.model.Musica;
import com.musicManager.service.MusicaService;

@Controller
public class MusicaController {

	@Autowired
	private MusicaService musicaService;
	
	@RequestMapping(value = "/musicas", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Musica> adicionarMusica(@RequestBody Musica musica){
		if(musicaService.adicionarMusica(musica)) {
			return new ResponseEntity<>(musica, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	@RequestMapping(value="/musicas", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Musica>> getMusicas(){
		List<Musica> musicas = musicaService.getMusicas();
		return new ResponseEntity<List<Musica>>(musicas, HttpStatus.OK);
	}
	
}
