package testSpace;

import HDFJavaUtils.ObjectInputStream;
import HDFJavaUtils.ObjectOutputStream;
import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

public class testing {
	
	public static void main(String[] targs) {
		H5File file = new H5File("test.h5", FileFormat.CREATE);
		ObjectOutputStream o = new ObjectOutputStream(file);
		ObjectInputStream in = new ObjectInputStream(file);
		testDataA a = new testDataA(5);
		testDataA b = new testDataA();
		System.out.println(a.toString());
		o.writeObject(a);
		in.readObject(b);
		o.writeObject(b, "Updated Test");
		System.out.println(b.toString());
	}
	
}
