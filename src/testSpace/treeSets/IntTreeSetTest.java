package testSpace.treeSets;

import java.util.TreeSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class IntTreeSetTest implements HDF5Serializable {
	
	public TreeSet<Integer> test = new TreeSet<Integer>();
	
	public IntTreeSetTest() {
		
	}
	
	public IntTreeSetTest(int x, int y, int z) {
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