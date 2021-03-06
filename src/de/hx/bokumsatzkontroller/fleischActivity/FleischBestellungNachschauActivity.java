package de.hx.bokumsatzkontroller.fleischActivity;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.hx.bokumsatzkontroller.R;
import de.hx.bokumsatzkontroller.models.fleisch.FleischBestellungModel;
import de.hx.bokumsatzkontroller.models.fleisch.OneDayFleischBestellungenModel;
import de.hx.bokumsatzkontroller.util.Utils;
import de.hx.bokumsatzkontroller.xml.fleisch.FleischXmlParserHelper;

public class FleischBestellungNachschauActivity extends Activity {

	TableLayout table;
	TextView nettoUmsatzssummeView;
	TextView nettoEinkaufssummeView;
	
	OneDayFleischBestellungenModel thisDayBestellungen;
	DecimalFormat df = new DecimalFormat("#.##");
	DecimalFormat prozentZahl = new DecimalFormat("#.#");
	Utils utils = new Utils();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fleisch_bestellung);
		Button speichernBtn = (Button) findViewById(R.id.fleischSaveBtn);
		speichernBtn.setVisibility(View.GONE);
		LinearLayout datum = (LinearLayout) findViewById(R.id.datumFleischBestellung);
		datum.setVisibility(View.GONE);
		table = (TableLayout) findViewById(R.id.TableLayout1);
		nettoUmsatzssummeView = (TextView) findViewById(R.id.nettoUmsatzSumme);
		nettoEinkaufssummeView = (TextView) findViewById(R.id.nettoEinkaufssumme);
		Intent intent = getIntent();
		String bestellungID = intent.getStringExtra("BestellungID");
		FleischXmlParserHelper xph = new FleischXmlParserHelper();
		try {
			thisDayBestellungen = xph.getOneDayFBWithBestellungID(bestellungID);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(thisDayBestellungen.getDatum());
		renderDayBestellungTablle();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		finish();
		return true;
	}

	void renderDayBestellungTablle() {
		for (FleischBestellungModel i : thisDayBestellungen
				.getFleischbestellungen()) {
			TableRow tr = new TableRow(this);
			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
					TableLayout.LayoutParams.WRAP_CONTENT,
					TableLayout.LayoutParams.WRAP_CONTENT);

			tableRowParams.setMargins(2, 2, 2, 2);

			tr.setLayoutParams(tableRowParams);
			tr.setBackgroundResource(R.color.Brown);

			TextView artikelName = new TextView(this);
			artikelName.setText(i.getFleischModel().getArtikelName());
			artikelName.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			artikelName.setBackgroundResource(R.color.White);
			tr.addView(artikelName);

			TextView proBestell = new TextView(this);
			proBestell.setText(i.getProBestellung());
			proBestell.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			proBestell.setBackgroundResource(R.color.White);
			tr.addView(proBestell);

			TextView bestellungen = new TextView(this);
			bestellungen.setText(String.valueOf(i.getBestellungen()));
			bestellungen.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			bestellungen.setBackgroundResource(R.color.White);
			tr.addView(bestellungen);

			TextView total = new TextView(this);
			total.setText(String.valueOf(i.getTotal()));
			total.setTextAppearance(this, android.R.style.TextAppearance_Large);
			total.setBackgroundResource(R.color.White);
			tr.addView(total);

			TextView einkaufspreis = new TextView(this);
			einkaufspreis.setText(String.valueOf(i.getEinkaufspreis()) + "€");
			einkaufspreis.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			einkaufspreis.setBackgroundResource(R.color.White);
			tr.addView(einkaufspreis);

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

			TextView verkaufspreis = new TextView(this);
			verkaufspreis.setText(String.valueOf(i.getVerkaufspreis()) + "€");
			verkaufspreis.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			verkaufspreis.setBackgroundResource(R.color.White);
			tr.addView(verkaufspreis);

			TextView nettoUmsatz = new TextView(this);
			nettoUmsatz.setText(df.format(i.getNettoUmsatz()) + "€");
			nettoUmsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			nettoUmsatz.setBackgroundResource(R.color.White);
			tr.addView(nettoUmsatz);
			
			TextView anteil = new TextView(this);
			anteil.setText(prozentZahl.format((i.getNettoUmsatz() / thisDayBestellungen
					.getNettoUmsatzssumme()) * 100)
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
			wareneinsatz.setText(df.format(i.getWareneinsatz() * 100) + "%");
			wareneinsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			wareneinsatz.setBackgroundResource(R.color.White);
			tr.addView(wareneinsatz);

			nettoEinkaufssummeView.setText(df.format(thisDayBestellungen
					.getEinkaufssumme()) + "€");
			nettoUmsatzssummeView.setText(df.format(thisDayBestellungen
					.getNettoUmsatzssumme()) + "€");

			table.addView(tr);
			TableLayout.LayoutParams tlp = (TableLayout.LayoutParams) tr
					.getLayoutParams();
			tlp.topMargin = 5;
			tlp.bottomMargin = 5;
			tlp.leftMargin = 2;
			tlp.rightMargin = 2;

			utils.setMarginsToViews(Utils.VIEW_WITHOUT_EDIT_TEXT, artikelName,
					proBestell, bestellungen, total, verkaufspreis,
					nettoUmsatz, einkaufspreis, nettoEinkauf, bruttoEinkauf,
					bruttoUmsatz, wareneinsatz, anteil);
		}
	}
}
