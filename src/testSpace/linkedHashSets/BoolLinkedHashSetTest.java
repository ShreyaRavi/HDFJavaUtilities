package testSpace.linkedHashSets;

import java.util.LinkedHashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class BoolLinkedHashSetTest implements HDF5Serializable {
	
	public LinkedHashSet<Boolean> test = new LinkedHashSet<Boolean>();
	
	public BoolLinkedHashSetTest() {
		
	}
	
	public BoolLinkedHashSetTest(boolean x, boolean y, boolean z) {
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