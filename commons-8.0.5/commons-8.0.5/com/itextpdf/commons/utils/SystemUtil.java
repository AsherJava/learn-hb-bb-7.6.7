/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.utils;

import com.itextpdf.commons.utils.ProcessInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SystemUtil {
    private static final String SPLIT_REGEX = "((\".+?\"|[^'\\s]|'.+?')+)\\s*";

    public static long getTimeBasedSeed() {
        return System.currentTimeMillis();
    }

    public static int getTimeBasedIntSeed() {
        return (int)System.currentTimeMillis();
    }

    private SystemUtil() {
    }

    public static long getRelativeTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public static String getPropertyOrEnvironmentVariable(String name) {
        String s = System.getProperty(name);
        if (s == null) {
            s = System.getenv(name);
        }
        return s;
    }

    public static boolean runProcessAndWait(String exec, String params) throws IOException, InterruptedException {
        return SystemUtil.runProcessAndWait(exec, params, null);
    }

    public static boolean runProcessAndWait(String exec, String params, String workingDirPath) throws IOException, InterruptedException {
        return SystemUtil.runProcessAndGetExitCode(exec, params, workingDirPath) == 0;
    }

    public static int runProcessAndGetExitCode(String exec, String params) throws IOException, InterruptedException {
        return SystemUtil.runProcessAndGetExitCode(exec, params, null);
    }

    public static int runProcessAndGetExitCode(String exec, String params, String workingDirPath) throws IOException, InterruptedException {
        Process p = SystemUtil.runProcess(exec, params, workingDirPath);
        System.out.println(SystemUtil.getProcessOutput(p));
        return p.waitFor();
    }

    public static String runProcessAndGetOutput(String command, String params) throws IOException {
        return SystemUtil.getProcessOutput(SystemUtil.runProcess(command, params, null));
    }

    public static StringBuilder runProcessAndCollectErrors(String execPath, String params) throws IOException {
        return SystemUtil.printProcessErrorsOutput(SystemUtil.runProcess(execPath, params, null));
    }

    public static ProcessInfo runProcessAndGetProcessInfo(String command, String params) throws IOException, InterruptedException {
        Process p = SystemUtil.runProcess(command, params, null);
        String processStdOutput = SystemUtil.printProcessStandardOutput(p).toString();
        String processErrOutput = SystemUtil.printProcessErrorsOutput(p).toString();
        return new ProcessInfo(p.waitFor(), processStdOutput, processErrOutput);
    }

    static Process runProcess(String execPath, String params, String workingDirPath) throws IOException {
        List<String> cmdList = SystemUtil.prepareProcessArguments(execPath, params);
        String[] cmdArray = cmdList.toArray(new String[0]);
        if (workingDirPath != null) {
            File workingDir = new File(workingDirPath);
            return Runtime.getRuntime().exec(cmdArray, null, workingDir);
        }
        return Runtime.getRuntime().exec(cmdArray);
    }

    static List<String> prepareProcessArguments(String exec, String params) {
        ArrayList<String> cmdList = new File(exec).exists() ? new ArrayList<String>(Collections.singletonList(exec)) : new ArrayList<String>(SystemUtil.splitIntoProcessArguments(exec));
        cmdList.addAll(SystemUtil.splitIntoProcessArguments(params));
        return cmdList;
    }

    static List<String> splitIntoProcessArguments(String line) {
        ArrayList<String> list = new ArrayList<String>();
        Matcher m = Pattern.compile(SPLIT_REGEX).matcher(line);
        while (m.find()) {
            list.add(m.group(1).replace("'", "").replace("\"", "").trim());
        }
        return list;
    }

    static String getProcessOutput(Process p) throws IOException {
        String line;
        BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        StringBuilder result = new StringBuilder();
        while ((line = bri.readLine()) != null) {
            result.append(line);
        }
        bri.close();
        if (result.length() > 0) {
            result.append('\n');
        }
        while ((line = bre.readLine()) != null) {
            result.append(line);
        }
        bre.close();
        return result.toString();
    }

    static StringBuilder printProcessErrorsOutput(Process p) throws IOException {
        return SystemUtil.printProcessOutput(p.getErrorStream());
    }

    static StringBuilder printProcessStandardOutput(Process p) throws IOException {
        return SystemUtil.printProcessOutput(p.getInputStream());
    }

    private static StringBuilder printProcessOutput(InputStream processStream) throws IOException {
        String line;
        StringBuilder builder = new StringBuilder();
        BufferedReader bre = new BufferedReader(new InputStreamReader(processStream));
        while ((line = bre.readLine()) != null) {
            System.out.println(line);
            builder.append(line);
        }
        bre.close();
        return builder;
    }
}

