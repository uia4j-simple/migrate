package uia.simple.migrate;

import org.junit.Test;

public class SimpleDatabaseToolTest {

    @Test
    public void testCompareWithCli() throws Exception {
        String[] args = new String[] {
                "-c",
                "compare",
                "-ds",
                "oradev",
                "-dt",
                "pgdev",
                "--table-prefix",
                "BOM_"
        };

        SimpleDatabaseTool.main(args);
    }

    @Test
    public void testCompareWithPlan() throws Exception {
        String[] args = new String[] {
                "-c",
                "compare",
                "-ds",
                "dev",
                "-dt",
                "pgdev",
                "--print-failed",
                "-p",
                "compare.conf"
        };

        SimpleDatabaseTool.main(args);
    }

    @Test
    public void testCreateViewWithCli() throws Exception {
        String[] args = new String[] {
                "-c",
                "create",
                "-ds",
                "oradev",
                "-dt",
                "dev",
                "--view",
                "VIEW_HOLD_SFC_DETAIL",
                "VIEW_HOLD_SFC"
        };

        SimpleDatabaseTool.main(args);
    }

    @Test
    public void testCreateTableWithCli() throws Exception {
        String[] args = new String[] {
                "-c",
                "create",
                "-ds",
                "oradev",
                "-dt",
                "pgdev",
                "--table-prefix",
                "ANSI_"
        };

        SimpleDatabaseTool.main(args);
    }

    @Test
    public void testCreateTmd() throws Exception {
        String[] args = new String[] {
                "-c",
                "create",
                "-ds",
                "tmd",
                "-dt",
                "archtest",
                "--table-prefix",
                "zzt_"
        };

        SimpleDatabaseTool.main(args);
    }
}
