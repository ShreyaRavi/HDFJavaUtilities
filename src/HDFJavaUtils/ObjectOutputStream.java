package HDFJavaUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ncsa.hdf.hdf5lib.H5;
import HDFJavaUtils.interfaces.HDF5Serializable;
import HDFJavaUtils.interfaces.Ignore;
import HDFJavaUtils.interfaces.SerializeClassOptions;
import HDFJavaUtils.interfaces.SerializeFieldOptions;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;
import ncsa.hdf.object.Attribute;
import ncsa.hdf.object.Dataset;
import ncsa.hdf.object.Datatype;
import ncsa.hdf.object.h5.H5Datatype;
import ncsa.hdf.object.h5.H5File;
import ncsa.hdf.object.h5.H5Group;
import ncsa.hdf.object.h5.H5ScalarDS;

/**
 * The HDF5Util's ObjectOutputStream is a mirror to Java's ObjectOutputStream
 * The program gives the user tools to serialize a class to a HDF file
 * 
 * @author Ben Bressette
 * @version 0.1
 */
public class ObjectOutputStream {

	private H5File file;
	private String defaultPath;

	/**
	 * Constructor for the class, the user is required to input a H5File
	 * 
	 * @param file
	 *            The H5File being accessed within the program
	 */
	public ObjectOutputStream(H5File file) {
		this.file = file;
		defaultPath = "";
	}

	/**
	 * Constructor for the class, the user is required to input a H5File
	 * 
	 * @param file
	 *            The H5File being accessed within the program
	 * @param groupPath
	 *            represents the root directory the program will use in the file
	 */
	public ObjectOutputStream(H5File file, String groupPath) {
		this.file = file;
		defaultPath = groupPath;
		createPath(groupPath, null);
	}

	/**
	 * Creates a new dataset and writes a single Double value to it
	 * 
	 * @param val
	 *            The value being written to a dataset
	 * @param name
	 *            The name of the dataset
	 */
	public void writeDouble(double val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_DOUBLE);
		double[] data = { val };
		long[] dims = { 1 };
		writeData(type, data, dims, name);
	}

	/**
	 * Creates a new dataset and writes a single Integer value to it
	 * 
	 * @param val
	 *            The value being written to a dataset
	 * @param name
	 *            The name of the dataset
	 */
	public void writeInt(Object obj, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
		int[] data = { (int) obj };
		long[] dims = { 1 };
		writeData(type, data, dims, name);
	}

	public void writeArray(long[] dimensions, Object obj, String name, int HDF5Datatype) {
		
		final H5Datatype type = new H5Datatype(HDF5Datatype);
		try {
			Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null, type, dimensions, null, null, 0, null);
			int dset_id = dset.open();
			H5.H5Dwrite(dset_id, HDF5Datatype, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, obj);
			dset.close(dset_id);  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new dataset and writes a single String object to it
	 * 
	 * @param val
	 *            The value being written to a dataset
	 * @param name
	 *            The name of the dataset
	 */
	public void writeString(String obj, String name) {
		H5Datatype type = new H5Datatype(Datatype.CLASS_STRING, obj.length(), -1, -1);
		long[] dims = { 1 };
		String[] data = { obj };
		writeData(type, data, dims, name);
	}

	/**
	 * Creates a new dataset and writes a single Integer value to it
	 * 
	 * @param val
	 *            The value being written to a dataset
	 * @param name
	 *            The name of the dataset
	 */
	public void writeFloat(float val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_FLOAT);
		float[] data = { val };
		long[] dims = { 1 };
		writeData(type, data, dims, name);
	}

	/**
	 * Creates a new dataset and writes a single Boolean value to it
	 * 
	 * @param val
	 *            The value being written to a dataset
	 * @param name
	 *            The name of the dataset
	 */
	public void writeBoolean(boolean val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_HBOOL);
		int[] data = { val ? 1 : 0 };
		long[] dims = { 1 };
		writeData(type, data, dims, name);
	}

	/**
	 * Creates a new dataset and writes a single Long value to it
	 * 
	 * @param val
	 *            The value being written to a dataset
	 * @param name
	 *            The name of the dataset
	 */
	public void writeLong(long val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_LONG);
		long[] data = { val };
		long[] dims = { 1 };
		writeData(type, data, dims, name);
	}

	/**
	 * Creates a new dataset and writes a single Short value to it
	 * 
	 * @param val
	 *            The value being written to a dataset
	 * @param name
	 *            The name of the dataset
	 */
	public void writeShort(short val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_SHORT);
		short[] data = { val };
		long[] dims = { 1 };
		writeData(type, data, dims, name);
	}

	/**
	 * Creates a new dataset and writes a single Character value to it
	 * 
	 * @param val
	 *            The value being written to a dataset
	 * @param name
	 *            The name of the dataset
	 */
	public void writeChar(char val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_CHAR);
		int[] data = { val };
