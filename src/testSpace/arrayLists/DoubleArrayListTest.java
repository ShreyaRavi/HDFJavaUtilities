package testSpace.arrayLists;

import java.util.ArrayList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class DoubleArrayListTest implements HDF5Serializable {

	public ArrayList<Double> test = new ArrayList<Double>();

	public DoubleArrayListTest() {

	}

	public DoubleArrayListTest(double x, double y, double z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}

	public Double[] getData() {
		Double[] dataArr = new Double[test.size()];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test.get(i);
		}
		return dataArr;
	}

}