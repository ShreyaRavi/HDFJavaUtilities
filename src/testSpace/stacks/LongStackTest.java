package testSpace.stacks;

import java.util.Stack;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class LongStackTest implements HDF5Serializable {

	public Stack<Long> test = new Stack<Long>();

	public LongStackTest() {

	}

	public LongStackTest(long x, long y, long z) {
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