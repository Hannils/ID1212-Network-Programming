import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Hello
 */
public interface Hello extends Remote {
    String sayHello() throws RemoteException;
}
