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

public class FragmentPlunderCargo extends MyFragment {
	public View rootView;

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		rootView = inflater.inflate(R.layout.fragment_plunder_cargo, container, false);
		update();
		return rootView;
	}

	@Override
	public boolean update() {
		TextView tv;
		Button btn;
		int i;

		for (i = 0; i < GameState.MAXTRADEITEM; i++) {
			btn = (Button) rootView.findViewById(i == 0 ? R.id.btnPlunderCargo1 :
				i == 1 ? R.id.btnPlunderCargo2 : i == 2 ? R.id.btnPlunderCargo3 :
					i == 3 ? R.id.btnPlunderCargo4 : i == 4 ? R.id.btnPlunderCargo5 :
						i == 5 ? R.id.btnPlunderCargo6 : i == 6 ? R.id.btnPlunderCargo7 :
							i == 7 ? R.id.btnPlunderCargo8 : i == 8 ? R.id.btnPlunderCargo9 :
							                       /*i == 9 ?*/ R.id.btnPlunderCargo10);
			btn.setText(String.format("%2d", gameState.Opponent.cargo[i]));
		}

		tv = (TextView) rootView.findViewById(R.id.txtPlunderCargoBays);
		tv.setText(String.format("%d/%d", gameState.Ship.FilledCargoBays(),
			gameState.Ship.TotalCargoBays()));

		return true;
	}
}
