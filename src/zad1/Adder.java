package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Adder extends Server {

	public String add(String args) {
		System.out.println("Processing...");
		
		String[] tmp = args.split(" ");
		int result = 0;
		
		for(int i = 0; i < tmp.length; i++) {
			try {
				int val = Integer.parseInt(tmp[i]);
				System.out.println(val);
				result += val;
			} catch(NumberFormatException e) {
				System.out.println("Not a number");
				return "Not a number";
			}
		}
		

		System.out.println("RESULT: " + result);
		
		return result+"";
	}
	
	@Override
	protected void action(Socket client) {
		new ServerThread(client) {
			public void run() {
				String line;
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					
					if((line = in.readLine()) != null && !line.isEmpty())
						System.out.println(line);
					
					out.println(add(line));
				} catch (IOException e1) {
					// do nothing
				}
				
				try {
					socket.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}.start();
	}
	
	public static void main(String[] args) {
		new Adder().listenSocket(1_234);
	}

}
