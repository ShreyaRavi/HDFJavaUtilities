package HDFJavaUtils;

public class testDataA {
	public int integerTest;
	public long longTest;
	public double doubleTest;
	public float floatTest;
	public short shortTest;
	public char charTest;
	public char[] charArrayTest;
	public int[][] intArrayTest = {{1, 2}};
	public String stringTest;
	public boolean booleanTest;
	
	public testDataA(int val) {
		integerTest = val;
		longTest = val;
		doubleTest = val;
		floatTest = val;
		charTest = 'h';
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
		returnString += "int: " + integerTest;
		returnString += " long: " + longTest;
		returnString += " double: " + doubleTest;
		returnString += " float: " + floatTest;
		returnString += " short: " + shortTest;
		returnString += " char: " + charTest;
		returnString += " charArray: " + charArrayTest;
		returnString += " string: " + stringTest;
		returnString += " boolean: " + booleanTest;
		return returnString;
	}
}
