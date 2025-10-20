/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.logging;

import com.jiuqi.bi.logging.ILogable;
import com.jiuqi.bi.logging.ILogger;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class LogHelper {
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String TEMP_HOME = System.getProperty("java.io.tmpdir");
    private static final int MAX_TRACE_FILESIZE = 50;

    private LogHelper() {
    }

    public static File createTraceFile(String catagory, String fileName) {
        try {
            File logDir = new File(USER_HOME + "/" + catagory);
            if (!logDir.exists() && !logDir.mkdirs()) {
                return null;
            }
            LogHelper.scanTraceDir(logDir);
            return LogHelper.makeTraceFileName(logDir, fileName);
        }
        catch (SecurityException e) {
            return null;
        }
    }

    private static synchronized void scanTraceDir(File logDir) {
        File[] files = logDir.listFiles(new FileFilter(){

            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        if (files == null || files.length < 50) {
            return;
        }
        Arrays.sort(files, new Comparator<File>(){

            @Override
            public int compare(File o1, File o2) {
                long delta = o1.lastModified() - o2.lastModified();
                if (delta < 0L) {
                    return -1;
                }
                if (delta > 0L) {
                    return 1;
                }
                return 0;
            }
        });
        for (int i = 0; i <= files.length - 50; ++i) {
            files[i].delete();
        }
    }

    private static synchronized File makeTraceFileName(File logDir, String fileName) {
        String suffix;
        String prefix;
        int p = fileName.lastIndexOf(46);
        if (p == -1) {
            prefix = fileName;
            suffix = null;
        } else {
            prefix = fileName.substring(0, p);
            suffix = fileName.substring(p);
        }
        if (prefix == null || prefix.length() < 3) {
            prefix = "TMP" + prefix;
        }
        try {
            return File.createTempFile(prefix, suffix, logDir);
        }
        catch (IOException e) {
            return null;
        }
    }

    public static boolean trySetLogger(Object target, ILogger logger) {
        if (target instanceof ILogable) {
            ((ILogable)target).setLogger(logger);
            return true;
        }
        return false;
    }
}

