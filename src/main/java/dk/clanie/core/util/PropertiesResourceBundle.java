/*
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import dk.clanie.exception.RuntimeIOException;

/**
 * ResourceBundle which loads its properties from several property files.
 * 
 * Subclasses should implement a default constructor to supply an array of names
 * of property files to read.
 * 
 * @author Claus Nielsen
 */
public abstract class PropertiesResourceBundle extends ResourceBundle {

	private Properties props;
	private Map<String, Object> lookup;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PropertiesResourceBundle(String[] propertyFiles) {
		props = new Properties();
		for (int i = 0; i < propertyFiles.length; i++) {
			InputStream stream = getClass().getClassLoader()
					.getResourceAsStream(propertyFiles[i]);
			try {
				props.load(stream);
			} catch (IOException ioe) {
				throw new RuntimeIOException(ioe);
			}
		}
		lookup = new HashMap(props);
	}

	// Implements java.util.ResourceBundle.handleGetObject.
	// Inherits javadoc specification.
	@Override
	public Object handleGetObject(String key) {
		if (key == null) {
			throw new NullPointerException();
		}
		return lookup.get(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		ResourceBundle parent = this.parent;
		return new ResourceBundleEnumeration(lookup.keySet(),
				(parent != null) ? parent.getKeys() : null);
	}

}
