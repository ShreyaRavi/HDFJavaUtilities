package testSpace.arrays;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class DoubleArrayTest implements HDF5Serializable {
	
	public double[] test = {};
	
	public DoubleArrayTest() {
		
	}
	
	public DoubleArrayTest(double ...vals) {
		test = vals;
	}
	
	public Double[] getData() {
		Double[] dataArr = new Double[test.length];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test[i];
		}
		return dataArr;
	}
	
}