package de.hx.bokumsatzkontroller;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;

public class FleischXmlParser {

	Context context;

	public FleischXmlParser(Context context) {
		this.context = context;
	}

	public ArrayList<FleischModel> fleischParsen()
			throws XmlPullParserException, IOException {

		ArrayList<FleischModel> result = new ArrayList<FleischModel>();
		XmlPullParser xpp = context.getResources().getXml(R.xml.fleisch);
		xpp.next();
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (xpp.getName() != null) {
				if (xpp.getName().equals("fleisch")
						&& eventType == XmlPullParser.START_TAG) {
					eventType = xpp.next();
					while (!xpp.getName().equals("fleisch")) {
						if (xpp.getName().equals("item")) {
							FleischModel fleischModel;
							String artikelName = "", einheit = "";
							double einheitProBestellung = 0, schwund = 0, verkaufsfaktor = 0, bruttoPreis = 0, nettoPreis = 0, einkaufspreis = 0;
							eventType = xpp.next();
							while (!xpp.getName().equals("item")) {
								if (xpp.getName().equals("artikelName")
										&& eventType == XmlPullParser.START_TAG) {
									eventType = xpp.next();
									artikelName = xpp.getText();
								} else if (xpp.getName().equals("einheit")
										&& eventType == XmlPullParser.START_TAG) {
									eventType = xpp.next();
									einheit = xpp.getText();
								} else if (xpp.getName().equals(
										"einheitProBestellung")
										&& eventType == XmlPullParser.START_TAG) {
									eventType = xpp.next();
									einheitProBestellung = Double.valueOf(xpp
											.getText());
								} else if (xpp.getName().equals("schwund")
										&& eventType == XmlPullParser.START_TAG) {
									eventType = xpp.next();
									schwund = Double.valueOf(xpp.getText());
								} else if (xpp.getName().equals(
										"verkaufsfaktor")
										&& eventType == XmlPullParser.START_TAG) {
									eventType = xpp.next();
									verkaufsfaktor = Double.valueOf(xpp
											.getText());
								} else if (xpp.getName().equals("bruttoPreis")
										&& eventType == XmlPullParser.START_TAG) {
									eventType = xpp.next();
									bruttoPreis = Double.valueOf(xpp.getText());
								} else if (xpp.getName().equals("nettoPreis")
										&& eventType == XmlPullParser.START_TAG) {
									eventType = xpp.next();
									nettoPreis = Double.valueOf(xpp.getText());
								} else if (xpp.getName()
										.equals("einkaufspreis")
										&& eventType == XmlPullParser.START_TAG) {
									eventType = xpp.next();
									einkaufspreis = Double.valueOf(xpp
											.getText());
								}
								eventType = xpp.next();
							}
							fleischModel = new FleischModel(artikelName,
									einheit, einheitProBestellung, schwund,
									verkaufsfaktor, bruttoPreis, nettoPreis,
									einkaufspreis);
							result.add(fleischModel);
						}
						eventType = xpp.next();
					}
				}
				eventType = xpp.next();
			}
			eventType = xpp.next();
		}
		return result;
	}
}
