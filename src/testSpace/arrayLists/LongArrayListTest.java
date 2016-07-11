package testSpace.arrayLists;

import java.util.ArrayList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class LongArrayListTest implements HDF5Serializable {

	public ArrayList<Long> test = new ArrayList<Long>();

	public LongArrayListTest() {

	}

	public LongArrayListTest(long x, long y, long z) {
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