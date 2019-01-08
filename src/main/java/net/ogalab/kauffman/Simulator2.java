package net.ogalab.kauffman;

import org.apache.commons.cli.*;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Hello world!
 */
public class Simulator2 {


    int numberOfElements = 100;
    int numberOfTrials   = 1000;
    int interval         = 100;
    int reportType       = 0; // 0: Max group size. 1: All group size


    public static void main(String[] args) {

        Simulator2 obj = new Simulator2();
        obj.analyzeCommandLine(args);
        obj.simulate();
    }


    public void analyzeCommandLine(String[] args) {

        // An option list object.
        Options options = new Options();

        Option nElem = Option.builder("e")
                .longOpt("number-of-elements")
                .hasArg(true)
                .desc("Number of elements")
                .argName("numberOfElements")
                .build();
        options.addOption(nElem);

        Option nTrials = Option.builder("t")
                .longOpt("number-of-trials")
                .hasArg(true)
                .desc("Number of trials")
                .argName("numberOfTrials")
                .build();
        options.addOption(nTrials);

        Option interval = Option.builder("i")
                .longOpt("interval")
                .hasArg(true)
                .desc("interval of reports")
                .argName("interval")
                .build();
        options.addOption(interval);

        Option reportType = Option.builder("r")
                .longOpt("report-type")
                .hasArg(true)
                .desc("Report type")
                .argName("reportType")
                .build();
        options.addOption(interval);



        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("e")) {
                setNumberOfElements(Integer.valueOf(cmd.getOptionValue("e")));
            }

            if (cmd.hasOption("t")) {
                setNumberOfTrials(Integer.valueOf(cmd.getOptionValue("t")));
            }

            if (cmd.hasOption("i")) {
                setInterval(Integer.valueOf(cmd.getOptionValue("interval")));
            }

            if (cmd.hasOption("r")) {
                setReportType(Integer.valueOf(cmd.getOptionValue("reportType")));
            }



        } catch (ParseException e) {
            HelpFormatter help = new HelpFormatter();
            help.printHelp("net.ogalab.kauffman.Simulator2", options, true);
            System.exit(128);
        }

    }


    ArrayList<Set<Integer>> data = new ArrayList<>();


    public void simulate() {

        for (int t = 0; t < numberOfTrials; t++) {
            executeSingleStep();
            if (t % interval == 0) {
                report(t);
            }
        }
    }


    public void report(int t) {

        if (reportType == 0) {
            System.out.format("%d\t%d\n", t, calcMaxGroupSize());
        }
        else {
            for (int i = 0; i < data.size(); i++) {
                System.out.format("%d\t%d\t%d\n", t, data.size(), data.get(i).size());
            }
        }

    }

    public int calcMaxGroupSize() {
        int max = 0;
        for (int i=0; i<data.size(); i++) {
            if (data.get(i).size() > max) {
                max = data.get(i).size();
            }
        }
        return max;
    }


    public void executeSingleStep() {

        RandomDataGenerator random = new RandomDataGenerator();
        int r1 = random.nextInt(1, numberOfElements);
        int r2 = random.nextInt(1, numberOfElements);

        if (data.size() == 0) {
            createNewGroup(r1, r2);
        }
        else {

            int g1 = findGroup(r1);
            int g2 = findGroup(r2);


            if (g1 == g2) {
                // nothing to do.
            }
            else if (g1 >= 0 && g2 >=0) {
                data.get(g1).addAll(data.get(g2));
                data.remove(g2);
            }
            else if (g1 >= 0 && g2 < 0) {
                data.get(g1).add(r2);
            }
            else if (g1 < 0 && g2 > 0) {
                data.get(g2).add(r1);
            }
            else {
                createNewGroup(r1, r2);
            }

        }



        int maxSize = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).size() > maxSize) {
                maxSize = data.get(i).size();
            }
        }

        //return String.format("%d\t%d\t%d", r1, r2, maxSize);

    }


    public void createNewGroup(int r1, int r2) {
        HashSet<Integer> s = new HashSet<>();
        s.add(r1);
        s.add(r2);
        data.add(s);
    }


    public int findGroup(int r) {

        int result = -1;

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).contains(r)) {
                result = i;
                break;
            }
        }
        return result;
    }


    public String showGroup(int gIndex) {
        StringJoiner joiner = new StringJoiner(" ");
        Set<Integer> group = data.get(gIndex);
        for (int e : group) {
            joiner.add(String.valueOf(e));
        }
        return joiner.toString();
    }


    // -----

    public int getNumberOfTrials() {
        return numberOfTrials;
    }

    public void setNumberOfTrials(int numberOfTrials) {
        this.numberOfTrials = numberOfTrials;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }
}


