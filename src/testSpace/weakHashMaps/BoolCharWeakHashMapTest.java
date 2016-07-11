package testSpace.weakHashMaps;

import java.util.WeakHashMap;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class BoolCharWeakHashMapTest implements HDF5Serializable {
	
	public WeakHashMap<Boolean, Character> test = new WeakHashMap<Boolean, Character>();
	
	public BoolCharWeakHashMapTest() {
		
	}
	
	public BoolCharWeakHashMapTest(boolean keyX, char valX, boolean keyY, char valY, boolean keyZ, char valZ) {
		test.put(keyX, valX);
		test.put(keyY, valY);
		test.put(keyZ, valZ);
	}
	
	public Boolean[] getKeyData() {
		Boolean[] dataArr = new Boolean[test.size()];
		Iterator<Boolean> itr = test.keySet().iterator();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = itr.next();
		}
		return dataArr;
	}
	
	public Character[] getValData() {
		Character[] dataArr = new Character[test.size()];
		Boolean[] keyArr = getKeyData();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test.get(keyArr[i]);
		}
		return dataArr;
	}
	
}