package de.hx.bokumsatzkontroller.util;

import java.util.ArrayList;

import de.hx.bokumsatzkontroller.models.ArtikelBerichtModel;

public class Utils {
	public ArtikelBerichtModel getABWithArtikelNameFromList(
			ArrayList<ArtikelBerichtModel> aBList, String artikelName) {
		for (ArtikelBerichtModel thisAB : aBList) {
			if (thisAB.getArtikelName().equals(artikelName))
				return thisAB;
		}
		return null;
	}
}
