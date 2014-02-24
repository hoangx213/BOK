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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((artikelName == null) ? 0 : artikelName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(bruttoPreis);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(bruttoUmsatzProKarton);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((einheit == null) ? 0 : einheit.hashCode());
		temp = Double.doubleToLongBits(einheitProBestellung);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(einkaufspreis);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(nettoPreis);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(nettoUmsatzProEinheit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(nettoUmsatzProKarton);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(schwund);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(verkaufsfaktor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(wareneinsatz);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FleischModel other = (FleischModel) obj;
		if (artikelName == null) {
			if (other.artikelName != null)
				return false;
		} else if (!artikelName.equals(other.artikelName))
			return false;
		if (Double.doubleToLongBits(bruttoPreis) != Double
				.doubleToLongBits(other.bruttoPreis))
			return false;
		if (Double.doubleToLongBits(bruttoUmsatzProKarton) != Double
				.doubleToLongBits(other.bruttoUmsatzProKarton))
			return false;
		if (einheit == null) {
			if (other.einheit != null)
				return false;
		} else if (!einheit.equals(other.einheit))
			return false;
		if (Double.doubleToLongBits(einheitProBestellung) != Double
				.doubleToLongBits(other.einheitProBestellung))
			return false;
		if (Double.doubleToLongBits(einkaufspreis) != Double
				.doubleToLongBits(other.einkaufspreis))
			return false;
		if (Double.doubleToLongBits(nettoPreis) != Double
				.doubleToLongBits(other.nettoPreis))
			return false;
		if (Double.doubleToLongBits(nettoUmsatzProEinheit) != Double
				.doubleToLongBits(other.nettoUmsatzProEinheit))
			return false;
		if (Double.doubleToLongBits(nettoUmsatzProKarton) != Double
				.doubleToLongBits(other.nettoUmsatzProKarton))
			return false;
		if (Double.doubleToLongBits(schwund) != Double
				.doubleToLongBits(other.schwund))
			return false;
		if (Double.doubleToLongBits(verkaufsfaktor) != Double
				.doubleToLongBits(other.verkaufsfaktor))
			return false;
		if (Double.doubleToLongBits(wareneinsatz) != Double
				.doubleToLongBits(other.wareneinsatz))
			return false;
		return true;
	}
	
}
