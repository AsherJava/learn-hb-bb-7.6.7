/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.domain.Page
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 */
package com.jiuqi.nr.io.record.dao;

import com.jiuqi.np.user.domain.Page;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.io.record.bean.ImportHistory;
import com.jiuqi.nr.io.record.util.TransUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ImportHistoryDao
extends BaseDao {
    private static final Logger logger = LoggerFactory.getLogger(ImportHistoryDao.class);
    @Autowired
    private TransUtils transUtils;
    private static final String selectSql = String.format("select %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s from %s ", "REC_KEY", "TASK_KEY", "CALIBER_ENTITY", "FORM_SCHEME_KEY", "DATA_TIME", "MAPPING_KEY", "PARAM", "STATE", "CREATE_TIME", "END_TIME", "CREATE_USER", "PARAM_TYPE", "NR_DX_IMPORT_HISTORY");
    private static final String whereSql = String.format("%s = ?", "CREATE_USER");
    private static final String countAllSql = String.format("select count(%s) from %s ", "REC_KEY", "NR_DX_IMPORT_HISTORY");
    private static final String countSql = String.format("select count(%s) from %s where %s = ?", "REC_KEY", "NR_DX_IMPORT_HISTORY", "CREATE_USER");

    public Class<ImportHistory> getClz() {
        return ImportHistory.class;
    }

    public Class<TransUtils> getExternalTransCls() {
        return TransUtils.class;
    }

    public void insert(ImportHistory importHistory) {
        super.insert((Object)importHistory);
    }

    public void updateStateAndFinishTimeByRecKey(ImportHistory importHistory) {
        super.update((Object)importHistory);
    }

    public ImportHistory queryByRecKey(String recKey) {
        return (ImportHistory)super.getByKey((Object)recKey, this.getClz());
    }

    /*
     * Exception decompiling
     */
    public Page<ImportHistory> queryAll(int page, int size) {
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
    public Page<ImportHistory> queryByCreator(String createUser, int page, int size) {
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

    private int countAll() {
        Integer count = (Integer)this.jdbcTemplate.queryForObject(countAllSql, Integer.class);
        return count == null ? 0 : count;
    }

    private int count(String createUser) {
        Integer count = (Integer)this.jdbcTemplate.queryForObject(countSql, Integer.class, new Object[]{createUser});
        return count == null ? 0 : count;
    }

    private ImportHistory fillBean(ResultSet rs) throws SQLException {
        ImportHistory bean = new ImportHistory();
        bean.setRecKey(rs.getString("REC_KEY"));
        bean.setTaskKey(rs.getString("TASK_KEY"));
        bean.setCaliberEntity(rs.getString("CALIBER_ENTITY"));
        bean.setFormSchemeKey(rs.getString("FORM_SCHEME_KEY"));
        bean.setDataTime(rs.getString("DATA_TIME"));
        bean.setMappingKey(rs.getString("MAPPING_KEY"));
        bean.setParam(rs.getString("PARAM"));
        bean.setState(rs.getInt("STATE"));
        bean.setCreateTime(this.transUtils.transTimeStamp(rs.getTimestamp("CREATE_TIME")));
        bean.setEndTime(this.transUtils.transTimeStamp(rs.getTimestamp("END_TIME")));
        bean.setCreateUser(rs.getString("CREATE_USER"));
        bean.setParamType(rs.getString("PARAM_TYPE"));
        return bean;
    }

    public List<String> getNeedDeleteRecKeys(int days) {
        if (days < 0) {
            return new ArrayList<String>();
        }
        if (days == 0) {
            String sql = String.format("select %s from %s ", "REC_KEY", "NR_DX_IMPORT_HISTORY");
            return this.jdbcTemplate.queryForList(sql, String.class);
        }
        String sql = String.format("select %s from %s where %s < ?", "REC_KEY", "NR_DX_IMPORT_HISTORY", "CREATE_TIME");
        return this.jdbcTemplate.queryForList(sql, String.class, new Object[]{new Timestamp(System.currentTimeMillis() - (long)days * 24L * 60L * 60L * 1000L)});
    }

    public void deleteImportHistory(List<String> recKeys) {
        if (recKeys.isEmpty()) {
            return;
        }
        super.delete(recKeys.toArray());
    }
}

