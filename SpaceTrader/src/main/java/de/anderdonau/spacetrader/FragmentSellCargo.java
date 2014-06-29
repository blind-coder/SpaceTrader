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

import de.anderdonau.spacetrader.DataTypes.MyFragment;

public class FragmentSellCargo extends MyFragment {
	View rootView;

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		rootView = inflater.inflate(R.layout.fragment_sell_cargo, container, false);
		update();
		return rootView;
	}

	@Override
	public boolean update() {
		TextView tv;
		TextView name;
		Button btn;
		Button btnAll;
		int i;

		for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
			btn = (Button) rootView.findViewById(i == 0 ? R.id.btnSellCargo1 :
				i == 1 ? R.id.btnSellCargo2 : i == 2 ? R.id.btnSellCargo3 : i == 3 ? R.id.btnSellCargo4 :
					i == 4 ? R.id.btnSellCargo5 : i == 5 ? R.id.btnSellCargo6 : i == 6 ? R.id.btnSellCargo7 :
						i == 7 ? R.id.btnSellCargo8 : i == 8 ? R.id.btnSellCargo9 :
						                                  /*i == 9 ? */R.id.btnSellCargo10);
			btnAll = (Button) rootView.findViewById(i == 0 ? R.id.btnSellCargoAll1 :
				i == 1 ? R.id.btnSellCargoAll2 : i == 2 ? R.id.btnSellCargoAll3 :
					i == 3 ? R.id.btnSellCargoAll4 : i == 4 ? R.id.btnSellCargoAll5 :
						i == 5 ? R.id.btnSellCargoAll6 : i == 6 ? R.id.btnSellCargoAll7 :
							i == 7 ? R.id.btnSellCargoAll8 : i == 8 ? R.id.btnSellCargoAll9 :
							                                /*i == 9 ? */R.id.btnSellCargoAll10);
			tv = (TextView) rootView.findViewById(i == 0 ? R.id.txtSellCargoPrice1 :
				i == 1 ? R.id.txtSellCargoPrice2 : i == 2 ? R.id.txtSellCargoPrice3 :
					i == 3 ? R.id.txtSellCargoPrice4 : i == 4 ? R.id.txtSellCargoPrice5 :
						i == 5 ? R.id.txtSellCargoPrice6 : i == 6 ? R.id.txtSellCargoPrice7 :
							i == 7 ? R.id.txtSellCargoPrice8 : i == 8 ? R.id.txtSellCargoPrice9 :
							                                 /*i == 9 ? */R.id.txtSellCargoPrice10);
			name = (TextView) rootView.findViewById(i == 0 ? R.id.txtSellName1 :
				i == 1 ? R.id.txtSellName2 : i == 2 ? R.id.txtSellName3 : i == 3 ? R.id.txtSellName4 :
					i == 4 ? R.id.txtSellName5 : i == 5 ? R.id.txtSellName6 :
						i == 6 ? R.id.txtSellName7 : i == 7 ? R.id.txtSellName8 : i == 8 ? R.id.txtSellName9 :
		                                           /*i == 9 ? */R.id.txtSellName10);
			if (gameState.BuyingPrice[i] < gameState.SellPrice[i] * gameState.Ship.cargo[i]) {
				name.setTypeface(null, Typeface.BOLD);
			} else {
				name.setTypeface(null, Typeface.NORMAL);
			}
			if (gameState.SellPrice[i] > 0) {
				btn.setText(String.format("%d", gameState.Ship.cargo[i]));
				tv.setText(String.format("%d cr.", gameState.SellPrice[i]));
				tv.setVisibility(View.VISIBLE);
				btn.setVisibility(View.VISIBLE);
				btnAll.setVisibility(View.VISIBLE);
			} else {
				tv.setText("not trade");
				tv.setVisibility(View.VISIBLE);
				btn.setVisibility(View.INVISIBLE);
				btnAll.setVisibility(View.INVISIBLE);
			}
		}
		tv = (TextView) rootView.findViewById(R.id.txtSellCargoBays);
		tv.setText(String.format("Bays: %d/%d", gameState.Ship.FilledCargoBays(),
			gameState.Ship.TotalCargoBays()));
		tv = (TextView) rootView.findViewById(R.id.txtSellCargoCash);
		tv.setText(String.format("Cash: %d cr.", gameState.Credits));
		return true;
	}
}
