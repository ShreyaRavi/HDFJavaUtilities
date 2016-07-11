package testSpace.arrayLists;

import java.util.ArrayList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ShortArrayListTest implements HDF5Serializable {

	public ArrayList<Short> test = new ArrayList<Short>();

	public ShortArrayListTest() {

	}

	public ShortArrayListTest(short x, short y, short z) {
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