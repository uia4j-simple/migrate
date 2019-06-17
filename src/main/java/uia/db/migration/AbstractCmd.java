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

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;

import uia.utils.dao.Database;

public abstract class AbstractCmd {

    protected Database source;

    protected Database target;

    protected List<String> tables;

    protected List<String> views;

    public AbstractCmd() {
        this.tables = new ArrayList<String>();
        this.views = new ArrayList<String>();
    }

    public abstract void run(CommandLine cl) throws SQLException, IOException;

    public boolean initDatabase(CommandLine cl) throws SQLException, IOException {
        this.source = DB.connect(cl.getOptionValue("db-source"));
        if (this.source == null) {
            System.out.println("db source: " + cl.getOptionValue("db-source") + " not found");
            return false;
        }
        System.out.println("db source: " + this.source.getConnection().getMetaData().getURL());

        this.target = DB.connect(cl.getOptionValue("db-target"));
        if (this.target == null) {
            System.out.println("db target: " + cl.getOptionValue("db-target") + " not found");
            return false;
        }
        System.out.println("db target: " + this.target.getConnection().getMetaData().getURL());

        handleTable(cl);
        handleView(cl);

        if (cl.hasOption("plan")) {
            handlePlan(cl);
        }

        return true;
    }

    private void handlePlan(CommandLine cl) throws SQLException, IOException {
        String plan = cl.getOptionValue("plan");
        Properties p = new Properties(System.getProperties());
        p.load(new FileInputStream(plan));

        if (this.tables.isEmpty()) {
            String tableQuery = p.getProperty("table.query");
            String tableName = p.getProperty("table.name");
            if ("name".equalsIgnoreCase(tableQuery)) {
                if (tableName != null && tableName.trim().length() > 0) {
                    this.tables = Arrays.asList(tableName.split(","));
                }
            	else {
            		this.tables = this.source.selectTableNames();
            	}
            }
            else if ("prefix".equalsIgnoreCase(tableQuery)) {
                this.tables = this.source.selectTableNames(tableName);
            }
        }

        if (this.views.isEmpty()) {
            String viewQuery = p.getProperty("view.query");
            String viewName = p.getProperty("view.name");
            if ("name".equalsIgnoreCase(viewQuery)) {
                if (viewName != null && viewName.trim().length() > 0) {
                    this.views.addAll(Arrays.asList(viewName.split(",")));
                }
            	else {
            		this.views = this.source.selectViewNames();
            	}
            }
            else if ("prefix".equalsIgnoreCase(viewQuery)) {
                this.views.addAll(this.source.selectViewNames(viewName));
            }
        }
    }

    private void handleTable(CommandLine cl) throws SQLException {
        if (cl.hasOption("table")) {
        	String[] _tables = cl.getOptionValues("table");
        	if("*".equals(_tables[0])) {
        		this.tables = this.source.selectTableNames();
        	}
        	else {
                this.tables = Arrays.asList(_tables);
        	}
        }
        else if (cl.hasOption("table-prefix")) {
            this.tables = this.source.selectTableNames(cl.getOptionValue("table-prefix"));
        }
    }

    private void handleView(CommandLine cl) throws SQLException {
        if (cl.hasOption("view")) {
        	String[] _views = cl.getOptionValues("view");
       	if("*".equals(_views[0])) {
        		this.views = this.source.selectViewNames();
        	}
        	else {
                this.views = Arrays.asList(_views);
        	}
        }
        else if (cl.hasOption("view-prefix")) {
            this.views = this.source.selectViewNames(cl.getOptionValue("view-prefix"));
        }
    }
}
