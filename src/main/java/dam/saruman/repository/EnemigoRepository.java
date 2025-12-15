package dam.saruman.repository;

/*
Esto es para operaciones CRUD
 */

import dam.saruman.entity.Enemigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnemigoRepository extends JpaRepository<Enemigo, Long> {}
