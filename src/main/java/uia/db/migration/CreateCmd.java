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
