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

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

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
 * @version 0.1
 */
public class ObjectOutputStream {

	private H5File file;
	private String defaultPath;
	private int recursiveIterator;
	private static final Set<Class<?>> WRAPPER_TYPE = new HashSet<Class<?>>(
			Arrays.asList(new Class<?>[] { Boolean.class, Character.class, Integer.class, Short.class, Long.class,
					Float.class, Double.class, Byte.class, String.class }));
	private static final Set<Class<?>> PRIMITIVE_TYPE = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] {
			boolean.class, char.class, int.class, short.class, long.class, float.class, double.class, byte.class }));

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
	public void writeDouble(Object val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_DOUBLE);
		double[] data = { (double)val };
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
	public void writeFloat(Object val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_FLOAT);
		float[] data = { (float)val };
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
	public void writeBoolean(Object val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_HBOOL);
		int[] data = { (boolean)val ? 1 : 0 };
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
	public void writeLong(Object val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_LONG);
		long[] data = { (long)val };
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
	public void writeShort(Object val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_SHORT);
		short[] data = { (short)val };
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
	public void writeByte(Object val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_INT8);
		byte[] data = { (byte)val };
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
	public void writeChar(Object val, String name) {
		final H5Datatype type = new H5Datatype(HDF5Constants.H5T_NATIVE_CHAR);
		int[] data = { (int) ((char)val) };
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

	// Writes an array of n-dimensions to a dataset/file
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> void writeArray(long[] dimensions, Object obj, String name, int HDF5Datatype) {
		H5Datatype atrType = DataTypeUtils.getType("Integer");
		H5Datatype type = new H5Datatype(HDF5Datatype);

		// If type is primitive
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
			try {
				Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null, type, dimensions, null, null, 0,
						null);
				int dset_id = dset.open();
				H5.H5Dwrite(dset_id, HDF5Datatype, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
						HDF5Constants.H5P_DEFAULT, data);
				dset.close(dset_id);
			} catch (Exception e) {
				e.printStackTrace();
			}

			int[] intDims = new int[dimensions.length];
			for (int i = 0; i < dimensions.length; i++)
				intDims[i] = (int) dimensions[i];

			Attribute dimsAttr = new Attribute("dims", atrType, new long[] { intDims.length }, intDims);
			writeAttribute(name, dimsAttr);
		} else {
			ArrayList<Integer> locations = new ArrayList<>();
			createPath(name, null);

			int[] dims = getDimensions(obj);
			int length = 1;
			for (int d : dims)
				length *= d;

			T[] flat = (T[]) Array.newInstance(getBaseClass(obj), length);
			flattenArray(obj, flat);

			int index = 0;
			for (T i : flat) {
				if (i != null) {
					if (i.getClass().isArray()) {
						int[] innerDims = getDimensions(i);
						long[] longDims = new long[innerDims.length];
						for (int j = 0; j < innerDims.length; j++)
							longDims[j] = innerDims[j];
						writeArray(longDims, i, name + "/" + index, DataTypeUtils.getDataType(i));
					} else if (Collection.class.isAssignableFrom(i.getClass())) {
						if (List.class.isAssignableFrom(i.getClass()))
							writeList((List) i, name + "/" + index);
						else if (Set.class.isAssignableFrom(i.getClass()))
							writeSet((Set) i, name + "/" + index);
					} else if (Map.class.isAssignableFrom(i.getClass())) {
						writeMap((Map) i, name + "/" + index);
					} else {
						writeObjectHelper(i, name + "/" + index);
					}
					Datatype attrType = new H5Datatype(Datatype.CLASS_STRING, i.getClass().toString().length() + 1, -1,
							-1);
					Attribute classAttr = new Attribute("class", attrType, new long[] { 1 },
							new String[] { i.getClass().getName() });
					writeAttribute(name + "/" + index, classAttr);

					locations.add(index);
				}
				index++;
			}
			int loc[] = new int[locations.size()];
			for (int i = 0; i < loc.length; i++)
				loc[i] = locations.get(i);

			Attribute dimsAttr = new Attribute("dims", atrType, new long[] { dims.length }, dims);
			Attribute locationsAttr = new Attribute("locations", atrType, new long[] { locations.size() }, loc);
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

		H5Datatype type;
		if (id == -1)
			type = null;
		else
			type = new H5Datatype(DataTypeUtils.getDataType(compType));

		if (type != null) {
			if (compType.equals(Character.class))
				list = copyListCharToInt(list);
			else if (compType.equals(Boolean.class))
				list = copyListBoolToInt(list);

			T[] data = (T[]) Array.newInstance(list.get(0).getClass(), list.size());
			for (int i = 0; i < list.size(); i++)
				data[i] = (T) list.get(i);

			writeData(type, data, new long[] { data.length }, name);
		} else if (compType != null) {
			ArrayList<Integer> locations = new ArrayList<Integer>();
			createPath(name, "");

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null) {
					if (compType.isArray())
						writeArray(new long[] { Array.getLength(list.get(i)) }, list.get(i), name + "/" + i,
								DataTypeUtils.getDataType(list.get(i).getClass().toString()));
					else if (List.class.isAssignableFrom(compType))
						writeList((List) list.get(i), name + "/" + i);
					else if (Set.class.isAssignableFrom(compType))
						writeSet((Set) list.get(i), name + "/" + i);
					else if (Map.class.isAssignableFrom(compType))
						writeMap((Map) list.get(i), name + "/" + i);
					else
						writeObjectHelper(list.get(i), name + "/" + i);

					Datatype attrType = new H5Datatype(Datatype.CLASS_STRING,
							list.get(i).getClass().toString().length() + 1, -1, -1);
					Attribute classAttr = new Attribute("class", attrType, new long[] { 1 },
							new String[] { list.get(i).getClass().getName() });
					writeAttribute(name + "/" + i, classAttr);

					locations.add(i);
				}
			}
			H5Datatype atrType = DataTypeUtils.getType("Integer");

			int loc[] = new int[locations.size()];
			for (int i = 0; i < loc.length; i++)
				loc[i] = locations.get(i);

			Attribute locationsAttr = new Attribute("locations", atrType, new long[] { locations.size() }, loc);
			writeAttribute(name, locationsAttr);
		}
		Datatype attrType = new H5Datatype(Datatype.CLASS_STRING, compType.toString().length() + 1, -1, -1);
		Attribute classAttr = new Attribute("componentClass", attrType, new long[] { 1 },
				new String[] { compType.getName() });
		writeAttribute(name + "/", classAttr);
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

		H5Datatype type;
		if (id == -1)
			type = null;
		else
			type = new H5Datatype(DataTypeUtils.getDataType(compType));

		if (type != null) {
			if (compType.equals(Character.class)) {
				set = copySetCharToInt(set);
				it = set.iterator();
			} else if (compType.equals(Boolean.class)) {
				set = copySetBooltoInt(set);
				it = set.iterator();
			}
			T[] data = (T[]) Array.newInstance(set.toArray()[0].getClass(), set.size());
			int count = 0;
			while (it.hasNext()) {
				data[count] = it.next();
				count++;
			}

			long[] dims = { data.length };
			writeData(type, data, dims, name);
		} else if (compType != null) {
			ArrayList<Integer> locations = new ArrayList<Integer>();
			createPath(name, "");

			int i = 0;
			while (it.hasNext()) {
				if (it.next() != null) {
					Object obj = it.next();
					if (compType.isArray())
						writeArray(new long[] { Array.getLength(obj) }, obj, name + "/" + i,
								DataTypeUtils.getDataType(obj.getClass().toString()));
					else if (List.class.isAssignableFrom(compType))
						writeList((List) obj, name + "/" + i);
					else if (Set.class.isAssignableFrom(compType))
						writeSet((Set) obj, name + "/" + i);
					else if (Map.class.isAssignableFrom(compType))
						writeMap((Map) obj, name + "/" + i);

					Datatype attrType = new H5Datatype(Datatype.CLASS_STRING, obj.getClass().toString().length() + 1,
							-1, -1);
					Attribute classAttr = new Attribute("class", attrType, new long[] { 1 },
							new String[] { obj.getClass().getName() });
					writeAttribute(name + "/" + i, classAttr);

					locations.add(i);
				}
				i++;
			}
			int loc[] = new int[locations.size()];
			for (int j = 0; j < loc.length; j++)
				loc[j] = locations.get(j);

			H5Datatype atrType = DataTypeUtils.getType("Integer");
			Attribute locationsAttr = new Attribute("locations", atrType, new long[] { locations.size() }, loc);
			writeAttribute(name, locationsAttr);
		}
		Datatype attrType = new H5Datatype(Datatype.CLASS_STRING, compType.toString().length() + 1, -1, -1);
		Attribute classAttr = new Attribute("componentClass", attrType, new long[] { 1 },
				new String[] { compType.getName() });
		writeAttribute(name + "/", classAttr);
	}

	// Writes a map to a dataset
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> void writeMap(Map map, String name) {
		Set set = map.keySet();
		Class<?> valClass = null, keyClass = null;

		for (Object i : set) {
			if (i != null)
				keyClass = i.getClass();
			Object tmp = map.get(i);
			if (tmp != null)
				valClass = tmp.getClass();
			if (keyClass != null && valClass != null)
				break;
		}

		if (valClass != null && keyClass != null) {
			H5Datatype typeKey = null, typeValue = null;
			Iterator<T> it = set.iterator();
			int keyID = DataTypeUtils.getDataType(keyClass);
			int valID = DataTypeUtils.getDataType(valClass);

			createPath(name, null);

			if (keyID != -1)
				typeKey = new H5Datatype(keyID);
			if (valID != -1)
				typeValue = new H5Datatype(valID);

			T[] keys = (T[]) Array.newInstance(keyClass, set.size());
			T[] vals = (T[]) Array.newInstance(valClass, set.size());

			long[] dims = { vals.length };

			int count = 0;
			while (it.hasNext()) {
				keys[count] = it.next();
				vals[count] = (T) map.get(keys[count]);
				count++;
			}
			// Write the Keys
			// True when key type is primitive
			if (keyID != -1) {
				if (keyClass.equals(Character.class)) {
					T[] tmp = (T[]) Array.newInstance(Integer.class, set.size());
					copyArrayCharToInt(keys, tmp);
					keys = tmp;
				} else if (keyClass.equals(Boolean.class)) {
					T[] tmp = (T[]) Array.newInstance(Integer.class, set.size());
					copyArrayBoolToInt(keys, tmp);
					keys = tmp;
				}
				writeData(typeKey, keys, dims, name + "/keys");

				Datatype attrType = new H5Datatype(Datatype.CLASS_STRING, keyClass.getName().length() + 1, -1, -1);
				Attribute classAttr = new Attribute("class", attrType, new long[] { 1 },
						new String[] { keyClass.getName() });
				writeAttribute(name + "/keys", classAttr);
			} else {
				ArrayList<Integer> locations = new ArrayList<Integer>();
				createPath("keys", name);

				for (int i = 0; i < keys.length; i++) {
					if (keys[i] != null) {
						if (keyClass.isArray())
							writeArray(new long[] { Array.getLength(keys[i]) }, keys[i], name + "/keys/" + i,
									DataTypeUtils.getDataType(keys[i].getClass().toString()));
						else if (List.class.isAssignableFrom(keyClass))
							writeList((List) keys[i], name + "/keys/" + i);
						else if (Set.class.isAssignableFrom(keyClass))
							writeSet((Set) keys[i], name + "/keys/" + i);
						else if (Map.class.isAssignableFrom(keyClass))
							writeMap((Map) keys[i], name + "/keys/" + i);
						else
							writeObjectHelper(keys[i], name + "/keys/" + i);

						Datatype attrType = new H5Datatype(Datatype.CLASS_STRING,
								keys[i].getClass().toString().length() + 1, -1, -1);
						Attribute classAttr = new Attribute("class", attrType, new long[] { 1 },
								new String[] { keys[i].getClass().getName() });
						writeAttribute(name + "/keys/" + i, classAttr);

						locations.add(i);
					}
				}
				int loc[] = new int[locations.size()];
				for (int j = 0; j < loc.length; j++)
					loc[j] = locations.get(j);

				H5Datatype attrIntType = DataTypeUtils.getType("Integer");
				Datatype attrStringType = new H5Datatype(Datatype.CLASS_STRING, keyClass.toString().length() + 1, -1,
						-1);
				Attribute locationsAttr = new Attribute("locations", attrIntType, new long[] { locations.size() }, loc);
				Attribute classType = new Attribute("class", attrStringType, new long[] { 1 },
						new String[] { keyClass.getName() });
				writeAttribute(name + "/keys", locationsAttr, classType);
			}

			// Write the values
			// True when Value type is primitive
			if (valID != -1) {
				if (valClass.equals(Character.class)) {
					T[] tmp = (T[]) Array.newInstance(Integer.class, set.size());
					copyArrayCharToInt(vals, tmp);
					vals = tmp;
				} else if (valClass.equals(Boolean.class)) {
					T[] tmp = (T[]) Array.newInstance(Integer.class, set.size());
					copyArrayBoolToInt(vals, tmp);
					vals = tmp;
				}
				writeData(typeValue, vals, dims, name + "/" + "values");

				Datatype attrType = new H5Datatype(Datatype.CLASS_STRING, valClass.getName().length() + 1, -1, -1);
				Attribute classAttr = new Attribute("class", attrType, new long[] { 1 },
						new String[] { valClass.getName() });
				writeAttribute(name + "/values", classAttr);
			} else {
				ArrayList<Integer> locations = new ArrayList<Integer>();
				createPath("values", name);

				for (int i = 0; i < vals.length; i++) {
					if (vals[i] != null) {
						if (valClass.isArray())
							writeArray(new long[] { Array.getLength(vals[i]) }, vals[i], name + "/values/" + i,
									DataTypeUtils.getDataType(vals[i].getClass().toString()));
						else if (List.class.isAssignableFrom(valClass))
							writeList((List) vals[i], name + "/values/" + i);
						else if (Set.class.isAssignableFrom(valClass))
							writeSet((Set) vals[i], name + "/values/" + i);
						else if (Map.class.isAssignableFrom(valClass))
							writeMap((Map) vals[i], name + "/values/" + i);
						else
							writeObjectHelper(vals[i], name + "/values/" + i);

						Datatype attrType = new H5Datatype(Datatype.CLASS_STRING,
								vals[i].getClass().toString().length() + 1, -1, -1);
						Attribute classAttr = new Attribute("class", attrType, new long[] { 1 },
								new String[] { vals[i].getClass().getName() });
						writeAttribute(name + "/values/" + i, classAttr);

						locations.add(i);
					}
				}
				int loc[] = new int[locations.size()];
				for (int j = 0; j < loc.length; j++)
					loc[j] = locations.get(j);

				H5Datatype attrIntType = DataTypeUtils.getType("Integer");
				Datatype attrStringType = new H5Datatype(Datatype.CLASS_STRING, valClass.toString().length() + 1, -1,
						-1);
				Attribute locationsAttr = new Attribute("locations", attrIntType, new long[] { locations.size() }, loc);
				Attribute classType = new Attribute("class", attrStringType, new long[] { 1 },
						new String[] { valClass.getName() });
				writeAttribute(name + "/values", locationsAttr, classType);
			}
		}
	}

	// Writes the object to file
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
				recursiveIterator = 0;
				String name = "";
				long[] dims = { -1 };
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
						if (!localGroup.equals(""))
							createPath(localGroup, defaultPath + "/" + path);

						name = defaultPath + "/" + path + "/" + localGroup + "/" + name;

						Class<?> type = field.getType();
						if (type.equals(int.class) || type.equals(Integer.class)) {
							writeInt(field.get(obj), name);
						} else if (type.equals(long.class) || type.equals(Long.class)) {
							writeLong(field.get(obj), name);
						} else if (type.equals(double.class) || type.equals(Double.class)) {
							writeDouble(field.get(obj), name);
						} else if (type.equals(short.class) || type.equals(Short.class)) {
							writeShort(field.get(obj), name);
						} else if (type.equals(float.class) || type.equals(Float.class)) {
							writeFloat(field.get(obj), name);
						} else if (type.equals(byte.class) || type.equals(Byte.class)) {
							writeByte(field.get(obj), name);
						} else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
							writeBoolean(field.get(obj), name);
						} else if (type.equals(char.class) || type.equals(Character.class)) {
							writeChar(field.get(obj), name);
						} else if (type.equals(String.class)) {
							writeString((String) field.get(obj), name);
//						}
//						
//						 if(PRIMITIVE_TYPE.contains(type)) {
//							if (type.equals(int.class)) {
//								writeInt(field.get(obj), name);
//							} else if (type.equals(long.class)) {
//								writeLong(field.get(obj), name);
//							} else if (type.equals(double.class)) {
//								writeDouble(field.get(obj), name);
//							} else if (type.equals(float.class)) {
//								writeFloat(field.get(obj), name);
//							} else if (type.equals(short.class)) {
//								writeShort(field.get(obj), name);
//							} else if (type.equals(char.class)) {
//								writeChar(field.get(obj), name);
//							} else if (type.equals(boolean.class)) {
//								writeBoolean(field.get(obj), name);
//							} else if (type.equals(byte.class)) {
//								writeByte(field.get(obj), name);
//							}
//						} else if (WRAPPER_TYPE.contains(type)) {
//							if (type.equals(Integer.class)) {
//								writeInt(field.get(obj), name);
//							} else if (type.equals(Long.class)) {
//								writeLong(field.get(obj), name);
//							} else if (type.equals(Double.class)) {
//								writeDouble(field.get(obj), name);
//							} else if (type.equals(Float.class)) {
//								writeFloat(field.get(obj), name);
//							} else if (type.equals(Short.class)) {
//								writeShort(field.get(obj), name);
//							} else if (type.equals(Character.class)) {
//								writeChar(field.get(obj), name);
//							} else if (type.equals(String.class)) {
//								writeString((String) field.get(obj), name);
//							} else if (type.equals(Boolean.class)) {
//								writeBoolean(field.get(obj), name);
//							} else if (type.equals(Byte.class)) {
//								writeByte(field.get(obj), name);
//							}
						}  else if (type.isArray()) {
							if (dims[0] == -1) {
								int[] temp = getDimensions(field.get(obj));
								dims = new long[temp.length];
								for (int i = 0; i < temp.length; i++)
									dims[i] = (long) temp[i];
							}
							writeArray(dims, field.get(obj), name, DataTypeUtils.getDataType(field));
						} else if (List.class.isAssignableFrom(type)) {
							writeList((List) field.get(obj), name);
						} else if (Set.class.isAssignableFrom(type)) {
							writeSet((Set) field.get(obj), name);
						} else if (Map.class.isAssignableFrom(type)) {
							writeMap((Map) field.get(obj), name);
						} else if (field.get(obj) instanceof HDF5Serializable
								&& field.getDeclaringClass() != field.getClass()) {
							writeObjectHelper(field.get(obj), name);
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
			Dataset dset = (H5ScalarDS) file.createScalarDS("/" + name, null, type, dims, null, null, 0, null);
			int dataset_id = dset.open();
			dset.write(data);
			dset.close(dataset_id);
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

	// Converts a Character Array to an int array
	private static void copyArrayCharToInt(Object original, Object copy) {
		int n = Array.getLength(original);
		for (int i = 0; i < n; i++) {
			Object e = Array.get(original, i);
			if (e != null && e.getClass().isArray()) {
				copyArrayCharToInt(e, Array.get(copy, i));
			} else {
				char tmp = (char) Array.get(original, i);
				Integer tmpChar = (int) tmp;
				Array.set(copy, i, tmpChar);
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
				boolean boolTmp = (boolean) Array.get(original, i);
				int tmp = boolTmp ? 1 : 0;
				Array.set(copy, i, tmp);
			}
		}
	}

	// Returns the base class of an n-dimensional array
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
			} catch (Exception e) {

			} finally {
				base += str + "/";
			}
		}
	}

	// Writes one or more attributes to a file
	private void writeAttribute(String name, Attribute... attr) {
		HObject obj = null;

		try {
			obj = file.get(name);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		try {
			for (Attribute a : attr)
				file.writeAttribute(obj, a, false);
		} catch (HDF5Exception e) {
			e.printStackTrace();
		}
	}

	// Converts an n-dimensional array to a single dimension (Calls recursive
	// function)
	private void flattenArray(Object original, Object flattened) {
		recursiveIterator = 0;
		String compType = "" + original.getClass();
		int size = compType.length() - compType.replace("[", "").length();
		flattenArrayRecursiveHelper(original, flattened, size);
	}

	// Converts an n-dimensional array to a single dimension Recursively
	private void flattenArrayRecursiveHelper(Object original, Object flattened, int count) {
		int n = Array.getLength(original);
		for (int i = 0, max = count; i < n; i++, count = max) {
			Object e = Array.get(original, i);
			count--;
			if (e != null && count > 0) {
				if (count > 0)
					flattenArrayRecursiveHelper(e, flattened, count);
			} else {
				Array.set(flattened, recursiveIterator, Array.get(original, i));
				recursiveIterator++;
			}
		}
	}

	// Returns a converted Integer set from a character set
	private Set<Integer> copySetCharToInt(Set<Character> set) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		String type = set.getClass().toString();
		Set<Integer> newSet = null;

		Iterator<Character> itr = set.iterator();
		while (itr.hasNext()) {
			list.add((Integer) ((int) ((char) itr.next())));
		}

		if (type.equals("class java.util.HashSet")) {
			newSet = new HashSet<Integer>(list);
		} else if (type.equals("class java.util.LinkedHashSet")) {
			newSet = new LinkedHashSet<Integer>(list);
		} else if (type.equals("class java.util.TreeSet")) {
			newSet = new TreeSet<Integer>(list);
		}
		return newSet;
	}

	// Returns a converted Integer set from a boolean set
	private Set<Integer> copySetBooltoInt(Set<Boolean> set) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		String type = set.getClass().toString();
		Set<Integer> newSet = null;

		Iterator<Boolean> itr = set.iterator();
		while (itr.hasNext()) {
			int element = ((boolean) itr.next() ? (int) 1 : (int) 0);
			list.add((Integer) element);
		}

		if (type.equals("class java.util.HashSet"))
			newSet = new HashSet<Integer>(list);
		else if (type.equals("class java.util.LinkedHashSet"))
			newSet = new LinkedHashSet<Integer>(list);
		else if (type.equals("class java.util.TreeSet"))
			newSet = new TreeSet<Integer>(list);
		return newSet;
	}

	// Returns a converted integer list from a character list
	private List<Integer> copyListCharToInt(List<Character> list) {
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		String type = list.getClass().toString();
		List<Integer> newList = null;

		for (int i = 0; i < list.size(); i++)
			tempList.add((Integer) ((int) ((char) list.get(i))));

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

	// Returns a converted integer list from a boolean list
	private List<Integer> copyListBoolToInt(List<Boolean> list) {
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		String type = list.getClass().toString();
		List<Integer> newList = null;

		for (int i = 0; i < list.size(); i++) {
			int element = ((boolean) list.get(i) ? (int) 1 : (int) 0);
			tempList.add((Integer) element);
		}

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