package testSpace.arrayLists;

import java.util.ArrayList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class IntArrayListTest implements HDF5Serializable {

	public ArrayList<Integer> test = new ArrayList<Integer>();

	public IntArrayListTest() {

	}

	public IntArrayListTest(int x, int y, int z) {
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