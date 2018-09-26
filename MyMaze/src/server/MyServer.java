package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import interfaces.ClientHandler;

import interfaces.Server;

public class MyServer implements Server {
	
	private ServerSocket serverSocket;
    private int port;
    private boolean stop = false;
    
	public MyServer(int port){
		this.port = port;
	}
	
	private void startServer(ClientHandler clientHandler) throws IOException {
        //System.out.println("MyServer.startServer();");
		serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(1000);
        System.out.println("Server connected - waiting");

        while (!stop) {
            try {
                Socket aClient = serverSocket.accept();
                System.out.println("client connected");

                clientHandler.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                
                aClient.close();
            } catch (SocketTimeoutException e) {
            		//System.out.println("Client did not connect...");
            }
        }
        //System.out.println("server stopped");
        serverSocket.close();
    }

	@Override
	public void start(ClientHandler clientHandler) throws IOException {
		//System.out.println("MyServer.start();");
		new Thread(() -> {
	            try {
	                startServer(clientHandler);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }).start();
	}

	@Override
	public void stop() {
		//this.stop = true;
		System.out.println("Done");
	}

}