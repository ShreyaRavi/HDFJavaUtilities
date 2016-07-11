package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.concurrentSkipListMaps.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestConcurrentSkipListMaps {

	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;

	@BeforeClass
	public static void init() {
		file = new H5File("testConcurrentSkipListMaps.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Ignore("boolean and char need to be converted to numeric")
	@Test
	public void testBoolCharConcurrentSkipListMap() {
		BoolCharConcurrentSkipListMapTest objA = new BoolCharConcurrentSkipListMapTest(
				true, (char) 'b', false, (char) 'd', true, (char) 'Z');
		BoolCharConcurrentSkipListMapTest objB = new BoolCharConcurrentSkipListMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testByteDoubleConcurrentSkipListMap() {
		ByteDoubleConcurrentSkipListMapTest objA = new ByteDoubleConcurrentSkipListMapTest(
				(byte) 3, (double) 4.0, (byte) 7, (double) 9.0, (byte) 0,
				(double) 2.0);
		ByteDoubleConcurrentSkipListMapTest objB = new ByteDoubleConcurrentSkipListMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testFloatIntConcurrentSkipListMap() {
		FloatIntConcurrentSkipListMapTest objA = new FloatIntConcurrentSkipListMapTest(
				(float) 3.0, (int) 5, (float) 7.0, (int) 9, (float) 0.0,
				(int) 4);
		FloatIntConcurrentSkipListMapTest objB = new FloatIntConcurrentSkipListMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testLongShortConcurrentSkipListMap() {
		LongShortConcurrentSkipListMapTest objA = new LongShortConcurrentSkipListMapTest(
				(long) 3, (short) 5, (long) 7, (short) 9, (long) 0, (short) 4);
		LongShortConcurrentSkipListMapTest objB = new LongShortConcurrentSkipListMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

}
