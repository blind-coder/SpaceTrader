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

public class FragmentBuyEquipment extends Fragment {
	GameState gameState;

	public FragmentBuyEquipment(GameState gameState) {
		this.gameState = gameState;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_buy_equipment, container, false);
		TextView tv;
		Button btn;
		int i;

		tv = (TextView) rootView.findViewById(R.id.txtBuyEquipmentCash);
		tv.setText(String.format("Cash: %d cr.", gameState.Credits));

		i = -1;
		btn = (Button) rootView.findViewById(R.id.btnBuyPulseLaser);
		if (gameState.BASEWEAPONPRICE(++i) > 0) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}
		btn = (Button) rootView.findViewById(R.id.btnBuyBeamLaser);
		if (gameState.BASEWEAPONPRICE(++i) > 0) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}
		btn = (Button) rootView.findViewById(R.id.btnBuyMilitaryLaser);
		if (gameState.BASEWEAPONPRICE(++i) > 0) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}

		i = -1;
		btn = (Button) rootView.findViewById(R.id.btnBuyEnergyShield);
		if (gameState.BASESHIELDPRICE(++i) > 0) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}
		btn = (Button) rootView.findViewById(R.id.btnBuyReflectiveShield);
		if (gameState.BASESHIELDPRICE(++i) > 0) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}

		i = -1;
		btn = (Button) rootView.findViewById(R.id.btnBuy5CargoBays);
		if (gameState.BASEGADGETPRICE(++i) > 0) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}
		btn = (Button) rootView.findViewById(R.id.btnBuyAutoRepairSystem);
		if (gameState.BASEGADGETPRICE(++i) > 0) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}
		btn = (Button) rootView.findViewById(R.id.btnBuyNavigationSystem);
		if (gameState.BASEGADGETPRICE(++i) > 0) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}
		btn = (Button) rootView.findViewById(R.id.btnBuyTargetingSystem);
		if (gameState.BASEGADGETPRICE(++i) > 0) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}
		btn = (Button) rootView.findViewById(R.id.btnBuyCloakingSystem);
		if (gameState.BASEGADGETPRICE(++i) > 0) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}

		i = -1;
		tv = (TextView) rootView.findViewById(R.id.txtBuyPulseLaser);
		if (gameState.BASEWEAPONPRICE(++i) > 0) {
			tv.setText(String.format("%d cr.", gameState.BASEWEAPONPRICE(i)));
		} else {
			tv.setText("not sold");
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyBeamLaser);
		if (gameState.BASEWEAPONPRICE(++i) > 0) {
			tv.setText(String.format("%d cr.", gameState.BASEWEAPONPRICE(i)));
		} else {
			tv.setText("not sold");
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyMilitaryLaser);
		if (gameState.BASEWEAPONPRICE(++i) > 0) {
			tv.setText(String.format("%d cr.", gameState.BASEWEAPONPRICE(i)));
		} else {
			tv.setText("not sold");
		}

		i = -1;
		tv = (TextView) rootView.findViewById(R.id.txtBuyEnergyShield);
		if (gameState.BASESHIELDPRICE(++i) > 0) {
			tv.setText(String.format("%d cr.", gameState.BASESHIELDPRICE(i)));
		} else {
			tv.setText("not sold");
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyReflectiveShield);
		if (gameState.BASESHIELDPRICE(++i) > 0) {
			tv.setText(String.format("%d cr.", gameState.BASESHIELDPRICE(i)));
		} else {
			tv.setText("not sold");
		}

		i = -1;
		tv = (TextView) rootView.findViewById(R.id.txtBuy5CargoBays);
		if (gameState.BASEGADGETPRICE(++i) > 0) {
			tv.setText(String.format("%d cr.", gameState.BASEGADGETPRICE(i)));
		} else {
			tv.setText("not sold");
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyAutoRepairSystem);
		if (gameState.BASEGADGETPRICE(++i) > 0) {
			tv.setText(String.format("%d cr.", gameState.BASEGADGETPRICE(i)));
		} else {
			tv.setText("not sold");
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyNavigationSystem);
		if (gameState.BASEGADGETPRICE(++i) > 0) {
			tv.setText(String.format("%d cr.", gameState.BASEGADGETPRICE(i)));
		} else {
			tv.setText("not sold");
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyTargetingSystem);
		if (gameState.BASEGADGETPRICE(++i) > 0) {
			tv.setText(String.format("%d cr.", gameState.BASEGADGETPRICE(i)));
		} else {
			tv.setText("not sold");
		}
		tv = (TextView) rootView.findViewById(R.id.txtBuyCloakingSystem);
		if (gameState.BASEGADGETPRICE(++i) > 0) {
			tv.setText(String.format("%d cr.", gameState.BASEGADGETPRICE(i)));
		} else {
			tv.setText("not sold");
		}

		return rootView;
	}
}
