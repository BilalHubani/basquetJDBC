package model;
import java.time.LocalDate;

public class Equipo {
    private String nombre;
    private LocalDate fecha;
    private String localidad;

    public Equipo() {}
    public Equipo(String nombre) {this.nombre = nombre;}
    public Equipo(String nombre, LocalDate fecha, String localidad) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.localidad = localidad;
    }
    public String getLocalidad() {return localidad;}
    public void setLocalidad(String localidad) {this.localidad = localidad;}
    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;} //SQL date
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    @Override
    public String toString() {
        return "Equipo{" +
                "nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", localidad='" + localidad + '\'' +
                '}';
    }
}
