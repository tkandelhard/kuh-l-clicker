import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	public Server() {
		
		try {
			System.out.println("Die Adresse der Save Cloud ist " + InetAddress.getLocalHost().getHostAddress() + " Bitte tragen Sie diese IP in die host Variable ein");
			// 1. Server starten 			
			ServerSocket server = new ServerSocket(12345, 100);
			//server.setSoTimeout(6000);	//Timeout nach einer Minute

			System.out.println("Waiting for connection"); 

			// 2. auf eingehende Verbindung warten 
			Socket socket = server.accept(); 
			System.out.println("Connection received from " + socket.getInetAddress().getHostName());
			
			
			System.out.println("");
			
			
			
			InputStream inStream = socket.getInputStream();
			Scanner inputClient = new Scanner(inStream);
			String messageClient = inputClient.nextLine();
			
			
			
			
			// 3. PrintWriter erzeugen 
			OutputStream outStream = socket.getOutputStream(); 
			PrintWriter output = new PrintWriter(outStream); 
			output.println(messageClient); 
			
			// 4. Nachricht an Client 
			output.flush(); 
			output.close(); 
			
			// 5. Verbindung schliessen 
			socket.close();

		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		new Server();
	}

}