/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import com.jiuqi.np.period.PeriodType;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareStatusType;
import nr.single.para.compare.definition.common.CompareTableType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.definition.common.FieldUseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransUtil {
    private static final Logger logger = LoggerFactory.getLogger(TransUtil.class);

    public static Object parseInteger(Object obj) {
        if (obj instanceof Integer) {
            return obj;
        }
        if (obj instanceof BigDecimal) {
            return ((BigDecimal)obj).intValue();
        }
        if (obj instanceof Long) {
            return ((Long)obj).intValue();
        }
        return null;
    }

    public static Object parseDouble(Object obj) {
        if (obj instanceof Integer) {
            return obj;
        }
        if (obj instanceof Double) {
            return obj;
        }
        if (obj instanceof BigDecimal) {
            return ((BigDecimal)obj).doubleValue();
        }
        if (obj instanceof Long) {
            return ((Long)obj).doubleValue();
        }
        return null;
    }

    public Integer transBoolean(Boolean b) {
        return b != false ? 1 : 0;
    }

    public Boolean transBoolean(Integer b) {
        return b == 1;
    }

    private byte[] blobToBytes(Object obj) {
        Blob blob = (Blob)obj;
        byte[] b = null;
        try {
            b = blob.getBytes(1L, (int)blob.length());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return b;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static byte[] transBlob(Object obj) {
        try {
            if (!(obj instanceof InputStream)) {
                Blob blob = (Blob)obj;
                try (BufferedInputStream is = new BufferedInputStream(blob.getBinaryStream());){
                    int read;
                    byte[] bytes = new byte[(int)blob.length()];
                    int len = bytes.length;
                    for (int offset = 0; offset < len && (read = is.read(bytes, offset, len - offset)) >= 0; offset += read) {
                    }
                    byte[] byArray = bytes;
                    return byArray;
                }
            }
            InputStream in = (InputStream)obj;
            try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();){
                int ch;
                byte[] buffer = new byte[1024];
                while ((ch = in.read(buffer)) != -1) {
                    outStream.write(buffer, 0, ch);
                }
                byte[] bytes = outStream.toByteArray();
                outStream.close();
                byte[] byArray = bytes;
                return byArray;
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Object parseDate(Object obj) {
        return obj;
    }

    public static String transClob(String type) throws SQLException {
        return type;
    }

    /*
     * Exception decompiling
     */
    public static String transClob(Object obj) throws SQLException {
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

    public Instant transTimeStamp(Timestamp time) {
        return time != null ? time.toInstant() : null;
    }

    public Timestamp transTimeStamp(Instant date) {
        return date != null ? Timestamp.from(date) : null;
    }

    public CompareStatusType transCompareStatusType(Integer value) {
        return value != null ? CompareStatusType.valueOf(value) : null;
    }

    public Integer transCompareStatusType(CompareStatusType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public CompareDataType transCompareDataType(Integer value) {
        return value != null ? CompareDataType.valueOf(value) : null;
    }

    public Integer transCompareDataType(CompareDataType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public CompareUpdateType transCompareUpdateType(Integer value) {
        return value != null ? CompareUpdateType.valueOf(value) : null;
    }

    public Integer transCompareUpdateType(CompareUpdateType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public CompareContextType transCompareContextType(Integer value) {
        return value != null ? CompareContextType.valueOf(value) : null;
    }

    public Integer transCompareContextType(CompareContextType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public FieldUseType transFieldUseType(Integer value) {
        return value != null ? FieldUseType.valueOf(value) : null;
    }

    public Integer transFieldUseType(FieldUseType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public CompareTableType transCompareTableType(Integer value) {
        return value != null ? CompareTableType.valueOf(value) : null;
    }

    public Integer transCompareTableType(CompareTableType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public CompareChangeType transCompareChangeType(Integer value) {
        return value != null ? CompareChangeType.valueOf(value) : null;
    }

    public Integer transCompareChangeType(CompareChangeType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public PeriodType transPeriodType(Integer value) {
        return value != null ? PeriodType.fromType((int)value) : null;
    }

    public Integer transPeriodType(PeriodType value) {
        return value != null ? Integer.valueOf(value.type()) : null;
    }

    public String transBizKeys(String[] bizKeys) {
        if (bizKeys == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bizKeys.length; ++i) {
            sb.append(bizKeys[i]);
            if (i == bizKeys.length - 1) continue;
            sb.append(";");
        }
        return sb.toString();
    }

    public String[] transBizKeys(String bizKeys) {
        if (bizKeys == null) {
            return null;
        }
        return bizKeys.split(";");
    }
}

