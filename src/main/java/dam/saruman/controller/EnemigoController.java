package dam.saruman.controller;

import dam.saruman.entity.Enemigo;
import dam.saruman.service.EnemigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EnemigoController {
    @Autowired
    private EnemigoService enemigoService;

    @GetMapping("/enemigo")
    public List<Enemigo> obtenerEnemigos(){
        return enemigoService.obtenerTodos();
    }

    @GetMapping("/enemigo/ordenado")
    public List<Enemigo> obtenerEnemigosOrdenados(){
        return enemigoService.obtenerTodosOrdenadosPorNombre();
    }

    @GetMapping("/enemigo/buscar")
    public List<Enemigo> buscarEnemigos(@RequestParam String nombre){
        return enemigoService.buscarPorNombre(nombre);
    }

    @PostMapping("/enemigo")
    public ResponseEntity<?> crearEnemigo(@RequestBody Enemigo enemigo){
        try {
            Enemigo nuevoEnemigo = enemigoService.guardar(enemigo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEnemigo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/enemigo/{id}")
    public ResponseEntity<?> actualizarEnemigo(@PathVariable String id, @RequestBody Enemigo enemigo){
        try {
            Enemigo actualizado = enemigoService.actualizar(id, enemigo);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/enemigo/{id}")
    public ResponseEntity<?> eliminarEnemigo(@PathVariable String id){
        try {
            enemigoService.eliminar(id);
            return ResponseEntity.ok(Map.of("mensaje", "Enemigo eliminado con éxito"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}