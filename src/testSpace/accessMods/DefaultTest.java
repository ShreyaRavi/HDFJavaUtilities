package testSpace.accessMods;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class DefaultTest implements HDF5Serializable {
	
	int test;
	
	public DefaultTest() {
		
	}
	
	public DefaultTest(int val) {
		test = val;
	}
	
	public int getData() {
		return test;
	}
	
}
