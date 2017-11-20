package tools;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import classes.InfNode;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

public class XmlParser {

	public static InfNode getNodeFtomConfig(String conf) {

		File fXmlFile = new File(conf);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		InfNode infNode = new InfNode();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("thisNode");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					infNode.setId(eElement.getAttribute("id"));
					infNode.setNodeIp(eElement.getElementsByTagName("ip").item(0).getTextContent());
					infNode.setTcpPort(
							Integer.parseInt(eElement.getElementsByTagName("tcpPort").item(0).getTextContent()));
				}
			}
		} catch (Exception e) {
		}
		return infNode;
	}

	public static ArrayList<InfNode> getNodeList(String conf) {

		File fXmlFile = new File(conf);
		ArrayList<InfNode> listNodes = new ArrayList<InfNode>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// -----------------------------------------------
			NodeList nList = doc.getElementsByTagName("node");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					InfNode infNode = new InfNode();
					infNode.setId(eElement.getAttribute("id"));
					infNode.setNodeIp(eElement.getElementsByTagName("ip").item(0).getTextContent());
					infNode.setTcpPort(
							Integer.parseInt(eElement.getElementsByTagName("tcpPort").item(0).getTextContent()));
					listNodes.add(infNode);
				}
			}
		} catch (Exception e) {
		}
		return listNodes;
	}

}