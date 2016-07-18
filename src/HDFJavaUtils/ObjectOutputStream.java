package HDFJavaUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import HDFJavaUtils.interfaces.HDF5Serializable;
import HDFJavaUtils.interfaces.Ignore;
import HDFJavaUtils.interfaces.SerializeClassOptions;
import HDFJavaUtils.interfaces.SerializeFieldOptions;
import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;
import ncsa.hdf.object.Attribute;
import ncsa.hdf.object.Dataset;
import ncsa.hdf.object.Datatype;
import ncsa.hdf.object.Group;
import ncsa.hdf.object.HObject;
import ncsa.hdf.object.h5.H5Datatype;
import ncsa.hdf.object.h5.H5File;
import ncsa.hdf.object.h5.H5ScalarDS;

/**
 * The HDF5Util's ObjectOutputStream is a mirror to Java's ObjectOutputStream
 * The program gives the user tools to serialize a class to a HDF file
 * 
 * @author Ben Bressette
 * @author Shreya Ravi
 * @version 0.1
 */
public class ObjectOutputStream {

	private H5File file;
	private String defaultPath;
	private int recursiveIterator;

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
	public void writeInt(Object val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
		int[] data = { (int) val };
		long[] dims = { 1 };
		writeData(type, data, dims, name);
	}

