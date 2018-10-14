package uia.simple.migrate;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

public class SimpleDatabaseTool {

    private static TreeMap<String, String> cmds;

    static {
        cmds = new TreeMap<String, String>();
        cmds.put("create", CreateCmd.class.getName());
        cmds.put("compare", CompareCmd.class.getName());
    }

    public static void main(String[] args) throws Exception {
        Properties p = new Properties(System.getProperties());
        p.load(new FileInputStream("database.conf"));
        System.setProperties(p);

        Options options = SimpleDatabaseTool.createOptions();

        System.out.println();
        CommandLine cl = null;
        try {
            CommandLineParser parser = new DefaultParser();
            cl = parser.parse(options, args);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            help(options);
            System.exit(0);
        }

        String cmd = cl.getOptionValue("cmd");
        String cls = cmds.get(cmd);
        if (cls != null) {
            AbstractCmd instance = (AbstractCmd) Class.forName(cls).newInstance();
            instance.initDatabase(cl);
            instance.run(cl);
        }
        else {
            System.out.println(cmd + " not found");
            help(options);
        }
    }

    public static void help(Options options) {
        System.out.println();
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(200);
        formatter.printHelp("java -jar migrate-0.0.1-SNAPSHOT.jar\n", options, true);
    }

    static Options createOptions() {
        Option cmd = Option.builder("c")
                .longOpt("cmd")
                .desc("command name to be executed. [compare,create]")
                .required()
                .hasArg()
                .argName("commnad")
                .build();

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
                .desc("source database, {KEY} in database.conf")
                .required()
                .hasArg()
                .argName("db")
                .build();

        Option target = Option.builder("dt")
                .longOpt("db-target")
                .desc("destination database, {KEY} in database.conf")
                .required()
                .hasArg()
                .argName("db")
                .build();

        Option plan = Option.builder("p")
                .longOpt("plan")
                .desc("run a command with plan file")
                .hasArg()
                .argName("file")
                .build();

        Options options = new Options()
                .addOption(cmd)
                .addOption(source)
                .addOption(target)
                .addOption(plan)
                .addOption(printFailed)
                .addOption(printMissing)
                .addOptionGroup(optionTable())
                .addOptionGroup(optionView());

        return options;

    }

    private static OptionGroup optionTable() {
        Option table = Option.builder("t")
                .longOpt("table")
                .desc("names of tables")
                .hasArgs()
                .argName("table")
                .valueSeparator(',')
                .build();

        Option tablePrefix = Option.builder()
                .longOpt("table-prefix")
                .desc("table's name prefix")
                .hasArg()
                .argName("prefix")
                .optionalArg(true)
                .build();

        OptionGroup optionGroup = new OptionGroup()
                .addOption(table)
                .addOption(tablePrefix);
        return optionGroup;
    }

    private static OptionGroup optionView() {
        Option view = Option.builder("v")
                .longOpt("view")
                .desc("names of views")
                .hasArgs()
                .argName("view")
                .valueSeparator(',')
                .build();

        Option viewPrefix = Option.builder()
                .longOpt("view-prefix")
                .desc("view's name prefix")
                .hasArg()
                .argName("prefix")
                .optionalArg(true)
                .build();

        OptionGroup optionGroup = new OptionGroup()
                .addOption(view)
                .addOption(viewPrefix);
        return optionGroup;
    }
}
