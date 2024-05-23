package agendadawrubensalido;

import java.io.Serializable;

public class Contactos implements Serializable {
    private String nombre;
    private String apellido;
    private int telefono;
    private String email;
    private int edad;

    public Contactos(String nombre, String apellido, int telefono, String email, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return nombre + "   " + apellido + "   " + telefono + "   " + email + "   " + edad;
    }

}
