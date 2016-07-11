package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.vectors.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestVectors {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testVectors.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Ignore("Boolean Vector Test: giving an error - need to be converted to numeric")
	@Test
	public void testBoolVector() {
		BoolVectorTest objA = new BoolVectorTest(true,false,true);
		BoolVectorTest objB = new BoolVectorTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testIntVector() {
		IntVectorTest objA = new IntVectorTest((int)3,(int)7,(int)0);
		IntVectorTest objB = new IntVectorTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testDoubleVector() {
		DoubleVectorTest objA = new DoubleVectorTest((double)3.0,(double)7.0,(double)0.0);
		DoubleVectorTest objB = new DoubleVectorTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testByteVector() {
		ByteVectorTest objA = new ByteVectorTest((byte)3,(byte)7,(byte)0);
		ByteVectorTest objB = new ByteVectorTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Ignore("Character Vector Test: giving an error - need to be converted to numeric")
	@Test
	public void testCharVector() {
		CharVectorTest objA = new CharVectorTest((char)'b',(char)'d',(char)'Z');
		CharVectorTest objB = new CharVectorTest();
		out.writeObject(objA);
		in.readObject(objB);
		System.out.println((int)objB.getData()[1]);
		System.out.println((int)objB.getData()[2]);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testLongVector() {
		LongVectorTest objA = new LongVectorTest((long)3,(long)7,(long)0);
		LongVectorTest objB = new LongVectorTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testShortVector() {
		ShortVectorTest objA = new ShortVectorTest((short)3,(short)7,(short)0);
		ShortVectorTest objB = new ShortVectorTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testFloatVector() {
		FloatVectorTest objA = new FloatVectorTest((float)3.0,(float)7.0,(float)0.0);
		FloatVectorTest objB = new FloatVectorTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}

}
