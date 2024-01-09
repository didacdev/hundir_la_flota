package cliente;

import common.dataStructures.ListaSincronizada;
import common.rmi.IniciarRMI;
import common.server.ServicioAutenticacionInterfaz;
import common.server.ServicioGestorInterfaz;
import common.client.CallbackJugadorIterfaz;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class Jugador extends IniciarRMI {

    private static ServicioAutenticacionInterfaz servicioAutenticacion;
    private static ServicioGestorInterfaz servicioGestor;
    private static ListaSincronizada listaSincronizada;
    private static String URL_nombre_ges;

    public Jugador() {

        super(Jugador.class, 1099);
        listaSincronizada = new ListaSincronizada();
    }

    @Override
    public void operacionRMI() {

        try {

            servicioAutenticacion = (ServicioAutenticacionInterfaz) Naming.lookup("rmi://localhost:1099/ServicioAutenticacion/002");
            servicioGestor = (ServicioGestorInterfaz) Naming.lookup("rmi://localhost:1099/ServicioGestor/003");

            CallbackJugadorImpl callbackJugador = new CallbackJugadorImpl(listaSincronizada);
            CallbackJugadorIterfaz callbackJugadorStub = (CallbackJugadorIterfaz) UnicastRemoteObject.exportObject(callbackJugador, 0);

            String uniqueIdGes = "004";
            URL_nombre_ges = "rmi://" + this.host + ":" + this.registryPort + "/" + CallbackJugadorIterfaz.NOMBRE_SERVICIO + "/" + uniqueIdGes;

            Naming.rebind(URL_nombre_ges, callbackJugadorStub);

            System.out.println("Jugador iniciado...");
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

    public static ListaSincronizada getListaSincronizada() {
        return listaSincronizada;
    }

    public static void setListaSincronizada(ListaSincronizada lista) {
        listaSincronizada = lista;
    }

    public static void main(String[] args) {
        new Jugador();
        Interfaz interfaz = new Interfaz();
        interfaz.iniciar();
    }

}
