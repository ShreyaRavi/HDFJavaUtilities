package testSpace.arrays;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class IntObjTest implements HDF5Serializable {
	
	public Integer[] test = {};
	
	public IntObjTest() {
		
	}
	
	public IntObjTest(Integer ...vals) {
		test = vals;
	}
	
	public Integer[] getData() {
		return test;
	}
	
}