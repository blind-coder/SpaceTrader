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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import de.anderdonau.spacetrader.DataTypes.SolarSystem;

@SuppressWarnings("UnusedDeclaration")
public class NavigationChart extends View {
	protected final Paint     paint           = new Paint();
	public float   Multiplicator   = 20;
	public boolean mOffsetsDefined = false;
	public int     textColor       = Color.BLACK;
	protected       Main      main            = null;
	protected       GameState mGameState      = null;
	protected       int       mDrawWormhole   = -1;
	protected       int       mSelectedSystem = -1;
	protected float radius;
	protected float   mOffsetX            = 0;
	protected float   mOffsetY            = 0;
	protected boolean isShortRange        = true;
	protected float   mCurrentX           = 0;
	protected float   mCurrentY           = 0;
	protected Bitmap  planetclassic_green = BitmapFactory.decodeResource(getResources(),
		R.drawable.planetclassic_green);
	protected Bitmap  planetclassic_blue  = BitmapFactory.decodeResource(getResources(),
		R.drawable.planetclassic_blue);
	protected Bitmap  planetclassic_red   = BitmapFactory.decodeResource(getResources(),
		R.drawable.planetclassic_red);

	public NavigationChart(Context context) {
		super(context);
	}

	public NavigationChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NavigationChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setGameState(GameState mGameState) {
		this.mGameState = mGameState;
	}

	@SuppressWarnings("ConstantConditions")
	public void setMain(Main main) {
		this.main = main;
		TypedArray themeArray = main.getTheme().obtainStyledAttributes(
			new int[]{R.attr.navChartDrawColor});
		int index = 0;
		int defaultColourValue = Color.BLACK;
		this.textColor = themeArray.getColor(index, defaultColourValue);
	}

	public void setShortRange(boolean isShortRange) {
		this.isShortRange = isShortRange;
	}

	public int getSystemAt(float posX, float posY) {
		for (int i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			SolarSystem s = mGameState.SolarSystem[i];
			double x = s.x * Multiplicator + (int) mOffsetX;
			double y = s.y * Multiplicator + (int) mOffsetY;

			if (posX >= x - radius && posX <= x + radius) {
				if (posY >= y - radius && posY <= y + radius) {
					return i;
				}
			}
		}
		if (isShortRange) {
			return -1;
		}
		return getSystemCloseTo(posX, posY);
	}

	public int getSystemCloseTo(float posX, float posY) {
		SolarSystem s;
		int dist = Integer.MAX_VALUE;
		int retVal = -1;
		posX -= mOffsetX; // offsets are always negative, so we need to substract them
		posY -= mOffsetY; // to get the actual touch position relative to 0,0
		for (int i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			s = mGameState.SolarSystem[i];
			int nDist = (int) (Math.pow(posX - CoordToPixel(s.x), 2) + Math.pow(posY - CoordToPixel(s.y),
				2));
			if (dist > nDist) {
				dist = nDist;
				retVal = i;
			}
		}
		return retVal;
	}

	public int getWormholeAt(float posX, float posY) {
		int system = getSystemAt(posX - (radius * 2), posY);
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

	private void sanitizeOffsets() {
		mOffsetX = Math.max(mOffsetX, getWidth() - GameState.GALAXYWIDTH * Multiplicator);
		mOffsetX = Math.min(mOffsetX, 0);
		mOffsetY = Math.max(mOffsetY, getHeight() - GameState.GALAXYHEIGHT * Multiplicator);
		mOffsetY = Math.min(mOffsetY, 0);
	}

	@SuppressWarnings("NullableProblems")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mCurrentX = event.getX();
			mCurrentY = event.getY();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float x = event.getX();
			float y = event.getY();

			// Update how much the touch moved
			mOffsetX += x - mCurrentX;
			mOffsetY += y - mCurrentY;
			sanitizeOffsets();

			mCurrentX = x;
			mCurrentY = y;

			this.invalidate();
			return true;
		}
		return false;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mOffsetsDefined = false;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (mGameState == null) {
			return;
		}

		SolarSystem CURSYSTEM = mGameState.SolarSystem[mGameState.Mercenary[0].curSystem];
		SolarSystem s;
		float x;
		float y;

		if (isShortRange) {
			Multiplicator = Math.min(getHeight(), getWidth()) / (float) (mGameState.Ship
				.GetFuelTanks() * 2 + 3);
		} else {
			Multiplicator = (float) getHeight() / (float) GameState.GALAXYHEIGHT;
			//		  Multiplicator = (float) getWidth() / (float) GameState.GALAXYWIDTH;
		}
		radius = Multiplicator * 2;

		if (mSelectedSystem < 0) {
			mSelectedSystem = mGameState.Mercenary[0].curSystem;
		}
		s = mGameState.SolarSystem[mSelectedSystem];
		x = CoordToPixel(s.x);
		y = CoordToPixel(s.y);

		if (!mOffsetsDefined) {
			/* Short range chart always focuses on CURSYSTEM.
			 * Long range chart may focus on mSelectedSystem by using "Find" button.
			 */
			if (!isShortRange) {
				mOffsetX = -x + getWidth() / 2;
				mOffsetY = -y + getHeight() / 2;
			} else {
				mOffsetX = -CoordToPixel(CURSYSTEM.x) + getWidth() / 2;
				mOffsetY = -CoordToPixel(CURSYSTEM.y) + getHeight() / 2;
			}
			mOffsetsDefined = true;
			sanitizeOffsets();
		}

