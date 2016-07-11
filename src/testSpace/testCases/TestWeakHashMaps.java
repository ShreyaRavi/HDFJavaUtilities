package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.weakHashMaps.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestWeakHashMaps {

	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;

	@BeforeClass
	public static void init() {
		file = new H5File("testWeakHashMaps.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Ignore("boolean and char need to be converted to numeric")
	@Test
	public void testBoolCharWeakHashMap() {
		BoolCharWeakHashMapTest objA = new BoolCharWeakHashMapTest(true, (char) 'b',
				false, (char) 'd', true, (char) 'Z');
		BoolCharWeakHashMapTest objB = new BoolCharWeakHashMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testByteDoubleWeakHashMap() {
		ByteDoubleWeakHashMapTest objA = new ByteDoubleWeakHashMapTest((byte) 3,
				(double) 4.0, (byte) 7, (double) 9.0, (byte) 0, (double) 2.0);
		ByteDoubleWeakHashMapTest objB = new ByteDoubleWeakHashMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testFloatIntWeakHashMap() {
		FloatIntWeakHashMapTest objA = new FloatIntWeakHashMapTest((float) 3.0,
				(int) 5, (float) 7.0, (int) 9, (float) 0.0, (int) 4);
		FloatIntWeakHashMapTest objB = new FloatIntWeakHashMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testLongShortWeakHashMap() {
		LongShortWeakHashMapTest objA = new LongShortWeakHashMapTest((long) 3,
				(short) 5, (long) 7, (short) 9, (long) 0, (short) 4);
		LongShortWeakHashMapTest objB = new LongShortWeakHashMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

}