//		char[] data = {val};
		long[] dims = { 1 };
		writeData(type, data, dims, name);
	}

	/**
	 * Creates a new dataset and writes a single Character array to it
	 * 
	 * @param val
	 *            The value being written to a dataset
	 * @param name
	 *            The name of the dataset
	 */
	public void writeChar(char[] val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
		long[] dims = { val.length };
		int[] data = new int[val.length];
		for (int i = 0; i < val.length; i++)
			data[i] = (int) val[i];
		writeData(type, data, dims, name);
	}

	private int maxLength;

	private int maxStringLength(Object obj) {
		int n = Array.getLength(obj);
		for (int i = 0; i < n; i++) {
			Object arr = Array.get(obj, i);
			if (arr != null && arr.getClass().isArray()) {
				maxStringLength(arr);
			} else {
				int strLength = ((String) arr).length();
				maxLength = Math.max(maxLength, strLength);
			}
		}
		return maxLength;
	}

	/**
	 * Acts similar to Java's writeObject function. Will write basic data from a
	 * class and serialize it to the file Object must be implementing the
	 * HDF5Serializable interface Any field with the transient tag will be
	 * ignored
	 * 
	 * @param obj
	 *            The object to be serialized
	 */
	public void writeObject(Object obj) {
		writeObjectHelper(obj, null);
	}

	/**
	 * Acts similar to Java's writeObject function. Will write basic from a
	 * class and serialize it to the file Object must be implementing the
	 * HDF5Serializable interface Any field with the transient tag will be
	 * ignored
	 * 
	 * @param obj
	 *            The object to be serialized
	 * @param path
	 *            The location the datasets will be stored in the file
	 */
	public void writeObject(Object obj, String path) {
		writeObjectHelper(obj, path + "/");
	}

	/**
	 * Closes the H5 file. Run this function when no longer in use
	 */
	public void close() {
		try {
			file.close();
		} catch (HDF5Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Writes a list to a dataset
	private <T> void writeList(List list, String name, H5Datatype type) {
		if (type != null) {
			T[] data = (T[]) Array.newInstance(list.get(0).getClass(), list.size());
			for (int i = 0; i < list.size(); i++)
				data[i] = (T) list.get(i);
			long[] dims = { data.length };
			writeData(type, data, dims, name);
		}
	}

	// writes a set to a dataset
	private <T> void writeSet(Set set, String name, H5Datatype type) {
		if (type != null) {
			T[] data = (T[]) Array.newInstance(set.toArray()[0].getClass(), set.size());
			Iterator<T> it = set.iterator();
			int count = 0;
			while (it.hasNext()) {
				data[count] = it.next();
				count++;
			}
			long[] dims = { data.length };
			writeData(type, data, dims, name);
		}
	}

	// Writes a map to a dataset
	private <T> void writeMap(Map map, String name, Field field) {
		Set set = map.keySet();
		T[] data = (T[]) Array.newInstance(map.get(set.toArray()[0]).getClass(), set.size());
		long[] dims = { data.length };
		String[] split = field.getGenericType().toString().split(",");
		H5Datatype typeKey = DataTypeUtils.getType(split[0], false);
		H5Datatype typeData = DataTypeUtils.getType(split[1], false);
		if (typeKey != null && typeData != null) {
			T[] keyValues = (T[]) Array.newInstance(set.toArray()[0].getClass(), set.size());
			Iterator<T> it = set.iterator();
			int count = 0;
			while (it.hasNext()) {
				keyValues[count] = it.next();
				data[count] = (T) map.get(keyValues[count]);
				count++;
			}

			try {
				H5Group group = (H5Group) file.createGroup("/" + name, null);
				int group_id = group.open();
				Dataset dsetKey = (H5ScalarDS) file.createScalarDS("/" + "key", group, typeKey, dims, null, null, 0, null);
				Dataset dset = (H5ScalarDS) file.createScalarDS("/" + "data", group, typeData, dims, null, null, 0, null);
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
	}

	// Writes the actual object to file
	private <T> void writeObjectHelper(Object obj, String group) {
		if (obj instanceof HDF5Serializable) {
			Class<?> objClass = obj.getClass();
			Field[] fields = objClass.getFields();
			String path = "";
			SerializeClassOptions options = (SerializeClassOptions) objClass.getAnnotation(SerializeClassOptions.class);
			if (group != null)
				path += group + "/";
			if (options != null) {
				path += options.path();
				if (options.name().equals(""))
					path += obj.getClass().getSimpleName();
				else
					path += "/" + options.name();
			} else {
				path += "/" + obj.getClass().getSimpleName();
			}
			createPath(path, defaultPath);
			for (Field field : fields) {
				String name = "";
				long[] dims = { 1 };
				String localGroup = "";
				Annotation anno = field.getAnnotation(SerializeFieldOptions.class);
				SerializeFieldOptions fieldOptions = (SerializeFieldOptions) anno;
				if (anno != null) {
					name = fieldOptions.name();
					localGroup = fieldOptions.path();
					dims = fieldOptions.dimensions();
				}
				if (!Modifier.isTransient(field.getModifiers()) && !field.isAnnotationPresent(Ignore.class)) {
					try {
						if (name == "")
							name = field.getName();
						if (!localGroup.equals(""))
							createPath(localGroup, defaultPath + "/" + path);
						name = defaultPath + "/" + path + "/" + localGroup
								+ "/" + name;
						String type = field.get(obj).getClass().toString();
						// System.out.println("class: " + type + " type: " +
						// field.getGenericType());
						if (type.equals("class java.lang.Integer"))
							writeInt(field.get(obj), name);
						else if (type.equals("class java.lang.Long"))
							writeLong(field.getLong(obj), name);
						else if (type.equals("class java.lang.Double"))
							writeDouble(field.getDouble(obj), name);
						else if (type.equals("class java.lang.Float"))
							writeFloat(field.getFloat(obj), name);
						else if (type.equals("class java.lang.Short"))
							writeShort(field.getShort(obj), name);
						else if (type.equals("class java.lang.Character"))
							writeChar(field.getChar(obj), name);
						else if (type.equals("class java.lang.String"))
							writeString((String) field.get(obj), name);
						else if (type.equals("class java.lang.Boolean"))
							writeBoolean(field.getBoolean(obj), name);
						else if (type.contains("List")) {
							writeList((List) field.get(obj), name, DataTypeUtils.getType(field));
						} else if (type.contains("Set")) {
							writeSet((Set) field.get(obj), name, DataTypeUtils.getType(field));
						} else if (type.contains("Map")) {
							writeMap((Map) field.get(obj), name, field);
						} else if (field.get(obj) instanceof HDF5Serializable && field.getDeclaringClass() != field.getClass()) {
							writeObjectHelper(field.get(obj), "/" + path + "/" + localGroup);
						} else if (type.contains("[")) {
							long[] dimens;
							if (type.contains("[[[[I")) {
								dimens = new long[4];
								dimens[0] = 4;
								dimens[1] = 3;
								dimens[2] = 2;
								dimens[3] = 2;
							} else if (type.contains("[I")){
								dimens = new long[1];
								dimens[0] = 5;
							} else {
								dimens = new long[1];
							}
							
							writeArray(dimens, field.get(obj), name, DataTypeUtils.getDataType(field, obj));
//							writeArray(dims, field.get(obj), name); -- with annotations
						}
					} catch (IllegalArgumentException | IllegalAccessException
							| SecurityException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// Creates a dataset and writes data to it
	private void writeData(Datatype type, Object data, long[] dims, String name) {
		try {
			Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null,
					type, dims, null, null, 0, null);
			int dataset_id = dset.open();
			dset.write(data);
			dset.close(dataset_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Creates groups going to a String path, basePath is where to start
	// creating groups
	private void createPath(String path, String basePath) {
		String[] groupPath = path.split("/");
		String base = "";
		if (basePath != null)
			base = "/" + basePath + "/";
		for (String str : groupPath) {
			try {
				file.createGroup(base + str, null);
				base += str + "/";
			} catch (Exception e) {
				// e.printStackTrace();
				base += str + "/";
			}
		}
	}

}
