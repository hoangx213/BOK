package de.hx.bokumsatzkontroller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FleischActivity extends Activity implements OnClickListener {

	ArrayList<FleischModel> fleischList;
	ArrayList<FleischBestellungModel> fleischBestellungenList;
	Set<FleischBestellungModel> fleischBestellungenSet;
	FleischModelXmlParser fleischXP;
	TableLayout table;
	static final int PROBESTELLUNGID = 1111;
	static final int TOTALID = 2222;
	static final int NETTOUMSATZID = 3333;
	static final int NETTOEINKAUFID = 4444;
	static final int BRUTTOEINKAUFID = 5555;
	static final int BRUTTOUMSATZID = 6666;
	static final int WARENEINSATZID = 7777;
	static final int ANTEILID = 8888;
	static final int BESTELLUNGENID = 9999;
	static final int EINKAUFSPREISID = 1234;
	static final int VERKAUFSPREISID = 4321;
	DecimalFormat df = new DecimalFormat("#.##");
	DecimalFormat prozentZahl = new DecimalFormat("#");
	Button fleischSaveBtn;
	double nettoUmsatzsumme = 0;
	double nettoEinkaufssumme = 0;
	int daysFrom1970;
	String bestellungsdatum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fleisch_bestellung);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		Date d = new Date();
		bestellungsdatum = DateFormat.format("MMMM d, yyyy ", d.getTime())
				.toString();
		actionBar.setTitle(bestellungsdatum);

		Calendar calendar = Calendar.getInstance();
		daysFrom1970 = (int) (calendar.getTimeInMillis() / (1000 * 3600 * 24));

		fleischSaveBtn = (Button) findViewById(R.id.fleischSaveBtn);
		fleischSaveBtn.setOnClickListener(this);
		table = (TableLayout) findViewById(R.id.TableLayout1);
		fleischList = new ArrayList<FleischModel>();
		fleischBestellungenSet = new HashSet<FleischBestellungModel>();
		fleischXP = new FleischModelXmlParser(getApplicationContext());

		try {
			fleischList = fleischXP.fleischParsen();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (FleischModel i : fleischList) {
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

			TextView proBestell = new TextView(this);
			proBestell.setText(df.format(i.getEinheitProBestellung()) + " "
					+ i.getEinheit());
			proBestell.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			proBestell.setBackgroundResource(R.color.White);
			proBestell.setId(PROBESTELLUNGID);
			tr.addView(proBestell);

			EditText bestellungen = new EditText(this);
			bestellungen.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			bestellungen.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			bestellungen.setBackgroundResource(R.color.LightGrey);
			bestellungen.setId(BESTELLUNGENID);
			tr.addView(bestellungen);

			TextView total = new TextView(this);
			total.setTextAppearance(this, android.R.style.TextAppearance_Large);
			total.setBackgroundResource(R.color.White);
			total.setId(TOTALID);
			tr.addView(total);

			EditText einkaufspreis = new EditText(this);
			einkaufspreis.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			einkaufspreis.setText(String.valueOf(i.getEinkaufspreis()));
			einkaufspreis.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			einkaufspreis.setBackgroundResource(R.color.White);
			einkaufspreis.setId(EINKAUFSPREISID);
			tr.addView(einkaufspreis);

			TextView nettoEinkauf = new TextView(this);
			nettoEinkauf.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			nettoEinkauf.setBackgroundResource(R.color.White);
			nettoEinkauf.setId(NETTOEINKAUFID);
			tr.addView(nettoEinkauf);

			TextView bruttoEinkauf = new TextView(this);
			bruttoEinkauf.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			bruttoEinkauf.setBackgroundResource(R.color.White);
			bruttoEinkauf.setId(BRUTTOEINKAUFID);
			tr.addView(bruttoEinkauf);

			EditText verkaufspreis = new EditText(this);
			verkaufspreis.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			verkaufspreis.setText(String.valueOf(i.getNettoPreis()));
			verkaufspreis.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			verkaufspreis.setBackgroundResource(R.color.White);
			verkaufspreis.setId(VERKAUFSPREISID);
			tr.addView(verkaufspreis);

			TextView nettoUmsatz = new TextView(this);
			nettoUmsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			nettoUmsatz.setBackgroundResource(R.color.White);
			nettoUmsatz.setId(NETTOUMSATZID);
			tr.addView(nettoUmsatz);

			TextView bruttoUmsatz = new TextView(this);
			bruttoUmsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			bruttoUmsatz.setBackgroundResource(R.color.White);
			bruttoUmsatz.setId(BRUTTOUMSATZID);
			tr.addView(bruttoUmsatz);

			TextView wareneinsatz = new TextView(this);
			wareneinsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			wareneinsatz.setBackgroundResource(R.color.White);
			wareneinsatz.setId(WARENEINSATZID);
			tr.addView(wareneinsatz);

			TextView anteil = new TextView(this);
			anteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
			anteil.setBackgroundResource(R.color.White);
			anteil.setId(ANTEILID);
			tr.addView(anteil);

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

			bestellungen.addTextChangedListener(new MyTextWatcher(bestellungen,
					fleischList.indexOf(i)));
			einkaufspreis.addTextChangedListener(new MyTextWatcher(
					einkaufspreis, fleischList.indexOf(i)));
			verkaufspreis.addTextChangedListener(new MyTextWatcher(
					verkaufspreis, fleischList.indexOf(i)));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class MyTextWatcher implements TextWatcher {

		EditText view;
		int index;

		public MyTextWatcher(EditText view, int index) {
			this.view = view;
			this.index = index;
		}

		@Override
		public void afterTextChanged(Editable s) {
			TableRow tr = (TableRow) view.getParent();
			EditText bestellungenView = (EditText) tr
					.findViewById(BESTELLUNGENID);
			EditText einkaufspreisView = (EditText) tr
					.findViewById(EINKAUFSPREISID);
			EditText verkaufspreisView = (EditText) tr
					.findViewById(VERKAUFSPREISID);
			int bestellungen = 0;
			double einkaufspreis = 0, verkaufspreis = 0;
			if (s.toString().equals("")) {
				switch (view.getId()) {
				case BESTELLUNGENID:
					bestellungen = 0;
					einkaufspreis = Double.valueOf(einkaufspreisView.getText()
							.toString());
					verkaufspreis = Double.valueOf(verkaufspreisView.getText()
							.toString());
					break;
				case EINKAUFSPREISID:
					bestellungen = Integer.valueOf(bestellungenView.getText()
							.toString());
					einkaufspreis = 0;
					verkaufspreis = Double.valueOf(verkaufspreisView.getText()
							.toString());
					break;
				case VERKAUFSPREISID:
					bestellungen = Integer.valueOf(bestellungenView.getText()
							.toString());
					einkaufspreis = Double.valueOf(einkaufspreisView.getText()
							.toString());
					verkaufspreis = 0;
					break;
				default:
					break;
				}
			} else {
				bestellungen = Integer.valueOf(bestellungenView.getText()
						.toString());
				einkaufspreis = Double.valueOf(einkaufspreisView.getText()
						.toString());
				verkaufspreis = Double.valueOf(verkaufspreisView.getText()
						.toString());
			}
			TextView totalView = (TextView) tr.findViewById(TOTALID);
			TextView nettoUmsatzView = (TextView) tr
					.findViewById(NETTOUMSATZID);
			TextView nettoEinkaufView = (TextView) tr
					.findViewById(NETTOEINKAUFID);
			TextView bruttoEinkaufView = (TextView) tr
					.findViewById(BRUTTOEINKAUFID);
			TextView bruttoUmsatzView = (TextView) tr
					.findViewById(BRUTTOUMSATZID);
			TextView wareneinsatzView = (TextView) tr
					.findViewById(WARENEINSATZID);
			TextView nettoUmsatzssummeView = (TextView) findViewById(R.id.nettoUmsatzSumme);
			TextView einkaufssummeView = (TextView) findViewById(R.id.einkaufssumme);

			FleischModel fleisch = fleischList.get(index);

			int totalBestellung = (int) (bestellungen
					* fleisch.einheitProBestellung);
			totalView.setText(String.valueOf(totalBestellung));

			double verkaufsmenge = totalBestellung
					- (totalBestellung * fleisch.getSchwund());
			double nettoUmsatz = verkaufsmenge * fleisch.getVerkaufsfaktor()
					* verkaufspreis;
			nettoUmsatzView.setText(df.format(nettoUmsatz));

			double nettoEinkauf = totalBestellung * einkaufspreis;
			nettoEinkaufView.setText(df.format(nettoEinkauf));

			double bruttoEinkauf = nettoEinkauf * 1.12;
			bruttoEinkaufView.setText(df.format(bruttoEinkauf));

			double bruttoUmsatz = nettoUmsatz * 1.12;
			bruttoUmsatzView.setText(df.format(bruttoUmsatz));

			double wareneinsatz = nettoEinkauf / nettoUmsatz;
			wareneinsatzView.setText(df.format(wareneinsatz * 100) + "%");

			TableLayout tl = (TableLayout) tr.getParent();
			nettoUmsatzsumme = 0;
			nettoEinkaufssumme = 0;
			for (int j = 0; j < tl.getChildCount() - 1; j++) {
				TableRow thisTr = (TableRow) tl.getChildAt(j + 1);
				if (((TextView) thisTr.findViewById(NETTOUMSATZID)).getText() != "") {
					double thisNettoUmsatz = Double
							.valueOf(((String) ((TextView) thisTr
									.findViewById(NETTOUMSATZID)).getText())
									.replace(",", "."));
					nettoUmsatzsumme += thisNettoUmsatz;
				}
				if (((TextView) thisTr.findViewById(NETTOEINKAUFID)).getText() != "") {
					double thisEinkaufTotal = Double
							.valueOf(((String) ((TextView) thisTr
									.findViewById(NETTOEINKAUFID)).getText())
									.replace(",", "."));
					nettoEinkaufssumme += thisEinkaufTotal;
				}
			}

			for (int j = 0; j < tl.getChildCount() - 1; j++) {
				TableRow thisTr = (TableRow) tl.getChildAt(j + 1);
				TextView thisAnteilView = (TextView) thisTr
						.findViewById(ANTEILID);
				if (((TextView) thisTr.findViewById(NETTOUMSATZID)).getText() != "") {
					double thisNettoUmsatz = Double
							.valueOf(((String) ((TextView) thisTr
									.findViewById(NETTOUMSATZID)).getText())
									.replace(",", "."));
					double thisAnteil = thisNettoUmsatz / nettoUmsatzsumme;
					String gerundeteAnteil = prozentZahl
							.format(thisAnteil * 100);
					thisAnteilView.setText(gerundeteAnteil + "%");
				}
			}
			nettoUmsatzssummeView.setText(df.format(nettoUmsatzsumme) + "€");
			einkaufssummeView.setText(df.format(nettoEinkaufssumme) + "€");
			String proBestellung = ((TextView) tr.findViewById(PROBESTELLUNGID))
					.getText().toString();
			FleischBestellungModel fb = new FleischBestellungModel(fleisch,
					proBestellung, bestellungen, totalBestellung,
					einkaufspreis, nettoEinkauf, bruttoEinkauf, verkaufspreis,
					nettoUmsatz, bruttoUmsatz, wareneinsatz);
			fleischBestellungenSet.add(fb);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fleischSaveBtn:
			fleischBestellungenList = new ArrayList<FleischBestellungModel>(
					fleischBestellungenSet);
			FleischBestellungenXmlWriter fbw = new FleischBestellungenXmlWriter();
			try {
				fbw.writeFleischBestellungenXml(fleischBestellungenList,
						nettoUmsatzsumme, nettoEinkaufssumme, bestellungsdatum,
						daysFrom1970);
			} catch (TransformerFactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent(this, FleischBestellungenActivity.class);
			startActivity(intent);
		}
	}

}
