/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.bi.parameter.model.NoneDsDefValueFilterMode
 *  com.jiuqi.bi.parameter.model.ParameterDefaultValueFilterMode
 *  com.jiuqi.bi.parameter.model.ParameterSelectMode
 *  com.jiuqi.bi.parameter.model.ParameterWidgetType
 *  com.jiuqi.bi.parameter.model.datasource.DataSourceFilterMode
 */
package com.jiuqi.nr.query.common;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.parameter.model.NoneDsDefValueFilterMode;
import com.jiuqi.bi.parameter.model.ParameterDefaultValueFilterMode;
import com.jiuqi.bi.parameter.model.ParameterSelectMode;
import com.jiuqi.bi.parameter.model.ParameterWidgetType;
import com.jiuqi.bi.parameter.model.datasource.DataSourceFilterMode;
import java.io.BufferedInputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransUtil {
    private final Logger logger = LoggerFactory.getLogger(TransUtil.class);

    public Clob transClob(String type) throws SQLException {
        SerialClob value = new SerialClob(type.toCharArray());
        return value;
    }

    /*
     * Exception decompiling
     */
    public String transClob(Clob type) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
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

    public String transUUID(UUID u) {
        return u.toString();
    }

    public UUID transUUID(String u) {
        return UUID.fromString(u);
    }

    public Date transTimeStamp(Timestamp time) {
        return new Date(time.getTime());
    }

    public Timestamp transTimeStamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] transBlob(Blob blob) {
        try (BufferedInputStream is = new BufferedInputStream(blob.getBinaryStream());){
            byte[] bytes = new byte[(int)blob.length()];
            int len = bytes.length;
            int read = 0;
            for (int offset = 0; offset < len && (read = is.read(bytes, offset, len - offset)) >= 0; offset += read) {
            }
            byte[] byArray = bytes;
            return byArray;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Blob transBlob(byte[] bytes) {
        SerialBlob value = null;
        try {
            value = new SerialBlob(bytes);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return value;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String transBlobtoString(Blob blob) {
        try (BufferedInputStream is = new BufferedInputStream(blob.getBinaryStream());){
            byte[] bytes = new byte[(int)blob.length()];
            int len = bytes.length;
            int read = 0;
            for (int offset = 0; offset < len && (read = is.read(bytes, offset, len - offset)) >= 0; offset += read) {
            }
            String string = new String(bytes);
            return string;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Blob transBlobtoString(String strValue) {
        SerialBlob value = null;
        try {
            value = new SerialBlob(strValue.getBytes());
        }
        catch (Exception exception) {
            // empty catch block
        }
        return value;
    }

    public static String transBytes(byte[] bytes) {
        try {
            return new String(bytes);
        }
        catch (Exception exception) {
            return "";
        }
    }

    public static byte[] transBytes(String value) {
        try {
            return value.getBytes();
        }
        catch (Exception exception) {
            return new byte[0];
        }
    }

    public static String transParameterSelectMode(ParameterSelectMode parameterSelectMode) {
        return parameterSelectMode.toString();
    }

    public ParameterSelectMode transParameterSelectMode(String parameterSelectMode) {
        return ParameterSelectMode.valueOf((String)parameterSelectMode);
    }

    public static String transDataType(DataType type) {
        return type.toString();
    }

    public DataType transDataType(String type) {
        return DataType.valueOf((String)type);
    }

    public static String transNoneDsDefValueFilterMode(NoneDsDefValueFilterMode type) {
        return type.toString();
    }

    public NoneDsDefValueFilterMode transNoneDsDefValueFilterMode(String type) {
        return NoneDsDefValueFilterMode.valueOf((String)type);
    }

    public static String transParameterDefaultValueFilterMode(ParameterDefaultValueFilterMode type) {
        return type.toString();
    }

    public ParameterDefaultValueFilterMode transParameterDefaultValueFilterMode(String type) {
        return ParameterDefaultValueFilterMode.valueOf((String)type);
    }

    public static String transDataSourceFilterMode(DataSourceFilterMode type) {
        return type.toString();
    }

    public DataSourceFilterMode transDataSourceFilterMode(String type) {
        return DataSourceFilterMode.valueOf((String)type);
    }

    public static String transParameterWidgetType(ParameterWidgetType type) {
        return type.toString();
    }

    public ParameterWidgetType transParameterWidgetType(String type) {
        return ParameterWidgetType.valueOf((String)type);
    }
}

