package testSpace.treeSets;

import java.util.TreeSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class BoolTreeSetTest implements HDF5Serializable {
	
	public TreeSet<Boolean> test = new TreeSet<Boolean>();
	
	public BoolTreeSetTest() {
		
	}
	
	public BoolTreeSetTest(boolean x, boolean y, boolean z) {
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