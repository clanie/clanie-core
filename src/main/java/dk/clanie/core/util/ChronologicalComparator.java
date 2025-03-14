/**
 * Copyright (C) 2025, Claus Nielsen, clausn999@gmail.com
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
import java.util.Comparator;

/**
 * Comparator for ordering stuff implementing {@link Chronological} in chronological order.
 */
public class ChronologicalComparator<T extends Comparable<? super T>> implements Comparator<Chronological<T>> {


	@Override
    public int compare(Chronological<T> o1, Chronological<T> o2) {
        return o1.getChronoKey().compareTo(o2.getChronoKey());
    }


}
