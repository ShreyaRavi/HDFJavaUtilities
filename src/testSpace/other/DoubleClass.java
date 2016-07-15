package testSpace.other;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class DoubleClass implements HDF5Serializable {
	
	private double x;
	private double y;
	private IntClass obj;
	
	public DoubleClass() {
		obj = new IntClass();
	}
	
	public DoubleClass(double val, double val2, int val3) {
		x = val;
		y = val2;
		obj = new IntClass(val3);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getObjData() {
		return obj.getData();
	}

}
