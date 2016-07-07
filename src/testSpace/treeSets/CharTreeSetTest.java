package testSpace.treeSets;

import java.util.TreeSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class CharTreeSetTest implements HDF5Serializable {
	
	public TreeSet<Character> test = new TreeSet<Character>();
	
	public CharTreeSetTest() {
		
	}
	
	public CharTreeSetTest(char x, char y, char z) {
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