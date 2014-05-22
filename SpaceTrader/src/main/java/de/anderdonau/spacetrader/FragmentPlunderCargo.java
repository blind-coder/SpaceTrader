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
import android.widget.Button;
import android.widget.TextView;

public class FragmentPlunderCargo extends Fragment {
	GameState gameState;

	public FragmentPlunderCargo(GameState gameState) {
		this.gameState = gameState;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_plunder_cargo, container, false);
		TextView tv;
		Button btn;
		int i;

		for (i = 0; i < GameState.MAXTRADEITEM; i++) {
			btn = (Button) rootView.findViewById(i == 0 ? R.id.btnPlunderCargo1 :
			                                     i == 1 ? R.id.btnPlunderCargo2 :
			                                     i == 2 ? R.id.btnPlunderCargo3 :
			                                     i == 3 ? R.id.btnPlunderCargo4 :
			                                     i == 4 ? R.id.btnPlunderCargo5 :
			                                     i == 5 ? R.id.btnPlunderCargo6 :
			                                     i == 6 ? R.id.btnPlunderCargo7 :
			                                     i == 7 ? R.id.btnPlunderCargo8 :
			                                     i == 8 ? R.id.btnPlunderCargo9 :
					                           /*i == 9 ?*/ R.id.btnPlunderCargo10
			);
			btn.setText(String.format("%2d", gameState.Opponent.cargo[i]));
		}

		tv = (TextView) rootView.findViewById(R.id.txtPlunderCargoBays);
		tv.setText(String.format("%d/%d", gameState.FilledCargoBays(), gameState.TotalCargoBays()));

		return rootView;
	}
}
