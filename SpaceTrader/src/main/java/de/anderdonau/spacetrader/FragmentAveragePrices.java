/*
 * Copyright (c) 2014 Benjamin Schieder
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.anderdonau.spacetrader;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class FragmentAveragePrices extends MyFragment {
	View rootView;

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		rootView = inflater.inflate(R.layout.fragment_average_prices, container, false);
		update();
		return rootView;
	}

	@Override
	public boolean update() {
		CrewMember COMMANDER = gameState.Mercenary[0];
		SolarSystem CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		TextView tv, tvprice;
		Button btn;

		if (main.WarpSystem == null) {
			main.WarpSystem = CURSYSTEM;
		}

		tv = (TextView) rootView.findViewById(R.id.txtPriceListSystemName);
		tv.setText(main.SolarSystemName[main.WarpSystem.nameIndex]);

		tv = (TextView) rootView.findViewById(R.id.txtPriceListSpecialResources);
		if (main.WarpSystem.visited) {
			tv.setText(main.SpecialResources[main.WarpSystem.specialResources]);
		} else {
			tv.setText("Special resources unknown");
		}

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
		tv.setText(String.format("Bays: %d/%d", gameState.Ship.FilledCargoBays(),
			gameState.Ship.TotalCargoBays()));

		tv = (TextView) rootView.findViewById(R.id.txtPriceListCash);
		tv.setText(String.format("Cash: %d cr.", gameState.Credits));

		for (int i = 0; i < GameState.MAXTRADEITEM; ++i) {
			btn = (Button) rootView.findViewById(i == 0 ? R.id.btnPriceListBuy1 :
				i == 1 ? R.id.btnPriceListBuy2 : i == 2 ? R.id.btnPriceListBuy3 :
					i == 3 ? R.id.btnPriceListBuy4 : i == 4 ? R.id.btnPriceListBuy5 :
						i == 5 ? R.id.btnPriceListBuy6 : i == 6 ? R.id.btnPriceListBuy7 :
							i == 7 ? R.id.btnPriceListBuy8 : i == 8 ? R.id.btnPriceListBuy9 :
							                                /*i == 9 ?*/ R.id.btnPriceListBuy10);
			btn.setVisibility(gameState.BuyPrice[i] <= 0 ? View.INVISIBLE : View.VISIBLE);
			btn.setText(String.format("%d", CURSYSTEM.qty[i]));
			tv = (TextView) rootView.findViewById(i == 0 ? R.id.txtPriceListName1 :
				i == 1 ? R.id.txtPriceListName2 : i == 2 ? R.id.txtPriceListName3 :
					i == 3 ? R.id.txtPriceListName4 : i == 4 ? R.id.txtPriceListName5 :
						i == 5 ? R.id.txtPriceListName6 : i == 6 ? R.id.txtPriceListName7 :
							i == 7 ? R.id.txtPriceListName8 : i == 8 ? R.id.txtPriceListName9 :
							                                 /*i == 9 ?*/ R.id.txtPriceListName10);
			tvprice = (TextView) rootView.findViewById(i == 0 ? R.id.txtPriceListPrice1 :
				i == 1 ? R.id.txtPriceListPrice2 : i == 2 ? R.id.txtPriceListPrice3 :
					i == 3 ? R.id.txtPriceListPrice4 : i == 4 ? R.id.txtPriceListPrice5 :
						i == 5 ? R.id.txtPriceListPrice6 : i == 6 ? R.id.txtPriceListPrice7 :
							i == 7 ? R.id.txtPriceListPrice8 : i == 8 ? R.id.txtPriceListPrice9 :
							                                      /*i == 9 ?*/ R.id.txtPriceListPrice10);

			int Price = gameState.StandardPrice(i, main.WarpSystem.size, main.WarpSystem.techLevel,
				main.WarpSystem.politics,
				(main.WarpSystem.visited ? main.WarpSystem.specialResources : -1));

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
				if (gameState.PriceDifferences) {
					tvprice.setText(String.format("%+d cr.", Price - gameState.BuyPrice[i]));
				} else {
					tvprice.setText(String.format("%d cr.", Price));
				}
			}
		}
		return true;
	}
}
