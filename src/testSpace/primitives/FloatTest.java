package testSpace.primitives;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class FloatTest implements HDF5Serializable {
	
	public float test;
	
	public FloatTest() {
		
	}
	
	public FloatTest(float val) {
		test = val;
	}
	
	public float getData() {
		return test;
	}

}