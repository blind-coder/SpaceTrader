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

public class FragmentBuyEquipment extends MyFragment {
	View rootView;

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		rootView = inflater.inflate(R.layout.fragment_buy_equipment, container, false);
		update();
		return rootView;
	}

	@Override
	public boolean update() {
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
		return true;
	}
}
