package de.hx.bokumsatzkontroller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.hx.bokumsatzkontroller.models.ArtikelBerichtModel;
import de.hx.bokumsatzkontroller.models.FleischBestellungModel;
import de.hx.bokumsatzkontroller.models.OneDayFleischBestellungenModel;
import de.hx.bokumsatzkontroller.util.Utils;
import de.hx.bokumsatzkontroller.xml.XmlParserHelper;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FleischZwischenBerichtActivity extends Activity {

	TableLayout table;
	TextView nettoUmsatzTotalView;
	TextView nettoEinkaufTotalView;
	DecimalFormat df = new DecimalFormat("#.##");
	DecimalFormat prozentZahl = new DecimalFormat("#");
	XmlParserHelper xmlPH;
	int vonDaysFrom1970, bisDaysFrom1970;
	ArrayList<OneDayFleischBestellungenModel> daysFBList = new ArrayList<OneDayFleischBestellungenModel>();
	ArrayList<ArtikelBerichtModel> fleischBerichtList = new ArrayList<ArtikelBerichtModel>();
	double nettoEinkaufTotal = 0, nettoUmsatzTotal = 0;
	Utils utils = new Utils();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fleisch_zwischenbericht);
		table = (TableLayout) findViewById(R.id.tableLayout2);
		nettoUmsatzTotalView = (TextView) findViewById(R.id.berichtNettoUmsatzTotal);
		nettoEinkaufTotalView = (TextView) findViewById(R.id.berichtNettoEinkaufTotal);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Fleischbestellungsbericht");
		getBerichtList();
		renderBerichtTablle();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		startActivity(new Intent(this, FleischBestellungenActivity.class));
		return true;
	}
	
	void getBerichtList(){
		xmlPH = new XmlParserHelper();
		Intent intent = getIntent();
		vonDaysFrom1970 = intent.getIntExtra("vonDaysFrom1970", 0);
		bisDaysFrom1970 = intent.getIntExtra("bisDaysFrom1970", 0);
		try {
			daysFBList = xmlPH.getDaysFleischBestellungen(vonDaysFrom1970,
					bisDaysFrom1970);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (OneDayFleischBestellungenModel thisOneDayFB : daysFBList) {
			nettoEinkaufTotal += thisOneDayFB.getEinkaufssumme();
			nettoUmsatzTotal += thisOneDayFB.getNettoUmsatzssumme();
			for (FleischBestellungModel thisFB : thisOneDayFB
					.getFleischbestellungen()) {
				String thisArtikelName = thisFB.getFleischModel()
						.getArtikelName();
				ArtikelBerichtModel thisFAB = utils.getABWithArtikelNameFromList(
						fleischBerichtList, thisArtikelName);
				if (thisFAB == null) {
					ArtikelBerichtModel newFAB = new ArtikelBerichtModel(
							thisArtikelName, thisFB.getTotal(),
							thisFB.getNettoEinkauf(),
							thisFB.getBruttoEinkauf(), thisFB.getNettoUmsatz(),
							thisFB.getBruttoUmsatz());
					fleischBerichtList.add(newFAB);
				} else {
					thisFAB.addBruttoEinkauf(thisFB.getBruttoEinkauf());
					thisFAB.addBruttoUmsatz(thisFB.getBruttoUmsatz());
					thisFAB.addNettoEinkauf(thisFB.getNettoEinkauf());
					thisFAB.addNettoUmsatz(thisFB.getNettoUmsatz());
					thisFAB.addTotal(thisFB.getTotal());
				}
			}
		}
	}
	
	void renderBerichtTablle() {
		for (ArtikelBerichtModel i : fleischBerichtList) {
			TableRow tr = new TableRow(this);
			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
					TableLayout.LayoutParams.WRAP_CONTENT,
					TableLayout.LayoutParams.WRAP_CONTENT);

			tableRowParams.setMargins(2, 2, 2, 2);

			tr.setLayoutParams(tableRowParams);
			tr.setBackgroundResource(R.color.Brown);

			TextView artikelName = new TextView(this);
			artikelName.setText(i.getArtikelName());
			artikelName.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			artikelName.setBackgroundResource(R.color.White);
			tr.addView(artikelName);

			TextView total = new TextView(this);
			total.setText(String.valueOf(i.getTotal()));
			total.setTextAppearance(this, android.R.style.TextAppearance_Large);
			total.setBackgroundResource(R.color.White);
			tr.addView(total);

			TextView nettoEinkauf = new TextView(this);
			nettoEinkauf.setText(df.format(i.getNettoEinkauf())+"€");
			nettoEinkauf.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			nettoEinkauf.setBackgroundResource(R.color.White);
			tr.addView(nettoEinkauf);

			TextView bruttoEinkauf = new TextView(this);
			bruttoEinkauf.setText(df.format(i.getBruttoEinkauf())+"€");
			bruttoEinkauf.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			bruttoEinkauf.setBackgroundResource(R.color.White);
			tr.addView(bruttoEinkauf);

			TextView nettoUmsatz = new TextView(this);
			nettoUmsatz.setText(df.format(i.getNettoUmsatz())+"€");
			nettoUmsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			nettoUmsatz.setBackgroundResource(R.color.White);
			tr.addView(nettoUmsatz);

			TextView bruttoUmsatz = new TextView(this);
			bruttoUmsatz.setText(df.format(i.getBruttoUmsatz())+"€");
			bruttoUmsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			bruttoUmsatz.setBackgroundResource(R.color.White);
			tr.addView(bruttoUmsatz);

			TextView wareneinsatz = new TextView(this);
			wareneinsatz.setText(df.format(100*i.getNettoEinkauf()/i.getNettoUmsatz())+"%");
			wareneinsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			wareneinsatz.setBackgroundResource(R.color.White);
			tr.addView(wareneinsatz);

			TextView anteil = new TextView(this);
			anteil.setText(prozentZahl.format((i.getNettoUmsatz() / nettoUmsatzTotal) * 100)
					+ "%");
			anteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
			anteil.setBackgroundResource(R.color.White);
			tr.addView(anteil);

			nettoUmsatzTotalView.setText(df.format(nettoUmsatzTotal) + "€");
			nettoEinkaufTotalView.setText(df.format(nettoEinkaufTotal) + "€");

			table.addView(tr);
			TableLayout.LayoutParams tlp = (TableLayout.LayoutParams) tr
					.getLayoutParams();
			tlp.topMargin = 5;
			tlp.bottomMargin = 5;
			tlp.leftMargin = 2;
			tlp.rightMargin = 2;
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) artikelName
					.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
			lp = (LinearLayout.LayoutParams) total.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
			lp = (LinearLayout.LayoutParams) nettoUmsatz.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
			lp = (LinearLayout.LayoutParams) nettoEinkauf.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
			lp = (LinearLayout.LayoutParams) bruttoEinkauf.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
			lp = (LinearLayout.LayoutParams) bruttoUmsatz.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
			lp = (LinearLayout.LayoutParams) wareneinsatz.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
			lp = (LinearLayout.LayoutParams) anteil.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
		}
	}

}
