package ictgradschool;

import java.net.*;/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;*/
import java.io.*;
import java.sql.*;
import org.apache.derby.jdbc.*;
import java.io.IOException;
import java.io.PrintWriter;

public class SampleServer extends Thread {
	private ServerSocket serverSocket;

	public SampleServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(10000);
	}

	public void run() {
		boolean done = false;
		while (!done) {
			try {
				
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				
				DataInputStream in = new DataInputStream(server.getInputStream());
				String query = in.readUTF();
				String queryResult = "";
				
				try {
					Class cd = Class.forName("org.apache.derby.jdbc.ClientDriver");
					try (Connection conn = DriverManager
							.getConnection("jdbc:derby://localhost:1527/Lab19;create=true", "APP", "APP")) {
						// you use the connection here
						try (Statement stmt = conn.createStatement();
								ResultSet rs = stmt.executeQuery("SELECT * FROM RegisteredUsers WHERE UPPER(username) ='" + query.toUpperCase() +"'")) {
								//ResultSet rs = stmt.executeQuery("SELECT * FROM RegisteredUsers")) {
								while (rs.next()) {
								int numColumns = rs.getMetaData().getColumnCount();

								String[] columnNames = new String[]{"First Name: ", "Last Name: ", "Username: ", "Email: "};
								
								for (int i = 1; i <= numColumns; i++) {
									queryResult += columnNames[i-1] + "\n";
									queryResult += rs.getObject(i) + "\n";
								}
								queryResult += "\n";
							}
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (Exception except) {
					except.printStackTrace();
				}
				
				
				if (queryResult.isEmpty())
					queryResult = "No such username";
				else 
					done=true;
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF(queryResult);
				server.close();

			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		// int port = Integer.parseInt(args[0]);
		int port = Integer.parseInt(args[0]);
		try {
			Thread t = new SampleServer(port);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
