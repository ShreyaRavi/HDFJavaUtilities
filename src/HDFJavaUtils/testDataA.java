package HDFJavaUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import HDFJavaUtils.annotations.SerializeClassOptions;
import HDFJavaUtils.annotations.SerializeFieldOptions;

@SerializeClassOptions(path = "Hello", name = "Different Name")
public class testDataA implements HDF5Serializable{
	public int integerTest;
	public long longTest;
	@SerializeFieldOptions(path = "Hiding/Really well", name = "Other Name")
	public double doubleTest;
	public float floatTest;
	public short shortTest;
	public char charTest;
	public char[] charArrayTest;
	public int[] intArrayTest = {1, 12, 53, 45, 76};
	public List<Integer> listTest = new LinkedList<Integer>();
	public Set<Integer> setTest = new TreeSet<Integer>();
	public Map<Integer, Double> mapTest = new HashMap<Integer, Double>();
	public String stringTest;
	public boolean booleanTest;
	public testDataA(int val) {
		integerTest = val;
		longTest = val;
		doubleTest = val;
		floatTest = val;
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
		charArrayTest = ("Hello World").toCharArray();
		shortTest = (short) val;
	}
	
	public testDataA() {
		charArrayTest = new char[2];
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
				returnString += " " + field.getType() + ": " + field.get(this) + "\n";
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
	    }
		return returnString;
	}

}
