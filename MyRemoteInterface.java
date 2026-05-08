import java.rmi.Remote;
import java.rmi.RemoteException;



public interface MyRemoteInterface extends Remote {
    
    String sayhello(String name) throws RemoteException;
}
