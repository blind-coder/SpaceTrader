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

public class FragmentBank extends Fragment {
	GameState gameState;

	public FragmentBank(GameState gameState) {
		this.gameState = gameState;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_bank, container, false);
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
		                         gameState.NoClaim == 90 ? " (maximum)" : ""
		)
		);

		tv = (TextView) rootView.findViewById(R.id.txtBankCost);
		tv.setText(String.format("%d cr. daily", gameState.InsuranceMoney()));

		tv = (TextView) rootView.findViewById(R.id.txtBankCash);
		tv.setText(String.format("%d cr.", gameState.Credits));

		return rootView;
	}
}

