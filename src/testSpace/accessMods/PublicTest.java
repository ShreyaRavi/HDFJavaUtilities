package testSpace.accessMods;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class PublicTest implements HDF5Serializable {
	
	public int test;
	
	public PublicTest() {
		
	}
	
	public PublicTest(int val) {
		test = val;
	}
	
	public int getData() {
		return test;
	}
	
}
