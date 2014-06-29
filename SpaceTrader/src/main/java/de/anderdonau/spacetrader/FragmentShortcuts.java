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

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.ShortcutArrayAdapter;

public class FragmentShortcuts extends MyFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//noinspection ConstantConditions
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		final View rootView = inflater.inflate(R.layout.fragment_shortcuts, container, false);
		//noinspection ConstantConditions
		final ListView listView = (ListView) rootView.findViewById(R.id.listView);

		String[] values = {"Shortcut 1", "Shortcut 2", "Shortcut 3", "Shortcut 4"};
		listView.setAdapter(new ShortcutArrayAdapter(main, values, gameState));

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				final int position = i;
				LinearLayout linearLayout = new LinearLayout(main);

				AlertDialog.Builder builder = new AlertDialog.Builder(main);
				builder.setTitle("Select new Shortcut");
				builder.setView(main.getLayoutInflater().inflate(R.layout.dialog_select_new_shortcut,
					linearLayout));
				builder.setCancelable(false);
				final AlertDialog alertDialog = builder.create();
				View.OnClickListener listener = new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						int i = -1;
						switch (view.getId()) {
							case R.id.dialogSelectNewShortcut10:
								i++;
							case R.id.dialogSelectNewShortcut9:
								i++;
							case R.id.dialogSelectNewShortcut8:
								i++;
							case R.id.dialogSelectNewShortcut7:
								i++;
							case R.id.dialogSelectNewShortcut6:
								i++;
							case R.id.dialogSelectNewShortcut5:
								i++;
							case R.id.dialogSelectNewShortcut4:
								i++;
							case R.id.dialogSelectNewShortcut3:
								i++;
							case R.id.dialogSelectNewShortcut2:
								i++;
							case R.id.dialogSelectNewShortcut1:
								i++;
							case R.id.dialogSelectNewShortcut0:
								i++;
						}
						switch (position) {
							case 0:
								gameState.Shortcut1 = i;
								break;
							case 1:
								gameState.Shortcut2 = i;
								break;
							case 2:
								gameState.Shortcut3 = i;
								break;
							case 3:
								gameState.Shortcut4 = i;
								break;
						}
						alertDialog.dismiss();
						main.invalidateOptionsMenu();
						main.changeFragment(Main.FRAGMENTS.SHORTCUTS);
					}
				};
				i = main.Shortcuts.length;
				for (int j : new int[]{R.id.dialogSelectNewShortcut10, R.id.dialogSelectNewShortcut9,
					R.id.dialogSelectNewShortcut8, R.id.dialogSelectNewShortcut7,
					R.id.dialogSelectNewShortcut6, R.id.dialogSelectNewShortcut5,
					R.id.dialogSelectNewShortcut4, R.id.dialogSelectNewShortcut3,
					R.id.dialogSelectNewShortcut2, R.id.dialogSelectNewShortcut1,
					R.id.dialogSelectNewShortcut0

				}) {
					Button button = (Button) linearLayout.findViewById(j);
					button.setOnClickListener(listener);
					button.setText(main.Shortcuts[--i][1]);
				}
				alertDialog.show();
			}
		});
		return rootView;
	}
}
