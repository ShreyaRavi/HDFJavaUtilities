package testSpace.linkedHashSets;

import java.util.LinkedHashSet;
import java.util.Iterator;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class CharLinkedHashSetTest implements HDF5Serializable {
	
	public LinkedHashSet<Character> test = new LinkedHashSet<Character>();
	
	public CharLinkedHashSetTest() {
		
	}
	
	public CharLinkedHashSetTest(char x, char y, char z) {
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