package HDFJavaUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;
import ncsa.hdf.object.Dataset;
import ncsa.hdf.object.Datatype;
import ncsa.hdf.object.h5.H5Datatype;
import ncsa.hdf.object.h5.H5File;
import ncsa.hdf.object.h5.H5ScalarDS;

public class ObjectOutputStream {
	
	private H5File file;

	public ObjectOutputStream(H5File file) {
		this.file = file;
	}
	
	public void writeDouble(double val, String name) {
		final H5Datatype typeDouble = new H5Datatype(HDF5Constants.H5T_NATIVE_DOUBLE);
		try {
			long[] dims = {1, 1};
			Double[] data = {val};
			Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null, typeDouble, dims, null, null, 0, null);
			int dataset_id = dset.open();
			dset.write(data);
			dset.close(dataset_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void writeInt(Object obj, String name) {
		final H5Datatype typeInt = new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
		try {
			int dataset_id;
			Dataset dset;
			if(obj.getClass().isArray()) {
				long[] dims = {1, 1};
				dset = (H5ScalarDS) file.createScalarDS("/" + name, null, typeInt, dims, null, null, 0, null);
				dataset_id = dset.open();
				dset.write(obj);
			} else {
				long[] dims = {1, 1};
				dset = (H5ScalarDS) file.createScalarDS("/" + name, null, typeInt, dims, null, null, 0, null);
				dataset_id = dset.open();
				Integer[] data = {(int) obj};
				dset.write(data);
			}
			dset.close(dataset_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeString(Object obj, String name) {
			writeChar(((String) obj).toCharArray(), name);
	}
	
	public void writeFloat(float val, String name) {
		final H5Datatype typeFloat = new H5Datatype(HDF5Constants.H5T_NATIVE_FLOAT);
		try {
			long[] dims = {1, 1};
			Float[] data = {val};
			Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null, typeFloat, dims, null, null, 0, null);
			int dataset_id = dset.open();
			dset.write(data);
			dset.close(dataset_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeLong(long val, String name) {
		final H5Datatype typeLong = new H5Datatype(HDF5Constants.H5T_NATIVE_LONG);
		try {
			long[] dims = {1, 1};
			long[] data = {val};
			Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null, typeLong, dims, null, null, 0, null);
			int dataset_id = dset.open();
			dset.write(data);
			dset.close(dataset_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeShort(short val, String name) {
		final H5Datatype typeLong = new H5Datatype(HDF5Constants.H5T_NATIVE_SHORT);
		try {
			long[] dims = {1, 1};
			short[] data = {val};
			Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null, typeLong, dims, null, null, 0, null);
			int dataset_id = dset.open();
			dset.write(data);
			dset.close(dataset_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeChar(char val, String name) {
		final H5Datatype typeLong = new H5Datatype(HDF5Constants.H5T_NATIVE_CHAR);
		try {
			long[] dims = {1, 1};
			int[] data = {(int) val};
			Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null, typeLong, dims, null, null, 0, null);
			int dataset_id = dset.open();
			dset.write(data);
			dset.close(dataset_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeChar(char[] val, String name) {
		final H5Datatype typeLong = new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
		try {
			long[] dims = {1, val.length};
			int[] data = new int[val.length];
			for(int i = 0; i<val.length; i++) {
				data[i] = (int) val[i];		
			}
			Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null, typeLong, dims, null, dims, 0, data);
			int dataset_id = dset.open();
			int[] test = {2, 4};
		//	dset.write(test);
			dset.close(dataset_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeObject(Object obj) {
		Class<?> objClass = obj.getClass();
	    Field[] fields = objClass.getFields();
	    for(Field field : fields) {
	    	try {
	    		String type = field.get(obj).getClass().toString();
	    	//	System.out.println("class: " + type + " type: " + field.getType());
		    	if(type.equals("class java.lang.Integer") || type.contains("[I")) 
		    		writeInt(field.get(obj), field.getName());
		    	else if(type.equals("class java.lang.Long")) 
		    		writeLong(field.getLong(obj), field.getName());
		    	else if(type.equals("class java.lang.Double")) 
		    		writeDouble(field.getDouble(obj), field.getName());
		    	else if(type.equals("class java.lang.Float")) 
		    		writeFloat(field.getFloat(obj), field.getName());
		    	else if(type.equals("class java.lang.Short")) 
		    		writeShort(field.getShort(obj), field.getName());
		    	else if(type.equals("class [C")) {
		    		writeChar((char[]) field.get(obj), field.getName());
		    	}
		    	else if(type.equals("class java.lang.Character")) 
		    		writeChar(field.getChar(obj), field.getName());
		    	else if(type.equals("class java.lang.String")) 
		    		writeString(field.get(obj), field.getName());
	    	} catch(IllegalArgumentException | IllegalAccessException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
	public void close() {
		try {
			file.close();
		} catch (HDF5Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
