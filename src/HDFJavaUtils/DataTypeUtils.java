package HDFJavaUtils;

import java.lang.reflect.Field;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.object.Datatype;
import ncsa.hdf.object.h5.H5Datatype;

public class DataTypeUtils {

	/**
	 * Gets the H5Datatype object from the corresponding Field object type
	 * 
	 * @param field
	 *            The Field object representing the input data
	 * @return An H5Datatype object that corresponds to the input data
	 */
	public static H5Datatype getType(Field field) {
		String type = field.getGenericType().toString();
		try {
			return getType(type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets the H5Datatype object from the corresponding type of the input data
	 * 
	 * @param type
	 *            The type of the input data represented as a String object
	 * @return An H5Datatype object that corresponds to the input data
	 */
	public static H5Datatype getType(String type) {
		if (type.contains("java.lang.String")) {
			return new H5Datatype(Datatype.CLASS_STRING, 1024, -1, -1);
		}
		return new H5Datatype(getDataType(type));
	}

	/**
	 * Gets the HDF5Constant that corresponds with the type of input data
	 * 
	 * @param type
	 *            The name of the class of the input data/object
	 * @return An int that represents the HDF5Constant that represents the type
	 *         of input data
	 */
	public static int getDataType(String type) {
		if ((type.lastIndexOf("[I") == (type.length() - 2))
				|| type.contains("Integer"))
			return HDF5Constants.H5T_NATIVE_INT;
		else if ((type.lastIndexOf("[C") == (type.length() - 2))
				|| type.contains("Char"))
			return HDF5Constants.H5T_NATIVE_CHAR;
		else if ((type.lastIndexOf("[B") == (type.length() - 2))
				|| type.contains("Byte"))
			return HDF5Constants.H5T_NATIVE_INT8;
		else if ((type.lastIndexOf("[S") == (type.length() - 2))
				|| type.contains("Short"))
			return HDF5Constants.H5T_NATIVE_SHORT;
		else if ((type.lastIndexOf("[D") == (type.length() - 2))
				|| type.contains("Double"))
			return HDF5Constants.H5T_NATIVE_DOUBLE;
		else if ((type.lastIndexOf("[J") == (type.length() - 2))
				|| type.contains("Long"))
			return HDF5Constants.H5T_NATIVE_LONG;
		else if ((type.lastIndexOf("[F") == (type.length() - 2))
				|| type.contains("Float"))
			return HDF5Constants.H5T_NATIVE_FLOAT;
		else if ((type.lastIndexOf("[B") == (type.length() - 2))
				|| type.contains("Byte"))
			return HDF5Constants.H5T_NATIVE_CHAR;
		else if ((type.lastIndexOf("[Z") == (type.length() - 2))
				|| type.contains("Boolean"))
			return HDF5Constants.H5T_NATIVE_HBOOL;
		else
			return -1;
	}

	/**
	 * Gets the HDF5Constant from the corresponding Field object type
	 * 
	 * @param field
	 *            The field object representing the input data
	 * @return An int that represents the HDF5Constant that represents the type
	 *         of input data
	 */
	public static int getDataType(Field field) {
		String type = field.getGenericType().toString();
		return getDataType(type);
	}
	
	public static int getDataType(Object obj) {
		String type = obj.getClass().getComponentType().toString();
		return getDataType(type);
	}

	/**
	 * Gets the Class object representing the type of data in the array when the
	 * array is passed in
	 * 
	 * @param obj
	 *            The Array object that contains the data read from the H5File
	 * @return A Class object representing the type of data in the array
	 */
	public static Class<?> getArrayType(Object obj) {
		String type = obj.getClass().getName();
		if (type.lastIndexOf("[I") == (type.length() - 2)) {
			return Integer.TYPE;
		} else if (type.lastIndexOf("[C") == (type.length() - 2)) {
			return Character.TYPE;
		} else if (type.lastIndexOf("[S") == (type.length() - 2)) {
			return Short.TYPE;
		} else if (type.lastIndexOf("[D") == (type.length() - 2)) {
			return Double.TYPE;
		} else if (type.lastIndexOf("[J") == (type.length() - 2)) {
			return Long.TYPE;
		} else if (type.lastIndexOf("[F") == (type.length() - 2)) {
			return Float.TYPE;
		} else if (type.lastIndexOf("[B") == (type.length() - 2)) {
			return Byte.TYPE;
		} else if (type.lastIndexOf("[Z") == (type.length() - 2)) {
			return Boolean.TYPE;
		}
		return null;
	}

}
