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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import de.anderdonau.spacetrader.DataTypes.Ship;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;

@SuppressWarnings("UnusedDeclaration")
public class RenderShip extends View {
	protected final Paint     paint     = new Paint();
	private         GameState gameState = null;
	private Ship mShip;

	private boolean rotate = false;
	Bitmap bitmap;
	Bitmap bitmap_shield;
	Bitmap bitmap_damaged;
	Rect src = new Rect();
	Rect dst = new Rect();

	public RenderShip(Context context) {
		super(context);
	}

	public RenderShip(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RenderShip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	private Bitmap rotateBitmap(Bitmap src) {
		Matrix m = new Matrix();
		m.preScale(-1, 1);
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
	}

	public void setRotate(boolean rotate) {
		if (rotate != this.rotate) {
			bitmap = rotateBitmap(bitmap);
			bitmap_shield = rotateBitmap(bitmap_shield);
			bitmap_damaged = rotateBitmap(bitmap_damaged);
			this.rotate = rotate;
		}
	}

	public void setShip(Ship ship) {
		this.mShip = ship;
		this.rotate = false;
		if (gameState == null) {
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beetle);
			bitmap_shield = BitmapFactory.decodeResource(getResources(), R.drawable.beetle_shield);
			bitmap_damaged = BitmapFactory.decodeResource(getResources(), R.drawable.beetle_damaged);
		} else {
			bitmap = BitmapFactory.decodeResource(getResources(), mShip.getType().drawable);
			bitmap_damaged = BitmapFactory.decodeResource(getResources(),
			                                              mShip.getType().drawable_damaged
			);
			bitmap_shield = BitmapFactory.decodeResource(getResources(), mShip.getType().drawable_shield);
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		int dmgPercent;
		int shieldPercent;
		if (mShip == null) {
			Random rand = new Random();
			int[] cargo = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			int[] weapon = {1, -1, -1};
			int[] shield = {1, -1, -1};
			int[] shieldStrength = {rand.nextInt(GameState.ESHIELDPOWER), -1, -1};
			int[] gadget = {-1, -1, -1};
			int[] crew = {1, -1, -1};
			int fuel = 10;
			int hull = rand.nextInt(200);
			int tribbles = 0;
			Ship s = new Ship(ShipTypes.BEETLE, cargo, weapon, shield, shieldStrength, gadget, crew, fuel,
				hull, tribbles, gameState);
			this.setShip(s);
			this.setRotate(this.getId() == R.id.EncounterPlayerOpponent);
		}

		if (gameState == null) {
			dmgPercent = (mShip.hull * 100) / 200;
			shieldPercent = (mShip.shieldStrength[0] * 100 / GameState.ESHIELDPOWER);
		} else {
			if (mShip.TotalShields() > 0) {
				shieldPercent = (mShip.TotalShieldStrength() * 100) / mShip.TotalShields();
			} else {
				shieldPercent = -1;
			}
			dmgPercent = (mShip.hull * 100) / mShip.GetHullStrength();
		}

		if (shieldPercent > 0) {
			if (this.rotate) {
				int width;
				width = (int) Math.floor(bitmap_shield.getWidth() * shieldPercent / 100.0);
				src.top = 0;
				src.bottom = bitmap_shield.getHeight();
				src.left = 0;
				src.right = bitmap_shield.getWidth();

				dst.top = getHeight() / 2 - bitmap_shield.getHeight() / 2;
				dst.bottom = dst.top + (src.bottom - src.top);
				dst.left = getWidth() / 2 - bitmap_shield.getWidth() / 2;
				dst.right = dst.left + (src.right - src.left);

				canvas.clipRect(dst.right - width, 0, getWidth(), getHeight(), Region.Op.REPLACE);
				canvas.drawBitmap(bitmap_shield, src, dst, paint);
				canvas.clipRect(0, 0, getWidth(), getHeight(), Region.Op.REPLACE);
			} else {
				src.top = 0;
				src.bottom = bitmap_shield.getHeight();
				src.left = 0;
				src.right = bitmap_shield.getWidth() * shieldPercent / 100;

				dst.top = getHeight() / 2 - bitmap_shield.getHeight() / 2;
				dst.bottom = dst.top + (src.bottom - src.top);
				dst.left = getWidth() / 2 - bitmap_shield.getWidth() / 2;
				dst.right = dst.left + (src.right - src.left);
				canvas.drawBitmap(bitmap_shield, src, dst, paint);
			}
		}

		src.top = 0;
		src.bottom = bitmap_damaged.getHeight();
		src.left = 0;
		src.right = bitmap_damaged.getWidth();

		dst.top = getHeight() / 2 - bitmap_damaged.getHeight() / 2;
		dst.bottom = dst.top + (src.bottom - src.top);
		dst.left = getWidth() / 2 - bitmap_damaged.getWidth() / 2;
		dst.right = dst.left + (src.right - src.left);
		canvas.drawBitmap(bitmap_damaged, src, dst, paint);

		if (this.rotate) {
			int width;
			width = (int) Math.floor(bitmap.getWidth() * dmgPercent / 100.0);
			src.top = 0;
			src.bottom = bitmap.getHeight();
			src.left = 0;
			src.right = bitmap.getWidth();

			dst.top = getHeight() / 2 - bitmap.getHeight() / 2;
			dst.bottom = dst.top + (src.bottom - src.top);
			dst.left = getWidth() / 2 - bitmap.getWidth() / 2;
			dst.right = dst.left + (src.right - src.left);

			canvas.clipRect(dst.right - width, 0, getWidth(), getHeight(), Region.Op.REPLACE);
			canvas.drawBitmap(bitmap, src, dst, paint);
			canvas.clipRect(0, 0, getWidth(), getHeight(), Region.Op.REPLACE);
		} else {
			src.top = 0;
			src.bottom = bitmap.getHeight();
			src.left = 0;
			src.right = bitmap.getWidth() * dmgPercent / 100;

			dst.top = getHeight() / 2 - bitmap.getHeight() / 2;
			dst.bottom = dst.top + (src.bottom - src.top);
			dst.left = getWidth() / 2 - bitmap.getWidth() / 2;
			dst.right = dst.left + (src.right - src.left);
			canvas.drawBitmap(bitmap, src, dst, paint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int size;
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
		int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();

		// set the dimensions
		if (widthWithoutPadding > heigthWithoutPadding) {
			size = heigthWithoutPadding;
		} else {
			size = widthWithoutPadding;
		}

		setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(),
		                     size + getPaddingTop() + getPaddingBottom()
		);
	}
}