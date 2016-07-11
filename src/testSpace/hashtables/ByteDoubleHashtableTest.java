package testSpace.hashtables;

import java.util.Hashtable;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class ByteDoubleHashtableTest implements HDF5Serializable {
	
	public Hashtable<Byte, Double> test = new Hashtable<Byte, Double>();
	
	public ByteDoubleHashtableTest() {
		
	}
	
	public ByteDoubleHashtableTest(byte keyX, double valX, byte keyY, double valY, byte keyZ, double valZ) {
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