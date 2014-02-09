package de.hx.bokumsatzkontroller;

public class FleischModel {

	String artikelName, einheit;
	double einheitProBestellung, schwund, verkaufsfaktor, bruttoPreis,
			nettoPreis, einkaufspreis;
	double nettoUmsatzProKarton, bruttoUmsatzProKarton, nettoUmsatzProEinheit,
			wareneinsatz;

	public FleischModel(String artikelname, String einheit,
			double einheitProBestellung, double schwund, double verkaufsfaktor,
			double bruttoPreis, double nettoPreis, double einkaufspreis) {
		this.artikelName = artikelname;
		this.einheit = einheit;
		this.einheitProBestellung = einheitProBestellung;
		this.schwund = schwund;
		this.verkaufsfaktor = verkaufsfaktor;
		this.bruttoPreis = bruttoPreis;
		this.nettoPreis = nettoPreis;
		this.einkaufspreis = einkaufspreis;
		
		this.nettoUmsatzProKarton = (this.einheitProBestellung - this.einheitProBestellung * this.schwund) * this.verkaufsfaktor * this.nettoPreis;
		this.bruttoUmsatzProKarton = this.nettoUmsatzProEinheit * 0.12;
		this.nettoUmsatzProEinheit = this.nettoUmsatzProKarton / this.einheitProBestellung;
		this.wareneinsatz = this.einkaufspreis / this.nettoUmsatzProEinheit;
	}

	public String getArtikelName() {
		return artikelName;
	}

	public String getEinheit() {
		return einheit;
	}

	public double getEinheitProBestellung() {
		return einheitProBestellung;
	}

	public double getSchwund() {
		return schwund;
	}

	public double getVerkaufsfaktor() {
		return verkaufsfaktor;
	}

	public double getBruttoPreis() {
		return bruttoPreis;
	}

	public double getNettoPreis() {
		return nettoPreis;
	}

	public double getEinkaufspreis() {
		return einkaufspreis;
	}

	public double getNettoUmsatzProKarton() {
		return nettoUmsatzProKarton;
	}

	public double getBruttoUmsatzProKarton() {
		return bruttoUmsatzProKarton;
	}

	public double getNettoUmsatzProEinheit() {
		return nettoUmsatzProEinheit;
	}

	public double getWareneinsatz() {
		return wareneinsatz;
	}
	
}
