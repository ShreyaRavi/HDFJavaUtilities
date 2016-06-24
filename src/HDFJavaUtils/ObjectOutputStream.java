package HDFJavaUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;

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
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
		if(obj.getClass().isArray()) {
			int[] data = (int[]) obj;
			long[] dims = {1, data.length};
			writeData(type, obj, dims, name);
		} else {
			int[] data = {(int) obj};
			long[] dims = {1, 1};
			writeData(type, data, dims, name);
		}
	}
	
	public void writeString(Object obj, String name) {
			writeChar(((String) obj).toCharArray(), name);
	}
	
	public void writeFloat(float val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_FLOAT);
		float[] data = {val};
		long[] dims = {1, 1};
		writeData(type, data, dims, name);
	}
	
	private void writeIntList(Object obj, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
		List<Integer> data = (List) obj;
		int[] changed = new int[data.size()];
		for(int i = 0; i<data.size(); i++) 
			changed[i] = data.get(i);
		long[] dims = {1, data.size()};
		writeData(type, changed, dims, name);
	}
	
	public void writeBoolean(boolean val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
		int[] data = {val ? 1 : 0};
		long[] dims = {1, 1};
		writeData(type, data, dims, name);
	}
	
	public void writeLong(long val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_LONG);
		long[] data = {val};
		long[] dims = {1, 1};
		writeData(type, data, dims, name);
	}
	
	public void writeShort(short val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_SHORT);
		short[] data = {(short) val};
		long[] dims = {1, 1};
		writeData(type, data, dims, name);
	}
	
	public void writeChar(char val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_CHAR);
		int[] data = {(int) val};
		long[] dims = {1, 1};
		writeData(type, data, dims, name);
	}
	
	public void writeChar(char[] val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
		long[] dims = {1, val.length};
		int[] data = new int[val.length];
		for(int i = 0; i<val.length; i++) 
			data[i] = (int) val[i];				
		writeData(type, data, dims, name);
	}
	
	public void writeObject(Object obj) {
	    if(obj instanceof HDF5Serializable) {
			Class<?> objClass = obj.getClass();
		    Field[] fields = objClass.getFields();
		    for(Field field : fields) {
		    	try {
		    		String type = field.get(obj).getClass().toString();
		    		//System.out.println("class: " + type + " type: " + field.getType());
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
			    	else if(type.contains("List"))
			    		writeIntList(field.get(obj), field.getName());
		    	} catch(IllegalArgumentException | IllegalAccessException e) {
		    		e.printStackTrace();
		    	}
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
	
	private void writeData(Datatype type, Object data, long[] dims, String name) {
		try {
			Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null, type, dims, null, null, 0, null);
			int dataset_id = dset.open();
			dset.write(data);
			dset.close(dataset_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Unfinished
	private void writeDataArray(int type, Object data, long[] dims, String name, int rank) {
		try {
			final H5Datatype dataType = new H5Datatype(type);
			Dataset dset = (H5ScalarDS) file.createScalarDS(name, null, dataType, dims, null, null, 0, null);
            int dataset_id = dset.open();
            int memtype_id = H5.H5Tarray_create(HDF5Constants.H5T_NATIVE_INT, (int) rank, dims);
			H5.H5Dwrite(dataset_id, memtype_id, 
				        HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, 
				        data);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Unfinished
	private long[] getArrayDims(Object obj) {
		String type = obj.getClass().toString();
		int rank = type.substring(6, type.length()-1).length()-1;
		long[] dims = new long[rank];
		return arrayDimsCounter(obj, rank, dims);
	}
	
	//Unfinished
	private long[] arrayDimsCounter(Object obj, int count, long[] dims) {
		if(count > 0) {
			arrayDimsCounter(obj, count-1, dims);
			dims[count-1] = Array.getLength(obj);
		}
			return dims;
	}

	
}
