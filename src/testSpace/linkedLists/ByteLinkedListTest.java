package testSpace.linkedLists;

import java.util.LinkedList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ByteLinkedListTest implements HDF5Serializable {

	public LinkedList<Byte> test = new LinkedList<Byte>();

	public ByteLinkedListTest() {

	}

	public ByteLinkedListTest(byte x, byte y, byte z) {
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