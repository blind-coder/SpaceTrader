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

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.Ship;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class FragmentShipyard extends MyFragment {
	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		final View rootView = inflater.inflate(R.layout.fragment_shipyard, container, false);
		CrewMember COMMANDER = gameState.Mercenary[0];
		SolarSystem CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		Ship Ship = gameState.Ship;
		TextView tv;
		Button btn;

		if (Ship.GetFuel() < Ship.GetFuelTanks()) {
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

		if (Ship.hull < Ship.GetHullStrength()) {
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
		if (CURSYSTEM.techLevel >= ShipTypes.ShipTypes[0].minTechLevel) {
			btn.setText("Buy New Ship");
		} else {
			btn.setText("Ship Information");
		}

		btn = (Button) rootView.findViewById(R.id.btnShipyardBuyEscapePod);
		if (gameState.EscapePod || gameState
			.ToSpend() < 2000 || CURSYSTEM.techLevel < ShipTypes.ShipTypes[0].minTechLevel) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}

		tv = (TextView) rootView.findViewById(R.id.txtShipyardFuelReserve);
		tv.setText(String.format("You have fuel to fly %d parsec%s.", Ship.GetFuel(),
			Ship.GetFuel() == 1 ? "" : "s"));

		tv = (TextView) rootView.findViewById(R.id.txtShipyardFuelCost);
		if (Ship.GetFuel() < Ship.GetFuelTanks()) {
			tv.setText(String.format("A full tank costs %d cr.",
				(Ship.GetFuelTanks() - Ship.GetFuel()) * ShipTypes.ShipTypes[Ship.type].costOfFuel));
		} else {
			tv.setText("Your tank cannot hold more fuel.");
		}

		tv = (TextView) rootView.findViewById(R.id.txtShipyardHullStrength);
		tv.setText(String.format("Your hull strength is at %d%%.",
			(Ship.hull * 100) / Ship.GetHullStrength()));

		tv = (TextView) rootView.findViewById(R.id.txtShipyardRepairsNeeded);
		if (Ship.hull < Ship.GetHullStrength()) {
			tv.setText(String.format("Full repair will cost %d cr.",
				(Ship.GetHullStrength() - Ship.hull) * ShipTypes.ShipTypes[Ship.type].repairCosts));
		} else {
			tv.setText("No repairs are needed.");
		}

		tv = (TextView) rootView.findViewById(R.id.txtShipyardNewShipsForSale);
		if (CURSYSTEM.techLevel >= ShipTypes.ShipTypes[0].minTechLevel) {
			tv.setText("There are new ships for sale.");
		} else {
			tv.setText("No new ships are for sale.");
		}

		tv = (TextView) rootView.findViewById(R.id.txtShipyardCash);
		tv.setText(String.format("Cash: %d cr.", gameState.Credits));

		tv = (TextView) rootView.findViewById(R.id.txtShipyardBuyEscapePod);
		if (gameState.EscapePod) {
			tv.setText("You have an escape pod installed.");
		} else if (CURSYSTEM.techLevel < ShipTypes.ShipTypes[0].minTechLevel) {
			tv.setText("No escape pods are for sale.");
		} else if (gameState.ToSpend() < 2000) {
			tv.setText("You need 2000 cr. for an escape pod.");
		} else {
			tv.setText("You can buy an escape pod for 2000 cr.");
		}

		return rootView;
	}
}

