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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import de.anderdonau.spacetrader.Main;
import de.anderdonau.spacetrader.R;

public class Popup {
	public AlertDialog dialog   = null;
	public int         max      = -1;
	public boolean     wasShown = false;
	String         title      = "";
	String         content    = "";
	String         hint       = "";
	String         help       = "";
	String         positive   = "";
	String         negative   = "";
	buttonCallback cbPositive = null;
	buttonCallback cbNegative = null;
	buttonCallback cbMax      = null;
	LayoutInflater inflater   = null;
	public Main context = null;
	DialogInterface.OnClickListener doNothing = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialogInterface, int i) {}
	};
	View.OnClickListener            showHelp  = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Toast.makeText(Popup.this.context, help, Toast.LENGTH_LONG).show();
			// dialog.dismiss(); // don't dismiss dialog here
		}
	};

	public Popup(Main context, String title, String content, String help, String positive, buttonCallback cbPositive) {
		this.context = context;
		this.title = title;
		this.content = content;
		this.help = help;
		this.positive = positive;
		this.cbPositive = cbPositive;
	}

	public Popup(Main context, String title, String content, String hint, String help, String positive, String negative, buttonCallback cbPositive, buttonCallback cbNegative) {
		this.context = context;
		this.title = title;
		this.content = content;
		this.hint = hint;
		this.help = help;
		this.negative = negative;
		this.positive = positive;
		this.cbPositive = cbPositive;
		this.cbNegative = cbNegative;
	}

	public Popup(Main context, String title, String content, String help, String positive, String negative, buttonCallback cbPositive, buttonCallback cbNegative) {
		this.title = title;
		this.content = content;
		this.help = help;
		this.positive = positive;
		this.negative = negative;
		this.cbPositive = cbPositive;
		this.cbNegative = cbNegative;
		this.context = context;
	}

	public Popup(Main context, String title, String content, String hint, String help, int max, String positive, String negative, buttonCallback cbPositive, buttonCallback cbNegative, buttonCallback cbMax) {
		this.context = context;
		this.title = title;
		this.content = content;
		this.hint = hint;
		this.help = help;
		this.max = max;
		this.positive = positive;
		this.negative = negative;
		this.cbPositive = cbPositive;
		this.cbNegative = cbNegative;
		this.cbMax = cbMax;
	}

	public void showConfirm() {
		AlertDialog.Builder confirm = new AlertDialog.Builder(context);
		confirm.setTitle(title);
		confirm.setMessage(content);
		confirm.setCancelable(false);
		confirm.setPositiveButton(positive, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialog.dismiss();
				if (cbPositive != null) {
					cbPositive.execute(Popup.this, null);
				}
				context.showNextPopup();
			}
		}
		);
		confirm.setNegativeButton(negative, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialog.dismiss();
				if (cbNegative != null) {
					cbNegative.execute(Popup.this, null);
				}
				context.showNextPopup();
			}
		}
		);
		if (help.length() > 0) {
			confirm.setNeutralButton("Help", doNothing);
		}
		dialog = confirm.create();
		dialog.show();
		if (help.length() > 0) {
			//noinspection ConstantConditions
			dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(showHelp);
		}
	}

	public void showMessage() {
		AlertDialog.Builder confirm = new AlertDialog.Builder(context);
		confirm.setTitle(title);
		//TextView textView = new TextView(context);
		//textView.setAutoLinkMask(Linkify.ALL);
		//textView.setText(content);
		//confirm.setView(textView);
		confirm.setMessage(content);
		confirm.setCancelable(false);
		confirm.setPositiveButton(positive, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialog.dismiss();
				if (cbPositive != null) {
					cbPositive.execute(Popup.this, null);
				}
				context.showNextPopup();
			}
		}
		);
		if (help.length() > 0) {
			confirm.setNeutralButton("Help", doNothing);
		}
		dialog = confirm.create();
		dialog.show();
		if (help.length() > 0) {
			//noinspection ConstantConditions
			dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(showHelp);
		}
	}

	public void showTextInput() {
		final EditText input;
		final LinearLayout linearLayout = new LinearLayout(context);
		View view;

		view = inflater.inflate(R.layout.view_input_dialog_text, linearLayout, false);
		assert view != null;

		input = (EditText) view.findViewById(R.id.view_input_dialog_text);
		input.setHint(hint);

		AlertDialog.Builder confirm = new AlertDialog.Builder(context).setTitle(title).setMessage(
			content
		).setView(view).setPositiveButton(positive, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				if (cbPositive != null) {
					cbPositive.execute(Popup.this, input);
				}
				context.showNextPopup();
			}
		}
		).setNegativeButton(negative, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				if (cbNegative != null) {
					cbNegative.execute(Popup.this, input);
				}
				context.showNextPopup();
			}
		}
		);
		confirm.setCancelable(false);
		if (help.length() > 0) {
			confirm.setNeutralButton("Help", doNothing);
		}
		dialog = confirm.create();
		dialog.show();
		if (help.length() > 0) {
			//noinspection ConstantConditions
			dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(showHelp);
		}
	}

	public void showIntegerInput() {
		final LinearLayout linearLayout = new LinearLayout(context);
		final SeekBar seekBar;
		final TextView textView;
		final Button button;
		final View view;

		view = inflater.inflate(R.layout.view_input_dialog, linearLayout, false);
		assert view != null;
		button = (Button) view.findViewById(R.id.view_input_dialog_max);
		button.setText("Max");
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				if (cbMax != null) {
					cbMax.execute(Popup.this, view);
				}
				context.showNextPopup();
			}
		}
		);

		seekBar = (SeekBar) view.findViewById(R.id.view_input_dialog_seekbar);
		seekBar.setMax(max);

		textView = (TextView) view.findViewById(R.id.view_input_dialog_amount);
		textView.setText("0");

		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				textView.setText(String.format("%d", seekBar.getProgress()));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		}
		);

		AlertDialog.Builder confirm = new AlertDialog.Builder(context).setTitle(title).setMessage(
			content
		).setView(view).setPositiveButton(positive, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				if (cbPositive != null) {
					cbPositive.execute(Popup.this, seekBar);
				}
				context.showNextPopup();
			}
		}
		).setNegativeButton(negative, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				if (cbNegative != null) {
					cbNegative.execute(Popup.this, seekBar);
				}
				context.showNextPopup();
			}
		}
		);
		confirm.setCancelable(false);
		if (help.length() > 0) {
			confirm.setNeutralButton("Help", doNothing);
		}
		dialog = confirm.create();
		dialog.show();
		if (help.length() > 0) {
			//noinspection ConstantConditions
			dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(showHelp);
		}
	}

	public void show() {
		this.inflater = this.context.getLayoutInflater();

		this.wasShown = true;
		if (this.cbNegative == null && this.max == -1) {
			this.showMessage();
		} else if (this.cbNegative != null && this.cbPositive != null && this.max == -1 && this.hint
			.equals("")) {
			this.showConfirm();
		} else if (this.max == -1) {
			this.showTextInput();
		} else {
			this.showIntegerInput();
		}
	}

	public interface buttonCallback {
		public void execute(Popup popup, View view);
	}
}
