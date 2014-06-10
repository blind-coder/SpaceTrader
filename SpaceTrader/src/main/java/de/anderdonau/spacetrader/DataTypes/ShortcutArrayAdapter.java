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

package de.anderdonau.spacetrader.DataTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import de.anderdonau.spacetrader.GameState;
import de.anderdonau.spacetrader.Main;
import de.anderdonau.spacetrader.R;

public class ShortcutArrayAdapter extends ArrayAdapter<String> {
	private       Main      main;
	private final String[]  values;
	private final GameState gameState;

	public ShortcutArrayAdapter(Main main, String[] values, GameState gameState) {
		super(main, R.layout.listview_shortcut_entry, values);
		this.main = main;
		this.values = values;
		this.gameState = gameState;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) main.getSystemService(
			Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.listview_shortcut_entry, parent, false);
		//noinspection ConstantConditions
		TextView textView = (TextView) rowView.findViewById(R.id.txtShortcut);
		TextView textView1 = (TextView) rowView.findViewById(R.id.txtTarget);
		textView.setText(values[position]);

		int i = position == 0 ? gameState.Shortcut1 : position == 1 ? gameState.Shortcut2 :
			position == 2 ? gameState.Shortcut3 : gameState.Shortcut4;
		String s = main.Shortcuts[i][1];
		textView1.setText(s);

		return rowView;
	}
}
