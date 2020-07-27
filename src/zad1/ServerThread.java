package zad1;

import java.net.Socket;

public abstract class ServerThread extends Thread{
	protected Socket socket;

	public ServerThread(Socket socket) {
		super();
		this.socket = socket;
	}
}
