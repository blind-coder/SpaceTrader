/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class ShortRangeChart extends View {
	protected final Paint     paint          = new Paint();
	public          int       Multiplicator  = 20;
	public          int       WormholeOffset = 4;
	protected       GameState mGameState     = null;
	protected       int       mDrawWormhole  = -1;
	protected       int       mSelectedSystem = -1;
	protected       int       radius;
	protected       float     mOffsetX = 0;
	protected       float     mOffsetY = 0;
	protected       float     mCurrentX = 0;
	protected       float     mCurrentY = 0;

	public ShortRangeChart(Context context) {
		super(context);
	}

	public ShortRangeChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ShortRangeChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setGameState(GameState mGameState) {
		this.mGameState = mGameState;
	}

	public int getSystemAt(float posX, float posY) {
		SolarSystem CURSYSTEM = mGameState.SolarSystem[mGameState.Mercenary[0].curSystem];
		int offsetX = CURSYSTEM.x * Multiplicator;
		int offsetY = CURSYSTEM.y * Multiplicator;

		for (int i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			SolarSystem s = mGameState.SolarSystem[i];
			int x = s.x * Multiplicator - offsetX + getWidth() / 2 + (int)mOffsetX;
			int y = s.y * Multiplicator - offsetY + getHeight() / 2 + (int)mOffsetY;

			if (posX >= x - radius && posX <= x + radius) {
				if (posY >= y - radius && posY <= y + radius) {
					return i;
				}
			}
		}
		return -1;
	}

	public int getWormholeAt(float posX, float posY) {
		int system = getSystemAt(posX - (radius * 2 + WormholeOffset), posY);
		if (system < 0) {
			return -1;
		}

		for (int i = 0; i < GameState.MAXWORMHOLE; i++) {
			SolarSystem s = mGameState.SolarSystem[mGameState.Wormhole[i]];
			if (s.nameIndex == mGameState.SolarSystem[system].nameIndex) {
				i = (i + 1) % GameState.MAXWORMHOLE;
				return i;
			}
		}
		return -1;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (mGameState == null) { return; }

		radius = Math.min(getWidth(), getHeight()) / 20;
		SolarSystem CURSYSTEM = mGameState.SolarSystem[mGameState.Mercenary[0].curSystem];
		SolarSystem s;
		int x, y;
		int offsetX = CURSYSTEM.x * Multiplicator;
		int offsetY = CURSYSTEM.y * Multiplicator;

		canvas.translate(mOffsetX, mOffsetY);
		paint.setTextSize(40);
		paint.setStrokeWidth(0);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setStyle(Paint.Style.FILL);

		if (mSelectedSystem < 0){
			mSelectedSystem = mGameState.Mercenary[0].curSystem;
		}
		s = mGameState.SolarSystem[mSelectedSystem];
		x = s.x * Multiplicator - offsetX + getWidth() / 2;
		y = s.y * Multiplicator - offsetY + getHeight() / 2;

		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(2);

		canvas.drawLine(x - (radius * 1.25f), y, x+(radius*1.25f), y, paint);
		canvas.drawLine(x, y-(radius*1.25f), x, y+(radius*1.25f), paint);

		for (int i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			s = mGameState.SolarSystem[i];
			x = s.x * Multiplicator - offsetX + getWidth() / 2;
			y = s.y * Multiplicator - offsetY + getHeight() / 2;
			if (s.visited) {
				paint.setColor(Color.BLUE);
			} else {
				paint.setColor(Color.GREEN);
			}
			canvas.drawCircle(x, y, radius, paint);

			paint.setColor(Color.BLACK);
			canvas.drawText(mGameState.SolarSystemName[s.nameIndex], x, y - radius, paint);
		}

		for (int i = 0; i < GameState.MAXWORMHOLE; i++) {
			s = mGameState.SolarSystem[mGameState.Wormhole[i]];

			x = s.x * Multiplicator - offsetX + getWidth() / 2;
			y = s.y * Multiplicator - offsetY + getHeight() / 2;
			paint.setColor(Color.BLACK);
			canvas.drawCircle(x + radius * 2 + 4, y, radius, paint);
			for (int r = radius; r >= 0; r--) {
				paint.setARGB(255, (255 / radius) * r, (255 / radius) * r, 0);
				canvas.drawCircle(x + radius * 2 + WormholeOffset, y, r, paint);
			}
		}

		if (mGameState.GetFuel() > 0) {
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(5);
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, mGameState.GetFuel() * Multiplicator, paint
			);
			paint.setStrokeWidth(0);
		}
		paint.setStyle(Paint.Style.FILL_AND_STROKE);

		if (mGameState.TrackedSystem >= 0) {
			s = mGameState.SolarSystem[mGameState.TrackedSystem];
			int distToTracked = mGameState.RealDistance(CURSYSTEM, s);
			if (distToTracked > 0) {
				canvas.drawLine(getWidth() / 2, getHeight() / 2,
				                s.x * Multiplicator - offsetX + getWidth() / 2,
				                s.y * Multiplicator - offsetY + getHeight() / 2, paint
				);
			}
		}

		if (mDrawWormhole >= 0){
			int wormholeFrom = mDrawWormhole-1;
			if (wormholeFrom < 0){
				wormholeFrom += GameState.MAXWORMHOLE;
			}
			SolarSystem from = mGameState.SolarSystem[mGameState.Wormhole[wormholeFrom]];
			SolarSystem to = mGameState.SolarSystem[mGameState.Wormhole[mDrawWormhole]];
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(5);
			canvas.drawLine(from.x * Multiplicator - offsetX + getWidth() / 2 + radius * 2 + WormholeOffset,
		                  from.y * Multiplicator - offsetY + getHeight() / 2,
		                  to.x * Multiplicator - offsetX + getWidth() / 2,
		                  to.y * Multiplicator - offsetY + getHeight() / 2, paint);
			paint.setStrokeWidth(0);
		}
	}

	public void scrollBy(float x, float y){
		mOffsetX += x;
		mOffsetY += y;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mCurrentX = event.getRawX();
			mCurrentY = event.getRawY();
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float x = event.getRawX();
			float y = event.getRawY();

			// Update how much the touch moved
			mOffsetX += x - mCurrentX;
			mOffsetY += y - mCurrentY;

			mCurrentX = x;
			mCurrentY = y;

			this.invalidate();
		}
		return true;
	}
}