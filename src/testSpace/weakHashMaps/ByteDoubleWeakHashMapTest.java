package testSpace.weakHashMaps;

import java.util.WeakHashMap;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ByteDoubleWeakHashMapTest implements HDF5Serializable {
	
	public WeakHashMap<Byte, Double> test = new WeakHashMap<Byte, Double>();
	
	public ByteDoubleWeakHashMapTest() {
		
	}
	
	public ByteDoubleWeakHashMapTest(byte keyX, double valX, byte keyY, double valY, byte keyZ, double valZ) {
		test.put(keyX, valX);
		test.put(keyY, valY);
		test.put(keyZ, valZ);
	}
	
	public Byte[] getKeyData() {
		Byte[] dataArr = new Byte[test.size()];
		Iterator<Byte> itr = test.keySet().iterator();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = itr.next();
		}
		return dataArr;
	}
	
	public Double[] getValData() {
		Double[] dataArr = new Double[test.size()];
		Byte[] keyArr = getKeyData();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = test.get(keyArr[i]);
		}
		return dataArr;
	}
	
}