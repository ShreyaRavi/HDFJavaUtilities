package testSpace.primitives;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class IntTest implements HDF5Serializable {
	
	public int test;
	
	public IntTest() {
		
	}
	
	public IntTest(int val) {
		test = val;
	}
	
	public int getData() {
		return test;
	}
	
}
