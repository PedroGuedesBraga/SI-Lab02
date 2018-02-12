package com.musicManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musicManager.model.Musica;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Integer> {
	
	

}
