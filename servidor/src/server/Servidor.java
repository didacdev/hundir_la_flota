package server;

import common.client.CallbackJugadorIterfaz;
import common.database.Partida;
import common.database.ServicioDatosInterfaz;
import common.rmi.IniciarRMI;
import common.server.ServicioAutenticacionInterfaz;
import common.server.ServicioGestorInterfaz;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;

public class Servidor extends IniciarRMI {

    private static final Date fechaInicio = new Date();
    private static ServicioDatosInterfaz servicioDatos;
    private static String URL_nombre_aut;
    private static String URL_nombre_ges;
    private static HashMap<String, CallbackJugadorIterfaz> callbackJugadores = new HashMap<>();

    public Servidor() {
        super(Servidor.class, 1099);
        fechaInicio.setTime(System.currentTimeMillis());
    }

    @Override
    public void operacionRMI() {
        try {

            // Crear instancias de los objetos remotos
            ServicioAutenticacionImpl servicioAutenticacion = new ServicioAutenticacionImpl();
            ServicioGestorImpl servicioGestor = new ServicioGestorImpl();

            // Exportar el objeto remoto
            ServicioAutenticacionInterfaz serAutStub = (ServicioAutenticacionInterfaz) UnicastRemoteObject.exportObject(servicioAutenticacion, 0);
            ServicioGestorInterfaz serGesStub = (ServicioGestorInterfaz) UnicastRemoteObject.exportObject(servicioGestor, 0);

            // Crear identificadores únicos
            String uniqueIdAut = "002";
            String uniqueIdGes = "003";

            // Crear las URL
            URL_nombre_aut = "rmi://" + this.host + ":" + this.registryPort + "/" + ServicioAutenticacionInterfaz.NOMBRE_SERVICIO + "/" + uniqueIdAut;
            URL_nombre_ges = "rmi://" + this.host + ":" + this.registryPort + "/" + ServicioGestorInterfaz.NOMBRE_SERVICIO + "/" + uniqueIdGes;

            // Registrar el objeto remoto
            Naming.rebind(URL_nombre_aut, serAutStub);
            Naming.rebind(URL_nombre_ges, serGesStub);

            servicioDatos = (ServicioDatosInterfaz) Naming.lookup("rmi://localhost:1099/ServicioAutenticacion/001");

            System.out.println("Servidor iniciado...");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Date getFechaInicio() {
        return fechaInicio;
    }

    public static HashMap<Integer, Partida> getPartidasEnEjecucion() {
        try {
            return servicioDatos.getStartedGames();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static ServicioDatosInterfaz getServicioDatos() {
        return servicioDatos;
    }

    public static String getURL_nombre_aut() {
        try {
            return URL_nombre_aut;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static String getURL_nombre_ges() {
        try {
            return URL_nombre_ges;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static void addCallbackJugador(String username, CallbackJugadorIterfaz callbackJugador) {
        callbackJugadores.put(username, callbackJugador);
    }
    public static CallbackJugadorIterfaz getCallbackJugador(String username) {
        return callbackJugadores.get(username);
    }

    public static void main(String[] args) {

        new Servidor();
        Interfaz interfaz = new Interfaz();
        interfaz.iniciar();
    }
}
