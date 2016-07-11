package testSpace.hashtables;

import java.util.Hashtable;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class LongShortHashtableTest implements HDF5Serializable {
	
	public Hashtable<Long, Short> test = new Hashtable<Long, Short>();
	
	public LongShortHashtableTest() {
		
	}
	
	public LongShortHashtableTest(long keyX, short valX, long keyY, short valY, long keyZ, short valZ) {
		test.put(keyX, valX);
		test.put(keyY, valY);
		test.put(keyZ, valZ);
	}
	
	public Long[] getKeyData() {
		Long[] dataArr = new Long[test.size()];
		Iterator<Long> itr = test.keySet().iterator();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = itr.next();
		}
		return dataArr;
	}
	
	public Short[] getValData() {
		Short[] dataArr = new Short[test.size()];
		Long[] keyArr = getKeyData();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test.get(keyArr[i]);
		}
		return dataArr;
	}
	
}