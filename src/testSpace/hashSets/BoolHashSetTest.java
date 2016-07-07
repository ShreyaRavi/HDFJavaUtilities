package testSpace.hashSets;

import java.util.HashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class BoolHashSetTest implements HDF5Serializable {
	
	public HashSet<Boolean> test = new HashSet<Boolean>();
	
	public BoolHashSetTest() {
		
	}
	
	public BoolHashSetTest(boolean x, boolean y, boolean z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}
	
	public Boolean[] getData() {
		Boolean[] dataArr = new Boolean[test.size()];
		Iterator<Boolean> itr = test.iterator();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = itr.next();
		}
		return dataArr;
	}
	
}