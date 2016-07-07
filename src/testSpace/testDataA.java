package testSpace;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import HDFJavaUtils.annotations.SerializeClassOptions;
import HDFJavaUtils.annotations.SerializeFieldOptions;
import HDFJavaUtils.annotations.SerializeOptions;
import HDFJavaUtils.interfaces.HDF5Serializable;

@SerializeClassOptions(path = "Hello", name = "Different Name")
public class testDataA implements HDF5Serializable{
	public int integerTest;
	public long longTest;
	public double doubleTest;
	public float floatTest;
	private short shortTest;
	public char charTest;
	public int[] intArrayTest = {1, 12, 53, 45, 76};
	public List<Integer> listTest = new LinkedList<Integer>();
	public Set<Integer> setTest = new TreeSet<Integer>();
	public Map<Integer, Double> mapTest = new HashMap<Integer, Double>();
	public String stringTest;
	public byte byteTest;
	public boolean booleanTest;
	public testDataB[][] objectArrayTest = new testDataB[2][2];
	public boolean[][][] boolArrayTest = new boolean[4][2][3];
	public char[][] charArrayTest = new char[2][3];
	public int[][][][] multiArrTest = new int[4][3][2][2];
	
	public testDataA(int val) {
		integerTest = val;
		longTest = val;
		doubleTest = val;
		byteTest = 127;
		objectArrayTest[0][0] = new testDataB();
		objectArrayTest[0][0].test2 = "works";
		objectArrayTest[1][0] = new testDataB();
		objectArrayTest[1][0].test2 = "works twice";
		boolArrayTest[1][1][2] = true;
		floatTest = val;
		multiArrTest[0][0][0][0] = 55000;
		charTest = 'h';
		charArrayTest[0][1] = 'b';
		listTest.add(4);
		listTest.add(42);
		listTest.add(12);
		setTest.add(3);
		setTest.add(41);
		setTest.add(11);
		mapTest.put(2, 4.0);
		mapTest.put(3, 3.141592);
		booleanTest = true;
		stringTest = "Hello World";
		shortTest = (short) val;
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
