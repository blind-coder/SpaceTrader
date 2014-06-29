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
import android.widget.TableLayout;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;

@SuppressWarnings("ConstantConditions")
public class FragmentPersonnelRoster extends MyFragment {
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		int i, j;
		TableLayout tl;
		TextView tv;
		Button btn;
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		rootView = inflater.inflate(R.layout.fragment_personnel_roster, container, false);

		for (i = 0; i < 2; ++i) {
			if ((gameState.JarekStatus == 1 || gameState.WildStatus == 1) && i < 2) {
				if (gameState.JarekStatus == 1 && i == 0) { /* Jarek is always in 1st crew slot */
					tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew1);
					tv = (TextView) rootView.findViewById(R.id.txtNameCrew1);
					btn = (Button) rootView.findViewById(R.id.btnFireCrew1);
					tl.setVisibility(View.INVISIBLE);
					tv.setText("Jarek's quarters");
					btn.setVisibility(View.INVISIBLE);
					continue;
				} else if (gameState.JarekStatus == 1 && gameState.WildStatus == 1 && i == 1) { /* Wild is in 2nd crew slot if Jarek is here, too */
					tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew2);
					tv = (TextView) rootView.findViewById(R.id.txtNameCrew2);
					btn = (Button) rootView.findViewById(R.id.btnFireCrew2);
					tl.setVisibility(View.INVISIBLE);
					tv.setText("Wild's quarters");
					btn.setVisibility(View.INVISIBLE);
					continue;
				} else if (gameState.WildStatus == 1 && i == 0) {/* Wild is in 1st crew slot if Jarek is not here */
					tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew1);
					tv = (TextView) rootView.findViewById(R.id.txtNameCrew1);
					btn = (Button) rootView.findViewById(R.id.btnFireCrew1);
					tl.setVisibility(View.INVISIBLE);
					tv.setText("Wild's quarters");
					btn.setVisibility(View.INVISIBLE);
					continue;
				}
			}

			if (i == 0) {
				tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew1);
				tv = (TextView) rootView.findViewById(R.id.txtNameCrew1);
				btn = (Button) rootView.findViewById(R.id.btnFireCrew1);
			} else {
				tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew2);
				tv = (TextView) rootView.findViewById(R.id.txtNameCrew2);
				btn = (Button) rootView.findViewById(R.id.btnFireCrew2);
			}
			ShipTypes.ShipType Ship = ShipTypes.ShipTypes[gameState.Ship.type];
			if (Ship.crewQuarters <= i + 1) {
				tl.setVisibility(View.INVISIBLE);
				btn.setVisibility(View.INVISIBLE);
				tv.setText("No quarters available");
				continue;
			}

			j = i;
			if (gameState.WildStatus == 1) {
				j--;
			}
			if (gameState.JarekStatus == 1) {
				j--;
			}
			if (gameState.Ship.crew[j + 1] < 0) {
				tl.setVisibility(View.INVISIBLE);
				btn.setVisibility(View.INVISIBLE);
				tv.setText("Vacancy");
				continue;
			}

			tl.setVisibility(View.VISIBLE);
			btn.setVisibility(View.VISIBLE);
			DrawMercenary(i, gameState.Ship.crew[j + 1]); /* Crew idx 0 is the player */
		}

		int ForHire = gameState.GetForHire();
		tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrewNew);
		tv = (TextView) rootView.findViewById(R.id.txtNameCrewNew);
		btn = (Button) rootView.findViewById(R.id.btnHireCrewNew);
		if (ForHire < 0) {
			tl.setVisibility(View.INVISIBLE);
			tv.setText("No one for hire");
			btn.setVisibility(View.INVISIBLE);
		} else {
			tl.setVisibility(View.VISIBLE);
			btn.setVisibility(View.VISIBLE);
			DrawMercenary(2, ForHire);
		}

		return rootView;
	}

	public void DrawMercenary(int i, int idxCrewMember) {
		TextView txtPilot;
		TextView txtEngineer;
		TextView txtTrader;
		TextView txtFighter;
		TextView txtName;

		CrewMember c = gameState.Mercenary[idxCrewMember];

		if (i == 0) {
			txtPilot = (TextView) rootView.findViewById(R.id.txtPilotCrew1);
			txtEngineer = (TextView) rootView.findViewById(R.id.txtEngineerCrew1);
			txtTrader = (TextView) rootView.findViewById(R.id.txtTraderCrew1);
			txtFighter = (TextView) rootView.findViewById(R.id.txtFighterCrew1);
			txtName = (TextView) rootView.findViewById(R.id.txtNameCrew1);
		} else if (i == 1) {
			txtPilot = (TextView) rootView.findViewById(R.id.txtPilotCrew2);
			txtEngineer = (TextView) rootView.findViewById(R.id.txtEngineerCrew2);
			txtTrader = (TextView) rootView.findViewById(R.id.txtTraderCrew2);
			txtFighter = (TextView) rootView.findViewById(R.id.txtFighterCrew2);
			txtName = (TextView) rootView.findViewById(R.id.txtNameCrew2);
		} else /* if (i == 2) */ {
			txtPilot = (TextView) rootView.findViewById(R.id.txtPilotCrewNew);
			txtEngineer = (TextView) rootView.findViewById(R.id.txtEngineerCrewNew);
			txtTrader = (TextView) rootView.findViewById(R.id.txtTraderCrewNew);
			txtFighter = (TextView) rootView.findViewById(R.id.txtFighterCrewNew);
			txtName = (TextView) rootView.findViewById(R.id.txtNameCrewNew);
		}
		txtPilot.setText(String.format("%d", c.pilot));
		txtFighter.setText(String.format("%d", c.fighter));
		txtEngineer.setText(String.format("%d", c.engineer));
		txtTrader.setText(String.format("%d", c.trader));
		txtName.setText(main.MercenaryName[c.nameIndex]);
	}
}
