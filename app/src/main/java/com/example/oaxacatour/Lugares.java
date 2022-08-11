package com.example.oaxacatour;

public class Lugares {

    private String id;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String horario;
    private String precio;

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        nombre = nombre;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        descripcion = descripcion;
    }

    public String getdireccion() {
        return direccion;
    }

    public void setdireccion(String direccion) {
        direccion = direccion;
    }

    public String gethorario() {
        return horario;
    }

    public void sethorario(String horario) {
        horario = horario;
    }

    public String getprecio() {
        return precio;
    }

    public void sethprecio(String precio) {
        precio = precio;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
