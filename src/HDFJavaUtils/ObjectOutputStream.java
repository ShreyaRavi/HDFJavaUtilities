package HDFJavaUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import HDFJavaUtils.interfaces.HDF5Serializable;
import HDFJavaUtils.interfaces.Ignore;
import HDFJavaUtils.interfaces.SerializeClassOptions;
import HDFJavaUtils.interfaces.SerializeFieldOptions;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;
import ncsa.hdf.object.Dataset;
import ncsa.hdf.object.Datatype;
import ncsa.hdf.object.h5.H5Datatype;
import ncsa.hdf.object.h5.H5File;
import ncsa.hdf.object.h5.H5Group;
import ncsa.hdf.object.h5.H5ScalarDS;


/**
 * The HDF5Util's ObjectOutputStream is a mirror to Java's ObjectOutputStream
 * The program gives the user tools to serialize a class to a HDF file
 * @author Ben Bressette
 * @version 0.1
 * @since 2016-6-30
 */
public class ObjectOutputStream {
	
	private H5File file;
	private String defaultPath;

	/**
	 * Constructor for the class, the user is required to input a H5File
	 * @param file The H5File being accessed within the program
	 */
	public ObjectOutputStream(H5File file) {
		this.file = file;
		defaultPath = "";
	}
	
	/**
	 * Constructor for the class, the user is required to input a H5File
	 * @param file The H5File being accessed within the program
	 * @param groupPath represents the root directory the program will use in the file
	 */
	public ObjectOutputStream(H5File file, String groupPath) {
		this.file = file;
		defaultPath = groupPath;
		createPath(groupPath, null);
	}
	
	/**
	 * Creates a new dataset and writes a single
	 * Double value to it
	 * @param val he value being written to a dataset
	 * @param name  the name of the dataset
	 */
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
	
	/**
	 * Creates a new dataset and writes a single
	 * Integer value to it
	 * @param val he value being written to a dataset
	 * @param name  the name of the dataset
	 */
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
	
	/**
	 * Creates a new dataset and writes a single
	 * String object to it
	 * @param val he value being written to a dataset
	 * @param name  the name of the dataset
	 */
	public void writeString(String obj, String name) {
		H5Datatype type = new H5Datatype(Datatype.CLASS_STRING, obj.length(), -1, -1);
		long[] dims = {1};
		String[] data = {obj};
		writeData(type, data, dims, name);
	}
	
