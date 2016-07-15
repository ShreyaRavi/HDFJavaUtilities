package testSpace.arrays;

import HDFJavaUtils.interfaces.HDF5Serializable;
import testSpace.other.IntClass;

public class IntClassObjTest implements HDF5Serializable {
	
	public IntClass[] test = {};
	
	public IntClassObjTest() {
		
	}
	
	public IntClassObjTest(int ...vals) {
		test = new IntClass[vals.length];
		for (int i = 0; i < vals.length; i++) {
			test[i] = new IntClass(vals[i]);
		}
	}
	
	public Integer[] getData() {
		Integer[] dataArr = new Integer[test.length];
		for (int i = 0; i < test.length; i++) {
			dataArr[i] = (Integer)test[i].getData();
		}
		return dataArr;
	}
	
}