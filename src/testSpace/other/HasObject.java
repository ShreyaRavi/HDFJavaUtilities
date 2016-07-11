package testSpace.other;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class HasObject implements HDF5Serializable{
	
	public int test;	
	public IntClass obj;
	
	public HasObject() {
		obj = new IntClass();
	}
	
	public HasObject(int val) {
		test = val;
		obj = new IntClass(val);
	}

	public int getDataFromClass() {
		return test;
	}
	
	public int getDataFromObject() {
		return obj.getData();
	}
	
}
