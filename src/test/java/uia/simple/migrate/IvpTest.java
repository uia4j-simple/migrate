package uia.simple.migrate;

import org.junit.Test;

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

        SimpleDatabaseTool.main(args);
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

        SimpleDatabaseTool.main(args);
    }
}
