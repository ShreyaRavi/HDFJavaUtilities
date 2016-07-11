package testSpace.arrayLists;

import java.util.ArrayList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class FloatArrayListTest implements HDF5Serializable {

	public ArrayList<Float> test = new ArrayList<Float>();

	public FloatArrayListTest() {

	}

	public FloatArrayListTest(float x, float y, float z) {
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