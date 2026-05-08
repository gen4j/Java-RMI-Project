import java.rmi.Naming;





public class MyRemoteClient {
    
    public static void main(String[] args){

        try{

            //Lookup remote object
            MyRemoteInterface remoteObject = (MyRemoteInterface) Naming.lookup("rmi://localhost/MyRemote");

            //Invoke remote method
            String result =  remoteObject.sayhello("Alice");

            System.out.println(result);

        } catch (Exception e){
            
            System.err.println("Error: " + e.getMessage());

            e.printStackTrace();
        }
    }
}
