package testSpace.annotations;

import HDFJavaUtils.interfaces.SerializeClassOptions;
import HDFJavaUtils.interfaces.SerializeFieldOptions;
import HDFJavaUtils.interfaces.HDF5Serializable;

@SerializeClassOptions(path = "classPath", name = "className")
public class ClassAnnotationsTest implements HDF5Serializable {
	
	@SerializeFieldOptions(path = "testPath", name = "intName")
	public int testInt;
	@SerializeFieldOptions(path = "testPath", name = "doubleName")
	public double testDouble;
	@SerializeFieldOptions(path = "testPath")
	public long testLong;
	@SerializeFieldOptions(name = "byteName")
	public byte testByte;
	
	public boolean testBoolean;
	
	public ClassAnnotationsTest() {
		
	}
	
	public ClassAnnotationsTest(byte val) {
		testInt = val;
		testDouble = val;
		testLong = val;
		testByte = val;
		testBoolean = (val > 0 ? true:false);
	}
	
	public int getTestInt() {
		return testInt;
	}
	
	public double getTestDouble() {
		return testDouble;
	}
	
	public long getTestLong() {
		return testLong;
	}
	
	public byte getTestByte() {
		return testByte;
	}
	
	public boolean getTestBoolean() {
		return testBoolean;
	}
	
}
