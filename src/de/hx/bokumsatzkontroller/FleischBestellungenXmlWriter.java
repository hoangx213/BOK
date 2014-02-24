package de.hx.bokumsatzkontroller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import android.os.Environment;

public class FleischBestellungenXmlWriter {

	public FleischBestellungenXmlWriter() {
	};

	public void writeFleischBestellungenXml(
			ArrayList<FleischBestellung> fbList, double nettoUmsatzsumme,
			double einkaufssumme) throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException {
		
		File xmlFile = new File(Environment.getExternalStorageDirectory()
				+ "/BOK", "fleisch_bestellungen.xml");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(xmlFile);
		
		Node fbNode = doc.getFirstChild();
		Node nettoUmsatzsummeNode = doc.createElement("nettoUmsatzsumme");
		nettoUmsatzsummeNode.setTextContent(String.valueOf(nettoUmsatzsumme));
		fbNode.appendChild(nettoUmsatzsummeNode);
		
		Node einkaufssummeNode = doc.createElement("einkaufssumme");
		einkaufssummeNode.setTextContent(String.valueOf(einkaufssumme));
		fbNode.appendChild(einkaufssummeNode);
		
		for(FleischBestellung i : fbList){
			Node itemNode = doc.createElement("item");
			Node artikelNameNode = doc.createElement("artikelName");
			artikelNameNode.setTextContent(i.getFleischModel().getArtikelName());
			itemNode.appendChild(artikelNameNode);
			Node bestellungenNode = doc.createElement("bestellungen");
			bestellungenNode.setTextContent(String.valueOf(i.getBestellungen()));
			itemNode.appendChild(bestellungenNode);
			Node totalNode = doc.createElement("total");
			totalNode.setTextContent(String.valueOf(i.getTotal()));
			itemNode.appendChild(totalNode);
			Node umsatzNode = doc.createElement("nettoUmsatz");
			umsatzNode.setTextContent(String.valueOf(i.getNettoUmsatz()));
			itemNode.appendChild(umsatzNode);
			Node einkaufTotalNode = doc.createElement("einkaufTotal");
			einkaufTotalNode.setTextContent(String.valueOf(i.getEinkaufTotal()));
			itemNode.appendChild(einkaufTotalNode);
			fbNode.appendChild(itemNode);
		}
		
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		StreamResult result = new StreamResult(xmlFile);
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
	}

}
