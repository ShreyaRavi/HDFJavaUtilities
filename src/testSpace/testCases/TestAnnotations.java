package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Test;

import testSpace.annotations.ClassAnnotationsTest;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;

public class TestAnnotations {

	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testAnnotations.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}
	
	@Test
	public void testClassPath() {
		ClassAnnotationsTest objA = new ClassAnnotationsTest((byte)3);
		ClassAnnotationsTest objB = new ClassAnnotationsTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getTestInt(), objB.getTestInt());
		assertEquals(objA.getTestDouble(), objB.getTestDouble(), 0.0);
		assertEquals(objA.getTestLong(), objB.getTestLong());
		assertEquals(objA.getTestByte(), objB.getTestByte());
		assertEquals(objA.getTestBoolean(), objB.getTestBoolean());
	}

}
