package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.hashtables.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestHashtables {

	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;

	@BeforeClass
	public static void init() {
		file = new H5File("testHashtables.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

//	@Ignore("boolean and char need to be converted to numeric")
	@Test
	public void testBoolCharHashtable() {
		BoolCharHashtableTest objA = new BoolCharHashtableTest(true, (char) 'b',
				false, (char) 'd', true, (char) 'Z');
		BoolCharHashtableTest objB = new BoolCharHashtableTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testByteDoubleHashtable() {
		ByteDoubleHashtableTest objA = new ByteDoubleHashtableTest((byte) 3,
				(double) 4.0, (byte) 7, (double) 9.0, (byte) 0, (double) 2.0);
		ByteDoubleHashtableTest objB = new ByteDoubleHashtableTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testFloatIntHashtable() {
		FloatIntHashtableTest objA = new FloatIntHashtableTest((float) 3.0,
				(int) 5, (float) 7.0, (int) 9, (float) 0.0, (int) 4);
		FloatIntHashtableTest objB = new FloatIntHashtableTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testLongShortHashtable() {
		LongShortHashtableTest objA = new LongShortHashtableTest((long) 3,
				(short) 5, (long) 7, (short) 9, (long) 0, (short) 4);
		LongShortHashtableTest objB = new LongShortHashtableTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

}
