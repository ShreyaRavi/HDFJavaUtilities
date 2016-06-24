package HDFJavaUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import ncsa.hdf.object.Dataset;
import ncsa.hdf.object.h5.H5File;

public class ObjectInputStream {
	
	private H5File file;
	
	public ObjectInputStream(H5File file) {
		this.file = file;
	}
	
	public int readInt(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return  Array.getInt(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public Double readDouble(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return  Array.getDouble(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1.0;
		}
	}
	
	public float readFloat(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return  Array.getFloat(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public long readLong(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return  Array.getLong(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public short readShort(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			short[] test = (short[]) dset.read();
			dset.writeMetadata(4);
			System.out.println(dset.getMetadata());
			for(short i : test) 
				System.out.print(i + ", ");
			return  Array.getShort(dset.read(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public char readChar(String name) {
		try {
			Dataset dset = (Dataset) file.get(name);
			return  (char) Array.getInt(dset.read(), 0);
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
			for(int i : intArray) {
				array += (char) i;
			}
			return array.toCharArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String readString(String name) {
		String array = "";
		try {
			Dataset dset = (Dataset) file.get(name);
			int[] intArray = (int[]) dset.getData();
			for(int i : intArray) {
				array += (char) i;
			}
			return array;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void readObject(Object obj) {
		Class<?> objClass = obj.getClass();
	    Field[] fields = objClass.getFields();
	    for(Field field : fields) {
	    	try {
	    		String type = field.get(obj).getClass().toString();
		    	if(type.equals("class java.lang.Integer")) 
		    		field.setInt(obj, readInt(field.getName()));
		    	else if(type.equals("class java.lang.Long")) 
		    		field.setLong(obj, readLong(field.getName()));
		    	else if(type.equals("class java.lang.Double")) 
		    		field.setDouble(obj, readDouble(field.getName()));
		    	else if(type.equals("class java.lang.Float")) 
		    		field.setFloat(obj, readFloat(field.getName()));
		    	else if(type.equals("class java.lang.Short")) 
		    		field.setShort(obj, readShort(field.getName()));
		    	else if(type.equals("class java.lang.Character")) 
		    		field.setChar(obj, readChar(field.getName()));
		    	else if(type.equals("class [C")) 
		    		field.set(obj, readCharArray(field.getName()));
		    	else if(type.equals("class java.lang.String"))
		    		field.set(obj, readString(field.getName()));
		    		
	    	} catch(IllegalArgumentException | IllegalAccessException | NullPointerException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
	
	
}
