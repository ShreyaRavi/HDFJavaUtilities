package HDFJavaUtils;

import java.lang.reflect.Array;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5Datatype;
import ncsa.hdf.object.h5.H5File;
import ncsa.hdf.object.h5.H5ScalarDS;

public class DatasetUtils {
	
	public void createFixedDS(String fileName, String name, long[] dims, H5Datatype type) {
		try {
			H5File file = new H5File(fileName, FileFormat.WRITE);
			file.open();
			file.createScalarDS("/" + name, null, type, dims, null, null, 0, null, null);
		} catch (Exception e) {
			
		}
	}
	
	public void createExtendibleDS(String fileName, String name, H5Datatype type, 
			long[] dims) {
		long[] chunkDims = null;
		createExtendibleDS(fileName, name, type, dims, chunkDims);
	}
	
	public void createExtendibleDS(String fileName, String name, H5Datatype type, 
			long[] dims, long[] chunkDims, int gzip) {
		boolean[] extendibleDims = getExtendibleDims(dims);
		createExtendibleDS(fileName, name, type, dims, extendibleDims, chunkDims, gzip);
	}
	
	public void createExtendibleDS(String fileName, String name, H5Datatype type, 
			long[] dims, long[] chunkDims) {
		int gzip = 0;
		createExtendibleDS(fileName, name, type, dims, chunkDims, gzip);
	}
	
	public void createExtendibleDS(String fileName, String name, H5Datatype type, 
			long[] dims, boolean[] extendibleDims) {
		long[] chunkDims = null;
		createExtendibleDS(fileName, name, type, dims, extendibleDims, chunkDims);
	}
	
	public void createExtendibleDS(String fileName, String name, H5Datatype type, 
			long[] dims, boolean[] extendibleDims, long[] chunkDims) {
		int gzip = 0;
		createExtendibleDS(fileName, name, type, dims, extendibleDims, chunkDims, gzip);
	}
	
	public void createExtendibleDS(String fileName, String name, H5Datatype type,
			long[] dims, boolean[] extendibleDims, long[] chunkDims, int gzip) {
		Object fill = null;
		createExtendibleDS(fileName, name, type, dims, extendibleDims, chunkDims, gzip, fill);
	}
	
	public void createExtendibleDS(String fileName, String name, H5Datatype type,
			long[] dims, boolean[] extendibleDims, long[] chunkDims, int gzip, Object fill) {
		try {
			H5File file = new H5File(fileName, FileFormat.WRITE);
			file.open();
			long[] maxDims = intToLongArr(getMaxDims(dims, extendibleDims));
			file.createScalarDS("/" + name, null, type, dims, maxDims, chunkDims, gzip, fill, null);
			file.close();
		} catch (Exception e) {
			
		}
	}
	
	public void writeExtendibleDS(String fileName, String name, Object obj) {
		long[] chunkDims = {1,1};
		int gzip = 0;
		long[] dims = intToLongArr(getDimensions(obj));
		writeExtendibleDS(fileName, name, obj, dims, chunkDims, gzip);
	}
	
	public void writeExtendibleDS(String fileName, String name, Object obj, long[] dims) {
		long[] chunkDims = {1,1};
		int gzip = 0;
		writeExtendibleDS(fileName, name, obj, dims, chunkDims, gzip);
	}
	
	public void writeExtendibleDS(String fileName, String name, Object obj, long[] dims, long[] chunkDims) {
		int gzip = 0;
		writeExtendibleDS(fileName, name, obj, dims, chunkDims, gzip);
	}

