package classes;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.ArrayList;

import colections.Worker;
import tools.FileReader;
import tools.XmlParser;

public class TcpServerThread extends Thread {

	public Socket server;

	ArrayList<InfNode> listNodes = new ArrayList<InfNode>();
	public ArrayList<Worker> listWorkers = new ArrayList<Worker>();
	public InfNode thisNode;

	public TcpServerThread(Socket clientSocket, InfNode thisNode) {
		this.server = clientSocket;
		this.thisNode = thisNode;

	}

	public void run() {

		try {
			DataInputStream in = new DataInputStream(server.getInputStream());
			String input = in.readUTF();

			System.out.println("Recived " + input);
			populateData();

    		Command cmd = new Command(server, thisNode, listWorkers, listNodes, input);
			cmd.execute();
			server.close();
			currentThread().interrupt();
		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	public void populateData() throws FileNotFoundException {

		listNodes = new ArrayList<InfNode>();
		listNodes = XmlParser.getNodeList("conf" + thisNode.getId() + ".xml");

		FileReader fr = new FileReader();
		listWorkers = fr.getObjFromFile("col" + thisNode.getId() + ".json");
	}

}
