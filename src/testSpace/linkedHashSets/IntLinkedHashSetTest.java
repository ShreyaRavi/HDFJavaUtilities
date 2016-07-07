package testSpace.linkedHashSets;

import java.util.LinkedHashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class IntLinkedHashSetTest implements HDF5Serializable {
	
	public LinkedHashSet<Integer> test = new LinkedHashSet<Integer>();
	
	public IntLinkedHashSetTest() {
		
	}
	
	public IntLinkedHashSetTest(int x, int y, int z) {
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