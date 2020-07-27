package zad1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Server {
	public void listenSocket(int port) {
		ServerSocket server = null;
		Socket client = null;
		
		try {
			server = new ServerSocket(port); 
		}
		catch (IOException e) {
			System.out.println("Could not listen");
			System.exit(-1);
		}
		
		System.out.println("Server listens on port: " + server.getLocalPort());
		
		while(true) {
			try {
				client = server.accept();
				System.out.println("Client connected: " + client.getInetAddress());
			}
			catch (IOException e) {
				System.out.println("Accept failed");
				System.exit(-1);
			}
			
			System.out.println("Action");
			action(client);
		}
	}
	
	public void listenSocket() {
		listenSocket(0);
	}
	
	protected abstract void action(Socket client);
}
