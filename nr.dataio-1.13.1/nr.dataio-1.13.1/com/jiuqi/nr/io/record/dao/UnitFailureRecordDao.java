/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.user.domain.Page
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 */
package com.jiuqi.nr.io.record.dao;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.user.domain.Page;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.io.record.bean.UnitFailureRecord;
import com.jiuqi.nr.io.record.bean.UnitFailureSubRecord;
import com.jiuqi.nr.io.record.dao.UnitFailureSubRecordDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class UnitFailureRecordDao
extends BaseDao {
    private static final Logger logger = LoggerFactory.getLogger(UnitFailureRecordDao.class);
    private static final String selectSql = String.format("select %s,%s,%s,%s,%s,%s,%s from %s ", "UNIT_FAIL_KEY", "REC_KEY", "FACTORY_ID", "DW_KEY", "DW_CODE", "DW_TITLE", "DW_ORDER", "NR_DX_UNIT_FAIL_RECORD");
    private static final String whereSql = String.format("%s = ? ", "REC_KEY");
    private static final String whereSql2 = String.format("%s =? and %s =? ", "REC_KEY", "FACTORY_ID");
    private static final String countSql = String.format("select count(%s) from %s where ", "UNIT_FAIL_KEY", "NR_DX_UNIT_FAIL_RECORD");
    @Autowired
    private UnitFailureSubRecordDao subRecordDao;

    public Class<UnitFailureRecord> getClz() {
        return UnitFailureRecord.class;
    }

    public void batchInsert(List<UnitFailureRecord> records) {
        super.insert(records.toArray());
        for (UnitFailureRecord record : records) {
            List<String> subRecords = record.getSubRecords();
            if (CollectionUtils.isEmpty(subRecords)) continue;
            ArrayList<UnitFailureSubRecord> subRecordList = new ArrayList<UnitFailureSubRecord>();
            for (String subRecord : subRecords) {
                UnitFailureSubRecord sub = new UnitFailureSubRecord();
                sub.setKey(UUIDUtils.getKey());
                sub.setRecKey(record.getRecKey());
                sub.setDwKey(record.getDwKey());
                sub.setFactoryId(record.getFactoryId());
                sub.setDesc(subRecord);
                subRecordList.add(sub);
            }
            this.subRecordDao.batchInsert(subRecordList);
        }
    }

    /*
     * Exception decompiling
     */
    public Page<UnitFailureRecord> queryFailureRecords(String recKey, int page, int size) {
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

    /*
     * Exception decompiling
     */
    public Page<UnitFailureRecord> queryFailureRecordsByFactory(String recKey, String factoryId, int page, int size) {
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

    public int count(String recKey) {
        Integer count = (Integer)this.jdbcTemplate.queryForObject(countSql + whereSql, Integer.class, new Object[]{recKey});
        return count == null ? 0 : count;
    }

    public int count(String recKey, String factoryId) {
        Integer count = (Integer)this.jdbcTemplate.queryForObject(countSql + whereSql2, Integer.class, new Object[]{recKey, factoryId});
        return count == null ? 0 : count;
    }

    private UnitFailureRecord fillBean(ResultSet rs) throws Exception {
        UnitFailureRecord bean = new UnitFailureRecord();
        bean.setKey(rs.getString("UNIT_FAIL_KEY"));
        bean.setRecKey(rs.getString("REC_KEY"));
        bean.setFactoryId(rs.getString("FACTORY_ID"));
        bean.setDwKey(rs.getString("DW_KEY"));
        bean.setDwCode(rs.getString("DW_CODE"));
        bean.setDwTitle(rs.getString("DW_TITLE"));
        bean.setDwOrder(rs.getBigDecimal("DW_ORDER"));
        List<String> subRecords = this.subRecordDao.querySubRecordsByRecKey(bean.getRecKey(), bean.getFactoryId(), bean.getDwKey());
        bean.setSubRecords(subRecords);
        return bean;
    }

    public void deleteByRecKeys(final List<String> recKeys) {
        StringBuffer sql = new StringBuffer("delete from ").append("NR_DX_UNIT_FAIL_RECORD").append(" where ").append("REC_KEY").append("=?");
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

