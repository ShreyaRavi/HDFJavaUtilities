package testSpace.hashSets;

import java.util.HashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class DoubleHashSetTest implements HDF5Serializable {
	
	public HashSet<Double> test = new HashSet<Double>();
	
	public DoubleHashSetTest() {
		
	}
	
	public DoubleHashSetTest(double x, double y, double z) {
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