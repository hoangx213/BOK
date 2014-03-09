package de.hx.bokumsatzkontroller;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class OneDayFleischBestellungenModel implements Parcelable{
	String datum;
	int daysFrom1970;
	ArrayList<FleischBestellungModel> fleischbestellungen;
	double nettoUmsatzssumme, einkaufssumme;
	public OneDayFleischBestellungenModel(String datum, int daysFrom1970,
			ArrayList<FleischBestellungModel> fleischbestellungen,
			double nettoUmsatzssumme, double einkaufssumme) {
		super();
		this.datum = datum;
		this.daysFrom1970 = daysFrom1970;
		this.fleischbestellungen = fleischbestellungen;
		this.nettoUmsatzssumme = nettoUmsatzssumme;
		this.einkaufssumme = einkaufssumme;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public int getDaysFrom1970() {
		return daysFrom1970;
	}
	public void setDaysFrom1970(int daysFrom1970) {
		this.daysFrom1970 = daysFrom1970;
	}
	public ArrayList<FleischBestellungModel> getFleischbestellungen() {
		return fleischbestellungen;
	}
	public void setFleischbestellungen(
			ArrayList<FleischBestellungModel> fleischbestellungen) {
		this.fleischbestellungen = fleischbestellungen;
	}
	public double getNettoUmsatzssumme() {
		return nettoUmsatzssumme;
	}
	public void setNettoUmsatzssumme(double nettoUmsatzssumme) {
		this.nettoUmsatzssumme = nettoUmsatzssumme;
	}
	public double getEinkaufssumme() {
		return einkaufssumme;
	}
	public void setEinkaufssumme(double einkaufssumme) {
		this.einkaufssumme = einkaufssumme;
	}
	
	public String toString(){
		return datum;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(datum);
		dest.writeInt(daysFrom1970);
		dest.writeTypedList(fleischbestellungen);
		dest.writeDouble(nettoUmsatzssumme);
		dest.writeDouble(einkaufssumme);
	}
	
	public static final Parcelable.Creator<OneDayFleischBestellungenModel> CREATOR = new Parcelable.Creator<OneDayFleischBestellungenModel>() {
        public OneDayFleischBestellungenModel createFromParcel(Parcel in) {
            return new OneDayFleischBestellungenModel(in);
        }

        public OneDayFleischBestellungenModel[] newArray(int size) {
            return new OneDayFleischBestellungenModel[size];
        }
    };

    private OneDayFleischBestellungenModel(Parcel in) {
    	datum = in.readString();
		daysFrom1970 = in.readInt();
		in.readTypedList(fleischbestellungen, FleischBestellungModel.CREATOR);
		nettoUmsatzssumme = in.readDouble();
		einkaufssumme = in.readDouble();
    }
	
}
