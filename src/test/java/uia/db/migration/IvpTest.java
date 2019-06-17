package uia.db.migration;

import org.junit.Test;

import uia.db.migration.App;

public class IvpTest {

    @Test
    public void testCompare() throws Exception {
        String[] args = new String[] {
                "-c",
                "compare",
                "-ds",
                "ivp1",
                "-dt",
                "ivp2",
                "--table-prefix",
                "ivp"
        };

        App.main(args);
    }

    @Test
    public void testCreate() throws Exception {
        String[] args = new String[] {
                "-c",
                "create",
                "-ds",
                "ivp1",
                "-dt",
                "ivp2",
                "--table",
                "ivp"
        };

        App.main(args);
    }
}
