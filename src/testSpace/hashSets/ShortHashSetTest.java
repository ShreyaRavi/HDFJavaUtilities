package testSpace.hashSets;

import java.util.HashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ShortHashSetTest implements HDF5Serializable {
	
	public HashSet<Short> test = new HashSet<Short>();
	
	public ShortHashSetTest() {
		
	}
	
	public ShortHashSetTest(short x, short y, short z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}
	
	public Short[] getData() {
		Short[] dataArr = new Short[test.size()];
		Iterator<Short> itr = test.iterator();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = itr.next();
		}
		return dataArr;
	}
	
}