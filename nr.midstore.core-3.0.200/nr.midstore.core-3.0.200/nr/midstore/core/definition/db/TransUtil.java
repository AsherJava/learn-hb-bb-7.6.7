/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package nr.midstore.core.definition.db;

import com.jiuqi.np.period.PeriodType;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import nr.midstore.core.definition.common.EnumStructType;
import nr.midstore.core.definition.common.ExchangeModeType;
import nr.midstore.core.definition.common.ExcutePeriodType;
import nr.midstore.core.definition.common.PublishStateType;
import nr.midstore.core.definition.common.StorageModeType;
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
                byte[] bytes = new byte[(int)blob.length()];
                try (BufferedInputStream is = new BufferedInputStream(blob.getBinaryStream());){
                    int len = bytes.length;
                    int offset = 0;
                    while (offset < len) {
                        int read = is.read(bytes, offset, len - offset);
                        if (read < 0) return bytes;
                        offset += read;
                    }
                    return bytes;
                }
            }
            InputStream in = (InputStream)obj;
            try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();){
                int ch;
                byte[] buffer = new byte[1024];
                while ((ch = in.read(buffer)) != -1) {
                    outStream.write(buffer, 0, ch);
                }
                byte[] byArray = outStream.toByteArray();
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

    public EnumStructType transEnumStructType(Integer value) {
        return value != null ? EnumStructType.valueOf(value) : null;
    }

    public Integer transEnumStructType(ExchangeModeType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public ExchangeModeType transExchangeModeType(Integer value) {
        return value != null ? ExchangeModeType.valueOf(value) : null;
    }

    public Integer transExchangeModeType(ExchangeModeType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public PublishStateType transPublishStateType(Integer value) {
        return value != null ? PublishStateType.valueOf(value) : null;
    }

    public Integer transPublishStateType(PublishStateType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public ExcutePeriodType transExcutePeriodType(Integer value) {
        return value != null ? ExcutePeriodType.valueOf(value) : null;
    }

    public Integer transExcutePeriodType(ExcutePeriodType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public StorageModeType transStorageModeType(Integer value) {
        return value != null ? StorageModeType.valueOf(value) : null;
    }

    public Integer transStorageModeType(StorageModeType value) {
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

