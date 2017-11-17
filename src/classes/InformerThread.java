package classes;

import tools.UdpTool;
import tools.XmlParser;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class InformerThread extends Thread {
	int thread;
	String configFile;
	InfNode thisNode = new InfNode();
	public ArrayList<InfNode> listNodes;

	public InformerThread(int i, String configFile) {

		this.configFile = configFile;
		this.thread = i;
	}
	public void run() {

		thisNode = XmlParser.getNodeFtomConfig(configFile);
		listNodes = XmlParser.getNodeList(configFile);

		new TcpServer(thisNode).start();
		int port = 7000;
		String group = "225.4.5.6";

		UdpTool udp = new UdpTool();
		DatagramPacket receivePacket;

		try {
			while (true) {

				receivePacket = udp.reciveMulticast(group, port);

				listNodes = XmlParser.getNodeList(configFile);
				String con = listNodes.size() + "";

				String res = "{\"nodeId\":\"" + thisNode.getId() + "\",\"nodeIP\":\"" + thisNode.getNodeIp()
						+ "\",\"tcpPort\":\"" + thisNode.getTcpPort() + "\"}";
				udp.sendResponceToClient(res, receivePacket, port, group);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
