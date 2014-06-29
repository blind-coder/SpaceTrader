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
import android.widget.ImageView;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;

public class FragmentShipInfo extends MyFragment {

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		ShipTypes.ShipType mType = ShipTypes.ShipTypes[gameState.ShipInfoId];
		final View rootView = inflater.inflate(R.layout.fragment_ship_info, container, false);
		TextView tv;
		ImageView img;

		tv = (TextView) rootView.findViewById(R.id.txtShipInfoTitle);
		tv.setText(mType.name);

		tv = (TextView) rootView.findViewById(R.id.txtShipInfoSize);
		tv.setText(main.SystemSize[mType.size]);

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

