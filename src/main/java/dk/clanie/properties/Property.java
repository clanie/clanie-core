/*
 * Copyright (C) 2010, Claus Nielsen, clausn999@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package dk.clanie.properties;


/**
 * Property interface.
 * 
 * Defines simplest possible interface for a (mutable) property.
 * 
 * @author Claus Nielsen 
 *
 * @param <T> - the type of the property.
 */
public interface Property <T> {


	/**
	 * Gets the current value of the property.
	 *
	 * @return T the current value of the property
	 */
	T get();


	/**
	 * Sets a new value for the property.
	 * 
	 * @param value - the property's new value.
	 */
	void set(T value);


}
