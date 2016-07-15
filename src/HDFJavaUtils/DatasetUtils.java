package HDFJavaUtils;

import java.lang.reflect.Array;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;
import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

public class DatasetUtils {
	
	public String fileName = "testExtendibleDatasets.h5";
	public H5File file = new H5File(fileName, FileFormat.CREATE);

	public void createExtendibleDataset(String datasetName,
			Object arr, long[] chunkDims, int HDF5Datatype) {
		long[] dims = intToLongArr(getDimensions(arr));
		int rank = dims.length;
		long[] maxDims = intToLongArr(getMaxDims(rank));
		try {
			int file_id = file.open();
			int dataspace_id = H5.H5Screate_simple(rank, dims, maxDims);
			int plist_id = H5.H5Pcreate(HDF5Constants.H5P_DATASET_CREATE);
			H5.H5Pset_chunk(plist_id, rank, chunkDims);
			H5.H5Dcreate(file_id, datasetName, HDF5Datatype, dataspace_id, HDF5Constants.H5P_DEFAULT, plist_id, HDF5Constants.H5P_DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static int[] getMaxDims(int rank) {
		int[] maxDims = new int[rank];
		for (int i = 0; i < rank; i++) {
			maxDims[i] = HDF5Constants.H5S_UNLIMITED;
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

	private static long[] intToLongArr(int[] intArr) {
		long[] longArr = new long[intArr.length];
		for (int i = 0; i < intArr.length; i++) {
			longArr[i] = (long) intArr[i];
		}
		return longArr;
	}

}
