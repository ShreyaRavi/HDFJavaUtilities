package testSpace.accessMods;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ProtectedTest implements HDF5Serializable {
	
	protected int test;
	
	public ProtectedTest() {
		
	}
	
	public ProtectedTest(int val) {
		test = val;
	}
	
	public int getData() {
		return test;
	}
	
}
