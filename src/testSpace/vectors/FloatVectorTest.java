package testSpace.vectors;

import java.util.Vector;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class FloatVectorTest implements HDF5Serializable {

	public Vector<Float> test = new Vector<Float>();

	public FloatVectorTest() {

	}

	public FloatVectorTest(float x, float y, float z) {
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