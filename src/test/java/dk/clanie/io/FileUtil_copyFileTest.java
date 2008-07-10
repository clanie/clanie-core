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

import static dk.clanie.io.FileUtil.copyFile;
import static dk.clanie.io.FileUtil.emptyAndDelete;
import static java.io.File.separator;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileUtil_copyFileTest {
	
	private static final File TEST_DIR = new File("target/test/dk_clanie_io");
	private static final File SRC_FILE = new File("src/test/resources/dk/clanie/io/aFile.txt");

	@BeforeClass
	public static void init() {
		emptyAndDelete(TEST_DIR);
		TEST_DIR.mkdirs();
	}

	@Test(expected=IOException.FileNotFound.class)
	public void testMissingSourceFile() throws IOException {
		copyFile(new File("noSuchFile"), TEST_DIR);
	}

	@Test
	public void testCopyToDirectory() throws IOException {
		copyFile(SRC_FILE, TEST_DIR);
		assertTrue(new File(TEST_DIR.getPath() + separator + "aFile.txt").exists());
	}

	@Test(expected=IOException.FileAlreadyExists.class)
	public void testOverwritePrevented() throws IOException {
		final File destFile = new File(TEST_DIR.getPath() + separator + "overwritePrevented.txt");
		copyFile(SRC_FILE, destFile);
		copyFile(SRC_FILE, destFile);
	}

	@Test
	public void testOverwriteAccepted() throws IOException {
		final File destFile = new File(TEST_DIR.getPath() + separator + "overwriteAccepted.txt");
		copyFile(SRC_FILE, destFile);
		copyFile(SRC_FILE, destFile, true);
	}

}
