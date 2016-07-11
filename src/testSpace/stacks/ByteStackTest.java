package testSpace.stacks;

import java.util.Stack;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ByteStackTest implements HDF5Serializable {

	public Stack<Byte> test = new Stack<Byte>();

	public ByteStackTest() {

	}

	public ByteStackTest(byte x, byte y, byte z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}

	public Byte[] getData() {
		Byte[] dataArr = new Byte[test.size()];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test.get(i);
		}
		return dataArr;
	}

}