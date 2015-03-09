/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attacksimulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author securonix
 */
public class FileFeedGenerator {

    private int weekendCount;
    private int weekDayCount;
    private String outputPrefix;
    private int monthStart;
    private int monthEnd;
    private int year;
    private TemplatingSystem tmp;
    /**
     * @param args the command line arguments
     */

    static final String months[] = {
        "Jan", "Feb", "Mar", "Apr",
        "May", "Jun", "Jul", "Aug",
        "Sep", "Oct", "Nov", "Dec"};

    public void generateFeed(String feedtype, int userid, String outputPrefix, String freq, Timestamp startdate, Timestamp enddate, String dateformat) {
        String filepath = "conf/Feedgenerator.properties";//args[1]
        // TODO code application logic here
        tmp = new TemplatingSystem(feedtype, userid);
        String[] splitFreqVals = freq.split("\\,");
        this.weekendCount = Integer.parseInt(splitFreqVals[0]);
        this.weekDayCount = Integer.parseInt(splitFreqVals[1]);
        this.outputPrefix = outputPrefix;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startdate);
        this.monthStart = calendar.get(Calendar.MONTH);
        calendar.setTime(enddate);
        this.monthEnd = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);

        Calendar baseDate = Calendar.getInstance();
        baseDate.add(Calendar.DATE, -1);

        /*
         * Uncomment this.. and use for jan 1 2011
         * 
         * Note Month starts with 0..
         * 0 = Jan
         * 1 = Feb
         * 
         */
        //baseDate.set(2011, 0, 1);
