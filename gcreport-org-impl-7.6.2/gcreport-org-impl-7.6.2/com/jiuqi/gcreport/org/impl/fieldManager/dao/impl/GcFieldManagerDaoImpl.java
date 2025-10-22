/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ColumnType
 *  com.jiuqi.common.base.util.ColumnTypeEnum
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.org.impl.fieldManager.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ColumnType;
import com.jiuqi.common.base.util.ColumnTypeEnum;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.org.impl.fieldManager.dao.GcFieldManagerDao;
import com.jiuqi.gcreport.org.impl.fieldManager.entity.GcOrgFieldEO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GcFieldManagerDaoImpl
extends GcDbSqlGenericDAO<GcOrgFieldEO, String>
implements GcFieldManagerDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GcFieldManagerDaoImpl() {
        super(GcOrgFieldEO.class);
    }

    @Override
    public List<GcOrgFieldEO> queryListByTableName(String tableName) {
        List orgFieldEOS = this.jdbcTemplate.query("select c.id,c.initname,c.name_1,c.code,c.enableVersion,c.allowMultiple,c.enablenull,c.readOnly,c.sortorder,c.boxchecked,c.refTableName from GC_ORGFIELDMANAGER c where refTableName = ? ", ps -> ps.setString(1, tableName), (rs, rowNum) -> {
            GcOrgFieldEO orgFieldEO = new GcOrgFieldEO();
            orgFieldEO.setId(rs.getString(1));
            orgFieldEO.setInitName(rs.getString(2));
            orgFieldEO.setName(rs.getString(3));
            orgFieldEO.setCode(rs.getString(4));
            orgFieldEO.setEnableVersion(rs.getInt(5));
            orgFieldEO.setAllowMultiple(rs.getInt(6));
            orgFieldEO.setEnableNull(rs.getInt(7));
            orgFieldEO.setReadOnly(rs.getInt(8));
            orgFieldEO.setSortOrder(rs.getDouble(9));
            orgFieldEO.setBoxChecked(rs.getInt(10));
            orgFieldEO.setRefTableName(rs.getString(11));
            return orgFieldEO;
        });
        return orgFieldEOS;
    }

    @Override
    public int deleteByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        String sql = "DELETE FROM GC_ORGFIELDMANAGER WHERE " + SqlBuildUtil.getInCondi((String)"ID", ids, (ColumnType)ColumnTypeEnum.STRING.getColumnType());
        return this.jdbcTemplate.update(sql);
    }

    /*
     * Exception decompiling
     */
    @Override
    public void save(List<GcOrgFieldEO> eoList) {
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
}

