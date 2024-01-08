package common.rmi;

public abstract class IniciarRMI {
    protected int registryPort;
    protected String host;

    public IniciarRMI(Class<?> clase, int port) {
        this.registryPort = port;
        this.host = "localhost";

        System.setProperty("java.rmi.server.codebase",
                clase.getProtectionDomain().getCodeSource().getLocation().toString());

        operacionRMI();
    }

    // Extenderemos esta clase para hacer las RMI
    public abstract void operacionRMI();

}
