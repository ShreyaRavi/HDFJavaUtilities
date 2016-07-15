package testSpace.testCases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import HDFJavaUtils.*;

public class TestMethods {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testMethods.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file, "hello");
		in = new ObjectInputStream(file, "hello");
	}

	@Test
	public void testInt() {
		out.writeInt((int)3, "Int");
		assertEquals((int)3, in.readInt("Int"));
	}
	
	@Test
	public void testDouble() {
		out.writeDouble((double)3.0, "Double");
		assertEquals((double)3.0, in.readDouble("Double"), 0.0);
	}
	
	@Test
	public void testChar() {
		out.writeChar((char)'Z', "Char");
		char[] testChar = {(char)'Z'};
		char[] resultChar = {in.readChar("Char")};
		assertArrayEquals(testChar, resultChar);
	}
	
	@Test
	public void testShort() {
		out.writeShort((short)3, "Short");
		assertEquals((short)3, in.readShort("Short"));
	}
	
	@Test
	public void testLong() {
		out.writeLong((long)3, "Long");
		assertEquals((long)3, in.readLong("Long"));
	}
	
	@Test
	public void testFloat() {
		out.writeFloat((float)3, "Float");
		assertEquals((float)3, in.readFloat("Float"), 0.0);
	}
	
	@Test
	public void testBoolean() {
		out.writeBoolean(true, "Boolean");
		assertTrue(in.readBoolean("Boolean"));
	}
	
	@Test
	public void testByte() {
		out.writeByte((byte)3, "Byte");
		assertEquals((byte)3, in.readByte("Byte"));
	}
	
	@Test
	public void testString() {
		out.writeString("testString", "String");
		assertEquals("testString", in.readString("String"));
	}
	
	@Test
	public void testCharArray() {
		String testString = "characterArray";
		char[] charArrayTest = testString.toCharArray();
		out.writeChar(charArrayTest, "CharArrTest");
		assertArrayEquals(charArrayTest, in.readCharArray("CharArrTest"));
	}
	
	@Ignore("cannot override an existing dataset by writing to one with the same name -- EXPECTED")
	@Test
	public void testOverwrite() {
		out.writeString("testString", "String");
		out.writeString("another", "String");
		assertEquals("testString", in.readString("String"));
	}
	
	@Ignore("can only read as a type that is holds more bits (upcasting) -- EXPECTED")
	@Test
	public void testTypeMix() {
		out.writeInt((int)3, "Type");
		assertEquals((double)3.0, in.readDouble("Type"), 0.0);
	}
	
	@Test
	public void testInteger() {
		Integer intVal = new Integer(3);
		Integer intVal2 = new Integer(1);
		out.writeObject(intVal);
		in.readObject(intVal2);
		assertEquals(intVal, intVal2);
	}

}
