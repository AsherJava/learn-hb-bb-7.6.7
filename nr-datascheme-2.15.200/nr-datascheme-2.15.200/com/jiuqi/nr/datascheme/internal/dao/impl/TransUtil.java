/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldRestrictType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.api.type.PeriodPattern
 *  com.jiuqi.nr.datascheme.api.type.RelationType
 *  com.jiuqi.nr.zb.scheme.common.ZbType
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldRestrictType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.api.type.PeriodPattern;
import com.jiuqi.nr.datascheme.api.type.RelationType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

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
     * Exception decompiling
     */
    public static byte[] transBlob(Object obj) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK], 17[CATCHBLOCK]], but top level block is 3[TRYBLOCK]
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

    public DataTableType transDataTableType(Integer value) {
        return value != null ? DataTableType.valueOf((int)value) : null;
    }

    public Integer transDataTableType(DataTableType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public Integer transDataTableGatherType(DataTableGatherType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public DataTableGatherType transDataTableGatherType(Integer value) {
        return value != null ? DataTableGatherType.valueOf((int)value) : null;
    }

    public DataFieldKind transDataFieldKind(Integer value) {
        return value != null ? DataFieldKind.valueOf((int)value) : null;
    }

    public Integer transDataFieldKind(DataFieldKind value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public DataFieldType transDataFieldType(Integer value) {
        return value != null ? DataFieldType.valueOf((int)value) : null;
    }

    public Integer transDataFieldType(DataFieldType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public Integer transDataFieldGatherType(DataFieldGatherType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public DataFieldGatherType transDataFieldGatherType(Integer value) {
        return value != null ? DataFieldGatherType.valueOf((int)value) : null;
    }

    public DimensionType transDimensionType(Integer value) {
        return value != null ? DimensionType.valueOf((int)value) : null;
    }

    public Integer transDimensionType(DimensionType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public PeriodType transPeriodType(Integer value) {
        return value != null ? PeriodType.fromType((int)value) : null;
    }

    public Integer transPeriodType(PeriodType value) {
        return value != null ? Integer.valueOf(value.type()) : null;
    }

    public Integer transDataGroupKind(DataGroupKind value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public DataGroupKind transDataGroupKind(Integer value) {
        return value != null ? DataGroupKind.valueOf((int)value) : null;
    }

    public DataFieldApplyType transDataFieldApplyType(Integer value) {
        return value != null ? DataFieldApplyType.valueOf((int)value) : null;
    }

    public Integer transDataFieldApplyType(DataFieldApplyType value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
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

    public DeployStatusEnum transDeployStatusEnum(Integer value) {
        return value != null ? DeployStatusEnum.valueOf((int)value) : null;
    }

    public Integer transDeployStatusEnum(DeployStatusEnum value) {
        return value != null ? Integer.valueOf(value.getValue()) : null;
    }

    public String transGatherFieldKeys(String[] gatherFieldKeys) {
        if (gatherFieldKeys == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gatherFieldKeys.length; ++i) {
            sb.append(gatherFieldKeys[i]);
            if (i == gatherFieldKeys.length - 1) continue;
            sb.append(";");
        }
        return sb.toString();
    }

    public String[] transGatherFieldKeys(String gatherFieldKeys) {
        if (gatherFieldKeys == null) {
            return null;
        }
        return gatherFieldKeys.split(";");
    }

    public Integer transDataSchemeType(DataSchemeType type) {
        return type == null ? 0 : type.getValue();
    }

    public DataSchemeType transDataSchemeType(Integer type) {
        return DataSchemeType.valueOf((Integer)type);
    }

    public Integer transPeriodPattern(PeriodPattern type) {
        return type == null ? 0 : type.getValue();
    }

    public PeriodPattern transPeriodPattern(Integer type) {
        return PeriodPattern.valueOf((Integer)type);
    }

    public Integer transRelationType(RelationType type) {
        return type == null ? 0 : type.getValue();
    }

    public RelationType transRelationType(Integer type) {
        return RelationType.valueOf((Integer)type);
    }

    public String transStringArray(String[] array) {
        if (array == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(array[i]);
            if (i == array.length - 1) continue;
            sb.append(";");
        }
        return sb.toString();
    }

    public String[] transStringArray(String str) {
        if (str == null) {
            return null;
        }
        return str.split(";");
    }

    public String transDeployResult(DeployResult value) {
        if (null == value) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString((Object)value);
        }
        catch (JsonProcessingException e) {
            throw new SchemeDataException((Throwable)e);
        }
    }

    public DeployResult transDeployResult(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return (DeployResult)mapper.readValue(value, DeployResult.class);
        }
        catch (JsonProcessingException e) {
            throw new SchemeDataException((Throwable)e);
        }
    }

    public ZbType transZbType(Integer value) {
        return value != null ? ZbType.forValue((int)value) : ZbType.GENERAL_ZB;
    }

    public Integer transZbType(ZbType value) {
        return value != null ? value.getValue() : ZbType.GENERAL_ZB.getValue();
    }

    public DataFieldRestrictType transRestrictType(Integer value) {
        return value != null ? DataFieldRestrictType.valueOf((int)value) : DataFieldRestrictType.DEFAULT;
    }

    public Integer transRestrictType(DataFieldRestrictType value) {
        return value != null ? value.getValue() : DataFieldRestrictType.DEFAULT.getValue();
    }
}

