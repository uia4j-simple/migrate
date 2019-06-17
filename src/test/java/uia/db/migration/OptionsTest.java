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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.junit.Assert;
import org.junit.Test;

import uia.db.migration.App;

public class OptionsTest {

    @Test
    public void testHelp() throws Exception {
        App.help(App.createOptions());
    }

    @Test
    public void testOptions1() throws Exception {
        String[] args = new String[] {
                "-c", "create",
                "-ds", "oradev",
                "-dt", "pgdev",
                "--table-prefix", "ANSI_",
                "-v", "VIEW_SFC,VIEW_SFC_ITEM"
        };

        CommandLineParser parser = new DefaultParser();
        CommandLine cl = parser.parse(App.createOptions(), args);

        Assert.assertEquals("create", cl.getOptionValue("cmd"));
        Assert.assertEquals("oradev", cl.getOptionValue("ds"));
        Assert.assertEquals("pgdev", cl.getOptionValue("dt"));
        Assert.assertEquals("ANSI_", cl.getOptionValue("table-prefix"));
        Assert.assertEquals(2, cl.getOptionValues("view").length);
    }

    @Test
    public void testOptions2() {
        String[] args = new String[] {
                "-c", "create",
                "-ds", "oradev",
                "--table-prefix", "ANSI_"
        };

        try {
            CommandLineParser parser = new DefaultParser();
            parser.parse(App.createOptions(), args);
            Assert.assertTrue(false);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testOptions3() throws Exception {
        String[] args = new String[] {
                "-c", "create",
                "-ds", "oradev",
                "-dt", "pgdev",
                "--table-prefix",
                "--view-prefix"
        };

        CommandLineParser parser = new DefaultParser();
        CommandLine cl = parser.parse(App.createOptions(), args);
        Assert.assertNull(cl.getOptionValue("table-prefix"));
        Assert.assertNull(cl.getOptionValue("view-prefix"));
    }

    @Test
    public void testOptions4() throws Exception {
        String[] args = new String[] {
                "-c", "create",
                "-ds", "oradev",
                "-dt", "pgdev",
                "--table-prefix=ZD_",
                "--view-prefix=VIEW_",
        };

        CommandLineParser parser = new DefaultParser();
        CommandLine cl = parser.parse(App.createOptions(), args);
        Assert.assertEquals("ZD_", cl.getOptionValue("table-prefix"));
        Assert.assertEquals("VIEW_", cl.getOptionValue("view-prefix"));
    }

    @Test
    public void testOptions5() throws Exception {
        String[] args = new String[] {
                "-c", "create",
                "-ds", "oradev",
                "-dt", "pgdev",
                "--table=ZD_SFC,ZD_SFC_ITEM",
                "--view=VIEW_SFC,VIEW_SFC_ITEM,VIEW_RESOURCE"
        };

        CommandLineParser parser = new DefaultParser();
        CommandLine cl = parser.parse(App.createOptions(), args);
        Assert.assertEquals(2, cl.getOptionValues("table").length);
        Assert.assertEquals(3, cl.getOptionValues("view").length);
    }

    @Test
    public void testOptions6() throws Exception {
        String[] args = new String[] {
                "-c", "create",
                "-ds", "oradev",
                "-dt", "pgdev",
                "-t"
            };

        CommandLineParser parser = new DefaultParser();
        CommandLine cl = parser.parse(App.createOptions(), args);

        Assert.assertEquals("create", cl.getOptionValue("cmd"));
        Assert.assertEquals("oradev", cl.getOptionValue("ds"));
        Assert.assertEquals("pgdev", cl.getOptionValue("dt"));
    }
}