//        vpnGenerator(HRDataFile,transactionFile,weekendCount,weekDayCount,outputPrefix,baseDate);
        /*
         * THis is for iterations
         */
        for (int month = monthStart; month <= monthEnd; month++) {
            calendar.set(year, month, 1);
            int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int day = 1; day <= maxDay; day++) {
                baseDate.set(year, month, day);
                vpnGenerator(weekendCount, weekDayCount, outputPrefix, baseDate, dateformat);
            }
        }
    }

    public void vpnGenerator(int weekendCount, int weekDayCount, String outputPrefix, Calendar baseDate, String dateformat) {

        String output = outputPrefix + "_" + months[baseDate.get(Calendar.MONTH)] + baseDate.get(Calendar.DATE) + ".csv";

        System.out.println("-- for " + baseDate.getTime());

        // String HRDataFile = HRDataFileBase;
        //  String HRDataFile = HRDataFileBase+"_"+baseDate.get(Calendar.WEEK_OF_YEAR)+".csv";
        Random generator2 = new Random(Calendar.getInstance().getTimeInMillis());

        try {
            String dateString = (baseDate.get(Calendar.MONTH) + 1) + "/" + baseDate.get(Calendar.DATE) + "/" + baseDate.get(Calendar.YEAR);

            int count = 0;
            if (baseDate.get(Calendar.DAY_OF_WEEK) == 7 || baseDate.get(Calendar.DAY_OF_WEEK) == 0) {
                count = weekendCount;
            } else {
                count = weekDayCount;
            }

            int morningCount = (1 * count) / 100;
            int dayCount = (90 * count) / 100;
            int eveningCount = (9 * count) / 100;

            System.out.println("morning " + morningCount);
            System.out.println("day " + dayCount);
            System.out.println("evening " + eveningCount);

            for (int hour = 0; hour < 24; hour++) {
                if (hour >= 0 && hour < 7) {
                    int toDoAvg = (morningCount + 1) / 6;
                    System.out.println("toDoAvg" + toDoAvg + " hour " + hour);

                    if (toDoAvg == 0) {
                        toDoAvg = 1;
                    }

                    if (morningCount > 0) {
                        int ran = generator2.nextInt(toDoAvg + 1);
                        morningCount = morningCount - ran;
                        System.out.println("\tmorning " + ran + " hour " + hour);

                        FileWriter fstream = new FileWriter(output, true);
                        BufferedWriter out = new BufferedWriter(fstream);

                        for (int i = 0; i <= ran; i++) {
//                            String userName = users.get(generator2.nextInt(users.size()));
//                            String IPAddress = usersIps.get(userName).get(generator2.nextInt(usersIps.get(userName).size()));
                            String timeString = hour + ":" + generator2.nextInt(60) + ":" + generator2.nextInt(60);
//
//                            String[] trans = transactions.get(generator2.nextInt(transactions.size())).split("\\|");
//
//                            for (String tran : trans) {
//                                out.write("" + userName + "|" + tran + "|" + IPAddress + "|" + dateString + "|" + timeString + "\n");
//                            }
                            int index = tmp.getRandomizedIndex();
                            String currentTransaction = tmp.getTransactions().get(index);
                            String pattern = "\\{\\{([\\w\\.]+)\\}\\}";
                            Pattern pat = Pattern.compile(pattern);
                            Matcher matcher = pat.matcher(currentTransaction);
                            while (matcher.find()) {
                                String var = matcher.group(1);
                                if (var.contains(".")) {
                                    var = var.split("\\.")[0];
                                    HashMap<String, String> res = null;
                                    try {
                                        res = tmp.getVgetMap().get(var).getValue();
                                        String pattempstr = "\\{\\{(" + var + "\\.\\w+)\\}\\}";
                                        Pattern pattemp = Pattern.compile(pattempstr);
                                        Matcher matchertemp = pattemp.matcher(currentTransaction);
                                        while (matchertemp.find()) {
                                            System.out.println("Matchertemp.group(): " + matchertemp.group() + " matchertemp.group(1): " + matchertemp.group(1));
                                            currentTransaction = currentTransaction.replace(matchertemp.group(), res.get(matchertemp.group(1)));
                                        }
                                    } catch (OperationNotSupportedException ex) {
                                        Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else {
                                    try {
                                        if (matcher.group(1).contains("datetime")) {
                                            String currentdate = dateString + " " + timeString;
                                            String wanteddate = convertToAnotherFormat(currentdate, dateformat);
                                            currentTransaction = currentTransaction.replace(matcher.group(), wanteddate);
                                        } else {
                                            currentTransaction = currentTransaction.replace(matcher.group(), tmp.getVgetMap().get(matcher.group(1)).getValue().get(matcher.group(1)));
                                        }
                                    } catch (OperationNotSupportedException ex) {
                                        Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                            //before outputting this transaction we will have to split it by the double pipe symbol and output each one in a different line.
                            String[] splitTransactions = currentTransaction.split("\\|\\|");
                            for (String transaction : splitTransactions) {
                                System.out.println(transaction.trim());
                                out.write(transaction.trim() + "\n");
                            }
                        }
                        out.close();
                    }
                }

                if (hour >= 7 && hour < 19) {
                    int toDoAvg = (dayCount + 1) / 6;
                    System.out.println("toDoAvg" + toDoAvg + " hour " + hour);

                    if (toDoAvg == 0) {
                        toDoAvg = 1;
                    }

                    if (dayCount > 0) {

                        int ran = generator2.nextInt(toDoAvg + 1);
                        dayCount = dayCount - ran;
                        System.out.println("\tdayCount " + ran + " hour " + hour);

                        FileWriter fstream = new FileWriter(output, true);
                        BufferedWriter out = new BufferedWriter(fstream);

                        for (int i = 0; i <= ran; i++) {
                            String timeString = hour + ":" + generator2.nextInt(60) + ":" + generator2.nextInt(60);
                            int index = tmp.getRandomizedIndex();
                            String currentTransaction = tmp.getTransactions().get(index);
                            String pattern = "\\{\\{([\\w\\.]+)\\}\\}";
                            Pattern pat = Pattern.compile(pattern);
                            Matcher matcher = pat.matcher(currentTransaction);
                            while (matcher.find()) {
                                String var = matcher.group(1);
                                if (var.contains(".")) {
                                    var = var.split("\\.")[0];
                                    HashMap<String, String> res = null;
                                    try {
                                        System.out.println("tmp="+tmp);
                                        System.out.println("map=" + tmp.getVgetMap());
                                        System.out.println(var + "=" + tmp.getVgetMap().get(var));
                                        res = tmp.getVgetMap().get(var).getValue();
                                        String pattempstr = "\\{\\{(" + var + "\\.\\w+)\\}\\}";
                                        Pattern pattemp = Pattern.compile(pattempstr);
                                        Matcher matchertemp = pattemp.matcher(currentTransaction);
                                        while (matchertemp.find()) {
                                            currentTransaction = currentTransaction.replace(matchertemp.group(), res.get(matchertemp.group(1)));
                                        }
                                    } catch (OperationNotSupportedException ex) {
                                        Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else {
                                    try {
                                        if (matcher.group(1).contains("datetime")) {
                                            String currentdate = dateString + " " + timeString;
                                            String wanteddate = convertToAnotherFormat(currentdate, dateformat);
                                            currentTransaction = currentTransaction.replace(matcher.group(), wanteddate);
                                        } else {
                                            currentTransaction = currentTransaction.replace(matcher.group(), tmp.getVgetMap().get(matcher.group(1)).getValue().get(matcher.group(1)));
                                        }
                                    } catch (OperationNotSupportedException ex) {
                                        Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                            //before outputting this transaction we will have to split it by the double pipe symbol and output each one in a different line.
                            String[] splitTransactions = currentTransaction.split("\\|\\|");
                            for (String transaction : splitTransactions) {
                                System.out.println(transaction.trim());
                                out.write(transaction.trim() + "\n");
                            }
                        }
                        out.close();
                    }
                }

                if (hour >= 19 && hour < 24) {
                    int toDoAvg = (eveningCount + 1) / 6;
                    System.out.println("night count" + toDoAvg + " hour " + hour);

                    if (toDoAvg == 0) {
                        toDoAvg = 1;
                    }

                    if (dayCount > 0) {
                        int ran = generator2.nextInt(toDoAvg + 1);
                        eveningCount = eveningCount - ran;
                        System.out.println("\tnight " + ran + " hour " + hour);

                        FileWriter fstream = new FileWriter(output, true);
                        BufferedWriter out = new BufferedWriter(fstream);

                        for (int i = 0; i <= ran; i++) {
                            String timeString = hour + ":" + generator2.nextInt(60) + ":" + generator2.nextInt(60);
                            int index = tmp.getRandomizedIndex();
                            String currentTransaction = tmp.getTransactions().get(index);
                            String pattern = "\\{\\{([\\w\\.]+)\\}\\}";
                            Pattern pat = Pattern.compile(pattern);
                            Matcher matcher = pat.matcher(currentTransaction);
                            while (matcher.find()) {
                                String var = matcher.group(1);
                                if (var.contains(".")) {
                                    var = var.split("\\.")[0];
                                    HashMap<String, String> res = null;
                                    try {
                                        res = tmp.getVgetMap().get(var).getValue();
                                        String pattempstr = "\\{\\{(" + var + "\\.\\w+)\\}\\}";
                                        Pattern pattemp = Pattern.compile(pattempstr);
                                        Matcher matchertemp = pattemp.matcher(currentTransaction);
                                        while (matchertemp.find()) {
                                            currentTransaction = currentTransaction.replace(matchertemp.group(), res.get(matchertemp.group(1)));
                                        }
                                    } catch (OperationNotSupportedException ex) {
                                        Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else {
                                    try {
                                        if (matcher.group(1).contains("datetime")) {
                                            String currentdate = dateString + " " + timeString;
                                            String wanteddate = convertToAnotherFormat(currentdate, dateformat);
                                            currentTransaction = currentTransaction.replace(matcher.group(), wanteddate);
                                        } else {
                                            currentTransaction = currentTransaction.replace(matcher.group(), tmp.getVgetMap().get(matcher.group(1)).getValue().get(matcher.group(1)));
                                        }
                                    } catch (OperationNotSupportedException ex) {
                                        Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                            //before outputting this transaction we will have to split it by the double pipe symbol and output each one in a different line.
                            String[] splitTransactions = currentTransaction.split("\\|\\|");
                            for (String transaction : splitTransactions) {
                                System.out.println(transaction.trim());
                                out.write(transaction.trim() + "\n");
                            }
                        }
                        out.close();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Long convertToEpoch(String datetimestring) {
        Long epoch = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date date = sdf.parse(datetimestring);
            epoch = date.getTime();
        } catch (ParseException ex) {
            Logger.getLogger(FileFeedGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return epoch;
    }

    public String convertToAnotherFormat(String currentTime, String dateformat) throws ParseException {
        String formattedDate = null;
        if (dateformat.equalsIgnoreCase("epoch")) {
            formattedDate = convertToEpoch(currentTime).toString();
        } else {
            DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            DateFormat targetFormat = new SimpleDateFormat(dateformat);
            Date date = originalFormat.parse(currentTime);
            formattedDate = targetFormat.format(date);
        }
        return formattedDate;
    }
}
