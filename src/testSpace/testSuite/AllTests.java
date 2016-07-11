package testSpace.testSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import testSpace.testCases.*;

@RunWith(Suite.class)
@SuiteClasses({
	TestHashSets.class,
	TestLinkedHashSets.class,
	TestPrimitives.class,
	TestTreeSets.class,
	TestArrays.class,
	TestArrayLists.class,
	TestAccessMods.class,
	TestOther.class,
	TestLinkedLists.class,
	TestVectors.class,
	TestStacks.class,
	TestHashMaps.class,
	TestConcurrentHashMaps.class,
	TestConcurrentSkipListMaps.class,
	TestWeakHashMaps.class,
	TestTreeMaps.class,
	TestHashtables.class
				})
public class AllTests {

}
