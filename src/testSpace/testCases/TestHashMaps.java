package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.hashMaps.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestHashMaps {

	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;

	@BeforeClass
	public static void init() {
		file = new H5File("testHashMaps.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Ignore("boolean and char need to be converted to numeric")
	@Test
	public void testBoolCharHashMap() {
		BoolCharHashMapTest objA = new BoolCharHashMapTest(true, (char) 'b',
				false, (char) 'd', true, (char) 'Z');
		BoolCharHashMapTest objB = new BoolCharHashMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testByteDoubleHashMap() {
		ByteDoubleHashMapTest objA = new ByteDoubleHashMapTest((byte) 3,
				(double) 4.0, (byte) 7, (double) 9.0, (byte) 0, (double) 2.0);
		ByteDoubleHashMapTest objB = new ByteDoubleHashMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testFloatIntHashMap() {
		FloatIntHashMapTest objA = new FloatIntHashMapTest((float) 3.0,
				(int) 5, (float) 7.0, (int) 9, (float) 0.0, (int) 4);
		FloatIntHashMapTest objB = new FloatIntHashMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testLongShortHashMap() {
		LongShortHashMapTest objA = new LongShortHashMapTest((long) 3,
				(short) 5, (long) 7, (short) 9, (long) 0, (short) 4);
		LongShortHashMapTest objB = new LongShortHashMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

}
