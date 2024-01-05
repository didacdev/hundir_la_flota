package common.rmi;

public abstract class IniciarRMI {
    protected int registryPort;

    public IniciarRMI(Class<?> clase, int port) {
        this.registryPort = port;

        System.setProperty("java.rmi.server.codebase",
                clase.getProtectionDomain().getCodeSource().getLocation().toString());

        operacionRMI();
    }

    // Extenderemos esta clase para hacer las RMI
    public abstract void operacionRMI();

}
