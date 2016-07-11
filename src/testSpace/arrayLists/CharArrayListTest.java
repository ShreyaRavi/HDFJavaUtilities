package testSpace.arrayLists;

import java.util.ArrayList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class CharArrayListTest implements HDF5Serializable {

	public ArrayList<Character> test = new ArrayList<Character>();

	public CharArrayListTest() {

	}

	public CharArrayListTest(char x, char y, char z) {
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