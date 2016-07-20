package HDFJavaUtils;

public enum SubsetSelect {
	
	/**
	 * Deselects everything previously selected and selects the indicated subset.
	 * Does not allow for overlapping blocks to be selected.
	 */
	SET,
	
	/**
	 * Deselects everything in the indicated subset
	 */
	NOTB,
	
	/**
	 * Selects the indicated subset and deselects everything previously selected
	 */
	NOTA,
	
	/**
	 * Selects all elements of the indicated subset and previously selected subset
	 */
	OR,
	
	/**
	 * Selects all elements that are in both the indicated subset and previously selected subset
	 */
	AND,
	
	/**
	 * Selects all elements that are in both the indicated subset and previously selected subset, but not both
	 */
	XOR	
}
