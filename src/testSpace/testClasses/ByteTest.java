package testSpace.testClasses;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ByteTest implements HDF5Serializable {
	
	public byte test;
	
	public ByteTest() {
		
	}
	
	public ByteTest(byte val) {
		test = val;
	}
	
	public byte getData() {
		return test;
	}

}
