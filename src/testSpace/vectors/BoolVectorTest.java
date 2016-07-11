package testSpace.vectors;

import java.util.Vector;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class BoolVectorTest implements HDF5Serializable {

	public Vector<Boolean> test = new Vector<Boolean>();

	public BoolVectorTest() {

	}

	public BoolVectorTest(boolean x, boolean y, boolean z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}

	public Boolean[] getData() {
		Boolean[] dataArr = new Boolean[test.size()];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test.get(i);
		}
		return dataArr;
	}

}