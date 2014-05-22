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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

@SuppressWarnings("ConstantConditions")
public class FragmentVeryRare extends Fragment {
	GameState gameState;

	public FragmentVeryRare(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_rare_encounter_cheats, container,
		                                       false
		);
		CheckBox checkBox;

		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxCheatAhab);
		checkBox
			.setChecked((gameState.VeryRareEncounter & GameState.ALREADYAHAB) == GameState.ALREADYAHAB);
		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxCheatHuie);
		checkBox
			.setChecked((gameState.VeryRareEncounter & GameState.ALREADYHUIE) == GameState.ALREADYHUIE);
		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxCheatConrad);
		checkBox
			.setChecked((gameState.VeryRareEncounter & GameState.ALREADYCONRAD) == GameState.ALREADYCONRAD);
		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxCheatMarieCeleste);
		checkBox
			.setChecked((gameState.VeryRareEncounter & GameState.ALREADYMARIE) == GameState.ALREADYMARIE);
		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxCheatGoodTonic);
		checkBox
			.setChecked((gameState.VeryRareEncounter & GameState.ALREADYBOTTLEGOOD) == GameState.ALREADYBOTTLEGOOD);
		checkBox = (CheckBox) rootView.findViewById(R.id.chkBoxCheatBadTonic);
		checkBox
			.setChecked((gameState.VeryRareEncounter & GameState.ALREADYBOTTLEOLD) == GameState.ALREADYBOTTLEOLD);

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
		}
		);

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
		}
		);
		return rootView;
	}
	public void numVeryRareChanceCallback(View view){
		EditText editText = (EditText) view;
		int amount;
		try {
			amount = Integer.parseInt(editText.getText().toString());
		} catch (Exception e){
			return;
		}
		switch (editText.getId()){
			case R.id.numCheatRareEncounter:
				gameState.ChanceOfVeryRareEncounter = amount;
				break;
			case R.id.numCheatOrbitTrade:
				gameState.ChanceOfTradeInOrbit = amount;
				break;
		}
	}

}
