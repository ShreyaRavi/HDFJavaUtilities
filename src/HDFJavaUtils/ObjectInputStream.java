package HDFJavaUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import HDFJavaUtils.interfaces.HDF5Serializable;
import HDFJavaUtils.interfaces.Ignore;
import HDFJavaUtils.interfaces.SerializeClassOptions;
import HDFJavaUtils.interfaces.SerializeFieldOptions;
import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.object.Dataset;
import ncsa.hdf.object.h5.H5File;
import testSpace.testDataB;

/**
 * The HDF5Util's ObjectOutputStream is a mirror to Java's ObjectInputStream The
 * program gives the user tools to de-serialize a class from a HDF5 file
 * 
 * @author Ben Bressette
 * @version 0.1
 */
public class ObjectInputStream {

	private H5File file;
	private String defaultPath;
	private int recursionIterator;

	/**
	 * Constructor for the class, the user is required to input a H5File
	 * 
	 * @param file
	 *            The H5File being accessed within the program
	 */
	public ObjectInputStream(H5File file) {
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
	public ObjectInputStream(H5File file, String path) {
		this.file = file;
		defaultPath = path;
	}

	/**
	 * Reads an integer from a dataset
	 * 
	 * @param name
	 *            The name of the dataset
	 */
	public int readInt(String name) {
		try {
			return Array.getInt(read(name), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Reads a double from a dataset
	 * 
	 * @param name
	 *            The name of the dataset
	 */
	public Double readDouble(String name) {
		try {
			return Array.getDouble(read(name), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1.0;
		}
	}

	/**
	 * Reads a float from a dataset
	 * 
	 * @param name
	 *            The name of the dataset
	 */
	public float readFloat(String name) {
		try {
			return Array.getFloat(read(name), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Reads a long from a dataset
	 * 
	 * @param name
	 *            The name of the dataset
	 */
	public long readLong(String name) {
		try {
			return Array.getLong(read(name), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Reads a short from a dataset
	 * 
	 * @param name
	 *            The name of the dataset
	 */
	public short readShort(String name) {
		try {
			return Array.getShort(read(name), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Reads a byte from a dataset
	 * 
	 * @param name
	 *            The name of the dataset
	 */
	public byte readByte(String name) {
		try {
			return Array.getByte(read(name), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Reads a character from a dataset
	 * 
	 * @param name
	 *            The name of the dataset
	 */
	public char readChar(String name) {
		try {
			return (char) Array.getInt(read(name), 0);
			// return Array.getChar(read(name), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return ' ';
		}
	}

	/**
	 * Reads an character array from a dataset
	 * 
	 * @param name
	 *            The name of the dataset
	 */
	public char[] readCharArray(String name) {
		String array = "";
		try {
			Dataset dset = (Dataset) file.get(name);
			int[] intArray = (int[]) dset.getData();
			for (int i : intArray) {
				array += (char) i;
			}
			return array.toCharArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads a String from a dataset
	 * 
	 * @param name
	 *            The name of the dataset
	 */
	public String readString(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return (String) Array.get(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads a generic object from a dataset
	 * 
	 * @param name
	 *            The name of the dataset
	 */
	public <T> Object read(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return dset.read();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads a boolean from a dataset
	 * 
	 * @param name
	 *            The name of the dataset
	 */
	public boolean readBoolean(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			int[] data = (int[]) dset.read();
			if (data[0] == 0)
				return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Acts similar to Java's readObject function. Will read basic data from a
	 * H5File and deserialize it to the object Object must be implementing the
	 * HDF5Serializable interface Any field with the transient tag will be
	 * ignored
	 * 
	 * @param obj
	 *            The object to be serialized
	 */
	public void readObject(Object obj) {
		readObjectHelper(obj, null);
	}

	/**
	 * Acts similar to Java's readObject function. Will read basic data from a
	 * H5File and deserialize it to the object Object must be implementing the
	 * HDF5Serializable interface Any field with the transient tag will be
	 * ignored
	 * 
	 * @param obj
	 *            The object to be serialized
	 * @param path
	 *            The location of the datasets
	 */
	public void readObject(Object obj, String path) {
		readObjectHelper(obj, path);
	}

	// Reads in an array
	private <T> Object readArray(String name, int HDF5Datatype,
			Class<?> datatype, Object obj) {
		if (datatype != null) {
			try {
				Dataset dset = (Dataset) file.get(name);
				int dset_id = dset.open();
				Object arr;
				Object data;

				dset.getMetadata();
				long[] dimensions = dset.getDims();

				int[] intDims = new int[dimensions.length];
				for (int i = 0; i < dimensions.length; i++) {
					intDims[i] = Long.valueOf(dimensions[i]).intValue();
				}
				if (datatype == char.class) {
					data = Array.newInstance(int.class, intDims);
					arr = Array.newInstance(datatype, intDims);
					H5.H5Dread(dset_id, HDF5Constants.H5T_NATIVE_INT, HDF5Constants.H5S_ALL,
							HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT,
							data);
					copyArrayIntToChar(data, arr);
				} else if (datatype == boolean.class) {
					data = Array.newInstance(int.class, intDims);
					arr = Array.newInstance(datatype, intDims);
					H5.H5Dread(dset_id, HDF5Constants.H5T_NATIVE_INT, HDF5Constants.H5S_ALL,
							HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT,
							data);
					copyArrayIntToBool(data, arr);
				} else {
					arr = Array.newInstance(datatype, intDims);
					H5.H5Dread(dset_id, HDF5Datatype, HDF5Constants.H5S_ALL,
							HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT,
							arr);
				}

				dset.close(dset_id);
				return arr;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				int[] info = (int[]) read(name + "/" + "info");
				int[] dims = (int[]) read(name + "/" + "dimensions");
				Class<?> cl = getBaseClass(obj);
				Object objectArray = Array.newInstance(cl, dims);
				T[] flatArray = (T[]) Array.newInstance(cl, info[0]);
				for (int i = 1; i < info.length; i++) {
					Array.set(flatArray, info[i], cl.newInstance());
					readObjectHelper(flatArray[info[i]], name + "/" + info[i]);
				}
				curlArray(flatArray, objectArray);
				return objectArray;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	// Converts an Int array to a char array
	private static void copyArrayIntToChar(Object original, Object copy) {
		int n = Array.getLength(original);
		for (int i = 0; i < n; i++) {
			Object e = Array.get(original, i);
			if (e != null && e.getClass().isArray()) {
				copyArrayIntToChar(e, Array.get(copy, i));
			} else {
				char tmp = (char) Array.getInt(original, i);
				Array.set(copy, i, tmp);
			}
		}
	}

	// Converts an Int Array to a boolean array
	private static void copyArrayIntToBool(Object original, Object copy) {
		int n = Array.getLength(original);
		for (int i = 0; i < n; i++) {
			Object e = Array.get(original, i);
			if (e != null && e.getClass().isArray()) {
				copyArrayIntToBool(e, Array.get(copy, i));
			} else {
				boolean tmp = Array.getInt(original, i) == 1 ? true : false;
				Array.set(copy, i, tmp);
			}
		}
	}

	private void curlArray(Object flat, Object curled) {
		int n = Array.getLength(curled);
		for (int i = 0; i < n; i++) {
			Object e = Array.get(curled, i);
			if (e != null && e.getClass().isArray()) {
				curlArray(flat, e);
			} else {
				Array.set(curled, i, Array.get(flat, recursionIterator));
				recursionIterator++;
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

	private List readList(List list, String name) {
		Object arr = read(name);
		try {
			int count = 0;
			while (true) {
				list.add(Array.get(arr, count));
				count++;
			}
		} catch (IndexOutOfBoundsException e) {

		}
		return list;
	}
	
	private Set readSet(Set set, String name) {
		Object arr = read(name);
		try {
			int count = 0;
			while (true) {
				set.add(Array.get(arr, count));
				count++;
			}
		} catch (IndexOutOfBoundsException e) {

		}
		return set;
	}
	
	private Map readMap(Map map, String name) {
		Object arrKeys = read(name + "/keys");
		Object arrVals = read(name + "/values");
		try {
			int count = 0;
			while (true) {
				map.put(Array.get(arrKeys, count), Array.get(arrVals, count));
				count++;
			}
		} catch (IndexOutOfBoundsException e) {

		}
		return map;
	}

	// Reads the actual Object
	private <T> void readObjectHelper(Object obj, String group) {
		if (obj != null && obj instanceof HDF5Serializable) {
			recursionIterator = 0;
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
			for (Field field : fields) {
				field.setAccessible(true);
				String name = "";
				long[] dims = { 1, 1 };
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
						String type = field.get(obj).getClass().toString();
						if (name == "") {
							name = field.getName();
						}
						name = defaultPath + "/" + path + "/" + localGroup + "/" + name;
						if (type.equals("class java.lang.Integer")) {
							field.setInt(obj, readInt(name));
						} else if (type.equals("class java.lang.Long")) {
							field.setLong(obj, readLong(name));
						} else if (type.equals("class java.lang.Double")) {
							field.setDouble(obj, readDouble(name));
						} else if (type.equals("class java.lang.Float")) {
							field.setFloat(obj, readFloat(name));
						} else if (type.equals("class java.lang.Short")) {
							field.setShort(obj, readShort(name));
						} else if (type.equals("class java.lang.Character")) {
							field.setChar(obj, readChar(name));
//						} else if (type.equals("class [C")) {
//							field.set(obj, readCharArray(name));
						} else if (type.equals("class java.lang.String")) {
							field.set(obj, readString(name));
						} else if (type.equals("class java.lang.Boolean")) {
							field.set(obj, readBoolean(name));
						} else if (type.equals("class java.lang.Byte")) {
							field.set(obj, readByte(name));
						} else if (type.contains("[")) {
							field.set(obj, readArray(name, DataTypeUtils.getDataType(field),
									DataTypeUtils.getArrayType(field.get(obj)),field.get(obj)));
						} else if (type.equals("class java.util.ArrayList")) {
							field.set(obj, readList(new ArrayList(), name));
						} else if (type.equals("class java.util.LinkedList")) {
							field.set(obj, readList(new LinkedList(), name));
						} else if (type.equals("class java.util.Vector")) {
							field.set(obj, readList(new Vector(), name));
						} else if (type.equals("class java.util.Stack")) {
							field.set(obj, readList(new Stack(), name));
						} else if (type.equals("class java.util.HashSet")) {
							field.set(obj, readSet(new HashSet(), name));
						} else if (type.equals("class java.util.TreeSet")) {
							field.set(obj, readSet(new TreeSet(), name));
						} else if (type.equals("class java.util.LinkedHashSet")) {
							field.set(obj, readSet(new LinkedHashSet(), name));
						} else if (type.equals("class java.util.HashMap")) {
							field.set(obj, readMap(new HashMap(), name));
						} else if (type.equals("class java.util.Hashtable")) {
							field.set(obj, readMap(new Hashtable(), name));
						} else if (type.equals("class java.util.WeakHashMap")) {
							field.set(obj, readMap(new WeakHashMap(), name));
						} else if (type.equals("class java.util.TreeMap")) {
							field.set(obj, readMap(new TreeMap(), name));
						} else if (type.equals("class java.util.concurrent.ConcurrentHashMap")) {
							field.set(obj, readMap(new ConcurrentHashMap(), name));
						} else if (type.equals("class java.util.concurrent.ConcurrentSkipListMap")) {
							field.set(obj, readMap(new ConcurrentSkipListMap(), name));
						} else if (field.get(obj) instanceof HDF5Serializable
								&& field.getDeclaringClass() != field.getClass()) {
							readObjectHelper(field.get(obj), "/" + path + "/" + localGroup);
						}
					} catch (IllegalArgumentException | IllegalAccessException | NullPointerException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

}
