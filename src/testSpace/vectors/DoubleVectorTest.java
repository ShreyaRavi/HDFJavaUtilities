package testSpace.vectors;

import java.util.Vector;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class DoubleVectorTest implements HDF5Serializable {

	public Vector<Double> test = new Vector<Double>();

	public DoubleVectorTest() {

	}

	public DoubleVectorTest(double x, double y, double z) {
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