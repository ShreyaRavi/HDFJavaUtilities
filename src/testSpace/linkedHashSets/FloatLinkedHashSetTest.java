package testSpace.linkedHashSets;

import java.util.LinkedHashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class FloatLinkedHashSetTest implements HDF5Serializable {
	
	public LinkedHashSet<Float> test = new LinkedHashSet<Float>();
	
	public FloatLinkedHashSetTest() {
		
	}
	
	public FloatLinkedHashSetTest(float x, float y, float z) {
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