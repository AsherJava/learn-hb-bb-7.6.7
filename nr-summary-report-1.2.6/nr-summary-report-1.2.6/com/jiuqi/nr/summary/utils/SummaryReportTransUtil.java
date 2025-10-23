/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.summary.utils;

import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.summary.model.report.SequenceType;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummaryReportTransUtil {
    private static final Logger log = LoggerFactory.getLogger(SummaryReportTransUtil.class);

    public Date transTimeStamp(Timestamp time) {
        return new Date(time.getTime());
    }

    public Timestamp transTimeStamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public Instant transInstant(Timestamp time) {
        return Instant.ofEpochMilli(time.getTime());
    }

    public Timestamp transInstant(Instant instant) {
        return new Timestamp(instant.toEpochMilli());
    }

    public String transClob(String type) throws SQLException {
        return type;
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

    public Integer transSequenceType(SequenceType sequenceType) {
        return sequenceType.value();
    }

    public SequenceType transSequenceType(Integer sequenceType) {
        return SequenceType.valueOf(sequenceType);
    }

    public Integer transDataFieldGatherType(DataFieldGatherType gatherType) {
        return gatherType.getValue();
    }

    public DataFieldGatherType transDataFieldGatherType(Integer gatherType) {
        return DataFieldGatherType.valueOf((int)gatherType);
    }

    public Integer transDataFieldType(DataFieldType fieldType) {
        return fieldType.getValue();
    }

    public DataFieldType transDataFieldType(Integer fieldType) {
        return DataFieldType.valueOf((int)fieldType);
    }

    public Integer transBoolean(Boolean value) {
        if (value.booleanValue()) {
            return 1;
        }
        return 0;
    }

    public boolean transBoolean(Integer value) {
        return value > 0;
    }
}

