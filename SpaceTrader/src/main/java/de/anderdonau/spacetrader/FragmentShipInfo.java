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
import android.widget.ImageView;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.ShipTypes;

public class FragmentShipInfo extends Fragment {
	private ShipTypes.ShipType mType;
	private GameState gameState;

	public FragmentShipInfo(GameState gameState) {
		this.gameState = gameState;
		mType = gameState.ShipTypes.ShipTypes[gameState.ShipInfoId];
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_ship_info, container, false);
		TextView tv;
		ImageView img;

		tv = (TextView) rootView.findViewById(R.id.txtShipInfoTitle);
		tv.setText(mType.name);

		tv = (TextView) rootView.findViewById(R.id.txtShipInfoSize);
		tv.setText(gameState.SystemSize[mType.size]);

		tv = (TextView) rootView.findViewById(R.id.txtShipInfoCargoBays);
		tv.setText(String.format("%d", mType.cargoBays));

		tv = (TextView) rootView.findViewById(R.id.txtShipInfoRange);
		tv.setText(String.format("%d parsecs", mType.fuelTanks));

		tv = (TextView) rootView.findViewById(R.id.txtShipInfoHull);
		tv.setText(String.format("%d", mType.hullStrength));

		tv = (TextView) rootView.findViewById(R.id.txtShipInfoWeapons);
		tv.setText(String.format("%d", mType.weaponSlots));

		tv = (TextView) rootView.findViewById(R.id.txtShipInfoShields);
		tv.setText(String.format("%d", mType.shieldSlots));

		tv = (TextView) rootView.findViewById(R.id.txtShipInfoGadgets);
		tv.setText(String.format("%d", mType.gadgetSlots));

		tv = (TextView) rootView.findViewById(R.id.txtShipInfoQuarters);
		tv.setText(String.format("%d", mType.crewQuarters));

		img = (ImageView) rootView.findViewById(R.id.imgShipInfoShip);
		img.setImageDrawable(getResources().getDrawable(mType.drawable));
		return rootView;
	}
}

