package model;
public class Jugador {
    public String nombre;
    public int id;
    public int puntos;
    public int ronda;
    public String estado;
    
    public Jugador() {
    }

    public Jugador(String nombre, int id, int puntos, int ronda, String estado) {
        this.nombre = nombre;
        this.id = id;
        this.puntos = puntos;
        this.ronda = ronda;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
     
}
