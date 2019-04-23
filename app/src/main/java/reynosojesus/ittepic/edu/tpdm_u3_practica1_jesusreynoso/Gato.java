package reynosojesus.ittepic.edu.tpdm_u3_practica1_jesusreynoso;

public class Gato {
    String nombre,raza,edad,descripcion;

    public Gato() {
    }

    public Gato(String nombre, String raza, String edad, String descripcion) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
