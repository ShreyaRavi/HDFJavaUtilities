package testSpace.stacks;

import java.util.Stack;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class BoolStackTest implements HDF5Serializable {

	public Stack<Boolean> test = new Stack<Boolean>();

	public BoolStackTest() {

	}

	public BoolStackTest(boolean x, boolean y, boolean z) {
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