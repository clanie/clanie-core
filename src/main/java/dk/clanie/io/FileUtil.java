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

/** File Utility Methods.
 * 
 * @author Claus Nielsen
 */
public class FileUtil {

	/**
	 * Delete a file or directory.
	 * 
	 * When deleting a directory files and subdirectories in it
	 * are also deleted. 
	 * 
	 * @param fileOrDir
	 */
	public static void emptyAndDelete(File fileOrDir) {
		if (fileOrDir.isDirectory()) {
			File[] files = fileOrDir.listFiles();
			for (File file : files) {
				emptyAndDelete(file);
			}
		}
		fileOrDir.delete();
	}

}
