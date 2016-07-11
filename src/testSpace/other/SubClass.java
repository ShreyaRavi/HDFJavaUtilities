package testSpace.other;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class SubClass extends SuperClass implements HDF5Serializable {
	
	private int testFromSub;
	
	public SubClass() {
		
	}
	
	public SubClass(int val) {
		testFromSub = val;
		testFromSuper = val;
	}
	
	public int[] getDataFromBoth() {
		int[] data = {testFromSub,testFromSuper};
		return data;
	}

}
