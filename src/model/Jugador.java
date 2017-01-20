package model;

import java.time.LocalDate;

public class Jugador {

    private int id;
    private String nombre;
    private LocalDate nacimiento;
    private int canastas;
    private int rebote;
    private int asistencias;
    private String posicion;

    public Jugador() {
    }

    public Jugador(int id,String nombre, LocalDate nacimiento, String posicion) {
        this.id = id;
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.posicion = posicion;
    }
    public Jugador(int id,String nombre, LocalDate nacimiento, int canastas, int rebote, int asistencias, String posicion) {
        this(id,nombre, nacimiento,posicion);
        this.canastas = canastas;
        this.rebote = rebote;
        this.asistencias = asistencias;
        this.posicion = posicion;
    }



    public String getPosicion() {
        return posicion;
    }
    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public int getAsistencias() {
        return asistencias;
    }
    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getRebote() {
        return rebote;
    }
    public void setRebote(int rebote) {
        this.rebote = rebote;
    }


    public int getCanastas() {
        return canastas;
    }
    public void setCanastas(int canastas) {
        this.canastas = canastas;
    }


    public LocalDate getNacimiento() {
        return nacimiento;
    }
    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


}
