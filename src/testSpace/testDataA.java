package testSpace;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;
import HDFJavaUtils.interfaces.HDF5Serializable;
import HDFJavaUtils.interfaces.SerializeFieldOptions;

public class testDataA implements HDF5Serializable{
	public testDataB objectTest;
	public Integer integerTest;
	public long longTest;
	public double doubleTest;
	public float floatTest;
	private short shortTest;
	private char charTest;
	public Object[] arrayObjectArrayTest;
	public int[] intArrayTest = {1, 12, 53, 45, 76};
//	public List<Integer> listTest = new ArrayList<Integer>();
	public List<int[]> listArrayTest = new ArrayList<int[]>();
	public List<Character> listTest = new LinkedList<Character>();
	public Set<Character> setTest = new TreeSet<Character>();
	public Map<Boolean, Character> mapTest = new HashMap<Boolean, Character>();
	@SerializeFieldOptions(name = "SuperHard", path = "hidden")
	public Map<Object, Object> advancedMapTest = new HashMap<Object, Object>();
	public String stringTest;
	public byte byteTest;
	public boolean booleanTest;
	public Collection[] collectionArrayTest = new Collection[3];
	public testDataB[][] objectArrayTest = new testDataB[2][2];
	public boolean[][][] boolArrayTest = new boolean[4][2][3];
	public char[][] charArrayTest = new char[2][3];
	public int[][][][] multiArrTest = new int[4][3][2][2];
	public int hi;
	
	public testDataA(int val) {
		objectTest = new testDataB();
		arrayObjectArrayTest = new Object[3];
		integerTest = val;
		longTest = val;
		doubleTest = val;
		byteTest = 127;
		listTest.add('a');
		listTest.add('a');
		multiArrTest[0][0][0][0] = 55000;
		listArrayTest.add(new int[] {1, 2, 3});
		listArrayTest.add(new int[] {4, 5, 6});
		objectArrayTest[0][0] = new testDataB();
		objectArrayTest[0][0].test2 = "works";
		objectArrayTest[1][0] = new testDataB();
		objectArrayTest[1][0].test2 = "works twice";
		boolArrayTest[0][0][2] = true;
		floatTest = val;
		charTest = 'h';
		charArrayTest[0][0] = 'a';
		charArrayTest[0][1] = 'b';
		charArrayTest[0][2] = 'c';
		charArrayTest[1][0] = 'd';
		charArrayTest[1][1] = 'e';
		charArrayTest[1][2] = 'f';
		setTest.add('a');
		setTest.add('b');
		setTest.add('c');
		mapTest.put(true, 'p');
		mapTest.put(false, 'i');
		booleanTest = true;
		stringTest = "Hello World";
		shortTest = (short) val;
		collectionArrayTest[0] = listTest;
		collectionArrayTest[1] = setTest;
		collectionArrayTest[2] = listArrayTest;
		arrayObjectArrayTest[0] = objectArrayTest;
		arrayObjectArrayTest[1] = multiArrTest;
		arrayObjectArrayTest[2] = boolArrayTest;
		advancedMapTest.put(collectionArrayTest, arrayObjectArrayTest);
//		intArr = new int[3];
//		intArr[0] = 7;
//		intArr[1] = 1;
//		intArr[2] = 1;
		hi = 8;
	}
	
	public testDataA() {
	//	stringTest = "";
		booleanTest = false;
	}
	
	public String toString() {
		String returnString = "";
		System.out.println("Test Data");
		Class<?> objClass = this.getClass();
	    Field[] fields = objClass.getDeclaredFields();
	    for(Field field : fields) {
	    	try {
				returnString += " " + field.getName() + ": " + field.get(this) + "\n";
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
	    }
		return returnString;
	}
	
	private void writeObject(ObjectOutputStream oos) {
		oos.defaultWriteObject(this);
		ArrayList<Integer> test = new ArrayList<Integer>();
	}
	
	private void readObject(ObjectInputStream in) {
		in.defaultReadObject(this);
	}

}
