package testSpace.primitives;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ShortTest implements HDF5Serializable {
	
	public short test;
	
	public ShortTest() {
		
	}
	
	public ShortTest(short val) {
		test = val;
	}
	
	public short getData() {
		return test;
	}

}