/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.JqLib
 */
package com.jiuqi.np.util;

import com.jiuqi.bi.util.JqLib;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DataConvert {
    public static final String YEAR_MONTH_DAY_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    private static final DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final ZoneId zoneId = ZoneId.systemDefault();
    public static final int ftString = 12;
    public static final int ftInteger = 4;
    public static final int ftBoolean = -7;
    public static final int ftFloat = 2;
    public static final int ftDate = 91;
    public static final int ftDateTime = 93;
    public static final String[] BoolTrueCaptions = new String[]{"\u662f", "\u771f", "True", "T", "1"};
    public static final String[] BoolFalseCaptions = new String[]{"\u5426", "\u5047", "False", "F", "0"};

    public static int stringToInt(String data) {
        try {
            return data == null || data.equals("") ? 0 : Integer.parseInt(data);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }

    public static boolean stringToBool(String str) {
        if (str == null) {
            return false;
        }
        for (int i = 0; i < BoolTrueCaptions.length; ++i) {
            if (!str.equalsIgnoreCase(BoolTrueCaptions[i])) continue;
            return true;
        }
        return false;
    }

    /*
     * Exception decompiling
     */
    public static Object stringToValue(String data, int zbtype) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 3[SWITCH]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static String valueToString(int value) {
        return String.valueOf(value);
    }

    public static String valueToString(Object value) {
        return DataConvert.valueToString(value, false);
    }

    public static String valueToString(Object value, boolean quoteString) {
        if (value == null) {
            return null;
        }
        if (value instanceof Double) {
            return value.toString();
        }
        if (value instanceof Integer) {
            return value.toString();
        }
        if (value instanceof Boolean) {
            return (Boolean)value != false ? "1" : "0";
        }
        if (value instanceof String) {
            if (!quoteString) {
                return (String)value;
            }
            return JqLib.quoteString((String)((String)value));
        }
        if (value instanceof Time) {
            Time time = (Time)value;
            Instant instant = Instant.ofEpochMilli(time.getTime());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
            return formatterTime.format(localDateTime);
        }
        if (value instanceof Timestamp) {
            Timestamp time = (Timestamp)value;
            Instant instant = Instant.ofEpochMilli(time.getTime());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
            return formatterTime.format(localDateTime);
        }
        if (value instanceof Date) {
            Date date = (Date)value;
            Instant instant = date.toInstant();
            LocalDate localDateTime = instant.atZone(zoneId).toLocalDate();
            return formatter.format(localDateTime);
        }
        if (value instanceof Calendar) {
            Calendar calendar = (Calendar)value;
            Date date = calendar.getTime();
            Instant instant = date.toInstant();
            LocalDate localDateTime = instant.atZone(zoneId).toLocalDate();
            return formatter.format(localDateTime);
        }
        return value.toString();
    }

    public static String valuesToString(Object value, boolean quoteString) {
        if (value == null) {
            return null;
        }
        if (value instanceof Object[]) {
            Object[] values = (Object[])value;
            String result = "";
            for (int i = 0; i < values.length; ++i) {
                result = result + DataConvert.valueToString(values[i], quoteString) + ';';
            }
            return result;
        }
        return DataConvert.valueToString(value, quoteString);
    }
}

