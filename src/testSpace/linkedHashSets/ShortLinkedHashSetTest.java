package testSpace.linkedHashSets;

import java.util.LinkedHashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ShortLinkedHashSetTest implements HDF5Serializable {
	
	public LinkedHashSet<Short> test = new LinkedHashSet<Short>();
	
	public ShortLinkedHashSetTest() {
		
	}
	
	public ShortLinkedHashSetTest(short x, short y, short z) {
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