package testSpace.hashtables;

import java.util.Hashtable;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class FloatIntHashtableTest implements HDF5Serializable {
	
	public Hashtable<Float, Integer> test = new Hashtable<Float, Integer>();
	
	public FloatIntHashtableTest() {
		
	}
	
	public FloatIntHashtableTest(float keyX, int valX, float keyY, int valY, float keyZ, int valZ) {
		test.put(keyX, valX);
		test.put(keyY, valY);
		test.put(keyZ, valZ);
	}
	
	public Float[] getKeyData() {
		Float[] dataArr = new Float[test.size()];
		Iterator<Float> itr = test.keySet().iterator();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = itr.next();
		}
		return dataArr;
	}
	
	public Integer[] getValData() {
		Integer[] dataArr = new Integer[test.size()];
		Float[] keyArr = getKeyData();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test.get(keyArr[i]);
		}
		return dataArr;
	}
	
}