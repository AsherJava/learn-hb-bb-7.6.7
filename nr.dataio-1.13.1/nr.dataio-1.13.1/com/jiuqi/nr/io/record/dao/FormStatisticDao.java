/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.domain.Page
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 */
package com.jiuqi.nr.io.record.dao;

import com.jiuqi.np.user.domain.Page;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.io.record.bean.FormStatisticLog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
public class FormStatisticDao
extends BaseDao {
    private static final Logger logger = LoggerFactory.getLogger(FormStatisticDao.class);
    private static final String selectSql = String.format("select %s,%s,%s,%s,%s,%s from %s ", "STATISTIC_KEY", "REC_KEY", "FORM_KEY", "FORM_ORDER", "FACTORY_ID", "DESCRIPTION", "NR_DX_FORM_STATISTIC");
    private static final String whereSql = String.format("%s = ? and %s = ?", "REC_KEY", "FACTORY_ID");
    private static final String countSql = String.format("select count(%s) from %s where ", "STATISTIC_KEY", "NR_DX_FORM_STATISTIC");

    public Class<FormStatisticLog> getClz() {
        return FormStatisticLog.class;
    }

    public void batchInsert(List<FormStatisticLog> logs) {
        super.insert(logs.toArray());
    }

    /*
     * Exception decompiling
     */
    public Page<FormStatisticLog> queryStatisticLogs(String recKey, String factoryId, int page, int size) {
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

    private int count(String recKey, String factoryId) {
        Integer count = (Integer)this.jdbcTemplate.queryForObject(countSql + whereSql, Integer.class, new Object[]{recKey, factoryId});
        return count == null ? 0 : count;
    }

    private FormStatisticLog fillBean(ResultSet rs) throws SQLException {
        FormStatisticLog bean = new FormStatisticLog();
        bean.setKey(rs.getString("STATISTIC_KEY"));
        bean.setRecKey(rs.getString("REC_KEY"));
        bean.setFormKey(rs.getString("FORM_KEY"));
        bean.setFormOrder(rs.getString("FORM_ORDER"));
        bean.setFactoryId(rs.getString("FACTORY_ID"));
        bean.setDesc(rs.getString("DESCRIPTION"));
        return bean;
    }

    public void deleteByRecKeys(final List<String> recKeys) {
        StringBuffer sql = new StringBuffer("delete from ").append("NR_DX_FORM_STATISTIC").append(" where ").append("REC_KEY").append("=?");
        this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setObject(1, recKeys.get(i));
            }

            public int getBatchSize() {
                return recKeys.size();
            }
        });
    }
}

