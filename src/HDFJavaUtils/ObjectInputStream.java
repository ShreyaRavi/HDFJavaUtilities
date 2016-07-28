package HDFJavaUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
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
import ncsa.hdf.object.Attribute;
import ncsa.hdf.object.Dataset;
import ncsa.hdf.object.HObject;
import ncsa.hdf.object.h5.H5File;

/**
 * The HDF5Util's ObjectOutputStream is a mirror to Java's ObjectInputStream The
 * program gives the user tools to de-serialize a class from a HDF5 file
 * 
 * @author Ben Bressette
 * @author Shreya Ravi
 * @version 0.1
 */
public class ObjectInputStream {

	private H5File file;
	private String defaultPath;
	private int recursionIterator;

	private static final Set<Class<?>> WRAPPER_TYPE = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { Boolean.class, Character.class, Integer.class, Short.class, Long.class,
					Float.class, Double.class, Byte.class }));
	private static final Set<Class<?>> PRIMITIVE_TYPE = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] {
			boolean.class, char.class, int.class, short.class, long.class, float.class, double.class, byte.class }));

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
			int[] intArray = (int[]) read(name);
			for (int i : intArray)
				array += (char) i;
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
			return (String) Array.get(read(name), 0);
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
			int[] data = (int[]) read(name);
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

	// Returns a character representation of a integer array
	private static Object copyArrayIntToChar(Object original, int[] intDims) {
		Object copy = Array.newInstance(char.class, intDims);
		copyArrayIntToChar(original, copy);
		return copy;
	}

	// Returns a Boolean representation of an Int array
	private static Object copyArrayIntToBool(Object original, int[] intDims) {
		Object copy = Array.newInstance(boolean.class, intDims);
		copyArrayIntToBool(original, copy);
		return copy;
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

	// Converts a one dimensional array (flat) into a multidemensional array
	// (curled)
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

	// Finds the base class of an array
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

	// Returns a character set based on an integer set
	private Set<Character> copySetIntToChar(Set<Integer> set) {
		ArrayList<Character> list = new ArrayList<Character>();
		Class<?> type = set.getClass();
		
		Iterator<Integer> itr = set.iterator();
		while (itr.hasNext())
			list.add((Character) ((char) ((int) itr.next())));

		if (type.equals(HashSet.class))
			return new HashSet<Character>(list);
		else if (type.equals(LinkedHashSet.class))
			return new LinkedHashSet<Character>(list);
		else if (type.equals(TreeSet.class))
			return new TreeSet<Character>(list);
		
		return null;
	}

	// Returns a boolean set based on an integer set
	private Set<Boolean> copySetIntToBool(Set<Integer> set) {
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		Class<?> type = set.getClass();
		
		Iterator<Integer> itr = set.iterator();
		while (itr.hasNext()) {
			boolean element = (itr.next() == 1 ? true : false);
			list.add(element);
		}
		if (type.equals(HashSet.class))
			return new HashSet<Boolean>(list);
		else if (type.equals(LinkedHashSet.class))
			return new LinkedHashSet<Boolean>(list);
		else if (type.equals(TreeSet.class))
			return new TreeSet<Boolean>(list);
		
		return null;
	}

	private List<Character> copyListIntToChar(List<Integer> list) {
		ArrayList<Character> tempList = new ArrayList<Character>();
		Class<?> type = list.getClass();
		
		for (int i = 0; i < list.size(); i++) {
			int tmpInt = list.get(i);
			char tmp = (char) tmpInt;
			tempList.add(tmp);
		}
		
		if (type.equals(ArrayList.class)) {
			return new ArrayList<Character>(tempList);
		} else if (type.equals(LinkedList.class)) {
			return new LinkedList<Character>(tempList);
		} else if (type.equals(Vector.class)) {
			return new Vector<Character>(tempList);
		} else if (type.equals(Stack.class)) {
			Stack<Character> newList = new Stack<Character>();
			newList.addAll(tempList);
			return newList;
		}
		
		return null;
	}

	private List<Boolean> copyListIntToBool(List<Integer> list) {
		ArrayList<Boolean> tempList = new ArrayList<Boolean>();
		Class<?> type = list.getClass();
		
		for (int i = 0; i < list.size(); i++) {
			boolean element = (list.get(i) == 1 ? true : false);
			tempList.add(element);
		}

		if (type.equals(ArrayList.class)) {
			return new ArrayList<Boolean>(tempList);
		} else if (type.equals(LinkedList.class)) {
			return new LinkedList<Boolean>(tempList);
		} else if (type.equals(Vector.class)) {
			return new Vector<Boolean>(tempList);
		} else if (type.equals(Stack.class)) {
			Stack<Boolean> newList = new Stack<Boolean>();
			newList.addAll(tempList);
			return newList;
		}
		
		return null;
	}

	// Reads in an array
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> Object readArray(String name, int HDF5Datatype, Object obj) {
		recursionIterator = 0;
		Class<?> datatype = DataTypeUtils.getArrayType(obj);

		if (datatype != null) {
			try {
				Dataset dset = (Dataset) file.get(name);
				int dset_id = dset.open();
				Object data;

				dset.getMetadata();
				int[] intDims = longToIntArr(dset.getDims());

				if (datatype == char.class) {
					data = Array.newInstance(int.class, intDims);
					H5.H5Dread(dset_id, HDF5Constants.H5T_NATIVE_INT, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
							HDF5Constants.H5P_DEFAULT, data);
					data = copyArrayIntToChar(data, intDims);
				} else if (datatype == boolean.class) {
					data = Array.newInstance(int.class, intDims);
					H5.H5Dread(dset_id, HDF5Constants.H5T_NATIVE_INT, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
							HDF5Constants.H5P_DEFAULT, data);
					data = copyArrayIntToBool(data, intDims);
				} else {
					data = Array.newInstance(datatype, intDims);
					H5.H5Dread(dset_id, HDF5Datatype, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
							HDF5Constants.H5P_DEFAULT, data);
				}

				dset.close(dset_id);
				return data;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				List<Attribute> attr = getObjectAttributes(name);
				int dims[] = getAttributeDims(attr);
				int info[] = getAttributeLocations(attr);
				Class<?> cl = getBaseClass(obj);
				
				int length = 1;
				for (int i : dims)
					length *= i;
				
				Object objectArray = Array.newInstance(cl, dims);
				T[] flatArray = (T[]) Array.newInstance(cl, length);
				
				for (int i = 0, j = 0; i <= info[info.length - 1]; i++) {
					if (info[j] == i) {
						attr = getObjectAttributes(name + "/" + info[j]);
						String objClass = (String) getAttribute(attr, "class");
						cl = Class.forName(objClass);
						
						if (cl.isArray()) {
							Object arr = Array.newInstance(cl, getAttributeDims(attr));
							Array.set(flatArray, info[j],
									readArray(name + "/" + info[j], DataTypeUtils.getDataType(getBaseClass(arr)), arr));
						} else if (Collection.class.isAssignableFrom(cl)) {
							if (List.class.isAssignableFrom(cl)) {
								Array.set(flatArray, info[j], readList((List) cl.newInstance(), name + "/" + info[j]));
							} else if (Set.class.isAssignableFrom(cl)) {
								Array.set(flatArray, info[j], readSet((Set) cl.newInstance(), name + "/" + info[j]));
							}
						} else if (Map.class.isAssignableFrom(cl)) {
							Array.set(flatArray, info[j], readMap((Map) cl.newInstance(), name + "/" + info[j]));
						} else {
							Array.set(flatArray, info[j], cl.newInstance());
							readObjectHelper(flatArray[info[j]], name + "/" + info[j]);
						}
						j++;
					} else {
						Array.set(flatArray, i, null);
					}
				}
				curlArray(flatArray, objectArray);
				return objectArray;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> List readList(List list, String name) {
		List<Attribute> attr = getObjectAttributes(name);
		String compClass = (String) getAttribute(attr, "componentClass");
		
		Class<?> cl = null;
		try {
			cl = Class.forName(compClass);
		} catch (ClassNotFoundException | NullPointerException e1) {

		}
		
		if (WRAPPER_TYPE.contains(cl) || PRIMITIVE_TYPE.contains(cl)) {
			Object arr = read(name);
			try {
				int count = 0;
				while (true) {
					list.add(Array.get(arr, count));
					count++;
				}
			} catch (IndexOutOfBoundsException e) {

			}
			
			if (cl == Character.class)
				list = copyListIntToChar(list);
			if (cl == Boolean.class)
				list = copyListIntToBool(list);
			
			return list;
		} else {
			try {
				int info[] = getAttributeLocations(attr);
				
				for (int i = 0, j = 0; i <= info[info.length - 1]; i++) {
					if (info[j] == i) {
						attr = getObjectAttributes(name + "/" + info[j]);
						String objClass = (String) getAttribute(attr, "class");
						cl = Class.forName(objClass);
						
						if (cl.isArray()) {
							Object arr = Array.newInstance(cl, getAttributeDims(attr));
							list.add(
									readArray(name + "/" + info[j], DataTypeUtils.getDataType(getBaseClass(arr)), arr));
						} else if (Collection.class.isAssignableFrom(cl)) {
							if (List.class.isAssignableFrom(cl)) {
								list.add(readList((List) cl.newInstance(), name + "/" + info[j]));
							} else if (Set.class.isAssignableFrom(cl)) {
								list.add(readSet((Set) cl.newInstance(), name + "/" + info[j]));
							}
						} else if (Map.class.isAssignableFrom(cl)) {
							list.add(readMap((Map) cl.newInstance(), name + "/" + info[j]));
						} else {
							Object obj = cl.newInstance();
							readObjectHelper(obj, name + "/" + info[j]);
							list.add(obj);
						}
						j++;
					} else {
						list.add(null);
					}
				}
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Set readSet(Set set, String name) {
		List<Attribute> attr = getObjectAttributes(name);
		String compClass = (String) getAttribute(attr, "componentClass");
		
		Class<?> cl = null;
		try {
			cl = Class.forName(compClass);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if (WRAPPER_TYPE.contains(cl)) {
			Object arr = read(name);
			try {
				int count = 0;
				while (true) {
					set.add(Array.get(arr, count));
					count++;
				}
			} catch (IndexOutOfBoundsException e) {

			}
			
			if (cl == Character.class)
				set = copySetIntToChar(set);
			if (cl == Boolean.class)
				set = copySetIntToBool(set);
			
			return set;
		} else {
			try {
				int info[] = getAttributeLocations(attr);
				for (int i = 0, j = 0; i <= info[info.length - 1]; i++) {
					if (info[j] == i) {
						attr = getObjectAttributes(name + "/" + info[j]);
						String objClass = (String) getAttribute(attr, "class");
						cl = Class.forName(objClass);
						
						if (cl.isArray()) {
							Object arr = Array.newInstance(cl, getAttributeDims(attr));
							set.add(readArray(name + "/" + info[j], DataTypeUtils.getDataType(getBaseClass(arr)), arr));
						} else if (Collection.class.isAssignableFrom(cl)) {
							if (List.class.isAssignableFrom(cl)) {
								set.add(readList((List) cl.newInstance(), name + "/" + info[j]));
							} else if (Set.class.isAssignableFrom(cl)) {
								set.add(readSet((Set) cl.newInstance(), name + "/" + info[j]));
							}
						} else if (Map.class.isAssignableFrom(cl)) {
							set.add(readMap((Map) cl.newInstance(), name + "/" + info[j]));
						} else {
							Object obj = cl.newInstance();
							readObjectHelper(obj, name + "/" + info[j]);
							set.add(obj);
						}
						j++;
					} else {
						set.add(null);
					}
				}
				return set;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map readMap(Map map, String name) {
		Object arrKeys = null;
		Object arrVals = null;
		List<Attribute> valAttr = getObjectAttributes(name + "/values");
		String valClassName = (String) getAttribute(valAttr, "class");
		Class<?> valClass = null;
		
		try {
			valClass = Class.forName(valClassName);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		List<Attribute> keyAttr = getObjectAttributes(name + "/keys");
		String keyClassName = (String) getAttribute(keyAttr, "class");
		Class<?> keyClass = null;
		
		try {
			keyClass = Class.forName(keyClassName);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if (WRAPPER_TYPE.contains(keyClass)) {
			arrKeys = read(name + "/keys");
			if (keyClass == Character.class) {
				Object tmp = Array.newInstance(Character.class, Array.getLength(arrKeys));
				copyArrayIntToChar(arrKeys, tmp);
				arrKeys = tmp;
			}
			if (keyClass == Boolean.class) {
				Object tmp = Array.newInstance(Boolean.class, Array.getLength(arrKeys));
				copyArrayIntToBool(arrKeys, tmp);
				arrKeys = tmp;
			}
		} else {
			int[] info = getAttributeLocations(keyAttr);
			arrKeys = Array.newInstance(keyClass, info[info.length - 1] + 1);
			
			for (int i = 0, j = 0; i <= info[info.length - 1]; i++) {
				if (info[j] == i) {
					try {
						keyAttr = getObjectAttributes(name + "/keys/" + info[j]);
						String objClass = (String) getAttribute(keyAttr, "class");
						keyClass = Class.forName(objClass);
						
						if (keyClass.isArray()) {
							Object arr = Array.newInstance(keyClass, getAttributeDims(keyAttr));
							Array.set(arrKeys, i, readArray(name + "/keys/" + info[j],
									DataTypeUtils.getDataType(getBaseClass(arr)), arr));
						} else if (Collection.class.isAssignableFrom(keyClass)) {
							if (List.class.isAssignableFrom(keyClass)) {
								Array.set(arrKeys, i,
										readList((List) keyClass.newInstance(), name + "/keys/" + info[j]));
							} else if (Set.class.isAssignableFrom(keyClass)) {
								Array.set(arrKeys, i, readSet((Set) keyClass.newInstance(), name + "/keys/" + info[j]));
							}
						} else if (Map.class.isAssignableFrom(keyClass)) {
							Array.set(arrKeys, i, readMap((Map) keyClass.newInstance(), name + "/keys/" + info[j]));
						} else {
							Object obj = keyClass.newInstance();
							readObjectHelper(obj, name + "/keys/" + info[j]);
							Array.set(arrKeys, i, obj);
						}
					} catch (IllegalArgumentException | InstantiationException | IllegalAccessException
							| ClassNotFoundException e) {

					}
					j++;
				}
			}
		}
		if (WRAPPER_TYPE.contains(valClass)) {
			arrVals = read(name + "/values");
			
			if (valClass == Character.class) {
				Object tmp = Array.newInstance(Character.class, Array.getLength(arrKeys));
				copyArrayIntToChar(arrVals, tmp);
				arrKeys = tmp;
			}
			if (valClass == Boolean.class) {
				Object tmp = Array.newInstance(Boolean.class, Array.getLength(arrKeys));
				copyArrayIntToBool(arrVals, tmp);
				arrKeys = tmp;
			}
		} else {
			int[] info = getAttributeLocations(valAttr);
			arrVals = Array.newInstance(valClass, info[info.length - 1] + 1);
			
			for (int i = 0, j = 0; i <= info[info.length - 1]; i++) {
				if (info[j] == i) {
					try {
						valAttr = getObjectAttributes(name + "/values/" + info[j]);
						String objClass = (String) getAttribute(valAttr, "class");
						valClass = Class.forName(objClass);
						
						if (valClass.isArray()) {
							Object arr = Array.newInstance(valClass, getAttributeDims(valAttr));
							Array.set(arrVals, i, readArray(name + "/values/" + info[j],
									DataTypeUtils.getDataType(getBaseClass(arr)), arr));
						} else if (Collection.class.isAssignableFrom(valClass)) {
							if (List.class.isAssignableFrom(valClass)) {
								Array.set(arrVals, i,
										readList((List) valClass.newInstance(), name + "/values/" + info[j]));
							} else if (Set.class.isAssignableFrom(valClass)) {
								Array.set(arrVals, i,
										readSet((Set) valClass.newInstance(), name + "/values/" + info[j]));
							}
						} else if (Map.class.isAssignableFrom(valClass)) {
							Array.set(arrVals, i, readMap((Map) valClass.newInstance(), name + "/values/" + info[j]));
						} else {
							Object obj = valClass.newInstance();
							readObjectHelper(obj, name + "/values/" + info[j]);
							Array.set(arrVals, i, obj);
						}
					} catch (IllegalArgumentException | InstantiationException | IllegalAccessException
							| ClassNotFoundException e) {

					}
					j++;
				}
			}
		}
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

	private static int[] longToIntArr(long[] longArr) {
		int[] intArr = new int[longArr.length];
		for (int i = 0; i < longArr.length; i++) 
			intArr[i] = Long.valueOf(longArr[i]).intValue();
		return intArr;
	}

	//Returns a list of Attributes from an Object
	@SuppressWarnings("unchecked")
	private List<Attribute> getObjectAttributes(String name) {
		try {
			HObject obj = file.get(name);
			return obj.getMetadata();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    private int[] getAttributeDims(List<Attribute> attr) {
        for (Attribute a : attr)
            if (a.getName().equals("dims"))
                return (int[]) a.getValue();
        return null;
    }
	
    private int[] getAttributeLocations(List<Attribute> attr) {
        for (Attribute a : attr)
            if (a.getName().equals("locations"))
                return (int[]) a.getValue();
        return null;
    }
    
	// Finds the attribute with the given name in an attribute list
	private Object getAttribute(List<Attribute> attr, String attrName) {
		for (Attribute a : attr)
			if (a.getName().equals(attrName))
				return Array.get(a.getValue(), 0);
		return null;
	}

	//Reads in the Object
	@SuppressWarnings("rawtypes")
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
				String localGroup = "";
				Annotation anno = field.getAnnotation(SerializeFieldOptions.class);
				SerializeFieldOptions fieldOptions = (SerializeFieldOptions) anno;
				
				if (anno != null) {
					name = fieldOptions.name();
					localGroup = fieldOptions.path();
				}
				
				if (!Modifier.isTransient(field.getModifiers()) && !field.isAnnotationPresent(Ignore.class)) {
					try {
						if (name == "")
							name = field.getName();
						name = defaultPath + "/" + path + "/" + localGroup + "/" + name;
						
						Class<?> type = field.getType();
						
						if (PRIMITIVE_TYPE.contains(type)) {
							if (type.equals(int.class)) {
								field.set(obj, readInt(name));
							} else if (type.equals(long.class)) {
								field.set(obj, readLong(name));
							} else if (type.equals(double.class)) {
								field.set(obj, readDouble(name));
							} else if (type.equals(float.class)) {
								field.set(obj, readFloat(name));
							} else if (type.equals(short.class)) {
								field.set(obj, readShort(name));
							} else if (type.equals(char.class)) {
								field.set(obj, readChar(name));
							} else if (type.equals(boolean.class)) {
								field.set(obj, readBoolean(name));
							} else if (type.equals(byte.class)) {
								field.set(obj, readByte(name));
							}
						} else if(WRAPPER_TYPE.contains(type)) {
							if (type.equals(Integer.class)) {
								field.set(obj, readInt(name));
							} else if (type.equals(Long.class)) {
								field.set(obj, readLong(name));
							} else if (type.equals(Double.class)) {
								field.set(obj, readDouble(name));
							} else if (type.equals(Float.class)) {
								field.set(obj, readFloat(name));
							} else if (type.equals(Short.class)) {
								field.set(obj, readShort(name));
							} else if (type.equals(Character.class)) {
								field.set(obj, readChar(name));
							} else if (type.equals(String.class)) {
								field.set(obj, readString(name));
							} else if (type.equals(Boolean.class)) {
								field.set(obj, readBoolean(name));
							} else if (type.equals(Byte.class)) {
								field.set(obj, readByte(name));
							}
						} else if (type.isArray()) {
							field.set(obj, readArray(name, DataTypeUtils.getDataType(field), field.get(obj)));
						} else if (List.class.isAssignableFrom(type)) {
							if (type.equals(ArrayList.class))
								field.set(obj, readList((ArrayList) field.get(obj), name));
							else if (type.equals(LinkedList.class))
								field.set(obj, readList(new LinkedList(), name));
							else if (type.equals(Vector.class))
								field.set(obj, readList(new Vector(), name));
							else if (type.equals(Stack.class))
								field.set(obj, readList(new Stack(), name));
							else if (type.equals(List.class))
								field.set(obj, readList(new ArrayList(), name));
						} else if (Set.class.isAssignableFrom(type)) {
							if (type.equals(HashSet.class))
								field.set(obj, readSet(new HashSet(), name));
							else if (type.equals(TreeSet.class))
								field.set(obj, readSet(new TreeSet(), name));
							else if (type.equals(LinkedHashSet.class))
								field.set(obj, readSet(new LinkedHashSet(), name));
							else if (type.equals(Set.class))
								field.set(obj, readSet(new HashSet(), name));
						} else if (Map.class.isAssignableFrom(type)) {
							if (type.equals(HashMap.class))
								field.set(obj, readMap(new HashMap(), name));
							else if (type.equals(Hashtable.class))
								field.set(obj, readMap(new Hashtable(), name));
							else if (type.equals(WeakHashMap.class))
								field.set(obj, readMap(new WeakHashMap(), name));
							else if (type.equals(TreeMap.class))
								field.set(obj, readMap(new TreeMap(), name));
							else if (type.equals(ConcurrentHashMap.class))
								field.set(obj, readMap(new ConcurrentHashMap(), name));
							else if (type.equals(ConcurrentSkipListMap.class))
								field.set(obj, readMap(new ConcurrentSkipListMap(), name));
							else if (type.equals(Map.class))
								field.set(obj, readMap(new HashMap(), name));
						} else if (field.get(obj) instanceof HDF5Serializable
								&& field.getDeclaringClass() != field.getClass()) {
							readObjectHelper(field.get(obj), name);
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {

					}
				}
			}

		}
	}

}
