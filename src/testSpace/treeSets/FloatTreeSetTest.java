package testSpace.treeSets;

import java.util.TreeSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class FloatTreeSetTest implements HDF5Serializable {
	
	public TreeSet<Float> test = new TreeSet<Float>();
	
	public FloatTreeSetTest() {
		
	}
	
	public FloatTreeSetTest(float x, float y, float z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}
	
	public Float[] getData() {
		Float[] dataArr = new Float[test.size()];
		Iterator<Float> itr = test.iterator();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = itr.next();
		}
		return dataArr;
	}
	
}