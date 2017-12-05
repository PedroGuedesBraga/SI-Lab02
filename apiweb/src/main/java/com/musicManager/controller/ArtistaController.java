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

import com.musicManager.model.Artista;
import com.musicManager.service.ArtistaService;

@Controller
public class ArtistaController {
	
	@Autowired
	ArtistaService artistaService;
	
	
	@RequestMapping(value = "/artistas", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artista> adicionarArtista(@RequestBody Artista artista){
		if(artistaService.adicionarArtista(artista)) {
			return new ResponseEntity<>(artista, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	
	@RequestMapping(value = "/artistas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Artista>> getArtistas(){
		return new ResponseEntity<>(artistaService.getArtistas(), HttpStatus.OK);
	}
	
	

}
