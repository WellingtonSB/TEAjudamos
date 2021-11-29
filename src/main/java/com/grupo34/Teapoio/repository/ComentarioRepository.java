package com.grupo34.Teapoio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo34.Teapoio.domain.Comentarios;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentarios, Integer>{

}
