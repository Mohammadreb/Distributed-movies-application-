
#  Distributed  movies  application 
 A distributed application for playing movies on a user's computer using JavaRMI and Sockets UDP.
 

## Used Tools

- Development language: Java
- Development package used: 1.4 JDK
- Development Environment:  Eclipse IDE
- Technologies: java RMI 
- Protocols: UDP

## Run Locally

 After you Clone the project

 Create a folder C:\test\server and put movie files in it, where  
 the folder represents files of existing films.

 Create the cash folder on the C drive.

Run the program using JDK version 1.4.2 and specify the JDK path.

```bash
 > set path= C:\j2sdk1.4.2_19\bin
```

Use javac *.java to compile the program.

```bash
> javac  *.java
```

Use rmic PlayerImpl to generate stub & skeleton 

```bash
> rmic PlayerImpl
```

 Use the rmiregistry command to start the names service.

```bash
> rmiregistry
```
Executing the server.java in a new command interpreter via Java Server.

```bash
> java Server
```
Executing the client.java in a new command interpreter via Client Java.

```bash
> java Client 
```
