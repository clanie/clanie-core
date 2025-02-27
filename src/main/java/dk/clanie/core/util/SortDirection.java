package dk.clanie.core.util;

public enum SortDirection {

	ASC,
	DESC;


	public boolean isAscending() {
		return this == ASC;
	}


	public boolean isDescending() {
		return this == DESC;
	}


}
