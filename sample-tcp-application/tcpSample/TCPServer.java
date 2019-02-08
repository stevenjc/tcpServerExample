package tcpSample;

import java.io.*;
import java.net.*;
import java.net.Socket;

class TCPServer {
	
	public static void main(String argv[]) throws Exception {
		String clientSentence;
		String capitalizedSentence;
		int port = 6789;
		boolean closeSocket = false; //set to true when msg 'exit' is received
		
		ServerSocket welcomeSocket = new ServerSocket(port);
		
		System.out.println("TCP Server has started on port "+ port + " - Send a message to receive it back in all caps");
		System.out.println("Send message 'exit' to shut down the server");
		
		while (!closeSocket) {
			//connectionSocket waits from incoming connection before continuing execution
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Socket Connected - " + connectionSocket.getPort());
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		   
			clientSentence = inFromClient.readLine();
		   
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		   
		   
			System.out.println("Received: " + clientSentence);
		   
			//check that a valid msg was received
			if(clientSentence != null && !clientSentence.equalsIgnoreCase("exit")) {
				capitalizedSentence = clientSentence.toUpperCase();
			   
				System.out.println("To Send: " + capitalizedSentence);
				   
				outToClient.writeBytes(capitalizedSentence);
				   
			}else if (clientSentence.equalsIgnoreCase("exit")) {
				closeSocket = true;
				outToClient.writeBytes("Server is shutting down");
			}
			
			inFromClient.close();
			outToClient.close();
			connectionSocket.close();
			  
		}
		System.out.println("Closing Server");
		welcomeSocket.close();
	}
	 
}