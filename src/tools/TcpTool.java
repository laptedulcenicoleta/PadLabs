package tools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TcpTool {

	public void tcpSend(String mes, Socket client) {

		try {
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF(mes);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String tcpReceive(Socket client) {
		InputStream inFromServer;
		try {
			inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			String mes = in.readUTF();

			return mes;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;

	}

}
