package testSpace.vectors;

import java.util.Vector;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class CharVectorTest implements HDF5Serializable {

	public Vector<Character> test = new Vector<Character>();

	public CharVectorTest() {

	}

	public CharVectorTest(char x, char y, char z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}

	public Character[] getData() {
		Character[] dataArr = new Character[test.size()];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test.get(i);
		}
		return dataArr;
	}

}