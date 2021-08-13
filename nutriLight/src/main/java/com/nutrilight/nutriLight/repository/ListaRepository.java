package com.nutrilight.nutriLight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nutrilight.nutriLight.model.Lista;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Long> {

}
