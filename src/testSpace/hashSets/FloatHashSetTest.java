package testSpace.hashSets;

import java.util.HashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class FloatHashSetTest implements HDF5Serializable {
	
	public HashSet<Float> test = new HashSet<Float>();
	
	public FloatHashSetTest() {
		
	}
	
	public FloatHashSetTest(float x, float y, float z) {
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