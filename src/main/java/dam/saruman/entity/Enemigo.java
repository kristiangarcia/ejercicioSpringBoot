package dam.saruman.entity;

import jakarta.persistence.*;

@Entity
@Table(name="enemigo_estado")
public class Enemigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String pais;

    @Column
    private String afiliacion_politica;

    public Enemigo() {

    }

    public Enemigo(Long id, String nombre, String pais, String afiliacion_politica) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.afiliacion_politica = afiliacion_politica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getAfiliacion_politica() {
        return afiliacion_politica;
    }

    public void setAfiliacion_politica(String afiliacion_politica) {
        this.afiliacion_politica = afiliacion_politica;
    }
}
