package testSpace.vectors;

import java.util.Vector;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class LongVectorTest implements HDF5Serializable {

	public Vector<Long> test = new Vector<Long>();

	public LongVectorTest() {

	}

	public LongVectorTest(long x, long y, long z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}

	public Long[] getData() {
		Long[] dataArr = new Long[test.size()];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test.get(i);
		}
		return dataArr;
	}

}