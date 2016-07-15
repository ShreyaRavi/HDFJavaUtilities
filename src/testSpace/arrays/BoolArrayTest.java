package testSpace.arrays;

import HDFJavaUtils.interfaces.SerializeFieldOptions;
import HDFJavaUtils.interfaces.HDF5Serializable;

public class BoolArrayTest implements HDF5Serializable {
	
	
	@SerializeFieldOptions()
	public boolean[] testBool = {};
	
	public BoolArrayTest() {
		
	}
	
	public BoolArrayTest(boolean ...vals) {
		testBool = vals;
	}
	
	public Boolean[] getData() {
		Boolean[] dataArr = new Boolean[testBool.length];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = testBool[i];
		}
		return dataArr;
	}
	
}