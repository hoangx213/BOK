package de.hx.bokumsatzkontroller;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Date;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FleischBestellungNachschauActivity extends Activity {

	TableLayout table;
	TextView nettoUmsatzssummeView;
	TextView einkaufssummeView;
	OneDayFleischBestellungenModel thisDayBestellungen;
	DecimalFormat df = new DecimalFormat("#.##");
	DecimalFormat prozentZahl = new DecimalFormat("#");

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fleisch_bestellung);
		Button speichernBtn = (Button) findViewById(R.id.fleischSaveBtn);
		speichernBtn.setVisibility(View.GONE);
		table = (TableLayout) findViewById(R.id.TableLayout1);
		nettoUmsatzssummeView = (TextView) findViewById(R.id.nettoUmsatzSumme);
		einkaufssummeView = (TextView) findViewById(R.id.einkaufssumme);
		Intent intent = getIntent();
		int daysFrom1970 = intent.getIntExtra("BestellungID", 0);
		XmlParserHelper xph = new XmlParserHelper();
		try {
			thisDayBestellungen = xph.getOneDayFBestellung(daysFrom1970);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(thisDayBestellungen.getDatum());
		renderDayBestellungTablle();
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
			einkaufspreis.setText(String.valueOf(i.getEinkaufspreis()));
			einkaufspreis.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			einkaufspreis.setBackgroundResource(R.color.White);
			tr.addView(einkaufspreis);

			TextView nettoEinkauf = new TextView(this);
			nettoEinkauf.setText(df.format(i.getNettoEinkauf()));
			nettoEinkauf.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			nettoEinkauf.setBackgroundResource(R.color.White);
			tr.addView(nettoEinkauf);

			TextView bruttoEinkauf = new TextView(this);
			bruttoEinkauf.setText(df.format(i.getBruttoEinkauf()));
			bruttoEinkauf.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			bruttoEinkauf.setBackgroundResource(R.color.White);
			tr.addView(bruttoEinkauf);

			TextView verkaufspreis = new TextView(this);
			verkaufspreis.setText(String.valueOf(i.getVerkaufspreis()));
			verkaufspreis.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			verkaufspreis.setBackgroundResource(R.color.White);
			tr.addView(verkaufspreis);

			TextView nettoUmsatz = new TextView(this);
			nettoUmsatz.setText(df.format(i.getNettoUmsatz()));
			nettoUmsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			nettoUmsatz.setBackgroundResource(R.color.White);
			tr.addView(nettoUmsatz);

			TextView bruttoUmsatz = new TextView(this);
			bruttoUmsatz.setText(df.format(i.getBruttoUmsatz()));
			bruttoUmsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			bruttoUmsatz.setBackgroundResource(R.color.White);
			tr.addView(bruttoUmsatz);

			TextView wareneinsatz = new TextView(this);
			wareneinsatz.setText(df.format(i.getWareneinsatz()));
			wareneinsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			wareneinsatz.setBackgroundResource(R.color.White);
			tr.addView(wareneinsatz);

			TextView anteil = new TextView(this);
			anteil.setText(prozentZahl.format((i.getNettoUmsatz()
					/ thisDayBestellungen.getNettoUmsatzssumme())*100)
					+ "%");
			anteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
			anteil.setBackgroundResource(R.color.White);
			tr.addView(anteil);

			einkaufssummeView.setText(df.format(thisDayBestellungen
					.getEinkaufssumme()));
			nettoUmsatzssummeView.setText(df.format(thisDayBestellungen
					.getNettoUmsatzssumme()));

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
			lp = (LinearLayout.LayoutParams) proBestell.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
			lp = (LinearLayout.LayoutParams) bestellungen.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
			lp = (LinearLayout.LayoutParams) total.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
			lp = (LinearLayout.LayoutParams) verkaufspreis.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
			lp = (LinearLayout.LayoutParams) nettoUmsatz.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;
			lp = (LinearLayout.LayoutParams) einkaufspreis.getLayoutParams();
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
