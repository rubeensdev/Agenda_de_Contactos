package agendadawrubensalido;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class Usuarios implements Serializable{

    private String nombre;
    private String contraseña;
    private int contador;

    public Usuarios() {
        Scanner datos = new Scanner(System.in);
        boolean datosValidos = false;

        do {
            System.out.println("Dame un nombre");
            this.nombre = datos.next();
            System.out.println("Dame una contraseña");
            this.contraseña = datos.next();
            if (nombre.contains(" ") || contraseña.contains(" ")) {
                System.out.println("El nombre no es valido");
            } else {
                datosValidos = true;
            }
        } while (!datosValidos);

        boolean usuarioExistente = false;

        try {
            FileReader miLector = new FileReader("usuarios.txt");
            BufferedReader miBuffer = new BufferedReader(miLector);

            String linea;
            while ((linea = miBuffer.readLine()) != null) {
                if (linea.contains(nombre + "," + contraseña)) {
                    System.out.println("Un usuario ya está creado con este nombre");
                    usuarioExistente = true;
                }
            }
            miBuffer.close();
            miLector.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.contador = 0;
        if (!usuarioExistente) {
            guardarUsuario(nombre, contraseña);
        }

    }

    public Usuarios(String nombre, String contraseña) {

        this.nombre = nombre;
        this.contraseña = contraseña;
        this.contador = 0;

    }

    public void guardarUsuario(String nombre, String contraseña) {

        try {
            FileWriter miEscritor = new FileWriter("usuarios.txt", true);
            BufferedWriter bufferEscritura = new BufferedWriter(miEscritor);

            bufferEscritura.write(nombre + "," + contraseña);
            bufferEscritura.newLine();
            bufferEscritura.close();
            miEscritor.close();
            System.out.println("Usuario guardado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el usuario: " + e.getMessage());
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
}
