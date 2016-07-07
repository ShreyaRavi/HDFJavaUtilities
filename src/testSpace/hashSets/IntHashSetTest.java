package testSpace.hashSets;

import java.util.HashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class IntHashSetTest implements HDF5Serializable {
	
	public HashSet<Integer> test = new HashSet<Integer>();
	
	public IntHashSetTest() {
		
	}
	
	public IntHashSetTest(int x, int y, int z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}
	
	public Integer[] getData() {
		Integer[] dataArr = new Integer[test.size()];
		Iterator<Integer> itr = test.iterator();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = itr.next();
		}
		return dataArr;
	}
	
}