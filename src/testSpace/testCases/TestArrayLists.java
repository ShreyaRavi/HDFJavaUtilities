package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.arrayLists.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestArrayLists {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testArrayLists.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Test
	public void testBoolArrayList() {
		BoolArrayListTest objA = new BoolArrayListTest(true,false,true);
		BoolArrayListTest objB = new BoolArrayListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testIntArrayList() {
		IntArrayListTest objA = new IntArrayListTest((int)3,(int)7,(int)0);
		IntArrayListTest objB = new IntArrayListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testDoubleArrayList() {
		DoubleArrayListTest objA = new DoubleArrayListTest((double)3.0,(double)7.0,(double)0.0);
		DoubleArrayListTest objB = new DoubleArrayListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testByteArrayList() {
		ByteArrayListTest objA = new ByteArrayListTest((byte)3,(byte)7,(byte)0);
		ByteArrayListTest objB = new ByteArrayListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testCharArrayList() {
		CharArrayListTest objA = new CharArrayListTest((char)'b',(char)'d',(char)'Z');
		CharArrayListTest objB = new CharArrayListTest();
		out.writeObject(objA);
		in.readObject(objB);
		System.out.println((int)objB.getData()[1]);
		System.out.println((int)objB.getData()[2]);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testLongArrayList() {
		LongArrayListTest objA = new LongArrayListTest((long)3,(long)7,(long)0);
		LongArrayListTest objB = new LongArrayListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testShortArrayList() {
		ShortArrayListTest objA = new ShortArrayListTest((short)3,(short)7,(short)0);
		ShortArrayListTest objB = new ShortArrayListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testFloatArrayList() {
		FloatArrayListTest objA = new FloatArrayListTest((float)3.0,(float)7.0,(float)0.0);
		FloatArrayListTest objB = new FloatArrayListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}

}
