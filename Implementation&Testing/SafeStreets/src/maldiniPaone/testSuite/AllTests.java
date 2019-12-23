package maldiniPaone.testSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import maldiniPaone.databaseConnection.ReportAndAssignmentTests;
import maldiniPaone.databaseConnection.UserDatabaseTests;

@RunWith(Suite.class)
@SuiteClasses({
	UserDatabaseTests.class,
	ReportAndAssignmentTests.class
	
})
public class AllTests {

}
