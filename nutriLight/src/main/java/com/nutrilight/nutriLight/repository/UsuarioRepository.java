package com.nutrilight.nutriLight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.nutrilight.nutriLight.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Optional<Usuario> findByUsername(String username);
	
	public List<Usuario> findAllByUsernameContainingIgnoreCase(String username);

}
