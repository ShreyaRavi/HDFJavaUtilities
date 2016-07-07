package testSpace.arrays;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class LongArrayTest implements HDF5Serializable {
	
	public long[] test = {};
	
	public LongArrayTest() {
		
	}
	
	public LongArrayTest(long ...vals) {
		test = vals;
	}
	
	public Long[] getData() {
		Long[] dataArr = new Long[test.length];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test[i];
		}
		return dataArr;
	}
	
}