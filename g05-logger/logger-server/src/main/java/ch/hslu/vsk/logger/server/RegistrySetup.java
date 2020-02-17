package ch.hslu.vsk.logger.server;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegistrySetup implements Runnable {

    @Override
    public void run() {
        try {
            final Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Thread.currentThread().join();
        } catch (final InterruptedException | RemoteException ex) {
            System.err.println("Error with the RegistrySetup");
            ex.printStackTrace();
        }
    }
}
