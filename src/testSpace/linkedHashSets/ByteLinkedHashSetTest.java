package testSpace.linkedHashSets;

import java.util.LinkedHashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ByteLinkedHashSetTest implements HDF5Serializable {
	
	public LinkedHashSet<Byte> test = new LinkedHashSet<Byte>();
	
	public ByteLinkedHashSetTest() {
		
	}
	
	public ByteLinkedHashSetTest(byte x, byte y, byte z) {
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