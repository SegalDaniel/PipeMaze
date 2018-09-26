package server;

import java.io.IOException;

import interfaces.Server;
import pipeGame.PipeGameSolver;

public class RunServer {

	public static void main(String[] args) {
	
		Server s = new MyServer(6400);//Take the port from the args
		MyClientHandler mch = new MyClientHandler();
		mch.setSolver(new PipeGameSolver());
        try {
			s.start(mch);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        s.stop();
        System.out.println("Closed server");
        
	}
	
}
