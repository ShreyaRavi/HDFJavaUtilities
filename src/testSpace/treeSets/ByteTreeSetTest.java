package testSpace.treeSets;

import java.util.TreeSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ByteTreeSetTest implements HDF5Serializable {
	
	public TreeSet<Byte> test = new TreeSet<Byte>();
	
	public ByteTreeSetTest() {
		
	}
	
	public ByteTreeSetTest(byte x, byte y, byte z) {
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