package testSpace.arrays;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class BoolArrayTest implements HDF5Serializable {
	
	public boolean[] test = {};
	
	public BoolArrayTest() {
		
	}
	
	public BoolArrayTest(boolean ...vals) {
		test = vals;
	}
	
	public Boolean[] getData() {
		Boolean[] dataArr = new Boolean[test.length];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test[i];
		}
		return dataArr;
	}
	
}