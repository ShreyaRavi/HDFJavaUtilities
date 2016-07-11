package testSpace.linkedLists;

import java.util.LinkedList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class FloatLinkedListTest implements HDF5Serializable {

	public LinkedList<Float> test = new LinkedList<Float>();

	public FloatLinkedListTest() {

	}

	public FloatLinkedListTest(float x, float y, float z) {
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