package testSpace.treeSets;

import java.util.TreeSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class DoubleTreeSetTest implements HDF5Serializable {
	
	public TreeSet<Double> test = new TreeSet<Double>();
	
	public DoubleTreeSetTest() {
		
	}
	
	public DoubleTreeSetTest(double x, double y, double z) {
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