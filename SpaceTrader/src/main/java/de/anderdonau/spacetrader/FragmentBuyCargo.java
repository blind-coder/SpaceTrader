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
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class FragmentBuyCargo extends MyFragment {
	View rootView;

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		rootView = inflater.inflate(R.layout.fragment_buy_cargo, container, false);
		update();
		return rootView;
	}

	@Override
	public boolean update() {
		CrewMember COMMANDER;
		SolarSystem CURSYSTEM;
		COMMANDER = gameState.Mercenary[0];
		CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		TextView tv;
		Button btn;
		Button btnAll;
		int i;

		for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
			btn = (Button) rootView.findViewById(i == 0 ? R.id.btnBuyCargo1 : i == 1 ? R.id.btnBuyCargo2 :
				i == 2 ? R.id.btnBuyCargo3 : i == 3 ? R.id.btnBuyCargo4 : i == 4 ? R.id.btnBuyCargo5 :
					i == 5 ? R.id.btnBuyCargo6 :
						i == 6 ? R.id.btnBuyCargo7 : i == 7 ? R.id.btnBuyCargo8 : i == 8 ? R.id.btnBuyCargo9 :
						                                  /*i == 9 ? */R.id.btnBuyCargo10);
			btnAll = (Button) rootView.findViewById(i == 0 ? R.id.btnBuyCargoAll1 :
				i == 1 ? R.id.btnBuyCargoAll2 : i == 2 ? R.id.btnBuyCargoAll3 :
					i == 3 ? R.id.btnBuyCargoAll4 : i == 4 ? R.id.btnBuyCargoAll5 :
						i == 5 ? R.id.btnBuyCargoAll6 : i == 6 ? R.id.btnBuyCargoAll7 :
							i == 7 ? R.id.btnBuyCargoAll8 : i == 8 ? R.id.btnBuyCargoAll9 :
							                                /*i == 9 ? */R.id.btnBuyCargoAll10);
			tv = (TextView) rootView.findViewById(i == 0 ? R.id.txtBuyCargoPrice1 :
				i == 1 ? R.id.txtBuyCargoPrice2 : i == 2 ? R.id.txtBuyCargoPrice3 :
					i == 3 ? R.id.txtBuyCargoPrice4 : i == 4 ? R.id.txtBuyCargoPrice5 :
						i == 5 ? R.id.txtBuyCargoPrice6 : i == 6 ? R.id.txtBuyCargoPrice7 :
							i == 7 ? R.id.txtBuyCargoPrice8 : i == 8 ? R.id.txtBuyCargoPrice9 :
						                                   /*i == 9 ? */R.id.txtBuyCargoPrice10);
			if (gameState.BuyPrice[i] > 0) {
				btn.setText(String.format("%d", CURSYSTEM.qty[i]));
				tv.setText(String.format("%d cr.", gameState.BuyPrice[i]));
				tv.setVisibility(View.VISIBLE);
				btn.setVisibility(View.VISIBLE);
				btnAll.setVisibility(View.VISIBLE);
			} else {
				tv.setText("not sold");
				tv.setVisibility(View.VISIBLE);
				btn.setVisibility(View.INVISIBLE);
				btnAll.setVisibility(View.INVISIBLE);
			}
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyCargoBays);
		tv.setText(String.format("Bays: %d/%d", gameState.Ship.FilledCargoBays(),
			gameState.Ship.TotalCargoBays()));
		tv = (TextView) rootView.findViewById(R.id.txtBuyCargoCash);
		tv.setText(String.format("Cash: %d cr.", gameState.Credits));
		return true;
	}
}
