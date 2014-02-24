package de.hx.bokumsatzkontroller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button fleischBtn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		// isExternalStorageWritable();
		copyFileToExternalStorage("fleisch.xml");
		copyFileToExternalStorage("fleisch_bestellungen.xml");
		fleischBtn = (Button) findViewById(R.id.fleischBtn);
		fleischBtn.setOnClickListener(this);
	}

	public void copyFileToExternalStorage(String filename) {
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/BOK");
		if (!folder.exists())
			folder.mkdir();
		File file = new File(
				Environment.getExternalStorageDirectory() + "/BOK", filename);
		if (!file.exists()) {
			AssetManager assetManager = getResources().getAssets();
			InputStream is = null;
			try {
				is = assetManager.open(filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
			OutputStream os = null;
			try {
				os = new FileOutputStream(file, true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			final int buffer_size = 1024 * 1024;
			try {
				byte[] bytes = new byte[buffer_size];
				for (;;) {
					int count = is.read(bytes, 0, buffer_size);
					if (count == -1)
						break;
					os.write(bytes, 0, count);
				}
				is.close();
				os.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fleischBtn:
			Intent intent = new Intent(MainActivity.this, FleischActivity.class);
			startActivity(intent);
		}

	}
}
