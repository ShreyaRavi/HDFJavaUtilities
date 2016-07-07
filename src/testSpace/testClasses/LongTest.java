package testSpace.testClasses;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class LongTest implements HDF5Serializable {
	
	public long test;
	
	public LongTest() {
		
	}
	
	public LongTest(long val) {
		test = val;
	}
	
	public long getData() {
		return test;
	}

}