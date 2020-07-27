package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class PortMapper extends Server{

	private HashMap<String, String> map;
	
	public PortMapper() {
		this.map = new HashMap<>();
	}
	
	public String register(String key, String str) {
		map.put(key, str);
		
		return "OK";
	}
	
	public String get(String name) {
		if(map.containsKey(name))
			return map.get(name);
		
		return "NO SUCH RECORD";
	}
	
	public String call(String name, String args) {
		String line = null;
		
		if(map.containsKey(name)){
			String[] tab = get(name).split(" ");
			String address = tab[0];
			int port = Integer.parseInt(tab[1]);
			
			Socket socket = null;
			PrintWriter out = null;
			BufferedReader in = null;
			
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
			
			try {
				out.println(args);
				
				line = in.readLine();
				System.out.println("CALL RESULT: " + line);
		    }
		    catch (IOException e) {
		    	System.out.println("Error during communication");
		    	System.exit(-1);
		    }
		    
			try {
				socket.close();
			}
			catch (IOException e) {
				System.out.println("Cannot close the socket");
		    	System.exit(-1);
			}
		}
		
		else {
			line = "NO SUCH RECORD";
		}
		
		return line;
	}
	
	public String delete(String name) {
		if(map.containsKey(name)) {
			map.remove(name);
			return "OK";
		}
		
		return "NO SUCH RECORD";
	}
	
	public String list() {
		return map.toString();
	}

	@Override
	protected void action(Socket client) {
		System.out.println("HANDLING CLIENT " + client.getInetAddress());
		
		new ServerThread(client) {
			public void run() {
				System.out.println("Client Handling Thread");
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					
					String line;
					while((line = in.readLine()) != null && !line.isEmpty()) {
						
						System.out.println(line);
					
						String[] tab = line.split(" ");
						String command = tab[0].toUpperCase();
						
						if(command.equals("REGISTER")) {
							if(tab.length == 4) {
								String servName = tab[1].toUpperCase();
								System.out.println("COMMAND: REGISTER\n");
								
								String args = "";
								for(int i = 2; i < tab.length; i++) {
									args += tab[i];
									
									if(i < tab.length-1)
										args += " ";
								}
								
								register(servName, args);
								out.println(servName + " REGISTERED");
							}
							else {
								out.println("WRONG NUMBER OF PARAMETERS");
							}
						}
						
						else if(command.equals("CALL")) {
							if(tab.length >= 3) {
								String servName = tab[1].toUpperCase();
								System.out.println("COMMAND: CALL\n");
								
								String args = "";
								for(int i = 2; i < tab.length; i++) {
									args += tab[i];
									
									if(i < tab.length-1)
										args += " ";
								}
								
								out.println(call(servName, args));
							}
							else {
								out.println("WRONG NUMBER OF PARAMETERS");
							}
						}
						
						else if(command.equals("GET")) {
							if(tab.length == 2) {
								String servName = tab[1].toUpperCase();
								System.out.println("COMMAND: GET\n");
								out.println(get(servName));
							}
							else {
								out.println("WRONG NUMBER OF PARAMETERS");
							}
						}
						
						else if(command.equals("DELETE")) {
							if(tab.length == 2) {
								String servName = tab[1].toUpperCase();
								System.out.println("COMMAND: DELETE\n");
								out.println(delete(servName));
							}
							else {
								out.println("WRONG NUMBER OF PARAMETERS");
							}
						}
						
						else if(command.equals("LIST")) {
							System.out.println("COMMAND: LIST\n");
							out.println(list());
						}				
						
						else {
							System.out.println("NO SUCH COMMAND\n");
							out.println("NO SUCH COMMAND");
						}
						
					}
					
					if(line != null)
						out.println("EMPTY MESSAGE");
					
					System.out.println("HANDLING CLIENT " + client.getInetAddress() + " COMPLETED\n\n");
					
				} catch (IOException e1) {
					System.out.println("NO I/O");
				}
				
				try {
					socket.close();
				} catch (IOException e) {
					//do nothing
				}
			}
			
		}.start();
	}
	
	public static void main(String[] args) {
		new PortMapper().listenSocket(3_333);
	}

}
