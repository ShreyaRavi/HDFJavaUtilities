package HDFJavaUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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
			System.out.println(dset.read());
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
			if(data[0] == 0)
				return false;
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	
	public <T> void readObject(Object obj) {
		if(obj instanceof HDF5Serializable) {
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
			    	else if(type.equals("[I")) 
			    		field.set(obj, readIntArray(field.getName()));
			    	else if(type.contains("List")) {
			    		List list = null;
			    		String genericType = field.getGenericType().toString();
			    		if(type.equals("class java.util.ArrayList")) 
			    			list = new ArrayList<T>();
			    			Object arr = read(field.getName());
		    			try {
		    				int count = 0;
		    				while(true) {
		    					list.add(Array.get(arr, count));
		    					count++;
		    				}
		    			} catch(IndexOutOfBoundsException e) {
		    				
		    			}
			    		field.set(obj, list);
			    	} else if(type.contains("Set")) {	
		    			List list = new ArrayList();
		    			Object arr = read(field.getName());
		    			try {
		    				int count = 0;
		    				while(true) {
		    					list.add(Array.get(arr, count));
		    					count++;
		    				}
		    			} catch(IndexOutOfBoundsException | NullPointerException e) {
		    				
		    			}
		    			if(type.contains("HashSet"))
		    				field.set(obj, new HashSet(list));
		    			else if(type.contains("TreeSet")) 
		    				field.set(obj, new TreeSet(list));
			    	} else if(type.contains("Map")) {			    		
			    		Map map = new HashMap();
			    		Object arrKey = read(field.getName() + "/key");
			    		Object arrData = read(field.getName() + "/data");
		    			try {
		    				int count = 0;
		    				while(true) {
		    					map.put(Array.get(arrKey, count), Array.get(arrData, count));
		    					count++;
		    				}
		    			} catch(IndexOutOfBoundsException e) {
		    				
		    			}
				    	field.set(obj, map);
			    		
			    	}
			    	else if(type.equals("class java.lang.Boolean"))
			    		field.set(obj, readBoolean(field.getName()));
		    	} catch(IllegalArgumentException | IllegalAccessException | NullPointerException e) {
		    		e.printStackTrace();
		    	}
		    }
		}
	}
	
	
}
