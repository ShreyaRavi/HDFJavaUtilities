package testSpace.hashMaps;

import java.util.HashMap;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class FloatIntHashMapTest implements HDF5Serializable {
	
	public HashMap<Float, Integer> test = new HashMap<Float, Integer>();
	
	public FloatIntHashMapTest() {
		
	}
	
	public FloatIntHashMapTest(float keyX, int valX, float keyY, int valY, float keyZ, int valZ) {
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