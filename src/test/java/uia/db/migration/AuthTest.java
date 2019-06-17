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

import org.junit.Test;

import uia.db.migration.App;

public class AuthTest {

    @Test
    public void testCompareWithCli() throws Exception {
    	String cmd1 = "-c compare -ds auth -dt auth_test --table * auth -view *";
        App.main(cmd1.split(" "));
    	String cmd2 = "-c compare -ds auth_test -dt auth_2 --table-prefix AUTH_ -view-prefix VIEW_AUTH_";
        App.main(cmd2.split(" "));
    }

    @Test
    public void testCompareWithPlan() throws Exception {
    	String cmd1 = "-c compare -ds auth -dt auth_2 --plan sample/auth/compare.conf";
        App.main(cmd1.split(" "));
    	String cmd2 = "-c compare -ds auth -dt auth_test --plan sample/auth/compare.conf";
        App.main(cmd2.split(" "));
    }

    @Test
    public void testCreateTableWithCli() throws Exception {
    	String cmd = "-c create -ds auth_test -dt auth_2 --table-prefix AUTH_";
        App.main(cmd.split(" "));
    }

    @Test
    public void testCreateViewWithCli() throws Exception {
    	String cmd = "-c create -ds auth_test -dt auth_2 --view-prefix VIEW_AUTH_";
        App.main(cmd.split(" "));
    }
}
