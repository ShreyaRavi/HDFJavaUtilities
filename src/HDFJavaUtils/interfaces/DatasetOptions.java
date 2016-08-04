package HDFJavaUtils.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import HDFJavaUtils.constants.DatasetConstants;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DatasetOptions {
	
	long[] dims() default {-1};
	long[] chunkDims() default {-1};
	int gzip() default 0;
	boolean isExtendible() default false;
	long[] extendibleDims() default {-1};
	double fill() default 0.0;
	
}
