import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;





public class MyRemoteServer {
    public static void main(String[] args){
        try{
            //create registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // create remote object
            MyRemoteImpl remoteImpl = new MyRemoteImpl();

            //Bind object to registry
            registry.bind("MyRemote", remoteImpl);

            System.out.println("Remote object bound to registry.");

        } catch (Exception e) {
            System.err.println("Error: "+ e.getMessage());

            e.printStackTrace();
        }
    }
    
}
