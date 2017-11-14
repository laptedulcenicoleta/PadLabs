package tools;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class UdpTool {

	public ArrayList<String> getInfNodes(String group, int port, String message) throws IOException {
		// client
		ArrayList<String> listNodes = new ArrayList<String>();
		MulticastSocket s = new MulticastSocket();

		InetAddress IPAddress = InetAddress.getByName(group);

		byte[] sendData = new byte[100];
		byte[] receiveData = new byte[100];

		sendData = message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		s.send(sendPacket);
		try {
			s.setSoTimeout(1000);

			while (s.getSoTimeout() > 0) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				s.receive(receivePacket);
				String resp = new String(receivePacket.getData());
				listNodes.add(resp);

			}
			s.close();
			return listNodes;
		} catch (Exception e) {
			System.out.println("Soket time out for receive");
		}
		return listNodes;
	}

	public DatagramPacket reciveMulticast(String group, int port) throws IOException {
		MulticastSocket s;

		s = new MulticastSocket(port);
		s.joinGroup(InetAddress.getByName(group));

		byte[] receiveData = new byte[100];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		s.receive(receivePacket);
		String sentence = new String(receivePacket.getData(), "UTF-8");
		System.out.println("RECEIVED: " + sentence);
		s.close();
		return receivePacket;

	}

	public void sendResponceToClient(String message, DatagramPacket receivePacket, int port, String group)
			throws IOException {
		MulticastSocket s;

		s = new MulticastSocket(port);

		s.joinGroup(InetAddress.getByName(group));
		byte[] sendData = new byte[100];
		InetAddress IPAddress = receivePacket.getAddress();
		int port1 = receivePacket.getPort();

		sendData = message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port1);
		s.send(sendPacket);
		s.close();
	}

}
