/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignIndexModelDefineImpl
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignTableModelDefineImpl
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import com.jiuqi.nr.datascheme.internal.entity.PreDeployInfoDO;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignIndexModelDefineImpl;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignTableModelDefineImpl;
import java.sql.Clob;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataSchemePreDeployInfoDaoImpl {
    private static final String DB_TABLE = "NR_DATASCHEME_PRE_DEPLOY_INFO";
    private static final String DB_FIELD_DS_KEY = "PDI_DS_KEY";
    private static final String DB_FIELD_DT_KEY = "PDI_DT_KEY";
    private static final String DB_FIELD_TYPE = "PDI_TYPE";
    private static final String DB_FIELD_CREATE_TIME = "PDI_CREATE_TIME";
    private static final String DB_FIELD_DETAILS = "PDI_DETAILS";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String serialize(PreDeployInfoDO.PreDeployDetails info) {
        if (null == info) {
            return "";
        }
        ObjectMapper objectMapper = DataSchemePreDeployInfoDaoImpl.getObjectMapper();
        try {
            return objectMapper.writeValueAsString((Object)info);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static PreDeployInfoDO.PreDeployDetails deserialize(String data) {
        if (null == data || "".equals(data)) {
            return null;
        }
        ObjectMapper objectMapper = DataSchemePreDeployInfoDaoImpl.getObjectMapper();
        try {
            return (PreDeployInfoDO.PreDeployDetails)objectMapper.readValue(data, PreDeployInfoDO.PreDeployDetails.class);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Instant.class, (JsonSerializer)new InstantJsonSerializer());
        module.addDeserializer(Instant.class, (JsonDeserializer)new InstantJsonDeserializer());
        module.addAbstractTypeMapping(DesignTableModelDefine.class, DesignTableModelDefineImpl.class);
        module.addAbstractTypeMapping(DesignColumnModelDefine.class, DesignColumnModelDefineImpl.class);
        module.addAbstractTypeMapping(DesignIndexModelDefine.class, DesignIndexModelDefineImpl.class);
        objectMapper.registerModule((Module)module);
        return objectMapper;
    }

    public List<PreDeployInfoDO> list(String dataSchemeKey) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s = ?", DB_FIELD_DS_KEY, DB_FIELD_DT_KEY, DB_FIELD_TYPE, DB_FIELD_CREATE_TIME, DB_FIELD_DETAILS, DB_TABLE, DB_FIELD_DS_KEY);
        return this.jdbcTemplate.query(sql, pss -> pss.setString(1, dataSchemeKey), (rs, rowNum) -> {
            String dsKey = rs.getString(DB_FIELD_DS_KEY);
            String tmKey = rs.getString(DB_FIELD_DT_KEY);
            PreDeployInfoDO.PreDeployType type = PreDeployInfoDO.PreDeployType.valueOf(rs.getInt(DB_FIELD_TYPE));
            Instant createTime = rs.getTimestamp(DB_FIELD_CREATE_TIME).toInstant();
            Clob clob = rs.getClob(DB_FIELD_DETAILS);
            PreDeployInfoDO info = new PreDeployInfoDO(dsKey, tmKey, type, createTime);
            info.setDeployDetails(DataSchemePreDeployInfoDaoImpl.deserialize(clob.getSubString(1L, (int)clob.length())));
            return info;
        });
    }

    public void delete(String dataSchemeKey) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", DB_TABLE, DB_FIELD_DS_KEY);
        this.jdbcTemplate.update(sql, pss -> pss.setString(1, dataSchemeKey));
    }

    /*
     * Exception decompiling
     */
    public void insert(List<PreDeployInfoDO> infos) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * java.lang.UnsupportedOperationException
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.NewAnonymousArray.getDimSize(NewAnonymousArray.java:142)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.isNewArrayLambda(LambdaRewriter.java:455)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:409)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:167)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:105)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.rewriters.ExpressionRewriterHelper.applyForwards(ExpressionRewriterHelper.java:12)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriterToArgs(AbstractMemberFunctionInvokation.java:101)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriter(AbstractMemberFunctionInvokation.java:88)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:103)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriter(AbstractMemberFunctionInvokation.java:87)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:103)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.rewriters.ExpressionRewriterHelper.applyForwards(ExpressionRewriterHelper.java:12)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriterToArgs(AbstractMemberFunctionInvokation.java:101)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriter(AbstractMemberFunctionInvokation.java:88)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:103)
         *     at org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredExpressionStatement.rewriteExpressions(StructuredExpressionStatement.java:70)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewrite(LambdaRewriter.java:88)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.rewriteLambdas(Op04StructuredStatement.java:1137)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:912)
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

    public void insert(PreDeployInfoDO info) {
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)", DB_TABLE, DB_FIELD_DS_KEY, DB_FIELD_DT_KEY, DB_FIELD_TYPE, DB_FIELD_CREATE_TIME, DB_FIELD_DETAILS);
        this.jdbcTemplate.update(sql, pss -> {
            pss.setString(1, info.getDataSchemeKey());
            pss.setString(2, info.getDataTableKey());
            pss.setInt(3, info.getType().getValue());
            pss.setTimestamp(4, Timestamp.from(info.getCreateTime()));
            Clob clob = pss.getConnection().createClob();
            clob.setString(1L, DataSchemePreDeployInfoDaoImpl.serialize(info.getDeployDetails()));
            pss.setClob(5, clob);
        });
    }
}

