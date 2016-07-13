package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.linkedLists.*;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestLinkedLists {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testLinkedLists.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Test
	public void testBoolLinkedList() {
		BoolLinkedListTest objA = new BoolLinkedListTest(true,false,true);
		BoolLinkedListTest objB = new BoolLinkedListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testIntLinkedList() {
		IntLinkedListTest objA = new IntLinkedListTest((int)3,(int)7,(int)0);
		IntLinkedListTest objB = new IntLinkedListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testDoubleLinkedList() {
		DoubleLinkedListTest objA = new DoubleLinkedListTest((double)3.0,(double)7.0,(double)0.0);
		DoubleLinkedListTest objB = new DoubleLinkedListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testByteLinkedList() {
		ByteLinkedListTest objA = new ByteLinkedListTest((byte)3,(byte)7,(byte)0);
		ByteLinkedListTest objB = new ByteLinkedListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testCharLinkedList() {
		CharLinkedListTest objA = new CharLinkedListTest((char)'b',(char)'d',(char)'Z');
		CharLinkedListTest objB = new CharLinkedListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testLongLinkedList() {
		LongLinkedListTest objA = new LongLinkedListTest((long)3,(long)7,(long)0);
		LongLinkedListTest objB = new LongLinkedListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testShortLinkedList() {
		ShortLinkedListTest objA = new ShortLinkedListTest((short)3,(short)7,(short)0);
		ShortLinkedListTest objB = new ShortLinkedListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}
	
	@Test
	public void testFloatLinkedList() {
		FloatLinkedListTest objA = new FloatLinkedListTest((float)3.0,(float)7.0,(float)0.0);
		FloatLinkedListTest objB = new FloatLinkedListTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertArrayEquals(objA.getData(), objB.getData());
	}

}
