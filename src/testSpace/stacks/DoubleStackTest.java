package testSpace.stacks;

import java.util.Stack;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class DoubleStackTest implements HDF5Serializable {

	public Stack<Double> test = new Stack<Double>();

	public DoubleStackTest() {

	}

	public DoubleStackTest(double x, double y, double z) {
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