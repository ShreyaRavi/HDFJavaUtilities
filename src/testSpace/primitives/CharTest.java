package testSpace.primitives;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class CharTest implements HDF5Serializable {
	
	public char test;
	
	public CharTest() {
		
	}
	
	public CharTest(char val) {
		test = val;
	}
	
	public char getData() {
		return test;
	}

}
