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
