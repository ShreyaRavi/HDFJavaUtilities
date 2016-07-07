package testSpace.linkedHashSets;

import java.util.LinkedHashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class LongLinkedHashSetTest implements HDF5Serializable {
	
	public LinkedHashSet<Long> test = new LinkedHashSet<Long>();
	
	public LongLinkedHashSetTest() {
		
	}
	
	public LongLinkedHashSetTest(long x, long y, long z) {
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