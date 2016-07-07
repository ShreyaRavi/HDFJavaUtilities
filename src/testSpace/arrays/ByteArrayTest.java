package testSpace.arrays;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ByteArrayTest implements HDF5Serializable {
	
	public byte[] test = {};
	
	public ByteArrayTest() {
		
	}
	
	public ByteArrayTest(byte ...vals) {
		test = vals;
	}
	
	public Byte[] getData() {
		Byte[] dataArr = new Byte[test.length];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test[i];
		}
		return dataArr;
	}
	
}