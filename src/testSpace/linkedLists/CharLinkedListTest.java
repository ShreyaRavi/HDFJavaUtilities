package testSpace.linkedLists;

import java.util.LinkedList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class CharLinkedListTest implements HDF5Serializable {

	public LinkedList<Character> test = new LinkedList<Character>();

	public CharLinkedListTest() {

	}

	public CharLinkedListTest(char x, char y, char z) {
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