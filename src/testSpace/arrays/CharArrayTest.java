package testSpace.arrays;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class CharArrayTest implements HDF5Serializable {
	
	public char[] test = {};
	
	public CharArrayTest() {
		
	}
	
	public CharArrayTest(char ...vals) {
		test = vals;
	}
	
	public Character[] getData() {
		Character[] dataArr = new Character[test.length];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test[i];
		}
		return dataArr;
	}
	
}