/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.Popup;

public class FragmentBuyNewShip extends Fragment {
	GameState gameState;
	WelcomeScreen welcomeScreen;
	public FragmentBuyNewShip(WelcomeScreen welcomeScreen, GameState gameState) {
		this.welcomeScreen = welcomeScreen;
		this.gameState = gameState;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
		           gameState.Ship.type == i ? "got one" :
		           String.format("%d cr.",gameState.ShipPrice[i])
		);
		btn = (Button) rootView.findViewById(R.id.btnBuyFlea);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceGnat);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
		           gameState.Ship.type == i ? "got one" :
		           String.format("%d cr.", gameState.ShipPrice[i])
		);
		btn = (Button) rootView.findViewById(R.id.btnBuyGnat);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceFirefly);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
		           gameState.Ship.type == i ? "got one" :
		           String.format("%d cr.", gameState.ShipPrice[i])
		);
		btn = (Button) rootView.findViewById(R.id.btnBuyFirefly);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceMosquito);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
		           gameState.Ship.type == i ? "got one" :
		           String.format("%d cr.", gameState.ShipPrice[i])
		);
		btn = (Button) rootView.findViewById(R.id.btnBuyMosquito);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceBumblebee);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
		           gameState.Ship.type == i ? "got one" :
		           String.format("%d cr.", gameState.ShipPrice[i])
		);
		btn = (Button) rootView.findViewById(R.id.btnBuyBumblebee);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceBeetle);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
		           gameState.Ship.type == i ? "got one" :
		           String.format("%d cr.", gameState.ShipPrice[i])
		);
		btn = (Button) rootView.findViewById(R.id.btnBuyBeetle);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceHornet);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
		           gameState.Ship.type == i ? "got one" :
		           String.format("%d cr.", gameState.ShipPrice[i])
		);
		btn = (Button) rootView.findViewById(R.id.btnBuyHornet);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceGrasshopper);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
		           gameState.Ship.type == i ? "got one" :
		           String.format("%d cr.", gameState.ShipPrice[i])
		);
		btn = (Button) rootView.findViewById(R.id.btnBuyGrasshopper);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceTermite);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
		           gameState.Ship.type == i ? "got one" :
		           String.format("%d cr.", gameState.ShipPrice[i])
		);
		btn = (Button) rootView.findViewById(R.id.btnBuyTermite);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNewShipPriceWasp);
		tv.setText(gameState.ShipPrice[++i] == 0 ? "not sold" :
		           gameState.Ship.type == i ? "got one" :
		           String.format("%d cr.", gameState.ShipPrice[i])
		);
		btn = (Button) rootView.findViewById(R.id.btnBuyWasp);
		if (gameState.ShipPrice[i] == 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}

		if (gameState.Ship.tribbles > 0 && !gameState.TribbleMessage) {
			Popup popup;
			popup = new Popup(welcomeScreen, "You've Got Tribbles",
			                  "Hm. I see you got a tribble infestation on your current ship. I'm sorry, but that severely reduces the trade-in price.",
			                  "Normally you would receive about 75% of the worth of a new ship as trade-in value, but a tribble infested ship will give you only 25%. It is a way to get rid of your tribbles, though.",
			                  "OK", WelcomeScreen.cbShowNextPopup
			);
			welcomeScreen.addPopup(popup);
			gameState.TribbleMessage = true;
		}

		return rootView;
	}
}

