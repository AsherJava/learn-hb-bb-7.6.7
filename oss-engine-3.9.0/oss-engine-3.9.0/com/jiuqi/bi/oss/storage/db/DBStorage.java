/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.bi.database.metadata.SQLMetadataException
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 */
package com.jiuqi.bi.oss.storage.db;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectNotFoundException;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.encrypt.ObjectEncryptManager;
import com.jiuqi.bi.oss.storage.AbstractObjectStorage;
import com.jiuqi.bi.oss.storage.DBSQLBuilder;
import com.jiuqi.bi.oss.storage.IConnectionProvider;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.StorageUtils;
import com.jiuqi.bi.oss.storage.db.DBStorageConfig;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBStorage
extends AbstractObjectStorage {
    private static final String F_OBJ_KEY = "OBJ_KEY";
    private static final String F_OBJ_DATA = "OBJ_DATA";
    private IConnectionProvider connProvider;
    private IDatabase database;
    private Logger logger = LoggerFactory.getLogger(DBStorage.class);

    public DBStorage(IObjectMetaAdapter metaAdapter) {
        super(metaAdapter);
    }

    private Connection newConnection() throws SQLException {
        return this.connProvider.openConnection();
    }

    @Override
    public void initialize(StorageConfig context) throws ObjectStorageException {
        super.initialize(context);
        this.connProvider = ((DBStorageConfig)context).getDataConnProvider();
        try (Connection conn = this.newConnection();){
            this.database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        }
        catch (SQLException e) {
            throw new ObjectStorageException(e.getMessage(), e);
        }
    }

    private String getObjectDataTableName(String bucketName) {
        return "OSS_B_" + bucketName.toUpperCase() + "_D";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean createBucket(Bucket bucket) throws ObjectStorageException {
        StorageUtils.checkBucketName(bucket.getName());
        LogicField keyField = new LogicField();
        keyField.setDataType(6);
        keyField.setFieldName(F_OBJ_KEY);
        keyField.setFieldTitle("\u5bf9\u8c61\u4e3b\u952e");
        keyField.setNullable(false);
        keyField.setSize(200);
        String dataTableName = this.getObjectDataTableName(bucket.getName());
        CreateTableStatement cts2 = new CreateTableStatement("", dataTableName);
        cts2.addColumn(keyField);
        LogicField dataField = new LogicField();
        dataField.setDataType(9);
        dataField.setFieldName(F_OBJ_DATA);
        dataField.setFieldTitle("\u5bf9\u8c61\u7684\u6570\u636e\u6d41");
        dataField.setNullable(false);
        cts2.addColumn(dataField);
        cts2.getPrimaryKeys().add(F_OBJ_KEY);
        try (Connection conn = this.newConnection();){
            IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            LogicTable table = db.createMetadata(conn).getTableByName(dataTableName);
            if (table != null) {
                boolean bl = false;
                return bl;
            }
            List sqls2 = cts2.interpret(this.database, conn);
            for (String sql : sqls2) {
                PreparedStatement ps2 = conn.prepareStatement(sql);
                Throwable throwable = null;
                try {
                    ps2.executeUpdate();
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (ps2 == null) continue;
                    if (throwable != null) {
                        try {
                            ps2.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    ps2.close();
                }
            }
            boolean bl = true;
            return bl;
        }
        catch (SQLInterpretException e) {
            throw new ObjectStorageException("\u6784\u9020\u5efa\u8868\u8bed\u53e5\u5931\u8d25", e);
        }
        catch (SQLException e) {
            throw new ObjectStorageException("\u521b\u5efa\u5143\u6570\u636e\u8868\u5931\u8d25", e);
        }
        catch (SQLMetadataException e) {
            throw new ObjectStorageException("\u83b7\u53d6\u8868\u5143\u6570\u636e\u5931\u8d25", e);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public boolean deleteBucket(String bucketName) throws ObjectStorageException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 4 blocks at once
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
     * Loose catch block
     */
    @Override
    public void upload(String bucketName, String key, InputStream input, ObjectInfo info) throws ObjectStorageException {
        Bucket bucket = this.getBucket(bucketName);
        String name = ObjectEncryptManager.getInstance().getDefaultEncryptName();
        String eKey = ObjectEncryptManager.getInstance().getDefaultEncryptKey();
        byte[] bytes = StorageUtils.putStreamToMemory(bucket, key, input, info, name, eKey, -1L);
        String tableName = this.getObjectDataTableName(bucketName);
        String insertSql = DBSQLBuilder.buildInsert(tableName, new String[]{F_OBJ_KEY, F_OBJ_DATA});
        try {
            Throwable throwable = null;
            try (Connection conn = this.newConnection();){
                conn.setAutoCommit(false);
                try (PreparedStatement ps = conn.prepareStatement(insertSql);){
                    ps.setString(1, info.getKey());
                    ps.setBytes(2, bytes);
                    ps.executeUpdate();
                    conn.commit();
                }
                catch (Throwable e) {
                    LogicTable table;
                    block35: {
                        block36: {
                            block37: {
                                conn.rollback();
                                table = null;
                                ISQLMetadata metadata = this.database.createMetadata(conn);
                                table = metadata.getTableByName(tableName);
                                if (table != null) break block35;
                                this.logger.error("\u5bf9\u8c61\u5b58\u50a8\u6570\u636e\u8868\u3010" + tableName + "\u3011\u4e22\u5931\uff0c\u5c1d\u8bd5\u91cd\u65b0\u521b\u5efa");
                                this.createBucket(bucket);
                                this.upload(bucketName, eKey, input, info);
                                if (conn == null) break block36;
                                if (throwable == null) break block37;
                                try {
                                    conn.close();
                                }
                                catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                                break block36;
                            }
                            conn.close();
                        }
                        return;
                    }
                    try {
                        block38: {
                            break block38;
                            catch (Exception e1) {
                                this.logger.error(e1.getMessage(), e1);
                            }
                        }
                        if (table != null) {
                            throw e;
                        }
                    }
                    catch (Throwable throwable3) {
                        throwable = throwable3;
                        throw throwable3;
                    }
                    catch (Throwable throwable4) {
                        throw throwable4;
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new ObjectStorageException("\u63d2\u5165\u5bf9\u8c61\u7684\u6570\u636e\u51fa\u9519", e);
        }
    }

    @Override
    public void move(String key, String srcBucket, String destBucket) throws ObjectStorageException {
        String src = this.getObjectDataTableName(srcBucket);
        String dest = this.getObjectDataTableName(destBucket);
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(dest).append("(").append(F_OBJ_KEY).append(",").append(F_OBJ_DATA).append(") ");
        sb.append("SELECT ").append(F_OBJ_KEY).append(",").append(F_OBJ_DATA).append(" FROM ").append(src).append(" WHERE ");
        sb.append(F_OBJ_KEY).append("=?");
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(sb.toString());){
            ps.setString(1, key);
            ps.executeUpdate();
            String deleteDataSql = DBSQLBuilder.buildDelete(src, new String[]{F_OBJ_KEY});
            try (PreparedStatement ps2 = conn.prepareStatement(deleteDataSql);){
                ps2.setString(1, key);
                ps2.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new ObjectStorageException(e.getMessage(), e);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public InputStream download(String bucketName, String key) throws ObjectStorageException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
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

    @Override
    public void download(String bucketName, String key, OutputStream output) throws ObjectStorageException {
        block52: {
            Bucket bucket = this.getBucket(bucketName);
            String tableName = this.getObjectDataTableName(bucketName);
            String sql = DBSQLBuilder.buildSelect(tableName, new String[]{F_OBJ_DATA}, new String[]{F_OBJ_KEY});
            try (Connection conn = this.newConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);){
                ps.setString(1, key);
                try (ResultSet rs = ps.executeQuery();){
                    if (rs.next()) {
                        try (InputStream in = rs.getBinaryStream(1);){
                            if (in != null) {
                                String name = ObjectEncryptManager.getInstance().getDefaultEncryptName();
                                String eKey = ObjectEncryptManager.getInstance().getDefaultEncryptKey();
                                StorageUtils.loadStreamToOutputStream(bucket, key, in, name, eKey, output);
                            }
                            break block52;
                        }
                    }
                    throw new ObjectNotFoundException("\u627e\u4e0d\u5230\u5bf9\u8c61\uff1a" + key);
                }
            }
            catch (Exception e) {
                throw new ObjectStorageException(e.getMessage(), e);
            }
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public boolean existObject(String bucketName, String key) throws ObjectStorageException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 5 blocks at once
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
    @Override
    public boolean deleteObject(String bucketName, String key) throws ObjectStorageException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
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
}

