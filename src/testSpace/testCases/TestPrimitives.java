package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.testClasses.*;
import HDFJavaUtils.*;

public class TestPrimitives {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("test.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Test
	public void testInt() {
		int test = 3;
		IntTest objA = new IntTest(test);
		IntTest objB = new IntTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(test, objB.getData(), 0);
	}
	
	@Test
	public void testDouble() {
		double test = 3.0;
		DoubleTest objA = new DoubleTest(test);
		DoubleTest objB = new DoubleTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(test, objB.getData(), 0.0);
	}
	
	@Test
	public void testChar() {
		char test = 'a';
		CharTest objA = new CharTest(test);
		CharTest objB = new CharTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(test, objB.getData());
	}
	
	@Test
	public void testShort() {
		short test = 3;
		ShortTest objA = new ShortTest(test);
		ShortTest objB = new ShortTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(test, objB.getData(), 0);
	}
	
	@Test
	public void testLong() {
		long test = 3;
		LongTest objA = new LongTest(test);
		LongTest objB = new LongTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(test, objB.getData(), 0);
	}
	
	@Test
	public void testFloat() {
		float test = 3.0f;
		FloatTest objA = new FloatTest(test);
		FloatTest objB = new FloatTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(test, objB.getData(), 0.0);
	}
	
	@Test
	public void testBoolean() {
		boolean test = true;
		BooleanTest objA = new BooleanTest(test);
		BooleanTest objB = new BooleanTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(test, objB.getData());
	}
	
	@Ignore("read/write not implemented for type byte yet")
	@Test
	public void testByte() {
		byte test = 3;
		ByteTest objA = new ByteTest(test);
		ByteTest objB = new ByteTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(test, objB.getData(), 0);
	}

}
