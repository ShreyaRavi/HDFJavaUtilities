package testSpace.arrayLists;

import java.util.ArrayList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class BoolArrayListTest implements HDF5Serializable {

	public ArrayList<Boolean> test = new ArrayList<Boolean>();

	public BoolArrayListTest() {

	}

	public BoolArrayListTest(boolean x, boolean y, boolean z) {
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