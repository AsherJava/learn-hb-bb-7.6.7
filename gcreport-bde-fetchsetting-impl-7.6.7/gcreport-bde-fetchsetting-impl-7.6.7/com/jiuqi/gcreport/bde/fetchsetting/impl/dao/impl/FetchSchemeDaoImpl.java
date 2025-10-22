/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl;

import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSchemeDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSchemeEO;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FetchSchemeDaoImpl
implements FetchSchemeDao {
    private static final String FILED_STRING = " id,name,formSchemeId,bizType, includeAdjustVchr, ordinal";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FetchSchemeEO> listFetchSchemeByFormSchemeId(String formSchemeId) {
        String sql = "  SELECT  id,name,formSchemeId,bizType, includeAdjustVchr, ordinal\n  FROM BDE_FETCHSCHEME  fs \n WHERE fs.FORMSCHEMEID = ? ORDER BY ORDINAL \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSchemeEO(rs), new Object[]{formSchemeId});
    }

    private FetchSchemeEO getFetchSchemeEO(ResultSet rs) throws SQLException {
        FetchSchemeEO eo = new FetchSchemeEO();
        eo.setId(rs.getString(1));
        eo.setName(rs.getString(2));
        eo.setFormSchemeId(rs.getString(3));
        eo.setBizType(BizTypeEnum.getEnumByCode((String)rs.getString(4)));
        eo.setIncludeAdjustVchr(rs.getInt(5));
        eo.setOrdinal(rs.getBigDecimal(6));
        return eo;
    }

    @Override
    public void save(FetchSchemeEO fetchScheme) {
        if (StringUtils.isEmpty((String)fetchScheme.getId())) {
            fetchScheme.setId(UUIDUtils.newHalfGUIDStr());
        }
        String sql = "  insert into  BDE_FETCHSCHEME \n (  id,name,formSchemeId,bizType, includeAdjustVchr, ordinal)\n values( ?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, new Object[]{fetchScheme.getId(), fetchScheme.getName(), fetchScheme.getFormSchemeId(), fetchScheme.getBizType().getCode(), Objects.isNull(fetchScheme.getIncludeAdjustVchr()) ? 0 : fetchScheme.getIncludeAdjustVchr(), System.currentTimeMillis()});
    }

    @Override
    public int delete(FetchSchemeEO fetchScheme) {
        String sql = "delete from BDE_FETCHSCHEME where id = ? ";
        return this.jdbcTemplate.update(sql, new Object[]{fetchScheme.getId()});
    }

    @Override
    public int update(FetchSchemeEO fetchScheme) {
        String sql = "update  BDE_FETCHSCHEME set name=?,formSchemeId=? where id=? ";
        return this.jdbcTemplate.update(sql, new Object[]{fetchScheme.getName(), fetchScheme.getFormSchemeId(), fetchScheme.getId()});
    }

    @Override
    public FetchSchemeEO selectById(String id) {
        String sql = "  SELECT  id,name,formSchemeId,bizType, includeAdjustVchr, ordinal\n  FROM BDE_FETCHSCHEME  \n WHERE id = ? \n";
        return (FetchSchemeEO)this.jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return null;
            }
            return this.getFetchSchemeEO(rs);
        }, new Object[]{id});
    }

    @Override
    public List<FetchSchemeEO> loadAllByBizType(String bizType) {
        String sql = "  SELECT  id,name,formSchemeId,bizType, includeAdjustVchr, ordinal \n  FROM BDE_FETCHSCHEME  fs \nWHERE BIZTYPE = ?";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSchemeEO(rs), new Object[]{bizType});
    }

    @Override
    public List<FetchSchemeEO> listFetchSchemeByIdList(List<String> idList) {
        String sql = "  SELECT  id,name,formSchemeId,bizType, includeAdjustVchr, ordinal\n  FROM BDE_FETCHSCHEME  fs \n WHERE  " + SqlBuildUtil.getStrInCondi((String)"ID", idList) + "\n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSchemeEO(rs));
    }

    @Override
    public int updateOrdinalById(String id, BigDecimal ordinal) {
        String sql = "update  BDE_FETCHSCHEME set ORDINAL = ?  where id=? ";
        return this.jdbcTemplate.update(sql, new Object[]{ordinal, id});
    }

    /*
     * Exception decompiling
     */
    @Override
    public List<FetchSchemeEO> selectByFormSchemesAndName(List<String> formSchemeIds, Optional<String> name) {
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
         *     at org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredAssignment.rewriteExpressions(StructuredAssignment.java:146)
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

    @Override
    public int updateIncludeAdjustVoucherByFetchSchemeId(String fetchSchemeId, int includeAdjustVoucher) {
        String sql = "UPDATE  BDE_FETCHSCHEME SET INCLUDEADJUSTVCHR = ?  WHERE id=? ";
        return this.jdbcTemplate.update(sql, new Object[]{includeAdjustVoucher, fetchSchemeId});
    }

    private /* synthetic */ FetchSchemeEO lambda$selectByFormSchemesAndName$6(ResultSet rs, int row) throws SQLException {
        return this.getFetchSchemeEO(rs);
    }

    private static /* synthetic */ Object[] lambda$selectByFormSchemesAndName$5() {
        return new Object[0];
    }
}

