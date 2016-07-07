package testSpace.arrays;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class IntArrayTest implements HDF5Serializable {
	
	public int[] test = {};
	
	public IntArrayTest() {
		
	}
	
	public IntArrayTest(int ...vals) {
		test = vals;
	}
	
	public Integer[] getData() {
		Integer[] dataArr = new Integer[test.length];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test[i];
		}
		return dataArr;
	}
	
}