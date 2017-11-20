package classes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TcpServer extends Thread {

	private ServerSocket serverSocket;
	private int port;
	public InfNode thisNode;

	public TcpServer(InfNode thisNode) {
		this.port = thisNode.getTcpPort();
		this.thisNode = thisNode;
	}

	public void run() {

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {

			e.printStackTrace();
		}

		while (true) {
			try {
				System.out.println("Waiting for client on port " + port );
				Socket server = serverSocket.accept();

				new TcpServerThread(server, thisNode).start();

			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				System.out.println("Client is deconectat!");
				e.printStackTrace();
				break;
			}
		}
	}
}
