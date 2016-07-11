package testSpace.arrayLists;

import java.util.ArrayList;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ByteArrayListTest implements HDF5Serializable {

	public ArrayList<Byte> test = new ArrayList<Byte>();

	public ByteArrayListTest() {

	}

	public ByteArrayListTest(byte x, byte y, byte z) {
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