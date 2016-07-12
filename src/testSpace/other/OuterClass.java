package testSpace.other;

import HDFJavaUtils.interfaces.HDF5Serializable;

public class OuterClass implements HDF5Serializable {
	
	private int x = 0;
	
	public class InnerClass implements HDF5Serializable {
		
		private int y = 0;
		
		public InnerClass() {
			
		}
		
		public InnerClass(int val) {
			y = val;
		}
		
		public void addToX(int num) {
			x += num;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
	}
	
	public OuterClass() {
		
	}
	
	public OuterClass(int val) {
		x = val;
	}
	
	public int getX() {
		return x;
	}

}
