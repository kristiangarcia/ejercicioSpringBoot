package dam.saruman.controller;

import dam.saruman.entity.Enemigo;
import dam.saruman.service.EnemigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EnemigoController {
    @Autowired
    private EnemigoService enemigoService;

    @GetMapping("/enemigo")
    public List<Enemigo> obtenerEnemigos(){
        return enemigoService.obtenerTodos();
    }

    @PostMapping("/enemigo")
    public Enemigo crearEnemigo(@RequestBody Enemigo enemigo){
        return enemigoService.guardar(enemigo);
    }

    @PutMapping("/enemigo/{id}")
    public Enemigo actualizarEnemigo(@PathVariable String id, @RequestBody Enemigo enemigo){
        return enemigoService.actualizar(id, enemigo);
    }

    @DeleteMapping("/enemigo/{id}")
    public void eliminarEnemigo(@PathVariable String id){
        enemigoService.eliminar(id);
    }
}
