package classes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import com.google.gson.Gson;
import colections.Message;
import colections.Worker;
import tools.TcpTool;
import org.json.JSONException;
import org.json.JSONObject;

public class Command {
	ArrayList<InfNode> listNodes = new ArrayList<InfNode>();
	TcpTool tcp = new TcpTool();
	public ArrayList<Worker> listWorkers = new ArrayList<Worker>();
	Socket server = null;
	String jsonCommand = null;
	Gson gson = new Gson();
	InfNode thisNode = null;
	static Mediator mediator = new Mediator();

	Command(Socket server, InfNode thisNode, ArrayList<Worker> listWorkers, ArrayList<InfNode> listNodes,
			String command) {
		this.server = server;
		this.listWorkers = listWorkers;
		this.listNodes = listNodes;
		this.jsonCommand = command;
		this.thisNode = thisNode;
	}

	public void execute() throws InstantiationException, IllegalAccessException, SecurityException,
			IllegalArgumentException, InvocationTargetException, JSONException {
		String nameMethod = getAttribute("message", jsonCommand);
		Class params[] = {};
		Class cls = this.getClass();
		Method method;
		try {
			method = cls.getDeclaredMethod(nameMethod, params);
			method.invoke(this, null);
		} catch (NoSuchMethodException e) {
			tcp.tcpSend("HAHA Command not found :P", server);
			System.out.println("HAHA Command not found   :P");
		}

	}

	public void all() {
		Message mes = new Message("command", "get", "getInfoByNode");
		tcp.tcpSend(askAll(mes).toString(), server);

	}

	public String getInfoByNode() {
		String jmes = gson.toJson(listWorkers);
		tcp.tcpSend(jmes, server);
		return jmes;
	}

	public void about() {
		String con = listNodes.size() + "";

		String res = "{\"nodeId\":\"" + thisNode.getId() + "\",\"nodeIP\":\"" + thisNode.getNodeIp()
				+ "\",\"tcpPort\":\"" + thisNode.getTcpPort() + "\",\"connections\":\"" + con + "\"}";

		tcp.tcpSend(res, server);
	}

	public ArrayList<String> askAll(Message mes) {
		ArrayList<String> listResp = new ArrayList<String>();
		for (InfNode node : listNodes) {
			Socket soket;
			try {
				soket = new Socket(node.getNodeIp(), node.getTcpPort());

				String jmes = gson.toJson(mes);
				tcp.tcpSend(jmes, soket);
				listResp.add(tcp.tcpReceive(soket));
				soket.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listResp;
	}
	public String getAttribute(String key, String input) throws JSONException {
		JSONObject jObject = new JSONObject(input);
		String command = jObject.getString(key);
		return command;
	}
}