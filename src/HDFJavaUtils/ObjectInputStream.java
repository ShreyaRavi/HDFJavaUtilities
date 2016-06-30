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

import HDFJavaUtils.annotations.SerializeClassOptions;
import HDFJavaUtils.annotations.SerializeFieldOptions;
import ncsa.hdf.object.Dataset;
import ncsa.hdf.object.h5.H5File;

public class ObjectInputStream {

	private H5File file;
	private String defaultPath;

	public ObjectInputStream(H5File file) {
		this.file = file;
		defaultPath = "";
	}

	public ObjectInputStream(H5File file, String path) {
		this.file = file;
		defaultPath = path;
	}

	public int readInt(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return Array.getInt(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Double readDouble(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			System.out.println(dset.read());
			return Array.getDouble(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1.0;
		}
	}

	public float readFloat(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return Array.getFloat(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public long readLong(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return Array.getLong(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public short readShort(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return Array.getShort(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public char readChar(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return (char) Array.getInt(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return ' ';
		}
	}

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

	public int[] readIntArray(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return (int[]) dset.read();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String readString(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return (String) Array.get(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public <T> Object read(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return dset.read();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

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

	public void readObject(Object obj) {
		readObjectHelper(obj, null);
	}

	private <T> void readObjectHelper(Object obj, String group) {
		if (obj instanceof HDF5Serializable) {
			Class<?> objClass = obj.getClass();
			Field[] fields = objClass.getFields();
			String path = "";
			SerializeClassOptions options = (SerializeClassOptions) objClass.getAnnotation(SerializeClassOptions.class);
			if (group != null)
				path += group;
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
				boolean ignore = false;
				String localGroup = "";
				Annotation anno = field.getAnnotation(SerializeFieldOptions.class);
				SerializeFieldOptions fieldOptions = (SerializeFieldOptions) anno;
				if (anno != null) {
					name = fieldOptions.name();
					localGroup = fieldOptions.path();
					dims = fieldOptions.dimensions();
					ignore = fieldOptions.ignore();
				}
				if (!Modifier.isTransient(field.getModifiers()) && !ignore) {
					try {
						String type = field.get(obj).getClass().toString();
						if (name == "")
							name = field.getName();
						name = defaultPath + "/" + path + "/" + localGroup + "/" + name;
						if (field.get(obj).getClass().isPrimitive()) {
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
							else if (type.equals("[I"))
								field.set(obj, readIntArray(name));
						} else if (type.contains("List") || type.contains("Vector") || type.contains("stack")) {
							List list = null;
							System.out.println(type);
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

						} else if (type.equals("class java.lang.Boolean"))
							field.set(obj, readBoolean(field.getName()));
					} catch (IllegalArgumentException | IllegalAccessException | NullPointerException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
