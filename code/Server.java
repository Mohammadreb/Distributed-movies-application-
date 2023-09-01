//package test14;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class Server {
	 public static void main(String[] args)throws Exception  {
		  Player p = new PlayerImpl();
	try {
        
     	UnicastRemoteObject.exportObject(p);
        Naming.rebind("/filesharing", p);
  
        System.out.println("Server ready...");
    } catch (RemoteException e) {
        System.out.println(e.getMessage());
    }
	 }
}
