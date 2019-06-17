package uia.db.migration;

import org.junit.Test;

import uia.db.migration.App;

public class AuthTest {

    @Test
    public void testCompareWithCli() throws Exception {
    	String cmd1 = "-c compare -ds auth -dt auth_test --table * auth -view *";
        App.main(cmd1.split(" "));
    	String cmd2 = "-c compare -ds auth_test -dt auth_2 --table-prefix AUTH_ -view-prefix VIEW_AUTH_";
        App.main(cmd2.split(" "));
    }

    @Test
    public void testCompareWithPlan() throws Exception {
    	String cmd1 = "-c compare -ds auth -dt auth_2 --plan sample/auth/compare.conf";
        App.main(cmd1.split(" "));
    	String cmd2 = "-c compare -ds auth -dt auth_test --plan sample/auth/compare.conf";
        App.main(cmd2.split(" "));
    }

    @Test
    public void testCreateTableWithCli() throws Exception {
    	String cmd = "-c create -ds auth_test -dt auth_2 --table-prefix AUTH_";
        App.main(cmd.split(" "));
    }

    @Test
    public void testCreateViewWithCli() throws Exception {
    	String cmd = "-c create -ds auth_test -dt auth_2 --view-prefix VIEW_AUTH_";
        App.main(cmd.split(" "));
    }
}
