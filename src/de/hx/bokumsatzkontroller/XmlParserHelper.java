package de.hx.bokumsatzkontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Environment;

public class XmlParserHelper {

	public OneDayFleischBestellungenModel getOneDayFBestellung(int daysFrom1970)
			throws XmlPullParserException, FileNotFoundException {
		OneDayFleischBestellungenModel result = null;
		File xmlFile = new File(Environment.getExternalStorageDirectory()
				+ "/BOK/fleisch_bestellungen.xml");
		if (!xmlFile.exists())
			return null;
		FileInputStream fis = new FileInputStream(xmlFile);
		DocumentBuilderFactory dFactory = null;
		DocumentBuilder builder = null;
		Document ret = null;
		try {
			dFactory = DocumentBuilderFactory.newInstance();
			builder = dFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			ret = builder.parse(new InputSource(fis));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Element zufindendeBestellung = null;
		NodeList nodes = ret.getElementsByTagName("bestellung");
		for(int i=0;i<nodes.getLength();i++){
			Element thisBestellung = (Element)nodes.item(i);
			Element thisDaysFrom1970Element = (Element)thisBestellung.getElementsByTagName("daysFrom1970").item(0);
			int thisDaysFrom1970 = Integer.valueOf(thisDaysFrom1970Element.getTextContent());
			if(thisDaysFrom1970 == daysFrom1970){
				zufindendeBestellung = thisBestellung;
				break;
			}
		}
		String datum;
		double nettoUmsatzsumme, einkaufssumme;
		ArrayList<FleischBestellungModel> fbList = new ArrayList<FleischBestellungModel>();
		datum = zufindendeBestellung.getElementsByTagName("datum").item(0).getTextContent();
		nettoUmsatzsumme = Double.valueOf(zufindendeBestellung.getElementsByTagName("nettoUmsatzsumme").item(0).getTextContent());
		einkaufssumme = Double.valueOf(zufindendeBestellung.getElementsByTagName("einkaufssumme").item(0).getTextContent());
		NodeList fleischBestellungNodeList = zufindendeBestellung.getElementsByTagName("item");
		for(int i=0;i<fleischBestellungNodeList.getLength();i++){
			FleischBestellungModel thisFleischBestellung;
			Element thisFleischBestellungElem = (Element)fleischBestellungNodeList.item(i);
			FleischModel fleischModel;
			String proBestellung;
			int bestellungen, total;
			double einkaufspreis, nettoEinkauf, bruttoEinkauf, 
			verkaufspreis, nettoUmsatz, bruttoUmsatz, wareneinsatz; 
			fleischModel = new FleischModel(thisFleischBestellungElem.getElementsByTagName("artikelName").item(0).getTextContent());
			proBestellung = thisFleischBestellungElem.getElementsByTagName("proBestellung").item(0).getTextContent();
			bestellungen = Integer.valueOf(thisFleischBestellungElem.getElementsByTagName("bestellungen").item(0).getTextContent());
			total = Integer.valueOf(thisFleischBestellungElem.getElementsByTagName("total").item(0).getTextContent());
			einkaufspreis = Double.valueOf(thisFleischBestellungElem.getElementsByTagName("einkaufspreis").item(0).getTextContent());
			nettoEinkauf = Double.valueOf(thisFleischBestellungElem.getElementsByTagName("nettoEinkauf").item(0).getTextContent());
			bruttoEinkauf = Double.valueOf(thisFleischBestellungElem.getElementsByTagName("bruttoEinkauf").item(0).getTextContent());
			verkaufspreis = Double.valueOf(thisFleischBestellungElem.getElementsByTagName("verkaufspreis").item(0).getTextContent());
			nettoUmsatz = Double.valueOf(thisFleischBestellungElem.getElementsByTagName("nettoUmsatz").item(0).getTextContent());
			bruttoUmsatz = Double.valueOf(thisFleischBestellungElem.getElementsByTagName("bruttoUmsatz").item(0).getTextContent());
			wareneinsatz = Double.valueOf(thisFleischBestellungElem.getElementsByTagName("wareneinsatz").item(0).getTextContent());
			thisFleischBestellung = new FleischBestellungModel(fleischModel, proBestellung, bestellungen, total, einkaufspreis, nettoEinkauf, bruttoEinkauf, verkaufspreis, nettoUmsatz, bruttoUmsatz, wareneinsatz);
			fbList.add(thisFleischBestellung);
		}
		result = new OneDayFleischBestellungenModel(datum, daysFrom1970, fbList, nettoUmsatzsumme, einkaufssumme);
		return result;
	}

}
