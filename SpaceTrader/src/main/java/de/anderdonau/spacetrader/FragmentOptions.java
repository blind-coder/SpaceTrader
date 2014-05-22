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
import android.widget.CheckBox;

public class FragmentOptions extends Fragment {
	GameState gameState;

	public FragmentOptions(GameState gameState) {
		this.gameState = gameState;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

		return rootView;
	}
}
