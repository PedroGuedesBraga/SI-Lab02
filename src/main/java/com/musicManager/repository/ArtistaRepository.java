package com.musicManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musicManager.model.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Integer> {

}
