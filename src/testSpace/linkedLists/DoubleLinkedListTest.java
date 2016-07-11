package testSpace.linkedLists;

import java.util.LinkedList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class DoubleLinkedListTest implements HDF5Serializable {

	public LinkedList<Double> test = new LinkedList<Double>();

	public DoubleLinkedListTest() {

	}

	public DoubleLinkedListTest(double x, double y, double z) {
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