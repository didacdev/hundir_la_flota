package cliente;

import common.rmi.IniciarRMI;
import common.server.ServicioAutenticacionInterfaz;
import common.server.ServicioGestorInterfaz;

import java.rmi.Naming;

public class Jugador extends IniciarRMI {

    private static ServicioAutenticacionInterfaz servicioAutenticacion;
    private static ServicioGestorInterfaz servicioGestor;

    public Jugador() {
        super(Jugador.class, 2001);
    }

    @Override
    public void operacionRMI() {

        try {

            servicioAutenticacion = (ServicioAutenticacionInterfaz) Naming.lookup("rmi://localhost:1099/ServicioAutenticacion/002");
            servicioGestor = (ServicioGestorInterfaz) Naming.lookup("rmi://localhost:1099/ServicioGestor/003");


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static ServicioAutenticacionInterfaz getServicioAutenticacion() {
        return servicioAutenticacion;
    }

    public static ServicioGestorInterfaz getServicioGestor() {
        return servicioGestor;
    }

    public static void main(String[] args) {
        new Jugador();
        Interfaz interfaz = new Interfaz();
        interfaz.iniciar();
    }

}
