
import java.rmi.*;
import java.net.*;

public interface Player extends Remote{
	public void Play(String filename,String ip,int port) throws Exception;
	



}