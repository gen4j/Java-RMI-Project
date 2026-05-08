import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class MyRemoteImpl extends UnicastRemoteObject implements MyRemoteInterface {
    
    public MyRemoteImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayhello(String name) throws RemoteException {
        return "Hello, " + name + "!";
        
    }
}
