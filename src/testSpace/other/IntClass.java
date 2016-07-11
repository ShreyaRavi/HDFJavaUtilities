package testSpace.other;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class IntClass implements HDF5Serializable {
	
	public int x;
	
	public IntClass() {
		
	}
	
	public IntClass(int val) {
		x = val;
	}
	
	public int getData() {
		return x;
	}

}
