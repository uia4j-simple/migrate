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
import java.util.Arrays;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class App {

    private static TreeMap<String, String> cmds;

    static {
        cmds = new TreeMap<>();
        cmds.put("create", CreateCmd.class.getName());
        cmds.put("compare", CompareCmd.class.getName());
    }

    public static void main(String[] args) throws Exception {
    	System.out.println("db-migration-0.2.0-SNAPSHOT");

    	Properties p = new Properties(System.getProperties());
        p.load(new FileInputStream("database.conf"));
        System.setProperties(p);
    	
        if(args.length == 0) {
        	System.out.println("no command");
        	return;
        }
        
    	String cmd = args[0];
    	String[] cmdArgs = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        System.out.println();
        String cls = cmds.get(cmd);
        if (cls != null) {
            AbstractCmd instance = (AbstractCmd) Class.forName(cls).newInstance();
            Options options = instance.createOptions();
            try {
                CommandLineParser parser = new DefaultParser();
                CommandLine cl = parser.parse(options, cmdArgs);
                instance.initDatabase(cl);
                instance.run(cl);
            }
            catch (Exception ex) {
                help(cmd, options);
                System.out.println(ex.getMessage());
            }
        }
        else {
            System.out.println(cmd + " not found");
        }
        System.exit(0);
    }

    public static void help(String cmd, Options options) {
        System.out.println();
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(200);
        formatter.printHelp("java -jar db-migration.jar " + cmd + "\n", options, true);
    }
}
