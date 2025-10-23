/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.temp.ITempTableProvider
 *  com.jiuqi.bi.database.temp.TempTable
 *  com.jiuqi.bi.database.temp.TempTableProviderFactory
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ConnectionCallback
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.file.impl;

import com.jiuqi.bi.database.temp.ITempTableProvider;
import com.jiuqi.bi.database.temp.TempTable;
import com.jiuqi.bi.database.temp.TempTableProviderFactory;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.IFileInfoDao;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value="fileInfoDao")
public class FileInfoDao
implements IFileInfoDao {
    private static final String FD_ID = "ID";
    private static final String TD_FILE = "SYS_FILE";
    private static final String FD_FILE_KEY = "F_KEY";
    private static final String FD_FILE_AREA = "F_AREA";
    private static final String FD_FILE_NAME = "F_NAME";
    private static final String FD_FILE_EXTENSION = "F_EXTENSION";
    private static final String FD_FILE_SIZE = "F_SIZE";
    private static final String FD_FILE_STATUS = "F_STATUS";
    private static final String FD_FILE_CREATER = "F_CREATER";
    private static final String FD_FILE_CREATETIME = "F_CREATETIME";
    private static final String FD_FILE_LASTOPERATOR = "F_LASTOPERATOR";
    private static final String FD_FILE_LASTOPERATETIME = "F_LASTOPERATETIME";
    private static final String FD_FILE_VERSION = "F_VERSION";
    private static final String FD_FILE_GROUP = "F_FILEGROUP";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(FileInfo fileInfo) {
        String sql = String.format("insert into %s (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) values(?,?,?,?,?,?,?,?,?,?,?,?,?)", TD_FILE, FD_ID, FD_FILE_KEY, FD_FILE_AREA, FD_FILE_NAME, FD_FILE_EXTENSION, FD_FILE_SIZE, FD_FILE_STATUS, FD_FILE_CREATER, FD_FILE_CREATETIME, FD_FILE_LASTOPERATOR, FD_FILE_LASTOPERATETIME, FD_FILE_VERSION, FD_FILE_GROUP);
        this.jdbcTemplate.update(sql, new Object[]{UUID.randomUUID().toString(), fileInfo.getKey(), fileInfo.getArea(), fileInfo.getName(), fileInfo.getExtension(), fileInfo.getSize(), fileInfo.getStatus().name(), fileInfo.getCreater(), fileInfo.getCreateTime(), fileInfo.getLastModifier(), fileInfo.getLastModifyTime(), fileInfo.getVersion(), fileInfo.getFileGroupKey()});
    }

    @Override
    public void update(FileInfo fileInfo) {
        String sql = String.format("update %s set %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? where %s=?", TD_FILE, FD_FILE_NAME, FD_FILE_EXTENSION, FD_FILE_SIZE, FD_FILE_STATUS, FD_FILE_LASTOPERATOR, FD_FILE_LASTOPERATETIME, FD_FILE_VERSION, FD_FILE_GROUP, FD_FILE_KEY);
        this.jdbcTemplate.update(sql, new Object[]{fileInfo.getName(), fileInfo.getExtension(), fileInfo.getSize(), fileInfo.getStatus().name(), fileInfo.getLastModifier(), fileInfo.getLastModifyTime(), fileInfo.getVersion(), fileInfo.getFileGroupKey(), fileInfo.getKey()});
    }

    @Override
    public void delete(String fileKey) {
        String sql = String.format("delete from %s where %s=?", TD_FILE, FD_FILE_KEY);
        this.jdbcTemplate.update(sql, new Object[]{fileKey});
    }

    public void deleteByGroup(String groupKey) {
        String sql = String.format("delete from %s where %s=?", TD_FILE, FD_FILE_GROUP);
        this.jdbcTemplate.update(sql, new Object[]{groupKey});
    }

    @Override
    public FileInfo getFileInfo(String fileKey, String area) {
        String sql = String.format("select %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s from %s where %s=? and %s=?", FD_FILE_KEY, FD_FILE_AREA, FD_FILE_NAME, FD_FILE_EXTENSION, FD_FILE_SIZE, FD_FILE_STATUS, FD_FILE_CREATER, FD_FILE_CREATETIME, FD_FILE_LASTOPERATOR, FD_FILE_LASTOPERATETIME, FD_FILE_VERSION, FD_FILE_GROUP, TD_FILE, FD_FILE_KEY, FD_FILE_AREA);
        Object[] args = new Object[]{fileKey, area};
        return (FileInfo)this.jdbcTemplate.query(sql, args, rs -> {
            if (rs.next()) {
                return FileInfoDao.parseFileInfo(rs);
            }
            return null;
        });
    }

    @Override
    public List<FileInfo> getFileInfosByGroup(String groupKey, String area) {
        if (groupKey == null) {
            return null;
        }
        String sql = String.format("select %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s from %s where %s=? and %s=?", FD_FILE_KEY, FD_FILE_AREA, FD_FILE_NAME, FD_FILE_EXTENSION, FD_FILE_SIZE, FD_FILE_STATUS, FD_FILE_CREATER, FD_FILE_CREATETIME, FD_FILE_LASTOPERATOR, FD_FILE_LASTOPERATETIME, FD_FILE_VERSION, FD_FILE_GROUP, TD_FILE, FD_FILE_GROUP, FD_FILE_AREA);
        Object[] args = new Object[]{groupKey, area};
        return (List)this.jdbcTemplate.query(sql, args, rs -> {
            ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
            while (rs.next()) {
                fileInfos.add(FileInfoDao.parseFileInfo(rs));
            }
            return fileInfos;
        });
    }

    @Override
    public Collection<FileInfo> getFileInfos(Collection<String> fileKeys, String area) {
        if (fileKeys == null || fileKeys.size() == 0) {
            return Collections.emptyList();
        }
        int limit = 50;
        return fileKeys.size() > 50 ? this.batchGetFileInfoByTempTable(fileKeys, area) : this.batchGetFileInfoByIn(fileKeys, area);
    }

    private Collection<FileInfo> batchGetFileInfoByIn(Collection<String> fileKeys, String area) {
        StringBuilder fileKeyBuilder = new StringBuilder();
        for (String fk : fileKeys) {
            fileKeyBuilder.append("'").append(fk).append("',");
        }
        fileKeyBuilder.setLength(fileKeyBuilder.length() - 1);
        String sql = String.format("select %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s from %s where %s in(%s) and %s=?", FD_FILE_KEY, FD_FILE_AREA, FD_FILE_NAME, FD_FILE_EXTENSION, FD_FILE_SIZE, FD_FILE_STATUS, FD_FILE_CREATER, FD_FILE_CREATETIME, FD_FILE_LASTOPERATOR, FD_FILE_LASTOPERATETIME, FD_FILE_VERSION, FD_FILE_GROUP, TD_FILE, FD_FILE_KEY, fileKeyBuilder, FD_FILE_AREA);
        Object[] args = new Object[]{area};
        return (Collection)this.jdbcTemplate.query(sql, args, rs -> {
            ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
            while (rs.next()) {
                fileInfos.add(FileInfoDao.parseFileInfo(rs));
            }
            return fileInfos;
        });
    }

    private Collection<FileInfo> batchGetFileInfoByTempTable(final Collection<String> fileKeys, final String area) {
        return (Collection)this.jdbcTemplate.execute((ConnectionCallback)new ConnectionCallback<List<FileInfo>>(){

            /*
             * Exception decompiling
             */
            private List<FileInfo> queryJoinTemptable(Connection con, String tempTableName) throws SQLException {
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
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public List<FileInfo> doInConnection(Connection con) throws SQLException, DataAccessException {
                ITempTableProvider tempTableProvider = TempTableProviderFactory.getInstance().getTempTableProvider();
                try (TempTable oneKeyTempTable = tempTableProvider.getOneKeyTempTable(con);){
                    ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
                    for (String fk : fileKeys) {
                        Object[] values = new Object[]{fk};
                        batchValues.add(values);
                    }
                    oneKeyTempTable.insertRecords(batchValues);
                    String tempTableName = oneKeyTempTable.getTableName();
                    List<FileInfo> list = this.queryJoinTemptable(con, tempTableName);
                    return list;
                }
                catch (Exception e) {
                    throw new SQLException(e);
                }
            }
        });
    }

    private static FileInfo parseFileInfo(ResultSet rs) throws SQLException {
        int col = 1;
        return FileInfoBuilder.newFileInfo(rs.getString(col++), rs.getString(col++), rs.getString(col++), rs.getString(col++), rs.getLong(col++), FileStatus.valueOf(rs.getString(col++)), rs.getString(col++), rs.getTimestamp(col++), rs.getString(col++), rs.getTimestamp(col++), rs.getInt(col++), rs.getString(col));
    }

    @Override
    public List<FileInfo> getFileInfos(String area) {
        if (area == null) {
            return null;
        }
        String sql = String.format("select %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s from %s where %s=?", FD_FILE_KEY, FD_FILE_AREA, FD_FILE_NAME, FD_FILE_EXTENSION, FD_FILE_SIZE, FD_FILE_STATUS, FD_FILE_CREATER, FD_FILE_CREATETIME, FD_FILE_LASTOPERATOR, FD_FILE_LASTOPERATETIME, FD_FILE_VERSION, FD_FILE_GROUP, TD_FILE, FD_FILE_AREA);
        Object[] args = new Object[]{area};
        return (List)this.jdbcTemplate.query(sql, args, rs -> {
            ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
            while (rs.next()) {
                fileInfos.add(FileInfoDao.parseFileInfo(rs));
            }
            return fileInfos;
        });
    }

    @Override
    public List<String> getAreas() {
        String sql = String.format("select %s from %s group by %s", FD_FILE_AREA, TD_FILE, FD_FILE_AREA);
        Object[] args = new Object[]{};
        return (List)this.jdbcTemplate.query(sql, args, rs -> {
            ArrayList<String> areas = new ArrayList<String>();
            while (rs.next()) {
                areas.add(rs.getString(1));
            }
            return areas;
        });
    }

    static /* synthetic */ FileInfo access$000(ResultSet x0) throws SQLException {
        return FileInfoDao.parseFileInfo(x0);
    }
}

