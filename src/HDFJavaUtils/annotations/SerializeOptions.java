package HDFJavaUtils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface SerializeOptions {
	
	String name() default "";
	
	long[] dimensions() default {1, 1};
	
	boolean ignore() default false;
	
}
