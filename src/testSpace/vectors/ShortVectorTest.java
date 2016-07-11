package testSpace.vectors;

import java.util.Vector;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ShortVectorTest implements HDF5Serializable {

	public Vector<Short> test = new Vector<Short>();

	public ShortVectorTest() {

	}

	public ShortVectorTest(short x, short y, short z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}

	public Short[] getData() {
		Short[] dataArr = new Short[test.size()];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test.get(i);
		}
		return dataArr;
	}

}