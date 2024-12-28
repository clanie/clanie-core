/**
 * Copyright (C) 2007, Claus Nielsen, clausn999@gmail.com
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
package dk.clanie.core.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class ResourceBundleEnumeration implements Enumeration<String> {

	private Enumeration<String> enumeration;
	private Iterator<String> iterator;

	public ResourceBundleEnumeration(Set<String> keySet,
			Enumeration<String> enumeration) {
		iterator = keySet.iterator();
		this.enumeration = enumeration;
	}

	@Override
	public boolean hasMoreElements() {
		return iterator.hasNext()
				|| (enumeration != null && enumeration.hasMoreElements());
	}

	@Override
	public String nextElement() {
		if (iterator.hasNext())
			return iterator.next();
		if (enumeration != null && enumeration.hasMoreElements())
			return enumeration.nextElement();
		throw new NoSuchElementException();
	}

}
