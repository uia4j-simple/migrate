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

import uia.dao.TableType;

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
