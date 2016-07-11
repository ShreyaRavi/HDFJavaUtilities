package testSpace.linkedLists;

import java.util.LinkedList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class BoolLinkedListTest implements HDF5Serializable {

	public LinkedList<Boolean> test = new LinkedList<Boolean>();

	public BoolLinkedListTest() {

	}

	public BoolLinkedListTest(boolean x, boolean y, boolean z) {
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