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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import de.anderdonau.spacetrader.DataTypes.MyFragment;

@SuppressWarnings("ConstantConditions")
public class FragmentVeryRare extends MyFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		final View rootView = inflater.inflate(R.layout.fragment_rare_encounter_cheats, container,
			false);
		CheckBox checkBox;

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxCheatAhab);
		checkBox.setChecked(
			(gameState.VeryRareEncounter & GameState.ALREADYAHAB) == GameState.ALREADYAHAB);
		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxCheatHuie);
		checkBox.setChecked(
			(gameState.VeryRareEncounter & GameState.ALREADYHUIE) == GameState.ALREADYHUIE);
		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxCheatConrad);
		checkBox.setChecked(
			(gameState.VeryRareEncounter & GameState.ALREADYCONRAD) == GameState.ALREADYCONRAD);
		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxCheatMarieCeleste);
		checkBox.setChecked(
			(gameState.VeryRareEncounter & GameState.ALREADYMARIE) == GameState.ALREADYMARIE);
		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxCheatGoodTonic);
		checkBox.setChecked(
			(gameState.VeryRareEncounter & GameState.ALREADYBOTTLEGOOD) == GameState.ALREADYBOTTLEGOOD);
		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxCheatBadTonic);
		checkBox.setChecked(
			(gameState.VeryRareEncounter & GameState.ALREADYBOTTLEOLD) == GameState.ALREADYBOTTLEOLD);

		final EditText editText;
		editText = (EditText) rootView.findViewById(R.id.numCheatRareEncounter);
		editText.setText(String.valueOf(gameState.ChanceOfVeryRareEncounter));
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				numVeryRareChanceCallback(editText);
			}
		});

		final EditText editText1 = (EditText) rootView.findViewById(R.id.numCheatOrbitTrade);
		editText1.setText(String.valueOf(gameState.ChanceOfTradeInOrbit));
		editText1.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				numVeryRareChanceCallback(editText1);
			}
		});
		return rootView;
	}

	public void numVeryRareChanceCallback(View view) {
		EditText editText = (EditText) view;
		int amount;
		try {
			amount = Integer.parseInt(editText.getText().toString());
		} catch (Exception e) {
			return;
		}
		switch (editText.getId()) {
			case R.id.numCheatRareEncounter:
				gameState.ChanceOfVeryRareEncounter = amount;
				break;
			case R.id.numCheatOrbitTrade:
				gameState.ChanceOfTradeInOrbit = amount;
				break;
		}
	}

}
