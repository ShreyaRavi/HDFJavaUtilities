package testSpace.testCases;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.BeforeClass;
import org.junit.Test;

import testSpace.accessMods.*;
import HDFJavaUtils.*;

public class TestAccessMods {
	
	static H5File file;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@BeforeClass
	public static void init() {
		file = new H5File("testAccessMods.h5", FileFormat.CREATE);
		out = new ObjectOutputStream(file);
		in = new ObjectInputStream(file);
	}

	@Test
	public void testPublicMod() {
		PublicTest objA = new PublicTest((int)3);
		PublicTest objB = new PublicTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getData(), objB.getData(), 0);
	}
	
	@Test
	public void testPrivateMod() {
		PrivateTest objA = new PrivateTest((int)3);
		PrivateTest objB = new PrivateTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getData(), objB.getData(), 0);
	}
	
	@Test
	public void testProtectedMod() {
		ProtectedTest objA = new ProtectedTest((int)3);
		ProtectedTest objB = new ProtectedTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getData(), objB.getData(), 0);
	}
	
	@Test
	public void testDefaultMod() {
		DefaultTest objA = new DefaultTest((int)3);
		DefaultTest objB = new DefaultTest();
		out.writeObject(objA);
		in.readObject(objB);
		assertEquals(objA.getData(), objB.getData(), 0);
	}

}
