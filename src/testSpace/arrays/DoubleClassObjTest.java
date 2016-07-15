package testSpace.arrays;

import HDFJavaUtils.interfaces.HDF5Serializable;
import testSpace.other.DoubleClass;

public class DoubleClassObjTest implements HDF5Serializable {
	
	public DoubleClass[] test = {};
	
	public DoubleClassObjTest() {
		
	}
	
	public DoubleClassObjTest(double val1, double val2, int val3, double val4,
			double val5, int val6, double val7, double val8, int val9) {
		test = new DoubleClass[3];
		test[0] = new DoubleClass(val1,val2,val3);
		test[1] = new DoubleClass(val4,val5,val6);
		test[2] = new DoubleClass(val7,val8,val9);
	}
	
	public Double[] getXs() {
		Double[] data = new Double[test.length];
		for (int i = 0; i < test.length; i++) {
			data[i] = (Double)test[i].getX();
		}
		return data;
	}
	
	public Double[] getYs() {
		Double[] data = new Double[test.length];
		for (int i = 0; i < test.length; i++) {
			data[i] = (Double)test[i].getY();
		}
		return data;
	}
	
	public Integer[] getObjsData() {
		Integer[] data = new Integer[test.length];
		for (int i = 0; i < test.length; i++) {
			data[i] = (Integer)test[i].getObjData();
		}
		return data;
	}
	
}