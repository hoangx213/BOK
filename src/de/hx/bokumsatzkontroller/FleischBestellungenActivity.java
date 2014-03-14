package de.hx.bokumsatzkontroller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import de.hx.bokumsatzkontroller.models.OneDayFleischBestellungenModel;
import de.hx.bokumsatzkontroller.util.DatePickerFragment;
import de.hx.bokumsatzkontroller.xml.FleischBestellungenXmlParser;
import de.hx.bokumsatzkontroller.xml.XmlParserHelper;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class FleischBestellungenActivity extends FragmentActivity implements
		OnClickListener {

	static ArrayList<OneDayFleischBestellungenModel> fleischBestelungenDaysList;
	ListView fleischBestellungenListView;
	Button vonDatumBtn, bisDatumBtn, addBestBtn, einkaufsberichtBtn,
			zwischenberichtBtn, endberichtBtn;
	TextView vonDatumTextView, bisDatumTextView;
	int vonDaysFrom1970, bisDaysFrom1970;
	static XmlParserHelper xmlPH = new XmlParserHelper();
	static FleischBestelungenListAdapter listViewAdapter;
	static int positionOfFBToRemove = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fleisch_bestellungen);
		fleischBestellungenListView = (ListView) findViewById(R.id.fleischBestellungenListView);
		vonDatumBtn = (Button) findViewById(R.id.vonDatumFBBtn);
		bisDatumBtn = (Button) findViewById(R.id.bisDatumFBBtn);
		vonDatumTextView = (TextView) findViewById(R.id.vonDatumFBTextView);
		bisDatumTextView = (TextView) findViewById(R.id.bisDatumFBTextView);
		einkaufsberichtBtn = (Button) findViewById(R.id.einkaufsberichtBtn);
		zwischenberichtBtn = (Button) findViewById(R.id.zwischenberichtBtn);
		FleischBestellungenXmlParser fbxp = new FleischBestellungenXmlParser(
				this);
		try {
			fleischBestelungenDaysList = fbxp.fleischParsen();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		View footerView = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.neue_fleisch_bestellung_button, null, false);
		fleischBestellungenListView.addFooterView(footerView);
		addBestBtn = (Button) footerView.findViewById(R.id.neueFBestellungBtn);
		addBestBtn.setOnClickListener(this);
		vonDatumBtn.setOnClickListener(this);
		bisDatumBtn.setOnClickListener(this);
		einkaufsberichtBtn.setOnClickListener(this);
		zwischenberichtBtn.setOnClickListener(this);
		listViewAdapter = new FleischBestelungenListAdapter(this,
				fleischBestelungenDaysList);
		fleischBestellungenListView.setAdapter(listViewAdapter);
		fleischBestellungenListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						OneDayFleischBestellungenModel thisDayFleischBestellungen = fleischBestelungenDaysList
								.get(position);
						String bestellungID = thisDayFleischBestellungen
								.getBestellungID();
						Intent intent = new Intent(
								FleischBestellungenActivity.this,
								FleischBestellungNachschauActivity.class);
						intent.putExtra("BestellungID", bestellungID);
						startActivity(intent);
					}
				});
		fleischBestellungenListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						positionOfFBToRemove = position;
						new RemoveOneDayFBDialogFragment().show(
								getSupportFragmentManager(), "removeFBDialog");
						return true;
					}
				});
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Fleischbestellungen");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		startActivity(new Intent(this, MainActivity.class));
		return true;
	}

	class FleischBestelungenListAdapter extends
			ArrayAdapter<OneDayFleischBestellungenModel> {

		ArrayList<OneDayFleischBestellungenModel> adapterFleischBestelungenDaysList;
		Context context;

		public FleischBestelungenListAdapter(
				Context context,
				ArrayList<OneDayFleischBestellungenModel> adapterFleischBestelungenDaysList) {
			super(context, R.layout.fleisch_bestellungen_list_item,
					R.id.vonDatumFBTextView, fleischBestelungenDaysList);
			this.adapterFleischBestelungenDaysList = adapterFleischBestelungenDaysList;
			this.context = context;
		}

		class ViewHolder {
			TextView dayTextView;

			ViewHolder(View base) {
				this.dayTextView = (TextView) base
						.findViewById(R.id.vonDatumFBTextView);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = (FleischBestellungenActivity.this
						.getLayoutInflater());
				convertView = inflater.inflate(
						R.layout.fleisch_bestellungen_list_item, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			OneDayFleischBestellungenModel thisDay = fleischBestelungenDaysList
					.get(position);
			holder.dayTextView.setText(thisDay.toString());
			return convertView;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.neueFBestellungBtn: {
			Intent intent = new Intent(this, FleischActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.vonDatumFBBtn: {
			DialogFragment newFragment = new DatePickerFragment(
					vonDatumTextView);
			newFragment.show(getSupportFragmentManager(), "fromDatePicker");
			break;
		}
		case R.id.bisDatumFBBtn: {
			DialogFragment newFragment = new DatePickerFragment(
					bisDatumTextView);
			newFragment.show(getSupportFragmentManager(), "toDatePicker");
			break;
		}
		case R.id.einkaufsberichtBtn: {
			getDaysFrom1970();
			break;
		}
		case R.id.zwischenberichtBtn: {
			if (getDaysFrom1970()) {
				Intent intent = new Intent(this,
						FleischZwischenBerichtActivity.class);
				intent.putExtra("vonDaysFrom1970", vonDaysFrom1970);
				intent.putExtra("bisDaysFrom1970", bisDaysFrom1970);
				startActivity(intent);
			}
			break;
		}
		default:
			break;
		}
	}

	boolean getDaysFrom1970() {
		if (vonDatumTextView.getText().toString().equals("")
				|| bisDatumTextView.getText().toString().equals("")) {
			Toast.makeText(this, "Bitte von und bis Datum auswählen",
					Toast.LENGTH_LONG).show();
			return false;
		} else {
			String von = vonDatumTextView.getText().toString();
			String bis = bisDatumTextView.getText().toString();
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			Date d = null;
			Calendar calendar = Calendar.getInstance();
			try {
				d = format.parse(von);
				calendar.setTime(d);
				vonDaysFrom1970 = (int) (calendar.getTimeInMillis() / (1000 * 3600 * 24));
				d = format.parse(bis);
				calendar.setTime(d);
				bisDaysFrom1970 = (int) (calendar.getTimeInMillis() / (1000 * 3600 * 24));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			return true;
		}
	}

	public static class RemoveOneDayFBDialogFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the Builder class for convenient dialog construction
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("Wollen Sie den Bestellungstag entfernen?")
					.setPositiveButton("Ja",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									OneDayFleischBestellungenModel thisDayFleischBestellung = fleischBestelungenDaysList
											.get(positionOfFBToRemove);
									try {
										xmlPH.removeOneDayFBWithBestellungID(thisDayFleischBestellung
												.getBestellungID());
									} catch (ParserConfigurationException e) {
										e.printStackTrace();
									} catch (SAXException e) {
										e.printStackTrace();
									} catch (IOException e) {
										e.printStackTrace();
									} catch (TransformerException e) {
										e.printStackTrace();
									}
									fleischBestelungenDaysList
											.remove(positionOfFBToRemove);
									listViewAdapter.notifyDataSetChanged();
								}
							})
					.setNegativeButton("Nein",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// User cancelled the dialog
								}
							});
			// Create the AlertDialog object and return it
			return builder.create();
		}
	}
}
