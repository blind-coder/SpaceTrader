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

public class FragmentDumpCargo extends Fragment {
	GameState gameState;

	public FragmentDumpCargo(GameState gameState) {
		this.gameState = gameState;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_dump_cargo, container, false);
		TextView tv;
		Button btn;
		int i;

		for (i = 0; i < GameState.MAXTRADEITEM; i++) {
			btn = (Button) rootView.findViewById(i == 0 ? R.id.btnDumpCargo1 :
			                                     i == 1 ? R.id.btnDumpCargo2 :
			                                     i == 2 ? R.id.btnDumpCargo3 :
			                                     i == 3 ? R.id.btnDumpCargo4 :
			                                     i == 4 ? R.id.btnDumpCargo5 :
			                                     i == 5 ? R.id.btnDumpCargo6 :
			                                     i == 6 ? R.id.btnDumpCargo7 :
			                                     i == 7 ? R.id.btnDumpCargo8 :
			                                     i == 8 ? R.id.btnDumpCargo9 :
					                           /*i == 9 ?*/ R.id.btnDumpCargo10
			);
			btn.setText(String.format("%2d", gameState.Ship.cargo[i]));
		}

		tv = (TextView) rootView.findViewById(R.id.txtDumpCargoBays);
		tv.setText(String.format("%d/%d", gameState.Ship.FilledCargoBays(),
		                         gameState.Ship.TotalCargoBays()
		)
		);

		return rootView;
	}
}
