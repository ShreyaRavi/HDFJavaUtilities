package testSpace.stacks;

import java.util.Stack;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ShortStackTest implements HDF5Serializable {

	public Stack<Short> test = new Stack<Short>();

	public ShortStackTest() {

	}

	public ShortStackTest(short x, short y, short z) {
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