package testSpace;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TestHashSets.class,
	TestLinkedHashSets.class,
	TestPrimitives.class,
	TestTreeSets.class,
	TestArrays.class
				})
public class AllTests {

}
