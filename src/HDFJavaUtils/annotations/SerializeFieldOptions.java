package HDFJavaUtils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SerializeFieldOptions {
	
	public String name() default "";
	
	public String path() default "DEFAULT";
	
	public long[] dimensions() default {1};
	
	public boolean ignore() default false;
	
}
