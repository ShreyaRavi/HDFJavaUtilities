package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.concurrentHashMaps.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestConcurrentHashMaps {

	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;

	@BeforeClass
	public static void init() {
		file = new H5File("testConcurrentHashMaps.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Ignore("boolean and char need to be converted to numeric")
	@Test
	public void testBoolCharConcurrentHashMap() {
		BoolCharConcurrentHashMapTest objA = new BoolCharConcurrentHashMapTest(
				true, (char) 'b', false, (char) 'd', true, (char) 'Z');
		BoolCharConcurrentHashMapTest objB = new BoolCharConcurrentHashMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testByteDoubleConcurrentHashMap() {
		ByteDoubleConcurrentHashMapTest objA = new ByteDoubleConcurrentHashMapTest(
				(byte) 3, (double) 4.0, (byte) 7, (double) 9.0, (byte) 0,
				(double) 2.0);
		ByteDoubleConcurrentHashMapTest objB = new ByteDoubleConcurrentHashMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testFloatIntConcurrentHashMap() {
		FloatIntConcurrentHashMapTest objA = new FloatIntConcurrentHashMapTest(
				(float) 3.0, (int) 5, (float) 7.0, (int) 9, (float) 0.0,
				(int) 4);
		FloatIntConcurrentHashMapTest objB = new FloatIntConcurrentHashMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testLongShortConcurrentHashMap() {
		LongShortConcurrentHashMapTest objA = new LongShortConcurrentHashMapTest(
				(long) 3, (short) 5, (long) 7, (short) 9, (long) 0, (short) 4);
		LongShortConcurrentHashMapTest objB = new LongShortConcurrentHashMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

}
