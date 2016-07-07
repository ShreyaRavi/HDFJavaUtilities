package testSpace.hashSets;

import java.util.HashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class CharHashSetTest implements HDF5Serializable {
	
	public HashSet<Character> test = new HashSet<Character>();
	
	public CharHashSetTest() {
		
	}
	
	public CharHashSetTest(char x, char y, char z) {
		test.add(x);
		test.add(y);
		test.add(z);
	}
	
	public Character[] getData() {
		Character[] dataArr = new Character[test.size()];
		Iterator<Character> itr = test.iterator();
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = itr.next();
		}
		return dataArr;
	}
	
}