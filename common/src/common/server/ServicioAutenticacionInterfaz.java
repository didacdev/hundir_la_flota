package common.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioAutenticacionInterfaz extends Remote {
    public static final String NOMBRE_SERVICIO = "ServicioAutenticacion";

    // Añadir métodos remotos

    /**
     * Registra un usuario en la base de datos
     * @param nombre nombre del usuario
     * @param password contraseña del usuario
     * @return true si se ha registrado correctamente, false si no
     * @throws RemoteException
     */
    public boolean registrarUsuario(String nombre, String password) throws RemoteException;

    /**
     * Inicia sesión en el servidor
     * @param nombre nombre del usuario
     * @param password contraseña del usuario
     * @return true si se ha iniciado sesión correctamente, false si no
     * @throws RemoteException
     */
    public boolean iniciarSesion(String nombre, String password) throws RemoteException;
}


