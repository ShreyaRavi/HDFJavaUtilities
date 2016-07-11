package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.treeSets.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestTreeSets {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testTreeSets.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Ignore("Boolean TreeSet Test: giving an error - need to be converted to numeric")
	@Test
	public void testBoolTreeSet() {
		BoolTreeSetTest objA = new BoolTreeSetTest(true,false,true);
		BoolTreeSetTest objB = new BoolTreeSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testIntTreeSet() {
		IntTreeSetTest objA = new IntTreeSetTest((int)3,(int)7,(int)0);
		IntTreeSetTest objB = new IntTreeSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testDoubleTreeSet() {
		DoubleTreeSetTest objA = new DoubleTreeSetTest((double)3.0,(double)7.0,(double)0.0);
		DoubleTreeSetTest objB = new DoubleTreeSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testByteTreeSet() {
		ByteTreeSetTest objA = new ByteTreeSetTest((byte)3,(byte)7,(byte)0);
		ByteTreeSetTest objB = new ByteTreeSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Ignore("Character TreeSet test: giving an error - need to be converted to numeric")
	@Test
	public void testCharTreeSet() {
		CharTreeSetTest objA = new CharTreeSetTest((char)'b',(char)'d',(char)'Z');
		CharTreeSetTest objB = new CharTreeSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testLongTreeSet() {
		LongTreeSetTest objA = new LongTreeSetTest((long)3,(long)7,(long)0);
		LongTreeSetTest objB = new LongTreeSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testShortTreeSet() {
		ShortTreeSetTest objA = new ShortTreeSetTest((short)3,(short)7,(short)0);
		ShortTreeSetTest objB = new ShortTreeSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testFloatTreeSet() {
		FloatTreeSetTest objA = new FloatTreeSetTest((float)3.0,(float)7.0,(float)0.0);
		FloatTreeSetTest objB = new FloatTreeSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}

}
