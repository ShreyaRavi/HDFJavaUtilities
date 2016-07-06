package HDFJavaUtils;

import java.lang.reflect.Field;

import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.object.h5.H5Datatype;

//Don't ask the user if its primitive or not

public class DataTypeUtils {
	
		public static Class<?> arrType = null;
	
	// Returns the H5Datatype based on a field
		public static H5Datatype getType(Field field) {
			String type = field.getGenericType().toString();
			return getType(type, field.getClass().isPrimitive());
//			if (type.contains("Integer"))
//				return new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
//			else if (type.contains("Char"))
//				return new H5Datatype(HDF5Constants.H5T_NATIVE_CHAR);
//			else if (type.contains("Short"))
//				return new H5Datatype(HDF5Constants.H5T_NATIVE_SHORT);
//			else if (type.contains("Double"))
//				return new H5Datatype(HDF5Constants.H5T_NATIVE_DOUBLE);
//			else if (type.contains("Long"))
//				return new H5Datatype(HDF5Constants.H5T_NATIVE_LONG);
	//
//			return null;
		}

		// returns a H5Datatype based on a String
		public static H5Datatype getType(String type, boolean isPrimitive) {
			return new H5Datatype(getDataType(type, isPrimitive));
//			if (type.contains("Integer"))
//				return new H5Datatype(HDF5Constants.H5T_NATIVE_INT);
//			else if (type.contains("Char"))
//				return new H5Datatype(HDF5Constants.H5T_NATIVE_CHAR);
//			else if (type.contains("Short"))
//				return new H5Datatype(HDF5Constants.H5T_NATIVE_SHORT);
//			else if (type.contains("Double"))
//				return new H5Datatype(HDF5Constants.H5T_NATIVE_DOUBLE);
//			else if (type.contains("Long"))
//				return new H5Datatype(HDF5Constants.H5T_NATIVE_LONG);
	//
//			return null;
		}
		
		// TODO: include rest of types (byte, float)
		public static int getDataType(String type, boolean isPrimitive) {
			if (isPrimitive) {
				if (type.contains("I")) {
					arrType = Integer.TYPE;
					return HDF5Constants.H5T_NATIVE_INT;
				} else if (type.contains("C")) {
					arrType = Character.TYPE;
					return HDF5Constants.H5T_NATIVE_CHAR;
				} else if (type.contains("S")) {
					arrType = Short.TYPE;
					return HDF5Constants.H5T_NATIVE_SHORT;
				} else if (type.contains("D")) {
					arrType = Double.TYPE;
					return HDF5Constants.H5T_NATIVE_DOUBLE;
				} else if (type.contains("J")) {
					arrType = Long.TYPE;
					return HDF5Constants.H5T_NATIVE_LONG;
				} else {
					arrType = null;
					return -1;
				}				
			} else {
				arrType = null;
				if (type.contains("Integer"))
					return HDF5Constants.H5T_NATIVE_INT;
				else if (type.contains("Char"))
					return HDF5Constants.H5T_NATIVE_CHAR;
				else if (type.contains("Short"))
					return HDF5Constants.H5T_NATIVE_SHORT;
				else if (type.contains("Double"))
					return HDF5Constants.H5T_NATIVE_DOUBLE;
				else if (type.contains("Long"))
					return HDF5Constants.H5T_NATIVE_LONG;
				return -1;
			}
		}
		
		// TODO: differentiate between primitive array and Object array
		public static int getDataType(Field field, Object obj) {
			String type = field.getGenericType().toString();
			boolean primitive = false;
			try {
				primitive = field.get(obj).getClass().isArray();
			} catch (Exception e) {
				e.printStackTrace();
			}	
			return getDataType(type, primitive);
		}

}
