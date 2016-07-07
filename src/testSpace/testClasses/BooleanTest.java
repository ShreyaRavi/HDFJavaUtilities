package testSpace.testClasses;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class BooleanTest implements HDF5Serializable {
	
	public boolean test;
	
	public BooleanTest() {
		
	}
	
	public BooleanTest(boolean val) {
		test = val;
	}
	
	public boolean getData() {
		return test;
	}

}
