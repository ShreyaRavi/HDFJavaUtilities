package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.linkedHashSets.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestLinkedHashSets {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testLinkedHashSets.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

//	@Ignore("Boolean LinkedHashSet Test: giving an error - need to be converted to numeric")
	@Test
	public void testBoolLinkedHashSet() {
		BoolLinkedHashSetTest objA = new BoolLinkedHashSetTest(true,false,true);
		BoolLinkedHashSetTest objB = new BoolLinkedHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testIntLinkedHashSet() {
		IntLinkedHashSetTest objA = new IntLinkedHashSetTest((int)3,(int)7,(int)0);
		IntLinkedHashSetTest objB = new IntLinkedHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testDoubleLinkedHashSet() {
		DoubleLinkedHashSetTest objA = new DoubleLinkedHashSetTest((double)3.0,(double)7.0,(double)0.0);
		DoubleLinkedHashSetTest objB = new DoubleLinkedHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testByteLinkedHashSet() {
		ByteLinkedHashSetTest objA = new ByteLinkedHashSetTest((byte)3,(byte)7,(byte)0);
		ByteLinkedHashSetTest objB = new ByteLinkedHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
//	@Ignore("Character LinkedHashSet test: giving an error - need to be converted to numeric")
	@Test
	public void testCharLinkedHashSet() {
		CharLinkedHashSetTest objA = new CharLinkedHashSetTest((char)'b',(char)'d',(char)'Z');
		CharLinkedHashSetTest objB = new CharLinkedHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testLongLinkedHashSet() {
		LongLinkedHashSetTest objA = new LongLinkedHashSetTest((long)3,(long)7,(long)0);
		LongLinkedHashSetTest objB = new LongLinkedHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testShortLinkedHashSet() {
		ShortLinkedHashSetTest objA = new ShortLinkedHashSetTest((short)3,(short)7,(short)0);
		ShortLinkedHashSetTest objB = new ShortLinkedHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testFloatLinkedHashSet() {
		FloatLinkedHashSetTest objA = new FloatLinkedHashSetTest((float)3.0,(float)7.0,(float)0.0);
		FloatLinkedHashSetTest objB = new FloatLinkedHashSetTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}

}
