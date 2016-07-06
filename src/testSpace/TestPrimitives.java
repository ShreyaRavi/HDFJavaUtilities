package testSpace;

import static org.junit.Assert.*;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.junit.Before;
import org.junit.Test;
import HDFJavaUtils.*;

public class TestPrimitives {
	
	H5File file;
	
	@Before
	public void init() {
		file = new H5File("test.h5", FileFormat.CREATE);
	}

	@Test
	public void testInt() {
				
	}

}
