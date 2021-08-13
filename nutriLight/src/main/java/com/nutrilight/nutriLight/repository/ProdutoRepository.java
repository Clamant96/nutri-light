package com.nutrilight.nutriLight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nutrilight.nutriLight.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
