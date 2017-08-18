package ictgradschool;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class SampleClient {
	public static void main(String[] args) {
		// String serverName = args[0];
		// int port = Integer.parseInt(args[1]);

		String serverName = args[0];
		int port = Integer.parseInt(args[1]);

		try (Socket client = new Socket(serverName, port)) {
			System.out.println("Connecting to " + serverName + " on port " + port);
			
			System.out.println("Just connected to " + client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			Scanner scanner = new Scanner(System.in);
			
			while (true) {
				System.out.println("Please type your query for the Users database: ");
				String query = scanner.next();
				out.writeUTF(query);

				InputStream inFromServer = client.getInputStream();
				DataInputStream in = new DataInputStream(inFromServer);
				String queryResult = in.readUTF();
				if (queryResult.equals("No such username"))
					break;
				System.out.println("Server says " + "\n\n" + queryResult);
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
