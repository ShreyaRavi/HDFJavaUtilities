package HDFJavaUtils.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SerializeFieldOptions {
	
	String name() default "";
	
	String path() default "DEFAULT";
	
	long[] dimensions() default {1, 1};
		
}
