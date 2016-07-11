package testSpace.linkedLists;

import java.util.LinkedList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class IntLinkedListTest implements HDF5Serializable {

	public LinkedList<Integer> test = new LinkedList<Integer>();

	public IntLinkedListTest() {

	}

	public IntLinkedListTest(int x, int y, int z) {
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