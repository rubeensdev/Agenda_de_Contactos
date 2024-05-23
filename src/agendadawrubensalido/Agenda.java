package agendadawrubensalido;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Agenda implements Serializable {

    private ArrayList<Usuarios> losUsuarios = new ArrayList<>();
    private ArrayList<Contactos> misContactos = new ArrayList<>();

    private String nombrelogin;

    public Agenda() {
        this.nombrelogin = " ";
    }

    public boolean login() {
        Scanner datos = new Scanner(System.in);
        System.out.println("Dime tu nombre de usuario:");
        String nombreBuscado = datos.next();
        setNombrelogin(nombreBuscado);
        System.out.println("Dime tu clave:");
        String contraseñaBuscado = datos.next();

        for (Usuarios actual : losUsuarios) {
            if ((nombreBuscado.equals(actual.getNombre())) && (contraseñaBuscado.equals(actual.getContraseña()))) {
                System.out.println("Te has logeado con el usuario: " + nombreBuscado);
                if (cargarAgenda()) {
                    System.out.println("Contactos cargados correctamente.");
                } else {
                    System.out.println("No se encontraron contactos.");
                }
                return true;
            }
        }
        System.out.println("No te has logeado");
        controlLoginUsuarios(nombreBuscado);
        return false;
    }

    public void logout() {
        System.out.println("Has cerrado sesion, hasta pronto");
        guardarAgenda();
        setNombrelogin(" ");
    }

    public void registrarContacto() {
        Scanner datos = new Scanner(System.in);
        boolean datosValidos = false;

        try {
            do {
                boolean existe = false;

                System.out.println("Vamos a añadir un contacto");
                System.out.println("Dime el nombre");
                String nombre = datos.next();
                System.out.println("Dime el apellido");
                String apellido = datos.next();
                System.out.println("Dime el telefono");
                int telefono = datos.nextInt();
                Boolean tlfValido = false;
                while (!tlfValido) {

                    if (telefono >= 600000000) {
                        tlfValido = true;
                    } else {
                        System.out.println("Dime el telefono de nuevo, no es valido. Ej: (6XXXXXXXX)");
                        telefono = datos.nextInt();
                    }
                }
                System.out.println("Dime el email");
                String email = datos.next();
                Boolean emailValido = false;
                while (!emailValido) {

                    if (email.contains("@")) {
                        emailValido = true;
                    } else {
                        System.out.println("Dime el email de nuevo, no lo has escrito bien");
                        email = datos.next();
                    }
                }
                System.out.println("Dime la edad");
                int edad = datos.nextInt();
                Boolean edadValida = false;
                while (!edadValida) {

                    if (edad >= 0 || edad < 100) {
                        edadValida = true;
                    } else {
                        System.out.println(
                                "Dime la edad de nuevo. No puedes guardar a una persona de menos de 0 años y ni mas de 100.");
                        edad = datos.nextInt();
                    }
                }

                if (!existe) {
                    datosValidos = true;
                    misContactos.add(new Contactos(nombre, apellido, telefono, email, edad));
                    System.out.println("Contacto añadido correctamente.");
                }

            } while (!datosValidos);
        } catch (InputMismatchException e) {
            System.out.println("Entrada invalida, saliendo al menu de usuario...");
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    public void verContactos() {
        for (Contactos actual : misContactos) {
            System.out.println(actual.toString());
        }
    }

    public void buscarContactos() {
        Scanner datos = new Scanner(System.in);
        System.out.println("Dime qué contacto quieres buscar:");
        System.out.println("Nombre del contacto:");
        String nombre = datos.next();
        System.out.println("Apellidos del contacto:");
        String apellidos = datos.next();

        boolean encontrado = false;
        for (Contactos actual : misContactos) {
            if (actual.getNombre().equals(nombre) || actual.getApellido().equals(apellidos)) {
                System.out.println("Hay una coincidencia");
                System.out.println("Su nombre: " + actual.getNombre());
                System.out.println("Su apellido: " + actual.getApellido());
                System.out.println("Su numero de telefono: " + actual.getTelefono());
                System.out.println("Su edad: " + actual.getEdad());
                System.out.println("Su email: " + actual.getEmail());
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No hay nadie que se llame así.");
        }
    }

    public void eliminarContacto() {
        Scanner datos = new Scanner(System.in);
        System.out.println("Dime qué contacto quieres borrar:");
        System.out.println("Nombre del contacto:");
        String nombre = datos.next();
        System.out.println("Apellidos del contacto:");
        String apellidos = datos.next();

        boolean encontrado = false;
        for (int x = 0; x < misContactos.size(); x++) {
            if (misContactos.get(x).getNombre().equals(nombre)
                    && misContactos.get(x).getApellido().equals(apellidos)) {
                System.out.println("Se ha encontrado a " + nombre + " " + apellidos);
                System.out.println("¿Estás seguro de que lo quieres borrar? (si/s o no/n)");

                boolean respuestaValida = false;
                while (!respuestaValida) {
                    String respuesta = datos.next();
                    if (respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("s")) {
                        misContactos.remove(x);
                        System.out.println("Contacto eliminado correctamente.");
                        respuestaValida = true;
                        encontrado = true;
                        break;
                    } else if (respuesta.equalsIgnoreCase("no") || respuesta.equalsIgnoreCase("n")) {
                        System.out.println("Se ha interrumpido el borrado.");
                        respuestaValida = true;
                        encontrado = true;
                        break;
                    } else {
                        System.out.println("Respuesta no válida. Por favor, responde 'si / s', 'no / n'.");
                    }
                }
                if (encontrado) {
                    break;
                }
            }
        }

        if (!encontrado) {
            System.out.println("No se ha encontrado el contacto.");
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún contacto con ese nombre y apellidos.");
        }
    }

    public boolean cargarAgenda() {
        try {
            FileInputStream archivoAgenda = new FileInputStream("agenda" + nombrelogin + ".dat");
            ObjectInputStream input = new ObjectInputStream(archivoAgenda);
            Agenda laAgenda = (Agenda) input.readObject();
            this.misContactos = laAgenda.misContactos;
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la agenda: " + e.getMessage());
        }
        return false;
    }

    public void guardarAgenda() {
        try {
            FileOutputStream archivoAgenda = new FileOutputStream("agenda" + nombrelogin + ".dat");
            ObjectOutputStream output = new ObjectOutputStream(archivoAgenda);
            output.writeObject(this);
            System.out.println("Agenda guardada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar la agenda: " + e.getMessage());
        }
    }

    public void cargarUsuarios() {
        try {
            FileReader miLector = new FileReader("usuarios.txt");
            BufferedReader miBuffer = new BufferedReader(miLector);
            String linea;

            losUsuarios.clear();
            while ((linea = miBuffer.readLine()) != null) {
                String[] partes = linea.split(",");
                losUsuarios.add(new Usuarios(partes[0], partes[1]));
            }
            miLector.close();
            miBuffer.close();
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    public void guardarUsuarios() {
        try {
            FileWriter miEscritor = new FileWriter("usuarios.txt");
            BufferedWriter miBuffer = new BufferedWriter(miEscritor);
            for (Usuarios actual : losUsuarios) {
                miBuffer.write(actual.getNombre() + "," + actual.getContraseña());
                miBuffer.newLine();
            }
            miBuffer.close();
            miEscritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    public boolean controlLoginUsuarios(String nombreUsuario) {
        for (Usuarios usuario : losUsuarios) {
            if (nombreUsuario.equals(usuario.getNombre())) {
                int contador = usuario.getContador();
                if (contador < 3) {
                    System.out.println("Intento " + (contador + 1) + "/3 del usuario " + nombreUsuario + " fallido");
                    usuario.setContador(contador + 1);
                    System.out.println("-------------------------------------");
                } else {
                    System.out.println(
                            "El usuario " + nombreUsuario + " ha excedido el límite de intentos. Se eliminará.");
                    System.out.println("Se elimnara su agenda con sus contactos");
                    System.out.println("-------------------------------------");
                    losUsuarios.remove(usuario);
                    File archivoUsuario = new File("agenda" + nombreUsuario + ".dat");
                    archivoUsuario.delete();
                    return true;
                }
            }
        }
        return false;
    }

    public void menuPrincipal() {
        Scanner datos = new Scanner(System.in);
        System.out.println("-------------------------------------");
        try {
            System.out.println("Elije una opcion");
            System.out.println("    1) Registrarse");
            System.out.println("    2) Iniciar sesion");
            System.out.println("    3) Salir");
            int x = datos.nextInt();
            switch (x) {
                case 1:
                    new Usuarios();
                    menuPrincipal();
                    break;
                case 2:
                    cargarUsuarios();
                    int contador = 0;
                    do {
                        contador++;
                        System.out.println("Intento " + (contador) + "/3");
                        boolean Logeo = login();
                        if (Logeo) {
                            System.out.println("-------------------------------------");
                            System.out.println("Bienvenido/a " + nombrelogin + "!!!!!");
                            menuSecundario();
                            break;
                        }
                    } while (contador < 3);

                    if (contador == 3) {
                        System.out.println("Te has quedado sin intentos, el programa se va a cerrar...");
                        controlLoginUsuarios(nombrelogin);
                        guardarUsuarios();
                    } else {
                        System.out.println("Cerrando programa...");
                    }

                    System.out.println("-------------------------------------");
                    break;
                default:
                    cargarUsuarios();
                    guardarUsuarios();
                    return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, introduce un número.");
            datos.next();
            menuPrincipal();
        }
    }

    public void menuSecundario() {
        Scanner datos = new Scanner(System.in);
        System.out.println("-------------------------------------");
        try {
            System.out.println("Elije una opcion");
            System.out.println("    1) Registrar Contacto");
            System.out.println("    2) Mostrar Contactos");
            System.out.println("    3) Buscar Contacto");
            System.out.println("    4) Eliminar Contacto");
            System.out.println("    5) Salir");
            int x = datos.nextInt();
            switch (x) {
                case 1:
                    registrarContacto();
                    guardarAgenda();
                    menuSecundario();
                    break;
                case 2:
                    verContactos();
                    menuSecundario();
                    break;
                case 3:
                    buscarContactos();
                    menuSecundario();
                    break;
                case 4:
                    eliminarContacto();
                    menuSecundario();
                    break;
                default:
                    guardarAgenda();
                    logout();
                    menuPrincipal();
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, introduce un número.");
            datos.next();
            menuSecundario();
        }
    }

    public void setNombrelogin(String nombrelogin) {
        this.nombrelogin = nombrelogin;
    }

}
