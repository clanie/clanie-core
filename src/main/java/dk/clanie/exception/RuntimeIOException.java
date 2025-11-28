/*
 * Copyright (C) 2008 - 2025, Claus Nielsen, clausn999@gmail.com
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
package dk.clanie.exception;

import java.io.File;

import lombok.experimental.StandardException;

/**
 * Runtime IOException.
 * 
 * @author Claus Nielsen
 */
@StandardException
@SuppressWarnings("serial")
public class RuntimeIOException extends AbstractRuntimeException {


	public static class FileNotFound extends RuntimeIOException {
		public FileNotFound(File file) {
			super("File not found: " + file.getPath() + ".");
		}
	}


	public static class FileAlreadyExists extends RuntimeIOException {
		public FileAlreadyExists(File file) {
			super("File already exists: " + file.getPath() + ".");
		}
	}


	public static class FailedToCreate extends RuntimeIOException {
		public FailedToCreate(File file) {
			super("Failed to create file " + file.getPath() + ".");
		}
	}


}
