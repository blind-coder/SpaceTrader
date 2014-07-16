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
import android.widget.CheckBox;

import de.anderdonau.spacetrader.DataTypes.MyFragment;

public class FragmentOptions extends MyFragment {
	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		final View rootView = inflater.inflate(R.layout.fragment_game_options, container, false);
		CheckBox checkBox;

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxIgnorePolice);
		checkBox.setChecked(gameState.AlwaysIgnorePolice);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxIgnorePiraces);
		checkBox.setChecked(gameState.AlwaysIgnorePirates);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxIgnoreTraders);
		checkBox.setChecked(gameState.AlwaysIgnoreTraders);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxIgnoreTradeOffers);
		checkBox.setChecked(gameState.AlwaysIgnoreTradeInOrbit);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxAutoFuel);
		checkBox.setChecked(gameState.AutoFuel);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxAutoRepair);
		checkBox.setChecked(gameState.AutoRepair);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxAlwaysInfo);
		checkBox.setChecked(gameState.AlwaysInfo);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxReserveMoney);
		checkBox.setChecked(gameState.ReserveMoney);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxContinuous);
		checkBox.setChecked(gameState.Continuous);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxAttackFleeing);
		checkBox.setChecked(gameState.AttackFleeing);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxAutoPayNewspaper);
		checkBox.setChecked(gameState.NewsAutoPay);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxDebtReminder);
		checkBox.setChecked(gameState.RemindLoans);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxSaveOnArrival);
		checkBox.setChecked(gameState.SaveOnArrival);

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxBetterGfx);
		checkBox.setChecked(gameState.BetterGfx);

		return rootView;
	}
}
