package com.nutrilight.nutriLight.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nutrilight.nutriLight.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	@Query(value="SELECT * FROM nutri_light.produtos WHERE usuario_id = :id", nativeQuery = true)
	public List<Produto> findAllByUsuario(@Param("id") long id);

}
