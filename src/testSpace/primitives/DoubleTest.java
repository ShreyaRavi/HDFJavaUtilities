package testSpace.primitives;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class DoubleTest implements HDF5Serializable {
	
	public double test;
	
	public DoubleTest() {
		
	}
	
	public DoubleTest(double val) {
		test = val;
	}
	
	public double getData() {
		return test;
	}

}
