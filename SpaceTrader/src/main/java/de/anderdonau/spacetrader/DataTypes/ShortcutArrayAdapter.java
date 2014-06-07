/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import de.anderdonau.spacetrader.GameState;
import de.anderdonau.spacetrader.R;

public class ShortcutArrayAdapter extends ArrayAdapter<String> {
	private final Context   context;
	private final String[]  values;
	private final GameState gameState;

	public ShortcutArrayAdapter(Context context, String[] values, GameState gameState) {
		super(context, R.layout.listview_shortcut_entry, values);
		this.context = context;
		this.values = values;
		this.gameState = gameState;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(
			Context.LAYOUT_INFLATER_SERVICE
		);

		View rowView = inflater.inflate(R.layout.listview_shortcut_entry, parent, false);
		//noinspection ConstantConditions
		TextView textView = (TextView) rowView.findViewById(R.id.txtShortcut);
		TextView textView1 = (TextView) rowView.findViewById(R.id.txtTarget);
		textView.setText(values[position]);

		int i = position == 0 ? gameState.Shortcut1 : position == 1 ? gameState.Shortcut2 :
		                                              position == 2 ? gameState.Shortcut3 :
		                                              gameState.Shortcut4;
		String s = gameState.Shortcuts[i][1];
		textView1.setText(s);

		return rowView;
	}
}
