package dk.clanie.core.exception;

import java.io.File;


public class FileNotFoundException extends AbstractRuntimeException {

	File file;
	
	public FileNotFoundException(String string, File file) {
		super(string);
		this.file = file;
	}

	public FileNotFoundException(File file) {
		this.file = file;
	}

	File getFile() {
		return file;
	}

}
