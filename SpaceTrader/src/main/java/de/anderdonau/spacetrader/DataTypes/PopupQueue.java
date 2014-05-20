/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import java.util.LinkedList;

/**
 * Created by blindcoder on 5/19/14.
 */
public class PopupQueue {
	private LinkedList<Popup> list = new LinkedList<Popup>();

	public void push(Popup item) {list.addLast(item);}

	public Popup pop() {return list.removeFirst();}

	public Popup peek() {return list.getFirst();}

	public int size() {return list.size();}

	public boolean isEmpty() {return list.isEmpty();}

	public void clear() {list.clear();}
}
