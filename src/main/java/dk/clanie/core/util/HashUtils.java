/**
 * Copyright (C) 2008, Claus Nielsen, clausn999@gmail.com
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import dk.clanie.exception.RuntimeIOException;
import lombok.extern.slf4j.Slf4j;

/**
 * Hash Utility Methods.
 * 
 * @author Claus Nielsen
 */
@Slf4j
public class HashUtils {


	/**
	 * Calculates SHA1 for the contents of the supplied File.
	 * 
	 * @param file File
	 * @return String with SHA1 calculated from the contents of <code>file</code>.
	 * 
	 * @throws RuntimeIOException
	 */
	public static String sha1(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			return sha1(is);
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeIOException.FileNotFound(file);
		} finally {
			// This must be in a catch block because sha1(InputStream) may throw RuntimeIOException.
			if (is != null) {
				try {
					is.close();
				} catch (IOException ioe) {
					log.warn("Failed to close stream for File: " + file, ioe);
					throw new RuntimeIOException(ioe);
				}
			}
		}
	}


	/**
	 * Calculates SHA1 for the contents referred to by the supplied URL.
	 * 
	 * @param url URL
	 * @return String with SHA1 calculated from the contents referred to by <code>url</code>.
	 * 
	 * @throws RuntimeIOException
	 */
	public static String sha1(URL url) {
		InputStream is = null;
		try {
			is = url.openStream();
			return sha1(is);
		} catch (IOException ioe) {
			log.warn("Error occourred while reading from URL: " + url, ioe);
			throw new RuntimeIOException(ioe);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ioe) {
					log.warn("Failed to close stream for URL: " + url, ioe);
					throw new RuntimeIOException(ioe);
				}
			}
		}
	}


	/**
	 * Calculates SHA1 for the contents of the supplied InputStream.
	 * 
	 * @param is InputStream
	 * @return String with SHA1 calculated from the contents of <code>is</code>.
	 * 
	 * @throws RuntimeIOException
	 */
	public static String sha1(InputStream is) {
		byte[] buf = new byte[1024*1024*5];
		int bytesRead = 0;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException nsae) {
			throw new RuntimeIOException(nsae.getMessage(), nsae);
		}
		try {
			while ((bytesRead = is.read(buf)) > 0) {
				md.update(buf, 0, bytesRead);
			}
		} catch (IOException ioe) {
			throw new RuntimeIOException(ioe.getMessage(), ioe);
		}
		byte[] digest = md.digest();
		String hexDigest = new String(Hex.encodeHex(digest));
		return hexDigest;
	}


}
