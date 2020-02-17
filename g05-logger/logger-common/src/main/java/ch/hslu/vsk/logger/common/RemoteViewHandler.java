package ch.hslu.vsk.logger.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An Interface for the Logger component to push {@link LogMessage} instances.
 */

public interface RemoteViewHandler extends Remote {

    /**
     * This method pushes the given {@link LogMessage} instance.
     * @param logMessage which has to be pushed
     */
    public void push(LogMessage logMessage) throws RemoteException;

}
