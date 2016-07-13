package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.stacks.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestStacks {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testStacks.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Test
	public void testBoolStack() {
		BoolStackTest objA = new BoolStackTest(true,false,true);
		BoolStackTest objB = new BoolStackTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testIntStack() {
		IntStackTest objA = new IntStackTest((int)3,(int)7,(int)0);
		IntStackTest objB = new IntStackTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testDoubleStack() {
		DoubleStackTest objA = new DoubleStackTest((double)3.0,(double)7.0,(double)0.0);
		DoubleStackTest objB = new DoubleStackTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testByteStack() {
		ByteStackTest objA = new ByteStackTest((byte)3,(byte)7,(byte)0);
		ByteStackTest objB = new ByteStackTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testCharStack() {
		CharStackTest objA = new CharStackTest((char)'b',(char)'d',(char)'Z');
		CharStackTest objB = new CharStackTest();
		out.writeObject(objA);
		in.readObject(objB);
		System.out.println((int)objB.getData()[1]);
		System.out.println((int)objB.getData()[2]);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testLongStack() {
		LongStackTest objA = new LongStackTest((long)3,(long)7,(long)0);
		LongStackTest objB = new LongStackTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testShortStack() {
		ShortStackTest objA = new ShortStackTest((short)3,(short)7,(short)0);
		ShortStackTest objB = new ShortStackTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testFloatStack() {
		FloatStackTest objA = new FloatStackTest((float)3.0,(float)7.0,(float)0.0);
		FloatStackTest objB = new FloatStackTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}

}
