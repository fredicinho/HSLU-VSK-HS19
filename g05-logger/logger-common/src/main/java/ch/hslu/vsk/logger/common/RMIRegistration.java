package ch.hslu.vsk.logger.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An Interface for the Logger component to register LogMessage instances.
 */


public interface RMIRegistration extends Remote {

    /**
     * This method registers the given RemoteViewHandler instance.
     * RemoteViewHandler which has to be pushed
     */

    public void register(RemoteViewHandler viewer) throws RemoteException;

}
