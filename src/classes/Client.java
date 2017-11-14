package classes;

import tools.TcpTool;
import tools.UdpTool;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import colections.Message;

public class Client {

	static ArrayList<String> listNodeInf = new ArrayList<String>();
	static int portTcp = 0;
	static TcpTool tcp = new TcpTool();
	static String serverName = "localhost";
    static Mediator mediator = new Mediator();

	public static void main(String[] args) throws IOException, InterruptedException {
	    Socket soket = new Socket("localhost", 5555);
	    Message mes = new Message("command", "get", "bestNode");
	    Gson gson = new Gson();
	    String jmes = gson.toJson(mes);
        tcp.tcpSend(jmes, soket);
        String resp = tcp.tcpReceive(soket);
        System.out.println("Best Node: " + resp);
        portTcp = Integer.parseInt(mediator.getAttribute("tcpPort", resp));
        System.out.println("TCP Port: " + portTcp);
        comunication();

        int port = 5000;
	    String group = "225.4.5.6";
	    String message = "Get info";
	    UdpTool udp = new UdpTool();
	    listNodeInf = udp.getInfNodes(group, port, message);
	    Thread.sleep(100);
	}

	public static void comunication() throws IOException {
		Socket soket = new Socket(serverName, portTcp);
        Message mes = new Message("command", "get", "all");
		Gson gson = new Gson();
		String jmes = gson.toJson(mes);

		tcp.tcpSend(jmes, soket);
		String res = tcp.tcpReceive(soket);

		System.out.println("\n tcp responce " + res + "\n");
		soket.close();
	}
}
