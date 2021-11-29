package com.grupo34.Teapoio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo34.Teapoio.domain.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Integer> {

}