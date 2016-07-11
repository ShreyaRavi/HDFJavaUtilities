package testSpace.linkedLists;

import java.util.LinkedList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ShortLinkedListTest implements HDF5Serializable {

	public LinkedList<Short> test = new LinkedList<Short>();

	public ShortLinkedListTest() {

	}

	public ShortLinkedListTest(short x, short y, short z) {
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