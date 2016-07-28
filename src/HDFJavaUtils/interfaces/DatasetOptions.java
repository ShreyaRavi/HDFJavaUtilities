package HDFJavaUtils.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import HDFJavaUtils.constants.DatasetConstants;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DatasetOptions {
	
	long[] chunkDims() default {-1};
	int gzip() default DatasetConstants.GZIP;
	boolean isExtendible() default DatasetConstants.IS_EXTENDIBLE;
	

}