	public void writeExtendibleDS(String fileName, String name, Object obj, long[] dims, long[] chunkDims, int gzip) {
		H5File file = new H5File(fileName, FileFormat.CREATE);
		long[] maxDims = intToLongArr(getMaxDims(dims, getExtendibleDims(dims)));
		int typeID = DataTypeUtils.getDataType(obj);
		if (typeID == HDF5Constants.H5T_NATIVE_CHAR) {
			obj = copyArrayCharToInt(obj);
			typeID = HDF5Constants.H5T_NATIVE_INT;
		} else if (typeID == HDF5Constants.H5T_NATIVE_HBOOL) {
			obj = copyArrayBoolToInt(obj);
			typeID = HDF5Constants.H5T_NATIVE_INT;
		}
		final H5Datatype type = new H5Datatype(typeID);
		try {
			file.createScalarDS("/" + name, null, type, dims, maxDims, chunkDims, gzip, null, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
  
	public void extendExtendibleDS(String fileName, String name, Object obj) {
		H5File file = new H5File(fileName, FileFormat.WRITE);
		long[] extDims = intToLongArr(getDimensions(obj));
		try {
			int typeID = DataTypeUtils.getDataType(obj);
			if (typeID == HDF5Constants.H5T_NATIVE_CHAR) {
				obj = copyArrayCharToInt(obj);
				typeID = HDF5Constants.H5T_NATIVE_INT;
			} else if (typeID == HDF5Constants.H5T_NATIVE_HBOOL) {
				obj = copyArrayBoolToInt(obj);
				typeID = HDF5Constants.H5T_NATIVE_INT;
			}
			H5ScalarDS dset = (H5ScalarDS) file.get(name);
			int dsetID = dset.open();
			dset.getMetadata();
			long[] dims = dset.getDims();
			dset.extend(extDims);
			int dspace_id = H5.H5Dget_space(dsetID);
			H5.H5Sselect_all(dspace_id);
			H5.H5Sselect_hyperslab(dspace_id, HDF5Constants.H5S_SELECT_NOTB, new long[] {0,0},
					null, dims, null);
			H5.H5Dwrite(dsetID, typeID, HDF5Constants.H5S_ALL,
					dspace_id, HDF5Constants.H5P_DEFAULT, obj);
			dset.close(dsetID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  
	public Object readExtendibleDataset(String fileName, String name, Object obj) {
		H5File file = new H5File(fileName, FileFormat.READ);
		Class<?> datatype = DataTypeUtils.getArrayType(obj);
		int HDF5Datatype = DataTypeUtils.getDataType(datatype);
		try {
			H5ScalarDS dset = (H5ScalarDS) file.get(name);
			int dsetID = dset.open();
			Object data;
			dset.getMetadata();
			int[] intDims = longToIntArr(dset.getDims());
			if (datatype == char.class) {
				data = Array.newInstance(int.class, intDims);
				H5.H5Dread(dsetID, HDF5Constants.H5T_NATIVE_INT, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
						HDF5Constants.H5P_DEFAULT, data);
				data = copyArrayIntToChar(data, intDims);
			} else if (datatype == boolean.class) {
				data = Array.newInstance(int.class, intDims);
				H5.H5Dread(dsetID, HDF5Constants.H5T_NATIVE_INT, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
						HDF5Constants.H5P_DEFAULT, data);
				data = copyArrayIntToBool(data, intDims);
			} else {
				data = Array.newInstance(datatype, intDims);
				H5.H5Dread(dsetID, HDF5Datatype, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL,
						HDF5Constants.H5P_DEFAULT, data);
			}
			dset.close(dsetID);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	private void selectSubset(int dspaceID, long[] start, long[] stride, long[] count, long[] block, SubsetSelect subsetSelect) {
		int selectType = 0;
		switch (subsetSelect) {
		case SET:
			selectType = HDF5Constants.H5S_SELECT_SET;
			break;
		case NOTA:
			selectType = HDF5Constants.H5S_SELECT_NOTA;
			break;
		case NOTB:
			selectType = HDF5Constants.H5S_SELECT_NOTB;
			break;
		case OR:
			selectType = HDF5Constants.H5S_SELECT_OR;
			break;
		case AND:
			selectType = HDF5Constants.H5S_SELECT_AND;
			break;
		case XOR:
			selectType = HDF5Constants.H5S_SELECT_XOR;
			break;
		}
		try {
			H5.H5Sselect_hyperslab(dspaceID, selectType, start, stride, count, block);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeHyperslab(String fileName, String name, Object obj, long[] dims, 
			long[] start, long[] stride, long[] count, long[] block) {
		try {
			H5File file = new H5File(fileName, FileFormat.WRITE);
			file.open();
			int typeID = DataTypeUtils.getDataType(obj);
			H5ScalarDS dset = (H5ScalarDS) file.get(name);
			int dsetID = dset.open();
			int dspaceID = H5.H5Dget_space(dsetID);
			selectSubset(dspaceID, start, stride, count, block, SubsetSelect.SET);
			H5.H5Dwrite(dsetID, typeID, HDF5Constants.H5S_ALL, dspaceID, HDF5Constants.H5P_DEFAULT, obj);
			dset.close(dsetID);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object readHyperslab(String fileName, String name, Object obj, long[] dims,
			long[] start, long[] stride, long[] count, long[] block) {
		
		try {
			H5File file = new H5File(fileName, FileFormat.READ);
			file.open();
			int typeID = DataTypeUtils.getDataType(obj);
			H5ScalarDS dset = (H5ScalarDS) file.get(name);
			int dsetID = dset.open();
			int dspaceID = H5.H5Dget_space(dsetID);
			selectSubset(dspaceID, start, stride, count, block, SubsetSelect.SET);
			H5.H5Dread(dsetID, typeID, HDF5Constants.H5S_ALL, dspaceID, HDF5Constants.H5P_DEFAULT, obj);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static boolean[] getExtendibleDims(long[] dims) {
		boolean[] extendibleDims = new boolean[dims.length];
		for (int i = 0; i < dims.length; i++) {
			extendibleDims[i] = true;
		}
		return extendibleDims;		
	}
	private static int[] getMaxDims(long[] dims, boolean[] customMaxDims) {
		int[] maxDims = new int[customMaxDims.length];
		
		for (int i = 0; i < customMaxDims.length; i++) {
			if (i > (dims.length - 1)) {
				if (customMaxDims[i]) {
					maxDims[i] = HDF5Constants.H5S_UNLIMITED;
				} else {
					maxDims[i] = 0;
				}
			} else {
				if (customMaxDims[i]) {
					maxDims[i] = HDF5Constants.H5S_UNLIMITED;
				} else {
					maxDims[i] = (int)dims[i];
				}
			}
		}
		return maxDims;
	}

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

	private static int[] longToIntArr(long[] longArr) {
		int[] intArr = new int[longArr.length];
		for (int i = 0; i < longArr.length; i++) {
			intArr[i] = (int) longArr[i];
		}
		return intArr;
	}
	
	private static long[] intToLongArr(int[] intArr) {
		long[] longArr = new long[intArr.length];
		for (int i = 0; i < intArr.length; i++) {
			longArr[i] = (long) intArr[i];
		}
		return longArr;
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
	
	private static Object copyArrayIntToChar(Object original, int[] intDims) {
		Object copy = Array.newInstance(char.class, intDims);
		copyArrayIntToCharHelper(original, copy);
		return copy;
	}
	
	private static Object copyArrayIntToBool(Object original, int[] intDims) {
		Object copy = Array.newInstance(boolean.class, intDims);
		copyArrayIntToBoolHelper(original, copy);
		return copy;
	}

	// Converts an Int array to a char array
	private static void copyArrayIntToCharHelper(Object original, Object copy) {
		int n = Array.getLength(original);
		for (int i = 0; i < n; i++) {
			Object e = Array.get(original, i);
			if (e != null && e.getClass().isArray()) {
				copyArrayIntToCharHelper(e, Array.get(copy, i));
			} else {
				char tmp = (char) Array.getInt(original, i);
				Array.set(copy, i, tmp);
			}
		}
	}

	// Converts an Int Array to a boolean array
	private static void copyArrayIntToBoolHelper(Object original, Object copy) {
		int n = Array.getLength(original);
		for (int i = 0; i < n; i++) {
			Object e = Array.get(original, i);
			if (e != null && e.getClass().isArray()) {
				copyArrayIntToBoolHelper(e, Array.get(copy, i));
			} else {
				boolean tmp = Array.getInt(original, i) == 1 ? true : false;
				Array.set(copy, i, tmp);
			}
		}
	}


}
