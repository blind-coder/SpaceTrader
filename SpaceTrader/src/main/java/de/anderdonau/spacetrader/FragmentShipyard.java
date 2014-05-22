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

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.Ship;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class FragmentShipyard extends Fragment {
	GameState gameState;

	public FragmentShipyard(GameState gameState) {
		this.gameState = gameState;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_shipyard, container, false);
		CrewMember COMMANDER = gameState.Mercenary[0];
		SolarSystem CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		Ship Ship = gameState.Ship;
		TextView tv;
		Button btn;

		if (gameState.GetFuel() < gameState.GetFuelTanks()) {
			btn = (Button) rootView.findViewById(R.id.btnShipyardBuyFuel);
			btn.setVisibility(View.VISIBLE);
			btn = (Button) rootView.findViewById(R.id.btnShipyardBuyMaxFuel);
			btn.setVisibility(View.VISIBLE);
		} else {
			btn = (Button) rootView.findViewById(R.id.btnShipyardBuyFuel);
			btn.setVisibility(View.INVISIBLE);
			btn = (Button) rootView.findViewById(R.id.btnShipyardBuyMaxFuel);
			btn.setVisibility(View.INVISIBLE);
		}

		if (Ship.hull < gameState.GetHullStrength()) {
			btn = (Button) rootView.findViewById(R.id.btnShipyardBuyRepairs);
			btn.setVisibility(View.VISIBLE);
			btn = (Button) rootView.findViewById(R.id.btnShipyardBuyFullRepairs);
			btn.setVisibility(View.VISIBLE);
		} else {
			btn = (Button) rootView.findViewById(R.id.btnShipyardBuyRepairs);
			btn.setVisibility(View.INVISIBLE);
			btn = (Button) rootView.findViewById(R.id.btnShipyardBuyFullRepairs);
			btn.setVisibility(View.INVISIBLE);
		}

		btn = (Button) rootView.findViewById(R.id.btnShipyardBuyNewShip);
		if (CURSYSTEM.techLevel >= gameState.ShipTypes.ShipTypes[0].minTechLevel) {
			btn.setText("Buy New Ship");
		} else {
			btn.setText("Ship Information");
		}

		btn = (Button) rootView.findViewById(R.id.btnShipyardBuyEscapePod);
		if (gameState.EscapePod || gameState
			.ToSpend() < 2000 || CURSYSTEM.techLevel < gameState.ShipTypes.ShipTypes[0].minTechLevel) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}

		tv = (TextView) rootView.findViewById(R.id.txtShipyardFuelReserve);
		tv.setText(String.format("You have fuel to fly %d parsec%s.", gameState.GetFuel(),
		                         gameState.GetFuel() == 1 ? "" : "s"
		)
		);

		tv = (TextView) rootView.findViewById(R.id.txtShipyardFuelCost);
		if (gameState.GetFuel() < gameState.GetFuelTanks()) {
			tv.setText(String.format("A full tank costs %d cr.", (gameState.GetFuelTanks() - gameState
				.GetFuel()) * gameState.ShipTypes.ShipTypes[Ship.type].costOfFuel
			)
			);
		} else {
			tv.setText("Your tank cannot hold more fuel.");
		}

		tv = (TextView) rootView.findViewById(R.id.txtShipyardHullStrength);
		tv.setText(String.format("Your hull strength is at %d%%.",
		                         (Ship.hull * 100) / gameState.GetHullStrength()
		)
		);

		tv = (TextView) rootView.findViewById(R.id.txtShipyardRepairsNeeded);
		if (Ship.hull < gameState.GetHullStrength()) {
			tv.setText(String.format("Full repair will cost %d cr.", (gameState
				.GetHullStrength() - Ship.hull) * gameState.ShipTypes.ShipTypes[Ship.type].repairCosts
			)
			);
		} else {
			tv.setText("No repairs are needed.");
		}

		tv = (TextView) rootView.findViewById(R.id.txtShipyardNewShipsForSale);
		if (CURSYSTEM.techLevel >= gameState.ShipTypes.ShipTypes[0].minTechLevel) {
			tv.setText("There are new ships for sale.");
		} else {
			tv.setText("No new ships are for sale.");
		}

		tv = (TextView) rootView.findViewById(R.id.txtShipyardCash);
		tv.setText(String.format("Cash: %d cr.", gameState.Credits));

		tv = (TextView) rootView.findViewById(R.id.txtShipyardBuyEscapePod);
		if (gameState.EscapePod) {
			tv.setText("You have an escape pod installed.");
		} else if (CURSYSTEM.techLevel < gameState.ShipTypes.ShipTypes[0].minTechLevel) {
			tv.setText("No escape pods are for sale.");
		} else if (gameState.ToSpend() < 2000) {
			tv.setText("You need 2000 cr. for an escape pod.");
		} else {
			tv.setText("You can buy an escape pod for 2000 cr.");
		}

		return rootView;
	}
}

