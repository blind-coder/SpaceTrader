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

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class FragmentBuyCargo extends Fragment {
	GameState gameState;

	public FragmentBuyCargo(GameState gameState) {
		this.gameState = gameState;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_buy_cargo, container, false);
		CrewMember COMMANDER;
		SolarSystem CURSYSTEM;
		COMMANDER = gameState.Mercenary[0];
		CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		TextView tv;
		Button btn;
		Button btnAll;
		int i;

		for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
			btn = (Button) rootView.findViewById(i == 0 ? R.id.btnBuyCargo1 :
			                                     i == 1 ? R.id.btnBuyCargo2 :
			                                     i == 2 ? R.id.btnBuyCargo3 :
			                                     i == 3 ? R.id.btnBuyCargo4 :
			                                     i == 4 ? R.id.btnBuyCargo5 :
			                                     i == 5 ? R.id.btnBuyCargo6 :
			                                     i == 6 ? R.id.btnBuyCargo7 :
			                                     i == 7 ? R.id.btnBuyCargo8 :
			                                     i == 8 ? R.id.btnBuyCargo9 :
					                                    /*i == 9 ? */R.id.btnBuyCargo10
			);
			btnAll = (Button) rootView.findViewById(i == 0 ? R.id.btnBuyCargoAll1 :
			                                        i == 1 ? R.id.btnBuyCargoAll2 :
			                                        i == 2 ? R.id.btnBuyCargoAll3 :
			                                        i == 3 ? R.id.btnBuyCargoAll4 :
			                                        i == 4 ? R.id.btnBuyCargoAll5 :
			                                        i == 5 ? R.id.btnBuyCargoAll6 :
			                                        i == 6 ? R.id.btnBuyCargoAll7 :
			                                        i == 7 ? R.id.btnBuyCargoAll8 :
			                                        i == 8 ? R.id.btnBuyCargoAll9 :
					                                    /*i == 9 ? */R.id.btnBuyCargoAll10
			);
			tv = (TextView) rootView.findViewById(i == 0 ? R.id.txtBuyCargoPrice1 :
			                                      i == 1 ? R.id.txtBuyCargoPrice2 :
			                                      i == 2 ? R.id.txtBuyCargoPrice3 :
			                                      i == 3 ? R.id.txtBuyCargoPrice4 :
			                                      i == 4 ? R.id.txtBuyCargoPrice5 :
			                                      i == 5 ? R.id.txtBuyCargoPrice6 :
			                                      i == 6 ? R.id.txtBuyCargoPrice7 :
			                                      i == 7 ? R.id.txtBuyCargoPrice8 :
			                                      i == 8 ? R.id.txtBuyCargoPrice9 :
					                                     /*i == 9 ? */R.id.txtBuyCargoPrice10
			);
			if (gameState.BuyPrice[i] > 0) {
				btn.setText(String.format("%d", CURSYSTEM.qty[i]));
				tv.setText(String.format("%d cr.", gameState.BuyPrice[i]));
				tv.setVisibility(View.VISIBLE);
				btn.setVisibility(View.VISIBLE);
				btnAll.setVisibility(View.VISIBLE);
			} else {
				tv.setText("not sold");
				tv.setVisibility(View.VISIBLE);
				btn.setVisibility(View.INVISIBLE);
				btnAll.setVisibility(View.INVISIBLE);
			}
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyCargoBays);
		tv.setText(String.format("Bays: %d/%d", gameState.Ship.FilledCargoBays(),
		                         gameState.Ship.TotalCargoBays()
		)
		);
		tv = (TextView) rootView.findViewById(R.id.txtBuyCargoCash);
		tv.setText(String.format("Cash: %d cr.", gameState.Credits));

		return rootView;
	}
}
