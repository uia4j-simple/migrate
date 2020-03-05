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

import java.sql.SQLException;

import uia.dao.Database;
import uia.dao.hana.Hana;
import uia.dao.ora.Oracle;
import uia.dao.pg.PostgreSQL;

public class DB {

    public static Database connect(String key) throws SQLException {
        String type = System.getProperty(String.format("db.%s.type", key));
        String host = System.getProperty(String.format("db.%s.host", key));
        String port = System.getProperty(String.format("db.%s.port", key));
        String schema = System.getProperty(String.format("db.%s.schema", key));
        String service = System.getProperty(String.format("db.%s.service", key));
        String user = System.getProperty(String.format("db.%s.user", key));
        String password = System.getProperty(String.format("db.%s.password", key));

        if ("HANA".equalsIgnoreCase(type)) {
            return hana(host, port, schema, service, user, password);
        }

        if ("ORA".equalsIgnoreCase(type)) {
            return ora(host, port, schema, service, user, password);
        }

        if ("PG".equalsIgnoreCase(type)) {
            return pg(host, port, schema, service, user, password);
        }

        return null;
    }

    private static Database hana(String host, String port, String schema, String service, String user, String password) throws SQLException {
        return new Hana(host, port, service, user, password);
    }

    private static Database ora(String host, String port, String schema, String service, String user, String password) throws SQLException {
        return new Oracle(host, port, service, user, password);
    }

    private static Database pg(String host, String port, String schema, String service, String user, String password) throws SQLException {
        return new PostgreSQL(host, port, service, user, password);
    }
}
