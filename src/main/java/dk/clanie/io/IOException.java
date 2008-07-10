/**
 * Copyright (C) 2008, Claus Nielsen, cn@cn-consult.dk.dk
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
package dk.clanie.io;

import java.io.File;

import dk.clanie.exception.AbstractRuntimeException;

/** IOException.
 * 
 * @author Claus Nielsen
 */
@SuppressWarnings("serial")
public class IOException extends AbstractRuntimeException {

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public IOException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param cause
	 */
	public IOException(String message, Throwable cause) {
		super(message, cause);
	}

	public static class FileNotFound extends IOException {
		public FileNotFound(File file) {
			super("File not found: " + file.getPath() + ".");
		}
	}

	public static class FileAlreadyExists extends IOException {
		public FileAlreadyExists(File file) {
			super("File already exists: " + file.getPath() + ".");
		}
	}

	public static class FailedToCreate extends IOException {
		public FailedToCreate(File file) {
			super("Failed to create file " + file.getPath() + ".");
		}
	}

}
