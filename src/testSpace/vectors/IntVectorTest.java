package testSpace.vectors;

import java.util.Vector;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class IntVectorTest implements HDF5Serializable {

	public Vector<Integer> test = new Vector<Integer>();

	public IntVectorTest() {

	}

	public IntVectorTest(int x, int y, int z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}

	public Integer[] getData() {
		Integer[] dataArr = new Integer[test.size()];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test.get(i);
		}
		return dataArr;
	}

}