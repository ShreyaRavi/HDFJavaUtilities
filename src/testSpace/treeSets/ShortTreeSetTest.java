package testSpace.treeSets;

import java.util.TreeSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ShortTreeSetTest implements HDF5Serializable {
	
	public TreeSet<Short> test = new TreeSet<Short>();
	
	public ShortTreeSetTest() {
		
	}
	
	public ShortTreeSetTest(short x, short y, short z) {
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