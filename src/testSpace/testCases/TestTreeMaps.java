package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.treeMaps.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestTreeMaps {

	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;

	@BeforeClass
	public static void init() {
		file = new H5File("testTreeMaps.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Ignore("boolean and char need to be converted to numeric")
	@Test
	public void testBoolCharTreeMap() {
		BoolCharTreeMapTest objA = new BoolCharTreeMapTest(true, (char) 'b',
				false, (char) 'd', true, (char) 'Z');
		BoolCharTreeMapTest objB = new BoolCharTreeMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testByteDoubleTreeMap() {
		ByteDoubleTreeMapTest objA = new ByteDoubleTreeMapTest((byte) 3,
				(double) 4.0, (byte) 7, (double) 9.0, (byte) 0, (double) 2.0);
		ByteDoubleTreeMapTest objB = new ByteDoubleTreeMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testFloatIntTreeMap() {
		FloatIntTreeMapTest objA = new FloatIntTreeMapTest((float) 3.0,
				(int) 5, (float) 7.0, (int) 9, (float) 0.0, (int) 4);
		FloatIntTreeMapTest objB = new FloatIntTreeMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

	@Test
	public void testLongShortTreeMap() {
		LongShortTreeMapTest objA = new LongShortTreeMapTest((long) 3,
				(short) 5, (long) 7, (short) 9, (long) 0, (short) 4);
		LongShortTreeMapTest objB = new LongShortTreeMapTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getKeyData(), objB.getKeyData());
		assertArrayEquals(objA.getValData(), objB.getValData());
	}

}
