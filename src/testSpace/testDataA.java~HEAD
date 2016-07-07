package testSpace;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import HDFJavaUtils.interfaces.HDF5Serializable;
import HDFJavaUtils.interfaces.Ignore;
import HDFJavaUtils.interfaces.SerializeClassOptions;
import HDFJavaUtils.interfaces.SerializeFieldOptions;

@SerializeClassOptions()
public class testDataA implements HDF5Serializable{
	public int integerTest;
	public long longTest;
	@SerializeFieldOptions(path = "Hiding/Really well", name = "Other Name", dimensions = {1, 1})
	public double doubleTest;
	public float floatTest;
	public short shortTest;
	public char charTest;
	public int[] intArrayTest = {1, 12, 53, 45, 76};
	public List<Integer> listTest = new LinkedList<Integer>();
	public Set<Integer> setTest = new TreeSet<Integer>();
	public Map<Integer, Double> mapTest = new HashMap<Integer, Double>();
	public String stringTest;
	public boolean booleanTest;
	public testDataB subclassTest = new testDataB();
	public char[][][] charArrayTest = new char[2][2][3];
	public int[][][][] multiArrTest3 = {{{{1,2},{3,4},{5,6}},
		{{7,8},{9,10},{11,12}},
		{{13,14},{15,16},{17,18}},
		{{19,20},{21,22},{23,24}}},
		{{{1,2},{3,4},{5,6}},
		{{7,8},{9,10},{11,12}},
		{{13,14},{15,16},{17,18}},
		{{19,20},{21,22},{23,24}}}};

	public testDataA(int val) {
		integerTest = val;
		longTest = val;
		doubleTest = val;
		floatTest = val;
		charArrayTest[0][0][0] = 'c';
		subclassTest.test1 = 199;
		charTest = 'h';
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
	    Field[] fields = objClass.getFields();
	    
	    for(Field field : fields) {
	    	try {
	    		System.out.print(" " + field.getType() + ": " + field.get(this) + "\n");
				//returnString += " " + field.getType() + ": " + field.get(this) + "\n";
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
	    }
		return returnString;
	}

}
