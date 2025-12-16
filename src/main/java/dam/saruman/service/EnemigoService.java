package dam.saruman.service;

import dam.saruman.entity.Enemigo;
import dam.saruman.repository.EnemigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnemigoService {
    @Autowired
    private EnemigoRepository enemigoRepository;

    public List<Enemigo> obtenerTodos(){
        List<Enemigo> enemigos = enemigoRepository.findAll(Sort.by("id").ascending());
        if(enemigos.isEmpty()){
            System.out.println("Acho que esto ta to triste");
        }else{
            System.out.println("Jefe esto va como una maquina");
            enemigos.forEach(enemigo -> {
                System.out.println("ID: "+enemigo.getId() + " Nombre: "+enemigo.getNombre()+" Pais: "+enemigo.getPais()+" Afiliación: "+enemigo.getAfiliacion_politica());
            });
        }
        return enemigos;
    }// Fin get

    public List<Enemigo> obtenerTodosOrdenadosPorNombre(){
        return enemigoRepository.findAll(Sort.by("nombre").ascending());
    }

    public List<Enemigo> buscarPorNombre(String nombre){
        return enemigoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Enemigo guardar(Enemigo enemigo){
        // Validar nombre mínimo 3 caracteres
        if(enemigo.getNombre() == null || enemigo.getNombre().length() < 3){
            throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres");
        }

        // Validar nombre único
        if(enemigoRepository.existsByNombre(enemigo.getNombre())){
            throw new IllegalArgumentException("Ya existe un enemigo con ese nombre");
        }

        return enemigoRepository.save(enemigo);
    }

    public Enemigo actualizar(String id, Enemigo enemigo){
        // Validar nombre mínimo 3 caracteres
        if(enemigo.getNombre() == null || enemigo.getNombre().length() < 3){
            throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres");
        }

        if(enemigoRepository.existsById(id)){
            // Verificar si el nombre ya existe en otro enemigo
            Enemigo existente = enemigoRepository.findByNombreContainingIgnoreCase(enemigo.getNombre())
                    .stream()
                    .filter(e -> e.getNombre().equalsIgnoreCase(enemigo.getNombre()) && !e.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if(existente != null){
                throw new IllegalArgumentException("Ya existe un enemigo con ese nombre");
            }

            enemigo.setId(id);
            return enemigoRepository.save(enemigo);
        }
        throw new IllegalArgumentException("No se encontró el enemigo con ID: " + id);
    }

    public void eliminar(String id){
        if(!enemigoRepository.existsById(id)){
            throw new IllegalArgumentException("No se encontró el enemigo con ID: " + id);
        }
        enemigoRepository.deleteById(id);
    }
}