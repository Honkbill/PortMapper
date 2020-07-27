package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String args[]) {
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		//String address = "127.0.0.1";
		String address = "172.21.217.6";
		int port = 3_333;
		
		try {
			socket = new Socket(address, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch (UnknownHostException e) {
			System.out.println("Unknown host");
			System.exit(-1);
		}
		catch  (IOException e) {
			System.out.println("No I/O");
			System.exit(-1);
		}
		
		/*System.out.println("register");
		out.println("REGISTER ADDER 172.21.217.130 1234");
		print(in);
		
		System.out.println("register");
		out.println("register ADAMPORTMAPPER 172.21.217.6 3333");
		print(in);
		
		System.out.println("get");
		out.println("GET ADDER");
		print(in);
		
		System.out.println("call");
		out.println("CALL ADDER 65 2 90 100 -6");
		print(in);
		
		System.out.println("list");
		out.println("LIST");
		print(in);*/
		
		/*System.out.println("get");
		out.println("GET ADDER");
		print(in);*/
		
		/*System.out.println("register");
		out.println("REGISTER TEST 172.21.217.130 1234");
		print(in);*/
		
		System.out.println("");
		out.println("REGISTER PROBLEM? HE HE");
		print(in);
		
		/*System.out.println("call");
		out.println("CALL AAA A A");
		print(in);*/
		
		/*System.out.println("register");
		out.println("register MAINPORTMAPPER 172.21.217.1 3333");
		print(in);
		
		System.out.println("CALL");
		out.println();
		print(in);*/
		
		try {
			socket.close();
		}
		catch (IOException e) {
			System.out.println("Cannot close the socket");
	    	System.exit(-1);
		}

	}
	
	public static void print(BufferedReader in) {
		try {
			String line = in.readLine();
			System.out.println(line);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}