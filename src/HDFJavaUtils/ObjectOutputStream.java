package HDFJavaUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
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
	private int maxLength;
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
		H5Datatype type = new H5Datatype(Datatype.CLASS_STRING, obj.length(),
				-1, -1);
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
		// char[] data = {val};
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

	// TODO: support arrays of wrapper classes (Integer, Long, etc.)
	private <T> void writeArray(long[] dimensions, Object obj, String name, int HDF5Datatype) {
		if (HDF5Datatype != -1) {
			Object data;
			if (HDF5Datatype == HDF5Constants.H5T_NATIVE_CHAR) {
				data = Array.newInstance(int.class, getDimensions(obj));
				copyArrayCharToInt(obj, data);
				HDF5Datatype = HDF5Constants.H5T_NATIVE_INT;
			} else if (HDF5Datatype == HDF5Constants.H5T_NATIVE_HBOOL) {
				data = Array.newInstance(int.class, getDimensions(obj));
				copyArrayBoolToInt(obj, data);
				HDF5Datatype = HDF5Constants.H5T_NATIVE_INT;
			} else {
				data = obj;
			}
			final H5Datatype type = new H5Datatype(HDF5Datatype);

			try {
				Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name,
						null, type, dimensions, null, null, 0, null, data);
//				int dset_id = dset.open();
//				H5.H5Dwrite(dset_id, HDF5Datatype, HDF5Constants.H5S_ALL,
//						HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
//				dset.close(dset_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			int[] dims = getDimensions(obj);
			int length = 1;
			for (int d : dims)
				length *= d;
			T[] flat = (T[]) Array.newInstance(getBaseClass(obj), length);
			flattenArray(obj, flat);
			int index = 0;
			ArrayList<Integer> locations = new ArrayList<>();
			locations.add(flat.length);
			for (T i : flat) {
				if (i != null) {
					writeObjectHelper(i, name + "/" + index);
					locations.add(index);
				}
				index++;
			}
			writeArray(new long[] { dims.length }, dims, name + "/"
					+ "dimensions", HDF5Constants.H5T_NATIVE_INT);
			writeList(locations, name + "/" + "info", HDF5Constants.H5T_NATIVE_INT);
		}
	}

	// Writes a list to a dataset
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> void writeList(List list, String name, int HDF5Datatype) {
		if (HDF5Datatype != -1) {
			if (HDF5Datatype == HDF5Constants.H5T_NATIVE_CHAR) {
				list = copyListCharToInt(list);
				HDF5Datatype = HDF5Constants.H5T_NATIVE_INT;
			} else if (HDF5Datatype == HDF5Constants.H5T_NATIVE_HBOOL) {
				list = copyListBoolToInt(list);
				HDF5Datatype = HDF5Constants.H5T_NATIVE_INT;
			}
			H5Datatype type = new H5Datatype(HDF5Datatype);
			T[] data = (T[]) Array.newInstance(list.get(0).getClass(), list.size());
			for (int i = 0; i < list.size(); i++)
				data[i] = (T) list.get(i);
			long[] dims = { data.length };
			writeData(type, data, dims, name);
		}
	}

	// writes a set to a dataset
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> void writeSet(Set set, String name, int HDF5Datatype) {
		if (HDF5Datatype != -1) {
			if (HDF5Datatype == HDF5Constants.H5T_NATIVE_CHAR) {
				set = copySetCharToInt(set);
				HDF5Datatype = HDF5Constants.H5T_NATIVE_INT;
			} else if (HDF5Datatype == HDF5Constants.H5T_NATIVE_HBOOL){
				set = copySetBoolToInt(set);
				HDF5Datatype = HDF5Constants.H5T_NATIVE_INT;
			}
			H5Datatype type = new H5Datatype(HDF5Datatype);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> void writeMap(Map map, String name, Field field) {
		Set keySet = map.keySet();
		List valSet = (List)new ArrayList(map.values());
		long[] dims = { keySet.size() };
		String[] split = field.getGenericType().toString().split(",");
		int datatypeKey = DataTypeUtils.getDataType(split[0]);
		int datatypeValue = DataTypeUtils.getDataType(split[1]);
		if (datatypeKey != -1 && datatypeValue != -1) {
			if (datatypeKey == HDF5Constants.H5T_NATIVE_CHAR) {
				keySet = copySetCharToInt(keySet);
				datatypeKey = HDF5Constants.H5T_NATIVE_INT;
			} else if (datatypeKey == HDF5Constants.H5T_NATIVE_HBOOL) {
				keySet = copySetBoolToInt(keySet);
				datatypeKey = HDF5Constants.H5T_NATIVE_INT;
			}
			if (datatypeValue == HDF5Constants.H5T_NATIVE_CHAR) {
				valSet = copyListCharToInt(valSet);
				datatypeValue = HDF5Constants.H5T_NATIVE_INT;
			} else if (datatypeValue == HDF5Constants.H5T_NATIVE_HBOOL) {
				valSet = copyListBoolToInt(valSet);
				datatypeValue = HDF5Constants.H5T_NATIVE_INT;
			}
			T[] keys = (T[]) Array.newInstance(keySet.toArray()[0].getClass(), (int)dims[0]);
			T[] vals = (T[]) Array.newInstance(valSet.toArray()[0].getClass(), (int)dims[0]);
			Iterator<T> keyItr = keySet.iterator();
			Iterator<T> valItr = valSet.iterator();
			int count = 0;
			while (keyItr.hasNext()) {
				keys[count] = (T)keyItr.next();
				vals[count] = (T)valItr.next();
				count++;
			}
			H5Datatype typeKey = new H5Datatype(datatypeKey);
			H5Datatype typeValue = new H5Datatype(datatypeValue);
			try {
				H5Group group = (H5Group) file.createGroup("/" + name, null);
				int group_id = group.open();
				Dataset dsetKeys = (H5ScalarDS) file.createScalarDS("/" + "keys",
						group, typeKey, dims, null, null, 0, null);
				Dataset dsetVals = (H5ScalarDS) file.createScalarDS("/" + "values",
						group, typeValue, dims, null, null, 0, null);
				int dataset_id_keys = dsetKeys.open();
				int dataset_id_vals = dsetVals.open();
				dsetKeys.write(keys);
				dsetVals.write(vals);
				dsetKeys.close(dataset_id_keys);
				dsetVals.close(dataset_id_vals);
				group.close(group_id);
			} catch (Exception e) {
				e.printStackTrace();
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
				}
				else {
					path += "/" + options.name();
				}
			} else {
				path += "/" + obj.getClass().getSimpleName();
			}
			createPath(path, defaultPath);
			for (Field field : fields) {
				field.setAccessible(true);
				recursiveIterator = 0;
				String name = "";
				long[] dims = { -1 };
//				long[] dims = null;
				String localGroup = "";
//				Annotation anno = field.getAnnotation(SerializeFieldOptions.class);
//				SerializeFieldOptions fieldOptions = (SerializeFieldOptions) anno;
				SerializeFieldOptions fieldOptions = field.getAnnotation(SerializeFieldOptions.class);
//				Annotation[] annos = field.getAnnotations();
				if (fieldOptions != null) {
					System.out.println("annotation present");
					name = fieldOptions.name();
					localGroup = fieldOptions.path();
					System.out.println(localGroup);
					dims = fieldOptions.dimensions();
				}
				if (!Modifier.isTransient(field.getModifiers()) && !field.isAnnotationPresent(Ignore.class)) {
					try {
						if (name.equals("")) {
							name = field.getName();
						}
						if (!localGroup.equals("")) {
							createPath(localGroup, defaultPath + "/" + path);
						}
						name = defaultPath + "/" + path + "/" + localGroup + "/" + name;
						String type = "";
						type = field.get(obj).getClass().toString();
//						System.out.println("class: " + type + " type: " + field.getGenericType());
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
						} else if (type.equals("class java.util.Vector")
								|| type.equals("class java.util.Stack")
								|| type.equals("class java.util.ArrayList")
								|| type.equals("class java.util.LinkedList")) {
							writeList((List) field.get(obj), name, DataTypeUtils.getDataType(field));
						} else if (type.equals("class java.util.HashSet")
								|| type.equals("class java.util.TreeSet")
								|| type.equals("class java.util.LinkedHashSet")) {
							writeSet((Set) field.get(obj), name, DataTypeUtils.getDataType(field));
						} else if (type.equals("class java.util.HashMap")
								|| type.equals("class java.util.concurrent.ConcurrentHashMap")
								|| type.equals("class java.util.concurrent.ConcurrentSkipListMap")
								|| type.equals("class java.util.WeakHashMap")
								|| type.equals("class java.util.TreeMap")
								|| type.equals("class java.util.Hashtable")) {
							writeMap((Map) field.get(obj), name, field);
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
							writeArray(dims, field.get(obj), name, DataTypeUtils.getDataType(field));
						}
					} catch (IllegalArgumentException | IllegalAccessException
							| SecurityException | NullPointerException e) {
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
					type, dims, null, null, 0, null, data);
//			int dataset_id = dset.open();
//			dset.write(data);
//			dset.close(dataset_id);
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
		int ndim = 0;
		while (cl.isArray()) {
			cl = cl.getComponentType();
			ndim++;
		}
		cl = arr.getClass();
		int[] dims = new int[ndim];
		ndim = 0;
		while (cl.isArray()) {
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

	// Converts a Character Array to an int array
	private static void copyArrayCharToInt(Object original, Object copy) {
		int n = Array.getLength(original);
		for (int i = 0; i < n; i++) {
			Object e = Array.get(original, i);
			if (e != null && e.getClass().isArray()) {
				copyArrayCharToInt(e, Array.get(copy, i));
			} else {
				int tmp = (int) Array.getInt(original, i);
				Array.set(copy, i, tmp);
			}
		}
	}

	// Converts a Boolean array to an int array
	private static void copyArrayBoolToInt(Object original, Object copy) {
		int n = Array.getLength(original);
		for (int i = 0; i < n; i++) {
			Object e = Array.get(original, i);
			if (e != null && e.getClass().isArray()) {
				copyArrayBoolToInt(e, Array.get(copy, i));
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
		while (cl.isArray())
			cl = cl.getComponentType();
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
				file.createGroup(base + str, null);
				base += str + "/";
			} catch (Exception e) {
				// e.printStackTrace();
				base += str + "/";
			}
		}
	}

	private void flattenArray(Object original, Object flattened) {
		int n = Array.getLength(original);
		for (int i = 0; i < n; i++) {
			Object e = Array.get(original, i);
			if (e != null && e.getClass().isArray()) {
				flattenArray(e, flattened);
			} else {
				Array.set(flattened, recursiveIterator, Array.get(original, i));
				recursiveIterator++;
			}
		}
	}

	// Returns the max length of all strings in an n-dimensional array
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
	
	private static Set<Integer> copySetCharToInt(Set<Character> set) {
		ArrayList<Integer> list= new ArrayList<Integer>();
		Iterator<Character> itr = set.iterator();
		while (itr.hasNext()) {
			list.add((Integer)((int)((char)itr.next())));
		}
		String type = set.getClass().toString();
		Set<Integer> newSet = new LinkedHashSet<Integer>();
		if (type.equals("class java.util.HashSet")) {
			newSet = new HashSet<Integer>(list);
		} else if (type.equals("class java.util.LinkedHashSet")) {
			newSet = new LinkedHashSet<Integer>(list);
		} else if (type.equals("class java.util.TreeSet")) {
			newSet = new TreeSet<Integer>(list);
		} else {
			newSet.addAll(list);
		}
		return newSet;
	}
	
	private static Set<Integer> copySetBoolToInt(Set<Boolean> set) {
		ArrayList<Integer> list= new ArrayList<Integer>();
		Iterator<Boolean> itr = set.iterator();
		while (itr.hasNext()) {
			int element = ((boolean)itr.next() ? (int)1:(int)0);
			list.add((Integer)element);
		}
		String type = set.getClass().toString();
		Set<Integer> newSet = new LinkedHashSet<Integer>();
		if (type.equals("class java.util.HashSet")) {
			newSet = new HashSet<Integer>(list);
		} else if (type.equals("class java.util.LinkedHashSet")) {
			newSet = new LinkedHashSet<Integer>(list);
		} else if (type.equals("class java.util.TreeSet")) {
			newSet = new TreeSet<Integer>(list);
		} else {
			newSet.addAll(list);
		}
		return newSet;
	}
	
	private static List<Integer> copyListCharToInt(List<Character> list) {
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			tempList.add((Integer)((int)((char)list.get(i))));
		}
		String type = list.getClass().toString();
		List<Integer> newList = new ArrayList<Integer>();
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

	private static List<Integer> copyListBoolToInt(List<Boolean> list) {
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			int element = ((boolean)list.get(i) ? (int)1:(int)0);
			tempList.add((Integer)element);
		}
		String type = list.getClass().toString();
		List<Integer> newList = new ArrayList<Integer>();
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
