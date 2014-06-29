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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.Popup;

public class FragmentBuyNewShip extends MyFragment {
	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		final View rootView = inflater.inflate(R.layout.fragment_buy_new_ship, container, false);
		TextView tv;
		Button btn;
		int i;

		gameState.DetermineShipPrices();

		tv = (TextView) rootView.findViewById(R.id.txtBuyShipCash);
		tv.setText(String.format("Cash: %d cr.", gameState.Credits));

		i = -1;
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceFlea);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
			gameState.Ship.type == i ? "got one" : String.format("%d cr.", gameState.ShipPrice[i]));
		btn = (Button) rootView.findViewById(R.id.btnBuyFlea);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceGnat);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
			gameState.Ship.type == i ? "got one" : String.format("%d cr.", gameState.ShipPrice[i]));
		btn = (Button) rootView.findViewById(R.id.btnBuyGnat);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceFirefly);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
			gameState.Ship.type == i ? "got one" : String.format("%d cr.", gameState.ShipPrice[i]));
		btn = (Button) rootView.findViewById(R.id.btnBuyFirefly);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceMosquito);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
			gameState.Ship.type == i ? "got one" : String.format("%d cr.", gameState.ShipPrice[i]));
		btn = (Button) rootView.findViewById(R.id.btnBuyMosquito);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceBumblebee);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
			gameState.Ship.type == i ? "got one" : String.format("%d cr.", gameState.ShipPrice[i]));
		btn = (Button) rootView.findViewById(R.id.btnBuyBumblebee);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceBeetle);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
			gameState.Ship.type == i ? "got one" : String.format("%d cr.", gameState.ShipPrice[i]));
		btn = (Button) rootView.findViewById(R.id.btnBuyBeetle);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceHornet);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
			gameState.Ship.type == i ? "got one" : String.format("%d cr.", gameState.ShipPrice[i]));
		btn = (Button) rootView.findViewById(R.id.btnBuyHornet);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceGrasshopper);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
			gameState.Ship.type == i ? "got one" : String.format("%d cr.", gameState.ShipPrice[i]));
		btn = (Button) rootView.findViewById(R.id.btnBuyGrasshopper);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceTermite);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
			gameState.Ship.type == i ? "got one" : String.format("%d cr.", gameState.ShipPrice[i]));
		btn = (Button) rootView.findViewById(R.id.btnBuyTermite);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceWasp);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
			gameState.Ship.type == i ? "got one" : String.format("%d cr.", gameState.ShipPrice[i]));
		btn = (Button) rootView.findViewById(R.id.btnBuyWasp);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}

		if (gameState.Ship.tribbles > 0 && !gameState.TribbleMessage) {
			Popup popup;
			popup = new Popup(main, "You've Got Tribbles",
				"Hm. I see you got a tribble infestation on your current ship. I'm sorry, but that severely reduces the trade-in price.",
				"Normally you would receive about 75% of the worth of a new ship as trade-in value, but a tribble infested ship will give you only 25%. It is a way to get rid of your tribbles, though.",
				"OK", main.cbShowNextPopup);
			main.addPopup(popup);
			gameState.TribbleMessage = true;
		}

		return rootView;
	}
}

