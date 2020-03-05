package uia.db.migration;

import org.junit.Test;

public class PmsTest {

    @Test
    public void testFWKS() throws Exception {
        String cmd = "compare -ds pms -dt pms_fwks -table -view-prefix view_";
        App.main(cmd.split(" "));
    }

    @Test
    public void testCN() throws Exception {
        String cmd = "compare -ds pms -dt pms_cn -table -view-prefix view_";
        App.main(cmd.split(" "));
    }
}
