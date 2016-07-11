package testSpace.stacks;

import java.util.Stack;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class FloatStackTest implements HDF5Serializable {

	public Stack<Float> test = new Stack<Float>();

	public FloatStackTest() {

	}

	public FloatStackTest(float x, float y, float z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}

	public Float[] getData() {
		Float[] dataArr = new Float[test.size()];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test.get(i);
		}
		return dataArr;
	}

}