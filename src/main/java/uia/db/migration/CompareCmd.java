/*******************************************************************************
 * Copyright 2019 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uia.db.migration;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

import uia.dao.ComparePlan;
import uia.dao.CompareResult;
import uia.dao.TableType;

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
            CompareResult cr;
            if(table1 == null) {
            	cr = new CompareResult(tableName);
            	cr.setPassed(false);
            	cr.addMessage("table not found");
            }
            else {
            	cr = table1.sameAs(table2, this.tablePlan);
            }
            
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

            CompareResult cr;
            if(view1 == null) {
            	cr = new CompareResult(viewName);
            	cr.setPassed(false);
            	cr.addMessage("view not found");
            }
            else {
                cr = view1.sameAs(view2, this.viewPlan);
            }
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

	@Override
	public Options createOptions() {
        Option printFailed = Option.builder()
                .longOpt("print-failed")
                .desc("print failed items only")
                .build();

        Option printMissing = Option.builder()
                .longOpt("print-missing")
                .desc("print missing items only")
                .build();

        Option source = Option.builder("ds")
                .longOpt("db-source")
                .desc("source database, <db> defined in database.conf")
                .required()
                .hasArg()
                .argName("db")
                .build();

        Option target = Option.builder("dt")
                .longOpt("db-target")
                .desc("destination database, <db> defined in database.conf")
                .required()
                .hasArg()
                .argName("db")
                .build();

        Option plan = Option.builder("p")
                .longOpt("plan")
                .desc("run a command depending on the plan file")
                .hasArg()
                .argName("file")
                .build();

        return new Options()
                .addOption(source)
                .addOption(target)
                .addOption(plan)
                .addOption(printFailed)
                .addOption(printMissing)
                .addOptionGroup(optionTable())
                .addOptionGroup(optionView());
	}

    private static OptionGroup optionTable() {
        Option table = Option.builder("t")
                .longOpt("table")
                .desc("names of tables, e.g. -t, -t TABLE1,TABLE2,TAEBL3")
                .hasArgs()
                .argName("tables")
                .valueSeparator(',')
                .optionalArg(true)
                .build();

        Option tablePrefix = Option.builder()
                .longOpt("table-prefix")
                .desc("prefix of table name")
                .hasArg()
                .argName("prefix")
                .optionalArg(true)
                .build();

        return new OptionGroup()
                .addOption(table)
                .addOption(tablePrefix);
    }

    private static OptionGroup optionView() {
        Option view = Option.builder("v")
                .longOpt("view")
                .desc("names of views, e.g. -v, -v VIEW1,VIEW2,VIEW3")
                .hasArgs()
                .argName("views")
                .valueSeparator(',')
                .optionalArg(true)
                .build();

        Option viewPrefix = Option.builder()
                .longOpt("view-prefix")
                .desc("prefix of view name")
                .hasArg()
                .argName("prefix")
                .optionalArg(true)
                .build();

        return new OptionGroup()
                .addOption(view)
                .addOption(viewPrefix);
    }
}
