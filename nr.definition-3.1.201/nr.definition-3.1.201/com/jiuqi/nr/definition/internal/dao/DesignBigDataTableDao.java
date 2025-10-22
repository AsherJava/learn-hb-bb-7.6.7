/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.util.DefinitionUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class DesignBigDataTableDao
extends BaseDao {
    private static final String ATTR_KEY = "key";
    private static final String ATTR_CODE = "code";
    private static final String ATTR_LAND = "lang";
    public static final String DEFAULT_VERSION = "1.0";
    private Class<DesignBigDataTable> implClass = DesignBigDataTable.class;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public DesignBigDataTable queryigDataDefine(String key, String code, int lang) {
        List defines = this.list(new String[]{ATTR_KEY, ATTR_CODE, ATTR_LAND}, new Object[]{key, code, lang}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignBigDataTable)defines.get(0);
        }
        return null;
    }

    public List<DesignBigDataTable> queryigDataDefines(String key, String code) {
        return this.list(new String[]{ATTR_KEY, ATTR_CODE}, new Object[]{key, code}, this.implClass);
    }

    public DesignBigDataTable queryigDataDefine(String key, String code) {
        return this.queryigDataDefine(key, code, LanguageType.CHINESE.getValue());
    }

    public void deleteBigData(String key, String code, int lang) throws Exception {
        this.deleteBy(new String[]{ATTR_KEY, ATTR_CODE, ATTR_LAND}, new Object[]{key, code, lang});
    }

    public void deleteBigData(String Key2, String code) throws Exception {
        this.deleteBy(new String[]{ATTR_KEY, ATTR_CODE, ATTR_LAND}, new Object[]{Key2, code, LanguageType.CHINESE.getValue()});
    }

    public void updateData(DesignBigDataTable data) throws Exception {
        this.update(data, new String[]{ATTR_KEY, ATTR_CODE, ATTR_LAND}, new Object[]{data.getKey(), data.getCode(), data.getLang()});
    }

    public void delete(List<DesignBigDataTable> datas) {
        if (null == datas || datas.isEmpty()) {
            return;
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (DesignBigDataTable d : datas) {
            batchArgs.add(new Object[]{d.getKey(), d.getCode(), d.getLang()});
        }
        String sql = "DELETE FROM NR_PARAM_BIGDATATABLE_DES WHERE BD_KEY=? AND BD_CODE=? AND BD_LANG=? ";
        this.jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    public List<DesignBigDataTable> queryBigDataDefine(List<String> keys, String code, Integer lang) {
        return DefinitionUtils.limitExe(keys, subKeys -> this.listByKeys((List<String>)subKeys, code, lang));
    }

    private List<DesignBigDataTable> listByKeys(List<String> keys, String code, Integer lang) {
        StringBuffer sbr = new StringBuffer("SELECT D.* FROM NR_PARAM_BIGDATATABLE_DES D WHERE D.BD_KEY IN (:keys) ");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("keys", keys);
        if (null != code) {
            sbr.append("D.BD_CODE = :code ");
            sqlParameterSource.addValue(ATTR_CODE, (Object)code);
        }
        if (null != lang) {
            sbr.append("AND D.BD_LANG = :lang ");
            sqlParameterSource.addValue(ATTR_LAND, (Object)lang);
        }
        RowMapper rowMapper = (rs, rowNum) -> {
            DesignBigDataTable bigData = new DesignBigDataTable();
            this.readRecord(rs, bigData);
            return bigData;
        };
        this.buildMethod();
        return this.namedParameterJdbcTemplate.query(sbr.toString(), (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    public List<DesignBigDataTable> queryigDataDefine(String code) {
        return this.list(new String[]{ATTR_CODE, ATTR_LAND}, new Object[]{code, RunTimeBigDataTableDao.DEFAULT_lAND}, this.implClass);
    }

    public Map<String, Date> queryUpdateTime(List<String> keys, String code) {
        return this.queryUpdateTime(keys, code, RunTimeBigDataTableDao.DEFAULT_lAND);
    }

    public Map<String, Date> queryUpdateTime(List<String> keys, String code, int lang) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyMap();
        }
        String sql = "SELECT BD_KEY, BD_UPDATETIME FROM NR_PARAM_BIGDATATABLE_DES WHERE BD_KEY IN (:keys) AND BD_CODE = :code AND BD_LANG= :lang ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("keys", keys);
        sqlParameterSource.addValue(ATTR_CODE, (Object)code);
        sqlParameterSource.addValue(ATTR_LAND, (Object)lang);
        return (Map)this.namedParameterJdbcTemplate.query("SELECT BD_KEY, BD_UPDATETIME FROM NR_PARAM_BIGDATATABLE_DES WHERE BD_KEY IN (:keys) AND BD_CODE = :code AND BD_LANG= :lang ", (SqlParameterSource)sqlParameterSource, rs -> {
            HashMap<String, Timestamp> map = new HashMap<String, Timestamp>();
            while (rs.next()) {
                map.put(rs.getString(1), rs.getTimestamp(2));
            }
            return map;
        });
    }

    public void updateData(DesignBigDataTable bigData, boolean isUpdateTime) throws Exception {
        if (isUpdateTime) {
            this.updateData(bigData);
        } else {
            this.jdbcTemplate.update("UPDATE NR_PARAM_BIGDATATABLE_DES SET BD_DATA = ? WHERE BD_KEY = ? AND BD_CODE = ? AND BD_LANG = ?", pss -> {
                pss.setObject(1, bigData.getData());
                pss.setString(2, bigData.getKey());
                pss.setString(3, bigData.getCode());
                pss.setInt(4, bigData.getLang());
            });
        }
    }

    /*
     * Exception decompiling
     */
    public void insertData(List<DesignBigDataTable> data, boolean autoTime) throws Exception {
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

    public void syncUpdateTime(String srcKey, String desKey, String code, int lang) {
        String query = "SELECT BD_UPDATETIME FROM NR_PARAM_BIGDATATABLE_DES WHERE BD_KEY=? AND BD_CODE=? AND BD_LANG=? ";
        Timestamp updateTime = (Timestamp)this.jdbcTemplate.query("SELECT BD_UPDATETIME FROM NR_PARAM_BIGDATATABLE_DES WHERE BD_KEY=? AND BD_CODE=? AND BD_LANG=? ", pss -> {
            pss.setString(1, srcKey);
            pss.setString(2, code);
            pss.setInt(3, lang);
        }, rs -> {
            if (rs.next()) {
                return rs.getTimestamp(1);
            }
            return null;
        });
        String update = "UPDATE NR_PARAM_BIGDATATABLE_DES SET BD_UPDATETIME=? WHERE BD_KEY=? AND BD_CODE=? AND BD_LANG=? ";
        this.jdbcTemplate.update("UPDATE NR_PARAM_BIGDATATABLE_DES SET BD_UPDATETIME=? WHERE BD_KEY=? AND BD_CODE=? AND BD_LANG=? ", pss -> {
            pss.setTimestamp(1, updateTime);
            pss.setString(2, desKey);
            pss.setString(3, code);
            pss.setInt(4, lang);
        });
    }
}

