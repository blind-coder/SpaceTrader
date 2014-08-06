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

public class FragmentDumpCargo extends MyFragment {
	View rootView;

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		rootView = inflater.inflate(R.layout.fragment_dump_cargo, container, false);
		update();
		return rootView;
	}

	@Override
	public boolean update() {
		TextView tv;
		Button btn;
		int i;

		for (i = 0; i < GameState.MAXTRADEITEM; i++) {
			btn = (Button) rootView.findViewById(i == 0 ? R.id.btnDumpCargo1 :
				i == 1 ? R.id.btnDumpCargo2 : i == 2 ? R.id.btnDumpCargo3 : i == 3 ? R.id.btnDumpCargo4 :
					i == 4 ? R.id.btnDumpCargo5 : i == 5 ? R.id.btnDumpCargo6 : i == 6 ? R.id.btnDumpCargo7 :
						i == 7 ? R.id.btnDumpCargo8 : i == 8 ? R.id.btnDumpCargo9 :
						                         /*i == 9 ?*/ R.id.btnDumpCargo10);
			btn.setText(String.format("%2d", gameState.Ship.cargo[i]));
		}

		tv = (TextView) rootView.findViewById(R.id.txtDumpCargoBays);
		tv.setText(String.format("%d/%d", gameState.Ship.FilledCargoBays(),
			gameState.Ship.TotalCargoBays()));

		return true;
	}
}
