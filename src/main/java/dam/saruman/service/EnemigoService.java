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

    public Enemigo guardar(Enemigo enemigo){
        return enemigoRepository.save(enemigo);
    }

    public Enemigo actualizar(Long id, Enemigo enemigo){
        if(enemigoRepository.existsById(id)){
            enemigo.setId(id);
            return enemigoRepository.save(enemigo);
        }
        return null;
    }

    public void eliminar(Long id){
        enemigoRepository.deleteById(id);
    }
}
