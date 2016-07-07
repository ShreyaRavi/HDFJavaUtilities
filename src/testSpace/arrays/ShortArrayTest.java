package testSpace.arrays;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ShortArrayTest implements HDF5Serializable {
	
	public short[] test = {};
	
	public ShortArrayTest() {
		
	}
	
	public ShortArrayTest(short ...vals) {
		test = vals;
	}
	
	public Short[] getData() {
		Short[] dataArr = new Short[test.length];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test[i];
		}
		return dataArr;
	}
	
}