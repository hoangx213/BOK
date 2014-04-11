package de.hx.bokumsatzkontroller.util;

import java.util.ArrayList;

import android.view.View;
import android.widget.LinearLayout;
import de.hx.bokumsatzkontroller.models.ArtikelBerichtModel;

public class Utils {

	public static final int VIEW_WITH_EDIT_TEXT = 1;
	public static final int VIEW_WITHOUT_EDIT_TEXT = 0;

	public ArtikelBerichtModel getABWithArtikelNameFromList(
			ArrayList<ArtikelBerichtModel> aBList, String artikelName) {
		for (ArtikelBerichtModel thisAB : aBList) {
			if (thisAB.getArtikelName().equals(artikelName))
				return thisAB;
		}
		return null;
	}

	public void setMarginsToViews(int isWithEditText, View... views) {
		if (isWithEditText == VIEW_WITHOUT_EDIT_TEXT) {
			for (int i = 0; i < views.length; i++) {

				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) views[i]
						.getLayoutParams();
				lp.topMargin = 5;
				lp.leftMargin = 3;
				lp.bottomMargin = 5;
				lp.rightMargin = 3;
			}
		} else {
			for (int i = 0; i < views.length; i++) {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) views[i]
						.getLayoutParams();
				lp.topMargin = -8;
				lp.leftMargin = 3;
				lp.bottomMargin = -8;
				lp.rightMargin = 3;
			}
		}
	}
}
