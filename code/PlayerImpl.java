//package test14;

import java.rmi.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;



public class PlayerImpl implements Player{
	String na="";
	String ip = "";
	 int por=0;
	 
  
    

	public void Play(String filename,String ipadd,int port) throws Exception
{
		
		Client fr = new Client();
		this.na=filename;
		this.ip=ipadd;
		this.por=port;
		File fs = new FileSend();
	
		
		fs.ready(na,por,ip);	   
}
	
}
