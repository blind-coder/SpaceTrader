/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class FragmentAveragePrices extends Fragment {
	WelcomeScreen welcomeScreen;
	GameState gameState;

	public FragmentAveragePrices(WelcomeScreen welcomeScreen, GameState gameState) {
		this.welcomeScreen = welcomeScreen;
		this.gameState = gameState;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_average_prices, container, false);
		CrewMember COMMANDER = gameState.Mercenary[0];
		SolarSystem CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		TextView tv, tvprice;
		Button btn;

		if (welcomeScreen.WarpSystem == null) {
			welcomeScreen.WarpSystem = CURSYSTEM;
		}

		tv = (TextView) rootView.findViewById(R.id.txtPriceListSystemName);
		tv.setText(gameState.SolarSystemName[welcomeScreen.WarpSystem.nameIndex]);

		tv = (TextView) rootView.findViewById(R.id.txtPriceListSpecialResources);
		if (welcomeScreen.WarpSystem.visited)
			tv.setText(gameState.SpecialResources[welcomeScreen.WarpSystem.specialResources]);
		else
			tv.setText("Special resources unknown");

		tv = (TextView) rootView.findViewById(R.id.txtPriceListTitle);
		btn = (Button) rootView.findViewById(R.id.btnPriceListDiffAvg);
		if (gameState.PriceDifferences) {
			tv.setText("Price Differences");
			btn.setText("Absolute Prices");
		} else {
			tv.setText("Absolute Prices");
			btn.setText("Price Differences");
		}

		tv = (TextView) rootView.findViewById(R.id.txtPriceListBays);
		tv.setText(String.format("Bays: %d/%d", gameState.FilledCargoBays(),
		                         gameState.TotalCargoBays()
		)
		);

		tv = (TextView) rootView.findViewById(R.id.txtPriceListCash);
		tv.setText(String.format("Cash: %d cr.", gameState.Credits));

		for (int i = 0; i < GameState.MAXTRADEITEM; ++i) {
			btn = (Button) rootView.findViewById(i == 0 ? R.id.btnPriceListBuy1 :
			                                     i == 1 ? R.id.btnPriceListBuy2 :
			                                     i == 2 ? R.id.btnPriceListBuy3 :
			                                     i == 3 ? R.id.btnPriceListBuy4 :
			                                     i == 4 ? R.id.btnPriceListBuy5 :
			                                     i == 5 ? R.id.btnPriceListBuy6 :
			                                     i == 6 ? R.id.btnPriceListBuy7 :
			                                     i == 7 ? R.id.btnPriceListBuy8 :
			                                     i == 8 ? R.id.btnPriceListBuy9 :
					                                    /*i == 9 ?*/ R.id.btnPriceListBuy10
			);
			btn.setVisibility(gameState.BuyPrice[i] <= 0 ? View.INVISIBLE : View.VISIBLE);
			btn.setText(String.format("%d", CURSYSTEM.qty[i]));
			tv = (TextView) rootView.findViewById(i == 0 ? R.id.txtPriceListName1 :
			                                      i == 1 ? R.id.txtPriceListName2 :
			                                      i == 2 ? R.id.txtPriceListName3 :
			                                      i == 3 ? R.id.txtPriceListName4 :
			                                      i == 4 ? R.id.txtPriceListName5 :
			                                      i == 5 ? R.id.txtPriceListName6 :
			                                      i == 6 ? R.id.txtPriceListName7 :
			                                      i == 7 ? R.id.txtPriceListName8 :
			                                      i == 8 ? R.id.txtPriceListName9 :
					                                     /*i == 9 ?*/ R.id.txtPriceListName10
			);
			tvprice = (TextView) rootView.findViewById(i == 0 ? R.id.txtPriceListPrice1 :
			                                           i == 1 ? R.id.txtPriceListPrice2 :
			                                           i == 2 ? R.id.txtPriceListPrice3 :
			                                           i == 3 ? R.id.txtPriceListPrice4 :
			                                           i == 4 ? R.id.txtPriceListPrice5 :
			                                           i == 5 ? R.id.txtPriceListPrice6 :
			                                           i == 6 ? R.id.txtPriceListPrice7 :
			                                           i == 7 ? R.id.txtPriceListPrice8 :
			                                           i == 8 ? R.id.txtPriceListPrice9 :
					                                          /*i == 9 ?*/ R.id.txtPriceListPrice10
			);

			int Price = gameState.StandardPrice(i, welcomeScreen.WarpSystem.size, welcomeScreen.WarpSystem.techLevel,
			                                    welcomeScreen.WarpSystem.politics,
			                                    (welcomeScreen.WarpSystem.visited ? welcomeScreen.WarpSystem.specialResources : -1)
			);

			if (Price > gameState.BuyPrice[i] && gameState.BuyPrice[i] > 0 && CURSYSTEM.qty[i] > 0) {
				tv.setTypeface(null, Typeface.BOLD);
				tvprice.setTypeface(null, Typeface.BOLD);
			} else {
				tv.setTypeface(null, Typeface.NORMAL);
				tvprice.setTypeface(null, Typeface.NORMAL);
			}

			if (Price <= 0 || (gameState.PriceDifferences && gameState.BuyPrice[i] <= 0)) {
				tvprice.setText("---");
			} else {
				if (gameState.PriceDifferences)
					tvprice.setText(String.format("%+d cr.", Price - gameState.BuyPrice[i]));
				else
					tvprice.setText(String.format("%d cr.", Price));
			}
		}
		return rootView;
	}
}
