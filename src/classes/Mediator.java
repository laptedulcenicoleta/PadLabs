package classes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import colections.Message;
import tools.TcpTool;
import tools.XmlParser;

public class Mediator {
	static ArrayList<InfNode> listNodes = new ArrayList<InfNode>();
	static int port = 5555;

	public static void main(String[] args) throws IOException {
		TcpTool tcp = new TcpTool();
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {

				System.out.println("Waiting for client on port " + port );
				Socket server = serverSocket.accept();
				String receiveMessage = tcp.tcpReceive(server);

				String message = getAttribute("message", receiveMessage);

				if (message.equals("bestNode")) {

					tcp.tcpSend(getPortMaxNode(), server);
				}

				server.close();
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static String getPortMaxNode() throws IOException {
		TcpTool tcp = new TcpTool();
		int max = 0;
		listNodes = XmlParser.getNodeList("mediator.xml");
		String best = null;
		for (InfNode node : listNodes) {
			Socket soket = new Socket(node.nodeIp, node.tcpPort);
			Message mes = new Message("command", "get", "about");
			Gson gson = new Gson();
			String jmes = gson.toJson(mes);

			tcp.tcpSend(jmes, soket);

			String res = tcp.tcpReceive(soket);

			int con = Integer.parseInt(getAttribute("connections", res));

			if (con >= max) {
				max = con;
				best = res;
			}
		}
		return best;

	}

	public static String getAttribute(String key, String input) {
		JSONObject jObject;
		String command = null;
		try {
			jObject = new JSONObject(input);
			command = jObject.getString(key);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}

		return command;
	}

}
