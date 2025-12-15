package dam.saruman.repository;

/*
Esto es para operaciones CRUD
 */

import dam.saruman.entity.Enemigo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnemigoRepository extends MongoRepository<Enemigo, String> {}