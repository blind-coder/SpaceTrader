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

public class FragmentBank extends MyFragment {
	View rootView;

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		rootView = inflater.inflate(R.layout.fragment_bank, container, false);
		update();
		return rootView;
	}

	@Override
	public boolean update() {
		TextView tv;
		Button btn;

		btn = (Button) rootView.findViewById(R.id.btnBankGetLoan);
		if (gameState.Debt <= 0) {
			btn.setText("Get Loan");
		} else {
			btn.setText("Payback Loan");
		}

		btn = (Button) rootView.findViewById(R.id.btnBankBuyInsurance);
		if (gameState.Insurance) {
			btn.setText("Stop insurance");
		} else {
			btn.setText("Buy insurance");
		}

		tv = (TextView) rootView.findViewById(R.id.txtBankDebt);
		tv.setText(String.format("%d cr.", gameState.Debt));
		btn = (Button) rootView.findViewById(R.id.btnBankPaybackLoan);
		if (gameState.Debt <= 0) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}

		tv = (TextView) rootView.findViewById(R.id.txtBankMaxDebt);
		tv.setText(String.format("%d cr.", gameState.MaxLoan()));

		tv = (TextView) rootView.findViewById(R.id.txtBankShipValue);
		tv.setText(String.format("%d cr.", gameState.CurrentShipPriceWithoutCargo(true)));

		tv = (TextView) rootView.findViewById(R.id.txtBankNoClaim);
		tv.setText(String.format("%d%%%s", Math.min(gameState.NoClaim, 90),
			gameState.NoClaim == 90 ? " (maximum)" : ""));

		tv = (TextView) rootView.findViewById(R.id.txtBankCost);
		tv.setText(String.format("%d cr. daily", gameState.InsuranceMoney()));

		tv = (TextView) rootView.findViewById(R.id.txtBankCash);
		tv.setText(String.format("%d cr.", gameState.Credits));
		return true;
	}
}

