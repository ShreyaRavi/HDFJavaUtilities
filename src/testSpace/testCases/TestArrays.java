package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.arrays.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestArrays {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testArrays.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Test
	public void testBoolArray() {
		BoolArrayTest objA = new BoolArrayTest(true,false,true);
		BoolArrayTest objB = new BoolArrayTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testIntArray() {
		IntArrayTest objA = new IntArrayTest((int)3,(int)7,(int)0);
		IntArrayTest objB = new IntArrayTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testDoubleArray() {
		DoubleArrayTest objA = new DoubleArrayTest((double)3.0,(double)7.0,(double)0.0);
		DoubleArrayTest objB = new DoubleArrayTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testByteArray() {
		ByteArrayTest objA = new ByteArrayTest((byte)3,(byte)7,(byte)0);
		ByteArrayTest objB = new ByteArrayTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testCharArray() {
		CharArrayTest objA = new CharArrayTest((char)'b',(char)'d',(char)'Z');
		CharArrayTest objB = new CharArrayTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testLongArray() {
		LongArrayTest objA = new LongArrayTest((long)3,(long)7,(long)0);
		LongArrayTest objB = new LongArrayTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testShortArray() {
		ShortArrayTest objA = new ShortArrayTest((short)3,(short)7,(short)0);
		ShortArrayTest objB = new ShortArrayTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testFloatArray() {
		FloatArrayTest objA = new FloatArrayTest((float)3.0,(float)7.0,(float)0.0);
		FloatArrayTest objB = new FloatArrayTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}

}
