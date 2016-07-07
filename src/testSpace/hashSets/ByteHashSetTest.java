package testSpace.hashSets;

import java.util.HashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ByteHashSetTest implements HDF5Serializable {
	
	public HashSet<Byte> test = new HashSet<Byte>();
	
	public ByteHashSetTest() {
		
	}
	
	public ByteHashSetTest(byte x, byte y, byte z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}
	
	public Byte[] getData() {
		Byte[] dataArr = new Byte[test.size()];
		Iterator<Byte> itr = test.iterator();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = itr.next();
		}
		return dataArr;
	}
	
}