package testSpace.stacks;

import java.util.Stack;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class CharStackTest implements HDF5Serializable {

	public Stack<Character> test = new Stack<Character>();

	public CharStackTest() {

	}

	public CharStackTest(char x, char y, char z) {
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