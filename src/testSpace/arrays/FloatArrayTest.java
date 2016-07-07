package testSpace.arrays;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class FloatArrayTest implements HDF5Serializable {
	
	public float[] test = {};
	
	public FloatArrayTest() {
		
	}
	
	public FloatArrayTest(float ...vals) {
		test = vals;
	}
	
	public Float[] getData() {
		Float[] dataArr = new Float[test.length];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test[i];
		}
		return dataArr;
	}
	
}