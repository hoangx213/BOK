package de.hx.bokumsatzkontroller;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FleischBestellungenActivity extends Activity implements OnClickListener{

	ArrayList<OneDayFleischBestellungenModel> fleischBestelungenDaysList;
	ListView fleischBestellungenListView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fleisch_bestellungen);
		fleischBestellungenListView = (ListView) findViewById(R.id.fleischBestellungenListView);
		FleischBestellungenXmlParser fbxp = new FleischBestellungenXmlParser(
				this);
		try {
			fleischBestelungenDaysList = fbxp.fleischParsen();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		View footerView =  ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.neue_fleisch_bestellung_button, null, false);
        fleischBestellungenListView.addFooterView(footerView);
        Button addBestBtn = (Button) footerView.findViewById(R.id.neueFBestellungBtn);
        addBestBtn.setOnClickListener(this);
		fleischBestellungenListView
				.setAdapter(new FleischBestelungenListAdapter(this,
						fleischBestelungenDaysList));
		fleischBestellungenListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				OneDayFleischBestellungenModel thisDayFleischBestellungen = fleischBestelungenDaysList.get(position);
				int daysFrom1970 = thisDayFleischBestellungen.getDaysFrom1970();
				Intent intent = new Intent(FleischBestellungenActivity.this, FleischBestellungNachschauActivity.class);
				intent.putExtra("BestellungID", daysFrom1970);
				startActivity(intent);
			}
		});
	}

	class FleischBestelungenListAdapter extends
			ArrayAdapter<OneDayFleischBestellungenModel> {

		ArrayList<OneDayFleischBestellungenModel> adapterFleischBestelungenDaysList;
		Context context;
		
		public FleischBestelungenListAdapter(Context context,
				ArrayList<OneDayFleischBestellungenModel> adapterFleischBestelungenDaysList) {
			super(context,
					R.layout.fleisch_bestellungen_list_item,
					R.id.fleischBestellungenDayText, fleischBestelungenDaysList);
			this.adapterFleischBestelungenDaysList = adapterFleischBestelungenDaysList;
			this.context = context;
		}

		class ViewHolder {
			TextView dayTextView;

			ViewHolder(View base) {
				this.dayTextView = (TextView) base
						.findViewById(R.id.fleischBestellungenDayText);
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
		Intent intent = new Intent(this, FleischActivity.class);
		startActivity(intent);
	}

}
