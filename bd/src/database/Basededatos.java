package database;

import common.database.ServicioDatosInterfaz;
import common.rmi.IniciarRMI;
import common.server.ServicioAutenticacionInterfaz;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;


public class Basededatos extends IniciarRMI {

    private static String URL_nombre_dat;

    public Basededatos() {

        super(Basededatos.class, 1099);

    }

    @Override
    public void operacionRMI() {
        try {

            LocateRegistry.createRegistry(this.registryPort);

            ServicioDatosImpl servicioDatos = new ServicioDatosImpl();
            ServicioDatosInterfaz serDatStub = (ServicioDatosInterfaz) UnicastRemoteObject.exportObject(servicioDatos, 0);

            String uniqueIdDat = "001";
            URL_nombre_dat = "rmi://" + this.host + ":" + this.registryPort + "/" + ServicioAutenticacionInterfaz.NOMBRE_SERVICIO + "/" + uniqueIdDat;

            Naming.rebind(URL_nombre_dat, serDatStub);

            System.out.println("Base de datos ok...");

            // Ejecución de la interfaz
            Interfaz interfaz = new Interfaz(servicioDatos);
            interfaz.iniciar();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String getURL_nombre_dat() {
        return URL_nombre_dat;
    }

    public static void main(String[] args) {

        new Basededatos();

    }
}
