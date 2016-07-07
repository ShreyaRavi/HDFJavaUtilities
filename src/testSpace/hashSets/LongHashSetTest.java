package testSpace.hashSets;

import java.util.HashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class LongHashSetTest implements HDF5Serializable {
	
	public HashSet<Long> test = new HashSet<Long>();
	
	public LongHashSetTest() {
		
	}
	
	public LongHashSetTest(long x, long y, long z) {
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