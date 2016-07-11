package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import testSpace.other.*;
import HDFJavaUtils.*;

public class TestOther {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testOther.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	
//	Does not work bc inherited fields are not included
//	in getDeclaredFields() in the objectHelper methods
//	If we use getFields() instead, only public fields will be returned
	
//  TODO: 	To get inherited fields, need to do objClass.getSuperclass
//				recursively and in each superclass get the modifiers
//				and check to see if it's public or protected and if it's
//				either, add it to the fields
//  		If there is not access modifier (not public, not private, not protected),
//				check if the two classes are in the same package,
//				and if they are, add it to the fields
	
	@Ignore("Will work if above TODO is completed")
	@Test
	public void testInheritance() {
		SubClass objA = new SubClass((int)3);
		SubClass objB = new SubClass();
		out.writeObject(objA);
		in.readObject(objB);
		System.out.println(objA.getDataFromBoth()[0]);
		System.out.println(objA.getDataFromBoth()[1]);
		assertArrayEquals(objA.getDataFromBoth(), objB.getDataFromBoth());
	}
	
	@Test
	public void testSubclass() {
		HasObject objA = new HasObject((int)7);
		HasObject objB = new HasObject();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getDataFromClass(), objB.getDataFromClass());
		assertEquals(objA.getDataFromObject(), objB.getDataFromObject());
	}
	
	

}
