package testSpace.vectors;

import java.util.Vector;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ByteVectorTest implements HDF5Serializable {

	public Vector<Byte> test = new Vector<Byte>();

	public ByteVectorTest() {

	}

	public ByteVectorTest(byte x, byte y, byte z) {
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