

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;


public class Client {
    
    public static void main(String[] args)throws Exception  {
        System.out.println("Ready to receive!");
     
        String serverRoute = "C:\\udptest"; 
        Player p = (Player)  Naming.lookup("/filesharing");
        String name="";
		 String i="";
		 String po="";
		 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter file name");
		name =in.readLine();
		System.out.println("Enter ip");
		i =in.readLine();
		System.out.println("Enter port");
		po =in.readLine();
		int iport=Integer.parseInt(po);  
		
		p.Play(name,i,iport);
		
       
        display(iport);
       
    }
    
    
    
    private static void receiveFile(FileOutputStream outToFile, DatagramSocket socket) throws IOException {
        System.out.println("Receiving file");
        boolean flag; 
        int sequenceNumber = 0; // Order of sequences
        int foundLast = 0; // The las sequence found
        
        while (true) {
            byte[] message = new byte[1024]; // Where the data from the received datagram is stored
            byte[] fileByteArray = new byte[1021]; // Where we store the data to be writen to the file

            // Receive packet and retrieve the data
            DatagramPacket receivedPacket = new DatagramPacket(message, message.length);
            socket.receive(receivedPacket);
            message = receivedPacket.getData(); // Data to be written to the file

            // Get port and address for sending acknowledgment
            InetAddress address = receivedPacket.getAddress();
            int port = receivedPacket.getPort();

            // Retrieve sequence number
            sequenceNumber = ((message[0] & 0xff) << 8) + (message[1] & 0xff);
            // Check if we reached last datagram (end of file)
            flag = (message[2] & 0xff) == 1;
            
            // If sequence number is the last seen + 1, then it is correct
            // We get the data from the message and write the ack that it has been received correctly
            if (sequenceNumber == (foundLast + 1)) {

                // set the last sequence number to be the one we just received
                foundLast = sequenceNumber;

                // Retrieve data from message
                System.arraycopy(message, 3, fileByteArray, 0, 1021);

                // Write the retrieved data to the file and print received data sequence number
                outToFile.write(fileByteArray);
                System.out.println("Received: Sequence number:" + foundLast);

                // Send acknowledgement
                sendAck(foundLast, socket, address, port);
            } else {
                System.out.println("Expected sequence number: " + (foundLast + 1) + " but received " + sequenceNumber + ". DISCARDING");
                // Re send the acknowledgement
                sendAck(foundLast, socket, address, port);
            }
            // Check for last datagram
            if (flag) {
                outToFile.close();
                break;
            }
        }
    }    
    
    private static void sendAck(int foundLast, DatagramSocket socket, InetAddress address, int port) throws IOException {
        // send acknowledgement
        byte[] ackPacket = new byte[2];
        ackPacket[0] = (byte) (foundLast >> 8);
        ackPacket[1] = (byte) (foundLast);
        // the datagram packet to be sent
        DatagramPacket acknowledgement = new DatagramPacket(ackPacket, ackPacket.length, address, port);
        socket.send(acknowledgement);
        System.out.println("Sent ack: Sequence Number = " + foundLast);
    }
    //me 
    
    public static void display (int port) {
    	try{
    			
    	 File f = new File ("c:\\cash\\test.mp4");
    	 DatagramSocket socket = new DatagramSocket(port);
    	 FileOutputStream outToFile = new FileOutputStream(f);        
         receiveFile(outToFile, socket); 
    }
    	catch(Exception ex){
            ex.printStackTrace();
            System.exit(1);
        }   
    }
}