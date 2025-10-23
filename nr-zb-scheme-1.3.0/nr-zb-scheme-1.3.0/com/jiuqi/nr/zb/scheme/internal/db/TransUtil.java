/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.common.EntityCacheMode
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.InheritMode
 *  com.jiuqi.np.definition.common.TableGatherType
 *  com.jiuqi.np.definition.common.TableIndexType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.zb.scheme.internal.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.common.EntityCacheMode;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.InheritMode;
import com.jiuqi.np.definition.common.TableGatherType;
import com.jiuqi.np.definition.common.TableIndexType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.period.PeriodType;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
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

    public static Object parseInteger(Number obj) {
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
            return (double)((Double)obj);
        }
        if (obj instanceof BigDecimal) {
            return ((BigDecimal)obj).doubleValue();
        }
        if (obj instanceof Long) {
            return ((Long)obj).doubleValue();
        }
        return null;
    }

    public static Object parseDouble(Number obj) {
        if (obj instanceof Integer) {
            return obj;
        }
        if (obj instanceof Double) {
            return (double)((Double)obj);
        }
        if (obj instanceof BigDecimal) {
            return ((BigDecimal)obj).doubleValue();
        }
        if (obj instanceof Long) {
            return ((Long)obj).doubleValue();
        }
        return null;
    }

    public static Object parseBigDecimal(Object obj) {
        if (obj instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal)obj;
            if (decimal.scale() > 0) {
                return decimal.doubleValue();
            }
            return decimal.intValue();
        }
        return null;
    }

    public static Object parseBigDecimal(BigDecimal decimal) {
        if (decimal == null) {
            return null;
        }
        if (decimal.scale() > 0) {
            return decimal.doubleValue();
        }
        return decimal.intValue();
    }

    public String transUUID(UUID u) {
        return u.toString();
    }

    public UUID transUUID(String u) {
        return UUID.fromString(u);
    }

    public Integer transFieldType(FieldType t) {
        return t.getValue();
    }

    public FieldType transFieldType(Integer t) {
        return FieldType.forValue((int)t);
    }

    public Integer transBoolean(Boolean b) {
        return b != false ? 1 : 0;
    }

    public Boolean transBoolean(Integer b) {
        return b == 1;
    }

    public Integer transFieldValueType(FieldValueType t) {
        return t.getValue();
    }

    public FieldValueType transFieldValueType(Integer t) {
        return FieldValueType.forValue((int)t);
    }

    public Integer transFieldGatherType(FieldGatherType t) {
        return t.getValue();
    }

    public FieldGatherType transFieldGatherType(Integer t) {
        return FieldGatherType.forValue((int)t);
    }

    public Integer transTableKind(TableKind t) {
        return t.getValue();
    }

    public TableKind transTableKind(Integer t) {
        return TableKind.forValue((int)t);
    }

    public Integer transTableGatherType(TableGatherType t) {
        return t.getValue();
    }

    public TableGatherType transTableGatherType(Integer t) {
        return TableGatherType.forValue((int)t);
    }

    public Date transTimeStamp(Timestamp time) {
        return new Date(time.getTime());
    }

    public Timestamp transTimeStamp(Date date) {
        return new Timestamp(date.getTime());
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
                    byte[] bytes = new byte[(int)blob.length()];
                    int len = bytes.length;
                    int read = 0;
                    for (int offset = 0; offset < len && (read = is.read(bytes, offset, len - offset)) >= 0; offset += read) {
                    }
                    byte[] byArray = bytes;
                    return byArray;
                }
            }
            InputStream in = (InputStream)obj;
            try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();){
                byte[] bytes;
                byte[] buffer = new byte[1024];
                int ch = 0;
                while ((ch = in.read(buffer)) != -1) {
                    outStream.write(buffer, 0, ch);
                }
                byte[] byArray = bytes = outStream.toByteArray();
                return byArray;
            }
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Object parseDate(Object obj) throws SQLException {
        return obj;
    }

    public Integer transTableIndexType(TableIndexType type) {
        return type.getValue();
    }

    public TableIndexType transTableIndexType(Integer type) {
        return TableIndexType.forValue((int)type);
    }

    public InheritMode transInheritMode(Integer type) {
        return InheritMode.forValue((int)type);
    }

    public Integer transInheritMode(InheritMode type) {
        return type.getValue();
    }

    public EntityCacheMode transEntityCacheMode(Integer type) {
        return EntityCacheMode.forValue((int)type);
    }

    public Integer transEntityCacheMode(EntityCacheMode type) {
        return type.getValue();
    }

    public PeriodType transPeriodType(Integer type) {
        return PeriodType.fromType((int)type);
    }

    public Integer transPeriodType(PeriodType type) {
        return type == null ? PeriodType.YEAR.type() : type.type();
    }

    public byte[] transRaw(UUID raw) {
        if (raw == null) {
            return null;
        }
        ByteArrayOutputStream ba = new ByteArrayOutputStream(16);
        DataOutputStream da = new DataOutputStream(ba);
        try {
            da.writeLong(raw.getMostSignificantBits());
            da.writeLong(raw.getLeastSignificantBits());
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return ba.toByteArray();
    }

    public UUID transRaw(byte[] raw) {
        int i;
        if (raw == null || raw.length != 16) {
            return null;
        }
        long msb = 0L;
        long lsb = 0L;
        for (i = 0; i < 8; ++i) {
            msb = msb << 8 | (long)(raw[i] & 0xFF);
        }
        for (i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long)(raw[i] & 0xFF);
        }
        return new UUID(msb, lsb);
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

    public String transFormatProperties(FormatProperties formatProperties) {
        if (formatProperties != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString((Object)formatProperties);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public FormatProperties transFormatProperties(String formatProperties) {
        if (formatProperties != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return (FormatProperties)objectMapper.readValue(formatProperties, FormatProperties.class);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }
}

