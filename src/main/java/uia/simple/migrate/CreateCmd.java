package uia.simple.migrate;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.cli.CommandLine;

import uia.utils.dao.TableType;

public class CreateCmd extends AbstractCmd {

    @Override
    public void run(CommandLine cl) throws SQLException, IOException {
        boolean printPassed = !cl.hasOption("print-failed");

        System.out.println("\n====== table result ======");
        for (String tableName : this.tables) {
            TableType table = this.source.selectTable(tableName, false);
            if (table == null) {
                System.out.println("(x) " + tableName);
                System.out.println("    table not found");
            }
            else if (this.target.exists(tableName)) {
                System.out.println("(x) " + tableName);
                System.out.println("    table found");
            }
            else {
                this.target.createTable(table);
                if (printPassed) {
                    System.out.println("(o) " + tableName);
                }
            }
        }

        System.out.println("\n====== view result ======");
        for (String viewName : this.views) {
            String script = this.source.selectViewScript(viewName);
            if (script == null) {
                System.out.println("(x) " + viewName);
                System.out.println("    view not found");
            }
            else {
                if (this.target.exists(viewName)) {
                    this.target.dropView(viewName);
                }
                this.target.createView(viewName, script);
                if (printPassed) {
                    System.out.println("(o) " + viewName);
                }
            }
        }
    }
}