		canvas.translate(mOffsetX, mOffsetY);

		/*
		paint.setColor(Color.LTGRAY);
		paint.setStyle(Paint.Style.STROKE);
		for (x=0; x<GameState.GALAXYWIDTH;x++){
			for (y=0; y<GameState.GALAXYHEIGHT;y++){
				canvas.drawRect(x*Multiplicator, y*Multiplicator, x*Multiplicator+radius*2, y*Multiplicator+radius*2, paint);
			}
		}
		*/

		paint.setTextSize(isShortRange ? 40 : 20);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setStyle(Paint.Style.FILL);

		paint.setColor(this.textColor);
		paint.setStrokeWidth(2);

		if (isShortRange) {
			canvas.drawLine(x - (radius * 1.5f), y, x + (radius * 1.5f), y, paint);
			canvas.drawLine(x, y - (radius * 1.5f), x, y + (radius * 1.5f), paint);
		} else {
			canvas.drawLine(x - (radius * 3), y, x + (radius * 3), y, paint);
			canvas.drawLine(x, y - (radius * 3), x, y + (radius * 3), paint);
		}

		paint.setStyle(Paint.Style.FILL_AND_STROKE);

		if (mGameState.TrackedSystem >= 0) {
			s = mGameState.SolarSystem[mGameState.TrackedSystem];
			int distToTracked = mGameState.RealDistance(CURSYSTEM, s);
			if (distToTracked > 0) {
				paint.setStrokeWidth(isShortRange ? 5 : 2);
				canvas.drawLine(CoordToPixel(CURSYSTEM.x), CoordToPixel(CURSYSTEM.y), CoordToPixel(s.x),
					CoordToPixel(s.y), paint);
				paint.setStrokeWidth(0);
			}
		}

		if (mDrawWormhole >= 0) {
			int wormholeFrom = mDrawWormhole - 1;
			if (wormholeFrom < 0) {
				wormholeFrom += GameState.MAXWORMHOLE;
			}
			SolarSystem from = mGameState.SolarSystem[mGameState.Wormhole[wormholeFrom]];
			SolarSystem to = mGameState.SolarSystem[mGameState.Wormhole[mDrawWormhole]];
			paint.setColor(this.textColor);
			paint.setStrokeWidth(5);
			canvas.drawLine(CoordToPixel(from.x) + radius * 2, CoordToPixel(from.y), CoordToPixel(to.x),
				CoordToPixel(to.y), paint);
			paint.setStrokeWidth(0);
		}

		paint.setStrokeWidth(0);

		for (int i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			s = mGameState.SolarSystem[i];
			x = CoordToPixel(s.x);
			y = CoordToPixel(s.y);

			Rect src = new Rect();
			Rect dst = new Rect();
			src.top = 0;
			src.left = 0;
			dst.top = (int) (y - radius);
			dst.bottom = (int) (y + radius);
			dst.left = (int) (x - radius);
			dst.right = (int) (x + radius);

			if (s.visited && mGameState.BetterGfx) {
				Bitmap bitmap;
				if (s.specialResources == GameState.DESERT) {
					bitmap = main.desertBitmaps[i % main.desertBitmaps.length];
				} else if (s.specialResources == GameState.LIFELESS) {
					bitmap = main.lifeLessBitmaps[i % main.lifeLessBitmaps.length];
				} else {
					bitmap = main.planetsBitmaps[i % main.planetsBitmaps.length];
				}
				src.right = bitmap.getWidth();
				src.bottom = bitmap.getHeight();
				canvas.drawBitmap(bitmap, src, dst, paint);
			} else {
				Bitmap bitmap;
				if (i == mSelectedSystem) {
					bitmap = planetclassic_red;
					paint.setColor(Color.RED);
				} else if (s.visited) {
					bitmap = planetclassic_blue;
					paint.setColor(Color.BLUE);
				} else {
					bitmap = planetclassic_green;
					paint.setColor(Color.GREEN);
				}
				src.right = bitmap.getWidth();
				src.bottom = bitmap.getHeight();
				canvas.drawBitmap(bitmap, src, dst, paint);
				//				canvas.drawCircle(x, y, radius, paint);
			}

			//			if (isShortRange) {
			paint.setColor(this.textColor);
			canvas.drawText(main.SolarSystemName[s.nameIndex], x, y - radius, paint);
			//			}
		}

		for (int i = 0; i < GameState.MAXWORMHOLE; i++) {
			s = mGameState.SolarSystem[mGameState.Wormhole[i]];

			x = CoordToPixel(s.x) + radius * 2;
			y = CoordToPixel(s.y);
			paint.setColor(this.textColor);
			canvas.drawCircle(x, y, radius, paint);
			for (float r = radius; r >= 0; r--) {
				paint.setARGB(255, (int) ((255 / radius) * r), (int) ((255 / radius) * r), 0);
				canvas.drawCircle(x, y, r, paint);
			}
		}

		if (mGameState.Ship.GetFuel() > 0) {
			x = CoordToPixel(CURSYSTEM.x);
			y = CoordToPixel(CURSYSTEM.y);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(this.textColor);
			paint.setStrokeWidth(isShortRange ? 5 : 2);
			canvas.drawCircle(x, y, mGameState.Ship.GetFuel() * Multiplicator, paint);
			paint.setStrokeWidth(0);
		}
	}

	public float CoordToPixel(int coord) {
		return coord * Multiplicator;
	}
}
