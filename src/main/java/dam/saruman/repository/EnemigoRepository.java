package dam.saruman.repository;

/*
Esto es para operaciones CRUD
 */

import dam.saruman.entity.Enemigo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnemigoRepository extends MongoRepository<Enemigo, String> {

    boolean existsByNombre(String nombre);

    List<Enemigo> findByNombreContainingIgnoreCase(String nombre);
}