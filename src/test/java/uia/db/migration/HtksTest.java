package uia.db.migration;

import org.junit.Test;

import uia.db.migration.App;

public class HtksTest {

    @Test
    public void testCompareWithCli() throws Exception {
        String[] args = new String[] {
                "-c",
                "compare",
                "-ds",
                "prodarch",
                "-dt",
                "testarch",
                "--table-prefix",
                "ZZT_"
        };

        App.main(args);
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

        App.main(args);
    }

    @Test
    public void testCreateViewWithCli() throws Exception {
        String[] args = new String[] {
                "-c",
                "create",
                "-ds",
                "pie",
                "-dt",
                "pieht",
                "--view",
                "view_insp_rule"
        };

        App.main(args);
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

        App.main(args);
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

        App.main(args);
    }
}
