package de.hx.bokumsatzkontroller.fleischActivity;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.hx.bokumsatzkontroller.R;
import de.hx.bokumsatzkontroller.models.ArtikelBerichtModel;
import de.hx.bokumsatzkontroller.models.fleisch.FleischBestellungModel;
import de.hx.bokumsatzkontroller.models.fleisch.OneDayFleischBestellungenModel;
import de.hx.bokumsatzkontroller.util.Utils;
import de.hx.bokumsatzkontroller.xml.fleisch.FleischXmlParserHelper;

public class FleischZwischenBerichtActivity extends Activity {

	TableLayout table;
	TextView nettoUmsatzTotalView;
	TextView nettoEinkaufTotalView;
	DecimalFormat df = new DecimalFormat("#.##");
	DecimalFormat prozentZahl = new DecimalFormat("#");
	FleischXmlParserHelper xmlPH;
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

	void getBerichtList() {
		xmlPH = new FleischXmlParserHelper();
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
				ArtikelBerichtModel thisFAB = utils
						.getABWithArtikelNameFromList(fleischBerichtList,
								thisArtikelName);
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
			nettoEinkauf.setText(df.format(i.getNettoEinkauf()) + "€");
			nettoEinkauf.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			nettoEinkauf.setBackgroundResource(R.color.White);
			tr.addView(nettoEinkauf);

			TextView bruttoEinkauf = new TextView(this);
			bruttoEinkauf.setText(df.format(i.getBruttoEinkauf()) + "€");
			bruttoEinkauf.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			bruttoEinkauf.setBackgroundResource(R.color.White);
			tr.addView(bruttoEinkauf);

			TextView nettoUmsatz = new TextView(this);
			nettoUmsatz.setText(df.format(i.getNettoUmsatz()) + "€");
			nettoUmsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			nettoUmsatz.setBackgroundResource(R.color.White);
			tr.addView(nettoUmsatz);

			TextView anteil = new TextView(this);
			anteil.setText(prozentZahl.format((i.getNettoUmsatz() / nettoUmsatzTotal) * 100)
					+ "%");
			anteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
			anteil.setBackgroundResource(R.color.White);
			tr.addView(anteil);
			
			TextView bruttoUmsatz = new TextView(this);
			bruttoUmsatz.setText(df.format(i.getBruttoUmsatz()) + "€");
			bruttoUmsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			bruttoUmsatz.setBackgroundResource(R.color.White);
			tr.addView(bruttoUmsatz);

			TextView wareneinsatz = new TextView(this);
			wareneinsatz.setText(df.format(100 * i.getNettoEinkauf()
					/ i.getNettoUmsatz())
					+ "%");
			wareneinsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			wareneinsatz.setBackgroundResource(R.color.White);
			tr.addView(wareneinsatz);

			nettoUmsatzTotalView.setText(df.format(nettoUmsatzTotal) + "€");
			nettoEinkaufTotalView.setText(df.format(nettoEinkaufTotal) + "€");

			table.addView(tr);
			utils.setMarginsToViews(Utils.VIEW_WITHOUT_EDIT_TEXT, artikelName,
					total, nettoUmsatz, nettoEinkauf, bruttoEinkauf,
					bruttoUmsatz, wareneinsatz, anteil);
		}
	}

}
