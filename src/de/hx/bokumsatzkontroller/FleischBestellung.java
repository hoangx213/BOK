package de.hx.bokumsatzkontroller;

public class FleischBestellung {
	
	FleischModel fleischModel;
	double bestellungen, total, nettoUmsatz, einkaufTotal;
	public FleischBestellung(FleischModel fleischModel, double bestelungen,
			double total, double nettoUmsatz, double einkaufTotal) {
		this.fleischModel = fleischModel;
		this.bestellungen = bestelungen;
		this.total = total;
		this.nettoUmsatz = nettoUmsatz;
		this.einkaufTotal = einkaufTotal;
	}
	public FleischModel getFleischModel() {
		return fleischModel;
	}
	public void setFleischModel(FleischModel fleischModel) {
		this.fleischModel = fleischModel;
	}
	public double getBestellungen() {
		return bestellungen;
	}
	public void setBestellungen(double bestelungen) {
		this.bestellungen = bestelungen;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getNettoUmsatz() {
		return nettoUmsatz;
	}
	public void setNettoUmsatz(double nettoUmsatz) {
		this.nettoUmsatz = nettoUmsatz;
	}
	public double getEinkaufTotal() {
		return einkaufTotal;
	}
	public void setEinkaufTotal(double einkaufTotal) {
		this.einkaufTotal = einkaufTotal;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fleischModel == null) ? 0 : fleischModel.hashCode());
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
		FleischBestellung other = (FleischBestellung) obj;
		if (fleischModel == null) {
			if (other.fleischModel != null)
				return false;
		} else if (!fleischModel.equals(other.fleischModel))
			return false;
		return true;
	}
	
}
