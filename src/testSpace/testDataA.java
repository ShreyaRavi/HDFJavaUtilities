package testSpace;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import HDFJavaUtils.interfaces.SerializeClassOptions;
import HDFJavaUtils.interfaces.SerializeFieldOptions;
import HDFJavaUtils.interfaces.HDF5Serializable;

public class testDataA implements HDF5Serializable{
	public int integerTest;
	public long longTest;
	public double doubleTest;
	public float floatTest;
	private short shortTest;
	private char charTest;
	public int[] intArrayTest = {1, 12, 53, 45, 76};
//	public List<Integer> listTest = new ArrayList<Integer>();
	public List<int[]> listArrayTest = new ArrayList<int[]>();
	public List<Character> listTest = new ArrayList<Character>();
	public Set<Integer> setTest = new TreeSet<Integer>();
	public Map<Integer, Double> mapTest = new HashMap<Integer, Double>();
	public transient Map<Object, Object> advancedMapTest = new HashMap<Object, Object>();
	public String stringTest;
	public byte byteTest;
	public boolean booleanTest;
//	Uninitialized instance variables won't be read correctly
//		NullPointerException will be thrown because field.get(obj) will return null
//	public int[] intArr;
	public Collection[] collectionArrayTest = new Collection[3];
	public Object[] arrayObjectArrayTest = new Object[3];
	public testDataB[][] objectArrayTest = new testDataB[2][2];
	public boolean[][][] boolArrayTest = new boolean[4][2][3];
	public char[][] charArrayTest = new char[2][3];
	public int[][][][] multiArrTest = new int[4][3][2][2];
	public int hi;
	
	public testDataA(int val) {
		integerTest = val;
		longTest = val;
		doubleTest = val;
		byteTest = 127;
		listTest.add('a');
		listTest.add('d');
		listArrayTest.add(new int[] {1, 2, 3});
		listArrayTest.add(new int[] {4, 5, 6});
		listArrayTest.set(0, null);
		objectArrayTest[0][0] = new testDataB();
		objectArrayTest[0][0].test2 = "works";
		objectArrayTest[1][0] = new testDataB();
		objectArrayTest[1][0].test2 = "works twice";
		boolArrayTest[0][0][2] = true;
		floatTest = val;
		multiArrTest[0][0][0][0] = 55000;
		charTest = 'h';
		charArrayTest[0][0] = 'a';
		charArrayTest[0][1] = 'b';
		charArrayTest[0][2] = 'c';
		charArrayTest[1][0] = 'd';
		charArrayTest[1][1] = 'e';
		charArrayTest[1][2] = 'f';
		setTest.add(3);
		setTest.add(41);
		setTest.add(11);
		mapTest.put(2, 4.0);
		mapTest.put(3, 3.141592);
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
		stringTest = "";
		booleanTest = false;
	}
	
	public String toString() {
		String returnString = "";
		System.out.println("Test Data");
		Class<?> objClass = this.getClass();
	    Field[] fields = objClass.getDeclaredFields();
	    for(Field field : fields) {
	    	try {
				returnString += " " + field.getType() + ": " + field.get(this) + "\n";
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
	    }
		return returnString;
	}

}
