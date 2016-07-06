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
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.object.Attribute;
import ncsa.hdf.object.Dataset;
import ncsa.hdf.object.h5.H5File;
import HDFJavaUtils.interfaces.HDF5Serializable;
import HDFJavaUtils.interfaces.Ignore;
import HDFJavaUtils.interfaces.SerializeClassOptions;
import HDFJavaUtils.interfaces.SerializeFieldOptions;

/**
 * The HDF5Util's ObjectOutputStream is a mirror to Java's ObjectInputStream
 * The program gives the user tools to de-serialize a class from a HDF5 file
 * @author Ben Bressette
 * @version 0.1
 */
public class ObjectInputStream {
	
	private H5File file;
	private String defaultPath;

	/**
	 * Constructor for the class, the user is required to input a H5File
	 * @param file The H5File being accessed within the program
	 */
	public ObjectInputStream(H5File file) {
		this.file = file;
		defaultPath = "";
	}

	/**
	 * Constructor for the class, the user is required to input a H5File
	 * @param file The H5File being accessed within the program
	 * @param groupPath represents the root directory the program will use in the file
	 */
	public ObjectInputStream(H5File file, String path) {
		this.file = file;
		defaultPath = path;
	}

	/**
	 * Reads an integer from a dataset
	 * @param name The name of the dataset
	 */
	public int readInt(String name) {
		try {
			return  Array.getInt(read(name), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	/**
	 * Reads a double from a dataset
	 * @param name The name of the dataset
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
	 * @param name The name of the dataset
	 */
	public float readFloat(String name) {
		try {
			return  Array.getFloat(read(name), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Reads a long from a dataset
	 * @param name The name of the dataset
	 */
	public long readLong(String name) {
		try {
			return  Array.getLong(read(name), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Reads a short from a dataset
	 * @param name The name of the dataset
	 */
	public short readShort(String name) {
		try {
			return  Array.getShort(read(name), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Reads a character from a dataset
	 * @param name The name of the dataset
	 */
	public char readChar(String name) {
		try {
			return  (char) Array.getInt(read(name), 0);
//			return Array.getChar(read(name), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return ' ';
		}
	}

	/**
	 * Reads an character array from a dataset
	 * @param name The name of the dataset
	 */
	public char[] readCharArray(String name) {
		String array = "";
		try {
			Dataset dset = (Dataset) file.get(name);
			int[] intArray = (int[]) dset.getData();
			for(int i : intArray) {
				array += (char) i;
			}
			return array.toCharArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Object readArray(String name, Class <?> arrayType) {
		try {
			Dataset dset = (Dataset) file.get(name);
			int dset_id = dset.open();
			
			// must call dset.getMetadata() before calling dset.getDims() because dims are metadata
			dset.getMetadata();
			long[] dimensions = dset.getDims();
			
			int[] intDims = new int[dimensions.length];
			for (int i = 0; i < dimensions.length; i++) {
				intDims[i] = Long.valueOf(dimensions[i]).intValue();
			}
			int datatype = H5.H5Dget_type(dset_id);
			Object arr = Array.newInstance(arrayType, intDims);
			H5.H5Dread(dset_id, datatype, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, arr);
			dset.close(dset_id);
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	

	/**
	 * Reads a String from a dataset
	 * @param name The name of the dataset
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
	 * @param name The name of the dataset
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
	 * @param name The name of the dataset
	 */
	public boolean readBoolean(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			int[] data = (int[]) dset.read();
			if(data[0] == 0)
				return false;
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	

	/**
	 * Acts similar to Java's readObject function.
	 * Will read basic data from a H5File and deserialize it to the object
	 * Object must be implementing the HDF5Serializable interface
	 * Any field with the transient tag will be ignored
	 * @param obj The object to be serialized 
	 */
	public void readObject(Object obj) {
		readObjectHelper(obj, null);
	}
	
	/**
	 * Acts similar to Java's readObject function.
	 * Will read basic data from a H5File and deserialize it to the object
	 * Object must be implementing the HDF5Serializable interface
	 * Any field with the transient tag will be ignored
	 * @param obj The object to be serialized 
	 * @param path The location of the datasets
	 */
	public void readObject(Object obj, String path) {
		readObjectHelper(obj, path);
	}

	private int[] readIntArray(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return (int[]) dset.read();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}

	//Reads the actual Object
	private <T> void readObjectHelper(Object obj, String group) {
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
			for (Field field : fields) {
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
						if (name == "")
							name = field.getName();
						name = defaultPath + "/" + path + "/" + localGroup + "/" + name;
						if (type.equals("class java.lang.Integer"))
							field.setInt(obj, readInt(name));
						else if (type.equals("class java.lang.Long"))
							field.setLong(obj, readLong(name));
						else if (type.equals("class java.lang.Double"))
							field.setDouble(obj, readDouble(name));
						else if (type.equals("class java.lang.Float"))
							field.setFloat(obj, readFloat(name));
						else if (type.equals("class java.lang.Short"))
							field.setShort(obj, readShort(name));
						else if (type.equals("class java.lang.Character"))
							field.setChar(obj, readChar(name));
						else if (type.equals("class [C"))
							field.set(obj, readCharArray(name));
						else if (type.equals("class java.lang.String"))
							field.set(obj, readString(name));
						else if (type.equals("class java.lang.Boolean"))
							field.set(obj, readBoolean(name)); 
						else if (type.contains("[")) {
							Class<?> arrType = DataTypeUtils.getArrayType(field.get(obj));
							field.set(obj, readArray(name, arrType));
						}
						else if (type.contains("List") || type.contains("Vector") || type.contains("Stack")) {
							List list = null;
							if (type.equals("class java.util.ArrayList"))
								list = new ArrayList<T>();
							else if (type.equals("class java.util.LinkedList"))
								list = new LinkedList<T>();
							else if (type.equals("class java.util.Vector"))
								list = new Vector<T>();
							else if (type.equals("class java.util.Stack"))
								list = new Stack<T>();
							Object arr = read(name);
							try {
								int count = 0;
								while (true) {
									list.add(Array.get(arr, count));
									count++;
								}
							} catch (IndexOutOfBoundsException e) {

							}
							field.set(obj, list);
						} else if (type.contains("Set")) {
							List list = new ArrayList();
							Object arr = read(name);
							try {
								int count = 0;
								while (true) {
									list.add(Array.get(arr, count));
									count++;
								}
							} catch (IndexOutOfBoundsException | NullPointerException e) {

							}
							if (type.equals("class java.util.HashSet"))
								field.set(obj, new HashSet(list));
							else if (type.contains("class java.util.TreeSet"))
								field.set(obj, new TreeSet(list));
							else if (type.equals("class java.util.LinkedHashSet"))
								field.set(obj, new LinkedHashSet(list));
						} else if (type.contains("Map") || type.contains("Table")) {
							Map map = null;
							if (type.equals("class java.util.HashMap"))
								map = new HashMap();
							else if (type.equals("class java.util.Concurrent.ConcurrentHashMap"))
								map = new ConcurrentHashMap();
							else if (type.equals("class java.util.Concurrent.ConcurrentSkipListMap"))
								map = new ConcurrentSkipListMap();
							else if (type.equals("class java.util.Hashtable"))
								map = new Hashtable();
							else if (type.equals("class java.util.WeakHashMap"))
								map = new WeakHashMap();
							else if (type.equals("class java.util.TreeMap"))
								map = new TreeMap();
							Object arrKey = read(name + "/key");
							Object arrData = read(name + "/data");
							try {
								int count = 0;
								while (true) {
									map.put(Array.get(arrKey, count), Array.get(arrData, count));
									count++;
								}
							} catch (IndexOutOfBoundsException e) {

							}
							field.set(obj, map);

						} else if(field.get(obj) instanceof HDF5Serializable && field.getDeclaringClass() != field.getClass()) {
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
