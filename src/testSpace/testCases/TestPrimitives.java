package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Test;

import testSpace.primitives.*;
import HDFJavaUtils.*;

public class TestPrimitives {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testPrimitives.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Test
	public void testInt() {
		IntTest objA = new IntTest((int)3);
		IntTest objB = new IntTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getData(), objB.getData(), 0);
	}
	
	@Test
	public void testDouble() {
		DoubleTest objA = new DoubleTest((double)3.0);
		DoubleTest objB = new DoubleTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getData(), objB.getData(), 0.0);
	}
	
	@Test
	public void testChar() {
		CharTest objA = new CharTest((char)'a');
		CharTest objB = new CharTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testShort() {
		ShortTest objA = new ShortTest((short)3);
		ShortTest objB = new ShortTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getData(), objB.getData(), 0);
	}
	
	@Test
	public void testLong() {
		LongTest objA = new LongTest((long)3);
		LongTest objB = new LongTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getData(), objB.getData(), 0);
	}
	
	@Test
	public void testFloat() {
		FloatTest objA = new FloatTest((float)3.0);
		FloatTest objB = new FloatTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getData(), objB.getData(), 0.0);
	}
	
	@Test
	public void testBoolean() {
		BooleanTest objA = new BooleanTest((boolean)true);
		BooleanTest objB = new BooleanTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testByte() {
		ByteTest objA = new ByteTest((byte)3);
		ByteTest objB = new ByteTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getData(), objB.getData(), 0);
	}

}
