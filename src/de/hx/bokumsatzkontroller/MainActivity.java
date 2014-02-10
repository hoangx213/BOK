package de.hx.bokumsatzkontroller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {

	ArrayList<FleischModel> fleischList;
	FleischXmlParser fleischXP;
	TableLayout table;
	static final int PROBESTELLUNGID = 1111;
	static final int TOTALID = 2222;
	static final int NETTOUMSATZID = 3333;
	static final int EINKAUFTOTALID = 4444;
	DecimalFormat df = new DecimalFormat("#.##");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		table = (TableLayout) findViewById(R.id.TableLayout1);
		fleischList = new ArrayList<FleischModel>();
		fleischXP = new FleischXmlParser(getApplicationContext());

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

			tr.addView(bestellungen);

			TextView total = new TextView(this);
			total.setTextAppearance(this, android.R.style.TextAppearance_Large);
			total.setBackgroundResource(R.color.White);
			total.setId(TOTALID);
			tr.addView(total);

			TextView nettoPreis = new TextView(this);
			nettoPreis.setText(df.format(i.getNettoPreis()) + "€");
			nettoPreis.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			nettoPreis.setBackgroundResource(R.color.White);
			tr.addView(nettoPreis);

			TextView nettoUmsatz = new TextView(this);
			nettoUmsatz.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			nettoUmsatz.setBackgroundResource(R.color.White);
			nettoUmsatz.setId(NETTOUMSATZID);
			tr.addView(nettoUmsatz);

			TextView einkaufspreis = new TextView(this);
			einkaufspreis.setText(df.format(i.getEinkaufspreis()) + "€");
			einkaufspreis.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			einkaufspreis.setBackgroundResource(R.color.White);
			tr.addView(einkaufspreis);

			TextView einkaufTotal = new TextView(this);
			einkaufTotal.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			einkaufTotal.setBackgroundResource(R.color.White);
			einkaufTotal.setId(EINKAUFTOTALID);
			tr.addView(einkaufTotal);

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
			lp = (LinearLayout.LayoutParams) nettoPreis.getLayoutParams();
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
			lp = (LinearLayout.LayoutParams) einkaufTotal.getLayoutParams();
			lp.topMargin = -2;
			lp.leftMargin = 2;
			lp.bottomMargin = -2;
			lp.rightMargin = 2;

			bestellungen.addTextChangedListener(new MyTextWatcher(bestellungen,
					fleischList.indexOf(i)));
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
			if (s != null) {
				TableRow tr = (TableRow) view.getParent();
				TextView totalView = (TextView) tr.findViewById(TOTALID);
				TextView nettoUmsatzView = (TextView) tr
						.findViewById(NETTOUMSATZID);
				TextView einkaufTotalView = (TextView) tr
						.findViewById(EINKAUFTOTALID);
				TextView nettoUmsatzssummeView = (TextView) findViewById(R.id.nettoUmsatzSumme);
				TextView einkaufssummeView = (TextView) findViewById(R.id.einkaufssumme);

				FleischModel fleisch = fleischList.get(index);

				double totalBestellung = Integer.valueOf(s.toString())
						* fleisch.einheitProBestellung;
				totalView.setText(String.valueOf(totalBestellung));

				double verkaufsmenge = totalBestellung
						- (totalBestellung * fleisch.getSchwund());
				double nettoUmsatz = verkaufsmenge
						* fleisch.getVerkaufsfaktor() * fleisch.getNettoPreis();
				nettoUmsatzView.setText(df.format(nettoUmsatz) + "€");

				double einkaufTotal = totalBestellung * fleisch.einkaufspreis;
				einkaufTotalView.setText(df.format(einkaufTotal) + "€");

				double nettoUmsatzssumme = 0;
				double einkaufssumme = 0;
				TableLayout tl = (TableLayout) tr.getParent();
				for (int j = 0; j < tl.getChildCount() - 1; j++) {
					TableRow thisTr = (TableRow) tl.getChildAt(j + 1);
					if (((TextView) thisTr.findViewById(NETTOUMSATZID))
							.getText() != "") {
						double thisNettoUmsatz = Double
								.valueOf(((String) ((TextView) thisTr
										.findViewById(NETTOUMSATZID)).getText())
										.replace("€", "").replace(",", "."));
						nettoUmsatzssumme += thisNettoUmsatz;
					}
					if (((TextView) thisTr.findViewById(EINKAUFTOTALID))
							.getText() != "") {
						double thisEinkaufTotal = Double
								.valueOf(((String) ((TextView) thisTr
										.findViewById(EINKAUFTOTALID))
										.getText()).replace("€", "").replace(",", "."));
						einkaufssumme += thisEinkaufTotal;
					}
				}
				nettoUmsatzssummeView.setText(String.valueOf(nettoUmsatzssumme)	+ "€");
				einkaufssummeView.setText(String.valueOf(einkaufssumme) + "€");
			}
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

}
