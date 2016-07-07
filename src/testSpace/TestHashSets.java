package testSpace;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.hashSets.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestHashSets {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testHashSets.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Ignore("Boolean HashSet Test: giving an error - need to be converted to numeric")
	@Test
	public void testBoolHashSet() {
		BoolHashSetTest objA = new BoolHashSetTest(true,false,true);
		BoolHashSetTest objB = new BoolHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testIntHashSet() {
		IntHashSetTest objA = new IntHashSetTest((int)3,(int)7,(int)0);
		IntHashSetTest objB = new IntHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testDoubleHashSet() {
		DoubleHashSetTest objA = new DoubleHashSetTest((double)3.0,(double)7.0,(double)0.0);
		DoubleHashSetTest objB = new DoubleHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void tesByteHashSet() {
		ByteHashSetTest objA = new ByteHashSetTest((byte)3,(byte)7,(byte)0);
		ByteHashSetTest objB = new ByteHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Ignore("Character HashSet test: giving an error - need to be converted to numeric")
	@Test
	public void testCharHashSet() {
		CharHashSetTest objA = new CharHashSetTest((char)'b',(char)'d',(char)'Z');
		CharHashSetTest objB = new CharHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testLongHashSet() {
		LongHashSetTest objA = new LongHashSetTest((long)3,(long)7,(long)0);
		LongHashSetTest objB = new LongHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testShortHashSet() {
		ShortHashSetTest objA = new ShortHashSetTest((short)3,(short)7,(short)0);
		ShortHashSetTest objB = new ShortHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testFloatHashSet() {
		FloatHashSetTest objA = new FloatHashSetTest((float)3.0,(float)7.0,(float)0.0);
		FloatHashSetTest objB = new FloatHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}

}
