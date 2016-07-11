package testSpace.stacks;

import java.util.Stack;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class IntStackTest implements HDF5Serializable {

	public Stack<Integer> test = new Stack<Integer>();

	public IntStackTest() {

	}

	public IntStackTest(int x, int y, int z) {
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