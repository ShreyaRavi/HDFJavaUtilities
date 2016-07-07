package testSpace.linkedHashSets;

import java.util.LinkedHashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class DoubleLinkedHashSetTest implements HDF5Serializable {
	
	public LinkedHashSet<Double> test = new LinkedHashSet<Double>();
	
	public DoubleLinkedHashSetTest() {
		
	}
	
	public DoubleLinkedHashSetTest(double x, double y, double z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}
	
	public Double[] getData() {
		Double[] dataArr = new Double[test.size()];
		Iterator<Double> itr = test.iterator();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = itr.next();
		}
		return dataArr;
	}
	
}