	/**
	 * Creates a new dataset and writes a single
	 * Integer value to it
	 * @param val he value being written to a dataset
	 * @param name  the name of the dataset
	 */
	public void writeFloat(float val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_FLOAT);
		float[] data = {val};
		long[] dims = {1};
		writeData(type, data, dims, name);
	}
	
	/**
	 * Creates a new dataset and writes a single
	 * Boolean value to it
	 * @param val he value being written to a dataset
	 * @param name  the name of the dataset
	 */
	public void writeBoolean(boolean val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_HBOOL);
		int[] data = {val ? 1 : 0};
		long[] dims = {1, 1};
		writeData(type, data, dims, name);
	}
	
	/**
	 * Creates a new dataset and writes a single
	 * Long value to it
	 * @param val he value being written to a dataset
	 * @param name  the name of the dataset
	 */
	public void writeLong(long val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_LONG);
		long[] data = {val};
		long[] dims = {1, 1};
		writeData(type, data, dims, name);
	}
	
	/**
	 * Creates a new dataset and writes a single
	 * Short value to it
	 * @param val he value being written to a dataset
	 * @param name  the name of the dataset
	 */
	public void writeShort(short val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_SHORT);
		short[] data = {val};
		long[] dims = {1, 1};
		writeData(type, data, dims, name);
	}
	
	/**
	 * Creates a new dataset and writes a single
	 * Character value to it
	 * @param val he value being written to a dataset
	 * @param name  the name of the dataset
	 */
	public void writeChar(char val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_CHAR);
		int[] data = {val};
		long[] dims = {1, 1};
		writeData(type, data, dims, name);
	}
	
	/**
	 * Creates a new dataset and writes a single
	 * Character array to it
	 * @param val he value being written to a dataset
	 * @param name  the name of the dataset
	 */
	public void writeChar(char[] val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
		long[] dims = {1, val.length};
		int[] data = new int[val.length];
		for(int i = 0; i<val.length; i++) 
			data[i] = (int) val[i];				
		writeData(type, data, dims, name);
	}
	
	/**
	 * Acts similar to Java's writeObject function.
	 * Will write basic data from a class and serialize it to the file
	 * Object must be implementing the HDF5Serializable interface
	 * Any field with the transient tag will be ignored
	 * @param obj The object to be serialized 
	 */
	public void writeObject(Object obj) {
		writeObjectHelper(obj, null);
	}

	/**
	 * Acts similar to Java's writeObject function.
	 * Will write basic from a class and serialize it to the file
	 * Object must be implementing the HDF5Serializable interface
	 * Any field with the transient tag will be ignored
	 * @param obj The object to be serialized 
	 * @param path The location the datasets will be stored in the file
	 */
	public void writeObject(Object obj, String path) {
		writeObjectHelper(obj, path + "/");
	}

	private <T> void writeList(List list, String name, H5Datatype type) {
		T[] data = (T[]) Array.newInstance(list.get(0).getClass(), list.size());
		
		for (int i = 0; i < list.size(); i++) 
	        data[i] = (T) list.get(i);
		long[] dims = {1, data.length};
		writeData(type, data, dims, name);
	}

	private <T> void writeSet(Set set, String name, H5Datatype type) {
		T[] data = (T[]) Array.newInstance(set.toArray()[0].getClass(), set.size());
		Iterator<T> it = set.iterator();
		int count = 0;
		while(it.hasNext()) {
			data[count] = it.next();
			count++;
		}
		long[] dims = {1, data.length};
		writeData(type, data, dims, name);
	}

	private <T> void writeMap(Map map, String name, Field field) {
		Set set = map.keySet();
		T[] data = (T[]) Array.newInstance(map.get(set.toArray()[0]).getClass(), set.size());
		long[] dims = {1, data.length};
		String[] split = field.getGenericType().toString().split(",");
		
		T[] keyValues = (T[]) Array.newInstance(set.toArray()[0].getClass(), set.size());
		Iterator<T> it = set.iterator();
		int count = 0;
		while(it.hasNext()) {
			keyValues[count] = it.next();
			data[count] = (T) map.get(keyValues[count]);
			count++;
		}	
		
		try {
			H5Group group = (H5Group) file.createGroup("/" + name, null);
			int group_id = group.open();
			Dataset dsetKey = (H5ScalarDS) file.createScalarDS("/" + "key", group, getType(split[0]), dims, null, null, 0, null);
			Dataset dset = (H5ScalarDS) file.createScalarDS("/" + "data", group, getType(split[1]), dims, null, null, 0, null);
			int dataset_id_key = dsetKey.open();
			int dataset_id_data = dset.open();
			dset.write(data);
			dsetKey.write(keyValues);
			dsetKey.close(dataset_id_key);
			dset.close(dataset_id_data);
			group.close(group_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeObjectHelper(Object obj, String group) {
	    if(obj instanceof HDF5Serializable) {
				Class<?> objClass = obj.getClass();
			    Field[] fields = objClass.getFields();
			    String path = "";
			    SerializeClassOptions options = (SerializeClassOptions) objClass.getAnnotation(SerializeClassOptions.class);
			    if(group != null)
			    	path += group + "/";
			    if(options != null) {
			    	path += options.path();
			    	if(options.name().equals(""))
			    		path += obj.getClass().getSimpleName();
			    	else path += "/" + options.name();
			    } else {
			    	path += "/" + obj.getClass().getSimpleName();
			    }
			    createPath(path, defaultPath);
			    for(Field field : fields) {
				    String name = "";
				    long[] dims = {1, 1};
				    String localGroup = "";
			    	Annotation anno = field.getAnnotation(SerializeFieldOptions.class);
			    	SerializeFieldOptions fieldOptions = (SerializeFieldOptions) anno;
			    	if(anno != null) {
			    		name = fieldOptions.name();
			    		localGroup = fieldOptions.path();
			    		dims = fieldOptions.dimensions();
			    	}
			    	if(!Modifier.isTransient(field.getModifiers()) && !field.isAnnotationPresent(Ignore.class)) {
				    	try {
				    		if(name == "") name = field.getName();
				    		if(!localGroup.equals(""))
				    			createPath(localGroup, defaultPath + "/" + path);
				    		name = defaultPath + "/" + path + "/" + localGroup + "/" + name;
				    		String type = field.get(obj).getClass().toString();
//System.out.println("class: " + type + " type: " + field.getGenericType());
					    	if(type.equals("class java.lang.Integer") || type.contains("[I")) {
					    		writeInt(field.get(obj), name);
					    	}
					    	else if(type.equals("class java.lang.Long")) 
					    		writeLong(field.getLong(obj), name);
					    	else if(type.equals("class java.lang.Double")) 
					    		writeDouble(field.getDouble(obj), name);
					    	else if(type.equals("class java.lang.Float")) 
					    		writeFloat(field.getFloat(obj), name);
					    	else if(type.equals("class java.lang.Short")) 
					    		writeShort(field.getShort(obj), name);
					    	else if(type.equals("class [C")) 
					    		writeChar((char[]) field.get(obj), name);
					    	else if(type.equals("class java.lang.Character")) 
					    		writeChar(field.getChar(obj), name);
					    	else if(type.equals("class java.lang.String")) 
					    		writeString((String) field.get(obj), name);
					    	else if(type.equals("class java.lang.Boolean")) 
					    		writeBoolean(field.getBoolean(obj), name);
					    	else if(type.contains("List")) {
					    		writeList((List) field.get(obj), name, getType(field));
					    	} else if(type.contains("Set")) {
					    		writeSet((Set) field.get(obj), name, getType(field));
					    	} else if(type.contains("Map")) {
					    		writeMap((Map) field.get(obj), name, field);
					    	} else if(field.get(obj) instanceof HDF5Serializable && field.getDeclaringClass() != field.getClass()) {
				    			writeObjectHelper(field.get(obj), "/" + path + "/" + localGroup);
				    		}
				    	} catch(IllegalArgumentException | IllegalAccessException | SecurityException e) {
				    		e.printStackTrace();
				    	}
				    }
			    }
		    }
	}
	
	/**
	 * Closes the H5 file.
	 * Run this function when no longer in use
	 */
	public void close() {
		try {
			file.close();
		} catch (HDF5Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Returns the H5Datatype based on a field
	private H5Datatype getType(Field field) {
		String type = "" + field.getGenericType();
		if(type.contains("Integer"))
			return new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
		else if(type.contains("Char")) 
			return new H5Datatype(HDF5Constants.H5T_NATIVE_CHAR);
		else if(type.contains("Short"))
			return new H5Datatype(HDF5Constants.H5T_NATIVE_SHORT);
		else if(type.contains("Double"))
			return new H5Datatype(HDF5Constants.H5T_NATIVE_DOUBLE);
		else if(type.contains("Long"))
			return new H5Datatype(HDF5Constants.H5T_NATIVE_LONG);
		
		return null;
	}
	
	//returns a H5Datatype based on a String
	private H5Datatype getType(String type) {
		if(type.contains("Integer"))
			return new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
		else if(type.contains("Char")) 
			return new H5Datatype(HDF5Constants.H5T_NATIVE_CHAR);
		else if(type.contains("Short"))
			return new H5Datatype(HDF5Constants.H5T_NATIVE_SHORT);
		else if(type.contains("Double"))
			return new H5Datatype(HDF5Constants.H5T_NATIVE_DOUBLE);
		else if(type.contains("Long"))
			return new H5Datatype(HDF5Constants.H5T_NATIVE_LONG);
		
		return null;
	}
	
	
	//Creates a dataset and writes data to it
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
	
	//Creates groups going to a String path, basePath is where to start creating groups
	private void createPath(String path, String basePath) {
		String[] groupPath = path.split("/");
		String base = "";
		if(basePath != null)
			base = "/" + basePath + "/";
		for(String str : groupPath) {
			try {
				file.createGroup(base + str, null);
				base += str + "/";
			} catch (Exception e) {
			//	e.printStackTrace();
				base += str + "/";
			}
		}  
	}
	
}
