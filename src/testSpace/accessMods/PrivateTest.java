package testSpace.accessMods;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class PrivateTest implements HDF5Serializable {
	
	private int test;
	
	public PrivateTest() {
		
	}
	
	public PrivateTest(int val) {
		test = val;
	}
	
	public int getData() {
		return test;
	}
	
}
