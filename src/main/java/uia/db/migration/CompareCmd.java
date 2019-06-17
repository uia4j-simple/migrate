package uia.db.migration;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.cli.CommandLine;

import uia.utils.dao.ComparePlan;
import uia.utils.dao.CompareResult;
import uia.utils.dao.TableType;

public class CompareCmd extends AbstractCmd {

    private ComparePlan tablePlan;

    private ComparePlan viewPlan;

    public CompareCmd() {
        this.tablePlan = ComparePlan.table();
        this.viewPlan = ComparePlan.view();
    }

    @Override
    public void run(CommandLine cl) throws SQLException, IOException {
        initPlan(cl);
        boolean printMissing = cl.hasOption("print-missing");
        boolean printAll = !cl.hasOption("print-failed");

        System.out.println("\n====== table result ======");
        for (String tableName : this.tables) {
            TableType table1 = this.source.selectTable(tableName, false);
            TableType table2 = this.target.selectTable(tableName, false);
            CompareResult cr = table1 == null
                    ? new CompareResult(tableName, false, "table not found")
                    : table1.sameAs(table2, this.tablePlan);
            if (printMissing) {
                if (cr.isMissing()) {
                    cr.print(false);
                }
            }
            else {
                cr.print(printAll);
            }
        }

        System.out.println("\n====== view result ======");
        for (String viewName : this.views) {
            TableType view1 = this.source.selectTable(viewName, false);
            TableType view2 = this.target.selectTable(viewName, false);
            CompareResult cr = view1 == null
                    ? new CompareResult(viewName, false, "view not found")
                    : view1.sameAs(view2, this.viewPlan);
            if (printMissing) {
                if (cr.isMissing()) {
                    cr.print(false);
                }
            }
            else {
                cr.print(printAll);
            }
        }
    }

    private void initPlan(CommandLine cl) {
        if (!"true".equals(System.getProperty("table.compare.default"))) {
            boolean a1 = "true".equals(System.getProperty("table.compare.strictVarchar"));
            boolean a2 = "true".equals(System.getProperty("table.compare.strictNumeric"));
            boolean a3 = "true".equals(System.getProperty("table.compare.strictDateTime"));
            boolean a4 = "true".equals(System.getProperty("table.compare.checkNullable"));
            boolean a5 = "true".equals(System.getProperty("table.compare.checkDataSize"));
            this.tablePlan = new ComparePlan(a1, a2, a3, a4, a5);
        }

        if (!"true".equals(System.getProperty("view.compare.default"))) {
            boolean a1 = "true".equals(System.getProperty("view.compare.strictVarchar"));
            boolean a2 = "true".equals(System.getProperty("view.compare.strictNumeric"));
            boolean a3 = "true".equals(System.getProperty("view.compare.strictDateTime"));
            boolean a4 = "true".equals(System.getProperty("view.compare.checkNullable"));
            boolean a5 = "true".equals(System.getProperty("view.compare.checkDataSize"));
            this.viewPlan = new ComparePlan(a1, a2, a3, a4, a5);
        }
    }
}