	/**
	 * Creates a new dataset and writes a single String object to it
	 * 
	 * @param obj
	 *            The String being written to a dataset
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
	 * Creates a new dataset and writes a single byte value to it
	 * 
	 * @param val
	 *            The value being written to a dataset
	 * @param name
	 *            The name of the dataset
	 */
	public void writeByte(byte val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_INT8);
		byte[] data = { val };
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
			e.printStackTrace();
		}
	}

	// Writes an array to a dataset
	private <T> void writeArray(long[] dimensions, Object obj, String name) {
		H5Datatype atrType = DataTypeUtils.getType("Integer");
		int id = DataTypeUtils.getDataType(obj);
		if (id != -1) {
			if (id == HDF5Constants.H5T_NATIVE_CHAR) {
				obj = copyArrayCharToInt(obj);
				id = HDF5Constants.H5T_NATIVE_INT;
			} else if (id == HDF5Constants.H5T_NATIVE_HBOOL) {
				obj = copyArrayBoolToInt(obj);
				id = HDF5Constants.H5T_NATIVE_INT;
			}
			final H5Datatype type = new H5Datatype(id);

			try {
				Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null, type, dimensions,
						null, null, 0, null, obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int[] intDims = longToIntArr(dimensions);
			Attribute dimsAttr = new Attribute("dims", atrType, new long[] {intDims.length}, intDims);
			writeAttribute(name, dimsAttr);
		} else {
			int[] dims = getDimensions(obj);
			T[] flat = (T[])flattenArray(obj, dims);
			int index = 0;
			ArrayList<Integer> locations = new ArrayList<>();
			createPath(name, null);
			for (T i : flat) {
				if (i != null) {
					Class<?> elementType = i.getClass();
					if (elementType.isArray()) {
						writeArray(intToLongArr(getDimensions(i)), i, name + "/" + index);
					} else if (Collection.class.isAssignableFrom(elementType)) {
						if (List.class.isAssignableFrom(elementType))
							writeList((List) i, name + "/" + index);
						else if (Set.class.isAssignableFrom(elementType))
							writeSet((Set) i, name + "/" + index);
					} else if (Map.class.isAssignableFrom(elementType)) {
						writeMap((Map) i, name + "/" + index);
					} else {
						writeObjectHelper(i, name + "/" + index);
					}
					locations.add(index);
					String className = elementType.getName();
					Datatype attrType = new H5Datatype(Datatype.CLASS_STRING, className.length() + 1, -1, -1);
					Attribute classAttr = new Attribute("class", attrType, new long[] {1}, new String[] {className});
					writeAttribute(name +"/" + index, classAttr);
				}
				index++;
			}
			int loc[] = arrListToIntArr(locations);
			Attribute dimsAttr = new Attribute("dims", atrType, new long[] {dims.length}, dims);
			Attribute locationsAttr = new Attribute("locations", atrType, new long[] {locations.size()}, loc);
			writeAttribute(name, dimsAttr, locationsAttr);
		}
	}

	// Writes a list to a dataset
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> void writeList(List list, String name) {
		Class<?> compType = null;
		for (Object l : list) {
			if (l != null) {
				compType = l.getClass();
				break;
			}
		}
		int id = DataTypeUtils.getDataType(compType);
		if (id != -1) {
			if (compType.equals(Character.class)) {
				list = copyListCharToInt(list);
				id = HDF5Constants.H5T_NATIVE_INT;
				compType = Integer.class;
			} else if (compType.equals(Boolean.class)) {
				list = copyListBoolToInt(list);
				id = HDF5Constants.H5T_NATIVE_INT;
				compType = Integer.class;
			}
			H5Datatype type = new H5Datatype(id);
			T[] data = (T[]) Array.newInstance(compType, list.size());
			for (int i = 0; i < list.size(); i++)
				data[i] = (T) list.get(i);
			writeData(type, data, new long[] {data.length}, name);
		} else if (compType != null) {
			createPath(name, "");
			ArrayList<Integer> locations = new ArrayList<Integer>();
			for (int i = 0; i < list.size(); i++) {
				Object comp = list.get(i);
				if (comp != null) {
					compType = comp.getClass();
					if (compType.isArray()) {
						writeArray(new long[] { Array.getLength(comp) }, comp, name + "/" + i);
					} else if (List.class.isAssignableFrom(compType)) {
						writeList((List) comp, name + "/" + i);
					} else if (Set.class.isAssignableFrom(compType)) {
						writeSet((Set) comp, name + "/" + i);
					} else if (Map.class.isAssignableFrom(compType)) {
						writeMap((Map) comp, name + "/" + i);
					} else {
						writeObjectHelper(comp, name + "/" + i);
					}
					locations.add(i);
					String className = compType.getName();
					Datatype attrType = new H5Datatype(Datatype.CLASS_STRING, className.length() + 1, -1, -1);
					Attribute classAttr = new Attribute("class", attrType, new long[] {1}, new String[] {className});
					writeAttribute(name +"/" + i, classAttr);
				}
			}
			H5Datatype atrType = DataTypeUtils.getType("Integer");
			int loc[] = arrListToIntArr(locations);
			Attribute locationsAttr = new Attribute("locations", atrType, new long[] {locations.size()}, loc);
			writeAttribute(name, locationsAttr);
		}
	}

	// writes a set to a dataset
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> void writeSet(Set set, String name) {
		Iterator<T> it = set.iterator();
		Class<?> compType = null;
		for (Object s : set) {
			if (s != null) {
				compType = s.getClass();
				break;
			}
		}
		int id = DataTypeUtils.getDataType(compType);
		if (id != -1) {
			if (compType.equals(Character.class)) {
				set = copySetCharToInt(set);
				id = HDF5Constants.H5T_NATIVE_INT;
				compType = Integer.class;
			} else if (compType.equals(Boolean.class)) {
				set = copySetBooltoInt(set);
				id = HDF5Constants.H5T_NATIVE_INT;
				compType = Integer.class;
			}
			H5Datatype type = new H5Datatype(id);
			T[] data = (T[]) Array.newInstance(compType, set.size());
			int count = 0;
			while (it.hasNext()) {
				data[count] = it.next();
				count++;
			}
			writeData(type, data, new long [] {data.length}, name);
		} else if (compType != null) {
			createPath(name, "");
			ArrayList<Integer> locations = new ArrayList<Integer>();
			int i = 0;
			while (it.hasNext()) {
				if (it.next() != null) {
					Object obj = it.next();
					if (compType.isArray()) {
						writeArray(new long[] { Array.getLength(obj) }, obj, name + "/" + i);
					} else if (List.class.isAssignableFrom(compType)) {
						writeList((List) obj, name + "/" + i);
					} else if (Set.class.isAssignableFrom(compType)) {
						writeSet((Set) obj, name + "/" + i);
					} else if (Map.class.isAssignableFrom(compType)) {
						writeMap((Map) obj, name + "/" + i);
					}
					locations.add(i);
					String className = compType.getName();
					Datatype attrType = new H5Datatype(Datatype.CLASS_STRING, className.length() + 1, -1, -1);
					Attribute classAttr = new Attribute("class", attrType, new long[] {1}, new String[] {className});
					writeAttribute(name +"/" + i, classAttr);
				}
				i++;
			}
			H5Datatype atrType = DataTypeUtils.getType("Integer");
			int loc[] = arrListToIntArr(locations);
			Attribute locationsAttr = new Attribute("locations", atrType, new long[] {locations.size()}, loc);
			writeAttribute(name, locationsAttr);
		}
	}

	// Writes a map to a dataset
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> void writeMap(Map map, String name) {
		Set set = map.keySet();
		Class<?> valClass = map.get(set.toArray()[0]).getClass();
		Class<?> keyClass = set.toArray()[0].getClass();
		if (valClass != null && keyClass != null) {
			createPath(name, null);
			int keyID = DataTypeUtils.getDataType(keyClass);
			int valID = DataTypeUtils.getDataType(valClass);
			T[] keys = (T[]) Array.newInstance(keyClass, set.size());
			T[] vals = (T[]) Array.newInstance(valClass, set.size());
			long[] dims = { vals.length };
			Iterator<T> it = set.iterator();
			int count = 0;
			while (it.hasNext()) {
				keys[count] = it.next();
				vals[count] = (T) map.get(keys[count]);
				count++;
			}
			if (keyID != -1) {
				if (keyClass.equals(Character.class)) {
					keys = (T[])copyArrayCharToInt(keys);
					keyID = HDF5Constants.H5T_NATIVE_INT;
				} else if (keyClass.equals(Boolean.class)) {
					keys = (T[])copyArrayBoolToInt(keys);
					keyID = HDF5Constants.H5T_NATIVE_INT;
				}
				H5Datatype typeKey = new H5Datatype(keyID);
				writeData(typeKey, keys, dims, name + "/" + "keys");
			} else {
				ArrayList<Integer> locations = new ArrayList<Integer>();
				createPath("keys", name);
				for (int i = 0; i < keys.length; i++) {
					if (keys[i] != null) {
						Object comp = keys[i];
						if (keyClass.isArray()) {
							writeArray(new long[] { Array.getLength(comp) }, comp, name + "/keys/" + i);
						} else if (List.class.isAssignableFrom(keyClass)) {
							writeList((List) comp, name + "/keys/" + i);
						} else if (Set.class.isAssignableFrom(keyClass)) {
							writeSet((Set) comp, name + "/keys/" + i);
						} else if (Map.class.isAssignableFrom(keyClass)) {
							writeMap((Map) comp, name + "/keys/" + i);
						} else {
							writeObjectHelper(comp, name + "/keys/" + i);
						}
						locations.add(i);
						String className = comp.getClass().getName();
						Datatype attrType = new H5Datatype(Datatype.CLASS_STRING, className.length() + 1, -1, -1);
						Attribute classAttr = new Attribute("class", attrType, new long[] {1}, new String[] {className});
						writeAttribute(name +"/" + i, classAttr);
					}
				}
				H5Datatype atrType = DataTypeUtils.getType("Integer");
				int loc[] = arrListToIntArr(locations);
				Attribute locationsAttr = new Attribute("locations", atrType, new long[] {locations.size()}, loc);
				writeAttribute(name, locationsAttr);
			}
			if (valID != -1) {
				if (valClass.equals(Character.class)) {
					vals = (T[])copyArrayCharToInt(vals);
					valID = HDF5Constants.H5T_NATIVE_INT;
				} else if (valClass.equals(Boolean.class)) {
					vals = (T[])copyArrayBoolToInt(vals);
					valID = HDF5Constants.H5T_NATIVE_INT;
				}
				H5Datatype typeValue = new H5Datatype(valID);
				writeData(typeValue, vals, dims, name + "/" + "values");
			} else {
				ArrayList<Integer> locations = new ArrayList<Integer>();
				createPath("values", name);
				for (int i = 0; i < vals.length; i++) {
					if (vals[i] != null) {
						Object comp = vals[i];
						if (valClass.isArray()) {
							writeArray(new long[] { Array.getLength(comp) }, comp, name + "/values/" + i);
						} else if (List.class.isAssignableFrom(valClass)) {
							writeList((List) comp, name + "/values/" + i);
						} else if (Set.class.isAssignableFrom(valClass)) {
							writeSet((Set) comp, name + "/values/" + i);
						} else if (Map.class.isAssignableFrom(valClass)) {
							writeMap((Map) comp, name + "/values/" + i);
						} else {
							writeObjectHelper(comp, name + "/values/" + i);
						}
						locations.add(i);
						String className = comp.getClass().getName();
						Datatype attrType = new H5Datatype(Datatype.CLASS_STRING, className.length() + 1, -1, -1);
						Attribute classAttr = new Attribute("class", attrType, new long[] {1}, new String[] {className});
						writeAttribute(name +"/" + i, classAttr);
					}
				}
				H5Datatype atrType = DataTypeUtils.getType("Integer");
				int loc[] = arrListToIntArr(locations);
				Attribute locationsAttr = new Attribute("locations", atrType, new long[] {locations.size()}, loc);
				writeAttribute(name, locationsAttr);
			}
		}
	}

	// Writes the actual object to file
	@SuppressWarnings("rawtypes")
	private <T> void writeObjectHelper(Object obj, String group) {
		if (obj instanceof HDF5Serializable && obj != null) {
			Class<?> objClass = obj.getClass();
			Field[] fields = objClass.getDeclaredFields();
			String path = "";
			SerializeClassOptions options = (SerializeClassOptions) objClass.getAnnotation(SerializeClassOptions.class);
			if (group != null) {
				path += group + "/";
			}
			if (options != null) {
				path += options.path();
				if (options.name().equals("")) {
					path += obj.getClass().getSimpleName();
				} else {
					path += "/" + options.name();
				}
			} else {
				path += "/" + obj.getClass().getSimpleName();
			}
			createPath(path, defaultPath);
			for (Field field : fields) {
				field.setAccessible(true);
				String name = "";
				long[] dims = { -1 };
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
						if (name == "") {
							name = field.getName();
						}
						if (!localGroup.equals("")) {
							createPath(localGroup, defaultPath + "/" + path);
						}
						name = defaultPath + "/" + path + "/" + localGroup + "/" + name;
						String type = "";
						type = field.get(obj).getClass().toString();
						// System.out.println("class: " + type + " type: " +
						// field.getGenericType());
						if (type.equals("class java.lang.Integer")) {
							writeInt(field.get(obj), name);
						} else if (type.equals("class java.lang.Long")) {
							writeLong(field.getLong(obj), name);
						} else if (type.equals("class java.lang.Double")) {
							writeDouble(field.getDouble(obj), name);
						} else if (type.equals("class java.lang.Float")) {
							writeFloat(field.getFloat(obj), name);
						} else if (type.equals("class java.lang.Short")) {
							writeShort(field.getShort(obj), name);
						} else if (type.equals("class java.lang.Character")) {
							writeChar(field.getChar(obj), name);
						} else if (type.equals("class java.lang.String")) {
							writeString((String) field.get(obj), name);
						} else if (type.equals("class java.lang.Boolean")) {
							writeBoolean(field.getBoolean(obj), name);
						} else if (type.equals("class java.lang.Byte")) {
							writeByte(field.getByte(obj), name);
						} else if (List.class.isAssignableFrom(field.get(obj).getClass())) {
							writeList((List) field.get(obj), name);
						} else if (Set.class.isAssignableFrom(field.get(obj).getClass())) {
							writeSet((Set) field.get(obj), name);
						} else if (Map.class.isAssignableFrom(field.get(obj).getClass())) {
							writeMap((Map) field.get(obj), name);
						} else if (field.get(obj) instanceof HDF5Serializable
								&& field.getDeclaringClass() != field.getClass()) {
							writeObjectHelper(field.get(obj), "/" + path + "/" + localGroup);
						} else if (type.contains("[")) {
							if (dims[0] == -1) {
								int[] temp = getDimensions(field.get(obj));
								dims = new long[temp.length];
								for (int i = 0; i < temp.length; i++) {
									dims[i] = (long) temp[i];
								}
							}
							writeArray(dims, field.get(obj), name);
						}
					} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {

					}
				}
			}
		}
	}

	// Creates a dataset and writes data to it
	private void writeData(Datatype type, Object data, long[] dims, String name) {
		try {
			Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null, type, dims, null, null, 0, null, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Returns the dimensions of an n-dimensional array
	private static int[] getDimensions(Object arr) {
		if (arr == null)
			return null;
		Object obj = arr;
		Class<?> cl = arr.getClass();
		 int ndim = obj.getClass().toString().length() - obj.getClass().toString().replace("[", "").length();
		//int ndim = 0;
//		while (cl.isArray()) {
//			cl = cl.getComponentType();
//			ndim++;
//		}
		cl = arr.getClass();
		int[] dims = new int[ndim];
		ndim = 0;
		for (int i : dims) {
			dims[ndim] = -1;
			if (obj != null) {
				int length = Array.getLength(obj);
				if (length > 0) {
					dims[ndim] = length;
					obj = Array.get(obj, 0);
				} else {
					dims[ndim] = 0;
					obj = null;
				}
			}
			cl = cl.getComponentType();
			ndim++;
		}
		return dims;
	}
	
	private static Object copyArrayCharToInt(Object original) {
		Object copy = Array.newInstance(int.class, getDimensions(original));
		copyArrayCharToIntHelper(original, copy);
		return copy;
	}
	
	private static Object copyArrayBoolToInt(Object original) {
		Object copy = Array.newInstance(int.class, getDimensions(original));
		copyArrayBoolToIntHelper(original, copy);
		return copy;
	}
	
	// Converts a Character Array to an int array
	private static void copyArrayCharToIntHelper(Object original, Object copy) {
		int n = Array.getLength(original);
		for (int i = 0; i < n; i++) {
			Object e = Array.get(original, i);
			if (e != null && e.getClass().isArray()) {
				copyArrayCharToIntHelper(e, Array.get(copy, i));
			} else {
				int tmp = (int) Array.getInt(original, i);
				Array.set(copy, i, tmp);
			}
		}
	}

	// Converts a Boolean array to an int array
	private static void copyArrayBoolToIntHelper(Object original, Object copy) {
		int n = Array.getLength(original);
		for (int i = 0; i < n; i++) {
			Object e = Array.get(original, i);
			if (e != null && e.getClass().isArray()) {
				copyArrayBoolToIntHelper(e, Array.get(copy, i));
			} else {
				int tmp = Array.getBoolean(original, i) ? 1 : 0;
				Array.set(copy, i, tmp);
			}
		}
	}

	private static Class<?> getBaseClass(Object obj) {
		if (obj == null)
			return Void.TYPE;
		Class<?> cl = obj.getClass();
		int size = cl.toString().length() - cl.toString().replace("[", "").length();
		while (size > 0) {
			cl = cl.getComponentType();
			size--;
		}
		return cl;
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
				Group grp = file.createGroup(base + str, null);
				int test = H5.H5Gopen(file.getFID(), base + str, HDF5Constants.H5P_DEFAULT);

				base += str + "/";
			} catch (Exception e) {
				// e.printStackTrace();
				base += str + "/";
			}
		}
	}

	private void writeAttribute(String name, Attribute ...attr) {
		HObject obj = null;
		try {
			obj = file.get(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (obj != null) {
			try {
				for(Attribute a : attr)
					file.writeAttribute(obj, a, false);
			} catch (HDF5Exception e) {
				e.printStackTrace();
			}
		} 
	}
	
	private int[] longToIntArr(long[] longArr) {
		int[] intArr = new int[longArr.length];
		for (int i = 0; i < longArr.length; i++) {
			intArr[i] = (int) longArr[i];
		}
		return intArr;
	}
	
	private long[] intToLongArr(int[] intArr) {
		long[] longArr = new long[intArr.length];
		for (int i = 0; i < intArr.length; i++) {
			longArr[i] = (long) intArr[i];
		}
		return longArr;
	}
	
	private int[] arrListToIntArr(ArrayList<Integer> arrList) {
		int[] intArr = new int[arrList.size()];
		for (int i = 0; i < intArr.length; i++) {
			intArr[i] = arrList.get(i);
		}
		return intArr;
	}

	private <T> Object flattenArray(Object original, int[] dims) {
		recursiveIterator = 0;
		int length = 1;
		for (int d: dims) {
			length *= d;
		}
		T[] flat = (T[]) Array.newInstance(getBaseClass(original), length);
		flattenArrayRecursiveHelper(original, flat, dims.length);
		return flat;
	}

	private void flattenArrayRecursiveHelper(Object original, Object flattened, int count) {
		int n = Array.getLength(original);
		for (int i = 0, max = count; i < n; i++, count = max) {
			Object e = Array.get(original, i);
			count--;
			if (e != null && count > 0) {
				flattenArrayRecursiveHelper(e, flattened, count);
			} else {
				Array.set(flattened, recursiveIterator, Array.get(original, i));
				recursiveIterator++;
			}
		}
	}

	private Set<Integer> copySetCharToInt(Set<Character> set) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Iterator<Character> itr = set.iterator();
		while (itr.hasNext()) {
			list.add((Integer) ((int) ((char) itr.next())));
		}
		String type = set.getClass().toString();
		Set<Integer> newSet = null;
		if (type.equals("class java.util.HashSet")) {
			newSet = new HashSet<Integer>(list);
		} else if (type.equals("class java.util.LinkedHashSet")) {
			newSet = new LinkedHashSet<Integer>(list);
		} else if (type.equals("class java.util.TreeSet")) {
			newSet = new TreeSet<Integer>(list);
		}
		return newSet;
	}

	private Set<Integer> copySetBooltoInt(Set<Boolean> set) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Iterator<Boolean> itr = set.iterator();
		while (itr.hasNext()) {
			int element = ((boolean) itr.next() ? (int) 1 : (int) 0);
			list.add((Integer) element);
		}
		String type = set.getClass().toString();
		Set<Integer> newSet = null;
		if (type.equals("class java.util.HashSet"))
			newSet = new HashSet<Integer>(list);
		else if (type.equals("class java.util.LinkedHashSet"))
			newSet = new LinkedHashSet<Integer>(list);
		else if (type.equals("class java.util.TreeSet"))
			newSet = new TreeSet<Integer>(list);
		return newSet;
	}

	private List<Integer> copyListCharToInt(List<Character> list) {
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			tempList.add((Integer) ((int) ((char) list.get(i))));
		}
		String type = list.getClass().toString();
		List<Integer> newList = null;
		if (type.equals("class java.util.ArrayList")) {
			newList = new ArrayList<Integer>(tempList);
		} else if (type.equals("class java.util.LinkedList")) {
			newList = new LinkedList<Integer>(tempList);
		} else if (type.equals("class java.util.Vector")) {
			newList = new Vector<Integer>(tempList);
		} else if (type.equals("class java.util.Stack")) {
			newList = new Stack<Integer>();
			newList.addAll(tempList);
		}
		return newList;
	}

	private List<Integer> copyListBoolToInt(List<Boolean> list) {
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			int element = ((boolean) list.get(i) ? (int) 1 : (int) 0);
			tempList.add((Integer) element);
		}
		String type = list.getClass().toString();
		List<Integer> newList = null;
		if (type.equals("class java.util.ArrayList")) {
			newList = new ArrayList<Integer>(tempList);
		} else if (type.equals("class java.util.LinkedList")) {
			newList = new LinkedList<Integer>(tempList);
		} else if (type.equals("class java.util.Vector")) {
			newList = new Vector<Integer>(tempList);
		} else if (type.equals("class java.util.Stack")) {
			newList = new Stack<Integer>();
			newList.addAll(tempList);
		}
		return newList;
	}

}
