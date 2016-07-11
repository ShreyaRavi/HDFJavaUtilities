package testSpace.linkedLists;

import java.util.LinkedList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class LongLinkedListTest implements HDF5Serializable {

	public LinkedList<Long> test = new LinkedList<Long>();

	public LongLinkedListTest() {

	}

	public LongLinkedListTest(long x, long y, long z) {
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