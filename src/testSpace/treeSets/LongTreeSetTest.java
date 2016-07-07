package testSpace.treeSets;

import java.util.TreeSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class LongTreeSetTest implements HDF5Serializable {
	
	public TreeSet<Long> test = new TreeSet<Long>();
	
	public LongTreeSetTest() {
		
	}
	
	public LongTreeSetTest(long x, long y, long z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}
	
	public Long[] getData() {
		Long[] dataArr = new Long[test.size()];
		Iterator<Long> itr = test.iterator();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = itr.next();
		}
		return dataArr;
	}
	
}