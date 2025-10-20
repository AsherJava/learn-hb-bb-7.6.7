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
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.sql.parser.SQLParser
 *  com.jiuqi.bi.database.sql.parser.SQLParserException
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.CreateIndexStatement
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 *  com.jiuqi.bi.database.statement.SqlStatement
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.oss.storage;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.sql.parser.SQLParser;
import com.jiuqi.bi.database.sql.parser.SQLParserException;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.CreateIndexStatement;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectFilterCondition;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectOrderField;
import com.jiuqi.bi.oss.ObjectStorageMetadataException;
import com.jiuqi.bi.oss.storage.DBSQLBuilder;
import com.jiuqi.bi.oss.storage.IConnectionProvider;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBMetaAdapter
implements IObjectMetaAdapter {
    private static final String T_BUCKET = "OSS_BUCKET";
    private static final String F_BT_NAME = "BT_NAME";
    private static final String F_BT_OWNER = "BT_OWNER";
    private static final String F_BT_OPEN = "BT_OPEN";
    private static final String F_BT_DESC = "BT_DESC";
    private static final String F_BT_STORAGETYPE = "BT_STORAGETYPE";
    private static final String F_BT_STORAGECONFIG = "BT_STORAGECONFIG";
    private static final String F_BT_EXPIRETIME = "BT_EXPIRETIME";
    private static final String F_BT_STATUS = "BT_STATUS";
    private static final String F_BT_CREATETIME = "BT_CREATETIME";
    private static final String F_BT_GROUP = "BT_GROUP";
    private static final String F_BT_MANUAL = "BT_MANUAL";
    private static final String F_BT_JOBID = "BT_JOBID";
    private static final String F_BT_LINK = "BT_LINK";
    private static final String INSERT_BUCKET = DBSQLBuilder.buildInsert("OSS_BUCKET", new String[]{"BT_NAME", "BT_OWNER", "BT_OPEN", "BT_DESC", "BT_CREATETIME", "BT_EXPIRETIME", "BT_STORAGETYPE", "BT_STORAGECONFIG", "BT_GROUP", "BT_MANUAL", "BT_LINK"});
    private static final String LIST_BUCKET = DBSQLBuilder.buildSelect("OSS_BUCKET", new String[]{"BT_NAME", "BT_OWNER", "BT_OPEN", "BT_DESC", "BT_EXPIRETIME", "BT_STORAGETYPE", "BT_STORAGECONFIG", "BT_GROUP", "BT_MANUAL", "BT_STATUS", "BT_LINK"}, null);
    private static final String T_META_OBJ = "OSS_META_OBJ";
    private static final String F_META_OBJ_BUCKET = "BT_NAME";
    private static final String F_META_OBJ_KEY = "OBJ_KEY";
    private static final String F_META_OBJ_NAME = "OBJ_NAME";
    private static final String F_META_OBJ_OWNER = "OBJ_OWNER";
    private static final String F_META_OBJ_DIR = "OBJ_DIR";
    private static final String F_META_OBJ_CONTENTTYPE = "OBJ_TYPE";
    private static final String F_META_OBJ_EXTENSION = "OBJ_EXTENSION";
    private static final String F_META_OBJ_MD5 = "OBJ_MD5";
    private static final String F_META_OBJ_SIZE = "OBJ_SIZE";
    private static final String F_META_OBJ_CREATETIME = "OBJ_CREATETIME";
    private static final String INSERT_OBJECT = DBSQLBuilder.buildInsert("OSS_META_OBJ", new String[]{"BT_NAME", "OBJ_KEY", "OBJ_NAME", "OBJ_OWNER", "OBJ_DIR", "OBJ_TYPE", "OBJ_EXTENSION", "OBJ_MD5", "OBJ_SIZE", "OBJ_CREATETIME"});
    private static final String SELECT_OBJECT = DBSQLBuilder.buildSelect("OSS_META_OBJ", new String[]{"OBJ_NAME", "OBJ_OWNER", "OBJ_DIR", "OBJ_TYPE", "OBJ_EXTENSION", "OBJ_MD5", "OBJ_SIZE", "OBJ_CREATETIME"}, new String[]{"BT_NAME", "OBJ_KEY"});
    private static final String SELECT_OBJECT_BY_MD5 = DBSQLBuilder.buildSelect("OSS_META_OBJ", new String[]{"OBJ_KEY", "OBJ_NAME", "OBJ_OWNER", "OBJ_DIR", "OBJ_TYPE", "OBJ_EXTENSION", "OBJ_MD5", "OBJ_SIZE", "OBJ_CREATETIME"}, new String[]{"BT_NAME", "OBJ_MD5"});
    private static final String LIST_OBJECT = DBSQLBuilder.buildSelect("OSS_META_OBJ", new String[]{"OBJ_NAME", "OBJ_OWNER", "OBJ_DIR", "OBJ_TYPE", "OBJ_EXTENSION", "OBJ_MD5", "OBJ_SIZE", "OBJ_CREATETIME", "OBJ_KEY"}, new String[]{"BT_NAME"});
    private static final String DELETE_OBJECT = DBSQLBuilder.buildDelete("OSS_META_OBJ", new String[]{"BT_NAME", "OBJ_KEY"});
    private static final String SELECT_EXPIRE_OBJ = "SELECT OBJ_KEY FROM OSS_META_OBJ WHERE BT_NAME=? AND OBJ_CREATETIME<?";
    private static final String GET_OBJ_SIZE = "SELECT COUNT(1) FROM OSS_META_OBJ WHERE BT_NAME=?";
    private static final String T_META_OBJ_LINKOF = "OSS_META_OBJ_LINK";
    private static final String F_META_OBJ_LINKKEY = "LINK_OBJ_KEY";
    private static final String F_META_OBJ_REALKEY = "LINK_REALKEY";
    private static final String F_META_OBJ_DELETE_TAG = "LINK_DEL_TAG";
    private static final String SELECT_REALKEY_BYKEY = DBSQLBuilder.buildSelect("OSS_META_OBJ_LINK", new String[]{"LINK_REALKEY"}, new String[]{"BT_NAME", "LINK_OBJ_KEY"});
    private static final String SELECT_DEADLINK = "SELECT LINK_REALKEY,BT_NAME FROM OSS_META_OBJ_LINK T1 WHERE  LINK_DEL_TAG=1 AND NOT EXISTS(SELECT LINK_REALKEY FROM OSS_META_OBJ_LINK T2 WHERE LINK_DEL_TAG=0 AND T1.LINK_REALKEY=T2.LINK_REALKEY)";
    private static final String INSERT_LINK = DBSQLBuilder.buildInsert("OSS_META_OBJ_LINK", new String[]{"BT_NAME", "LINK_OBJ_KEY", "LINK_REALKEY", "LINK_DEL_TAG"});
    private static final String DELLINK = DBSQLBuilder.buildDelete("OSS_META_OBJ_LINK", new String[]{"BT_NAME", "LINK_OBJ_KEY"});
    private static final String UPDATE_DEL_TAG = "UPDATE OSS_META_OBJ_LINK SET LINK_DEL_TAG=1 WHERE BT_NAME=? AND LINK_OBJ_KEY=?";
    private static final String SELECT_LINK_TAG = DBSQLBuilder.buildSelect("OSS_META_OBJ_LINK", new String[]{"LINK_DEL_TAG"}, new String[]{"BT_NAME", "LINK_OBJ_KEY"});
    private static final String F_OBJ_KEY = "OBJ_KEY";
    private static final String F_OBJ_PNAME = "OBJ_PNAME";
    private static final String F_OBJ_PVALUE = "OBJ_PVALUE";
    private static final String SYS_FIELD_NAME = "name";
    private static final String SYS_FIELD_OWNER = "owner";
    private static final String SYS_FIELD_DIR = "dir";
    private static final String SYS_FIELD_CONTENTTYPE = "contentType";
    private static final String SYS_FIELD_EXTENSION = "extension";
    private static final String SYS_FIELD_MD5 = "md5";
    private static final String SYS_FIELD_SIZE = "size";
    private static final String SYS_FIELD_CREATETIME = "createtime";
    private static final Map<String, IPropertyRecorder> SYS_FIELDS = new HashMap<String, IPropertyRecorder>();
    private IConnectionProvider metaConnProvider;
    private Logger logger = LoggerFactory.getLogger(DBMetaAdapter.class);
    private static boolean sync;
    static String UPDATE_BUCKETCONFIG;
    static String UPDATE_BUCKET_DESC;
    static String OPEN_OBJECT_LINK;
    static String MARK_BUCKETS;
    static String QUERY_LOCK;
    static String DELETE_BUCKET;

    public DBMetaAdapter(IConnectionProvider provider) {
        this.metaConnProvider = provider;
        try {
            this.syncMetadata();
        }
        catch (Exception e) {
            this.logger.error("\u540c\u6b65\u5143\u6570\u636e\u4fe1\u606f\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    @Override
    public void createBucket(Bucket bucket) throws ObjectStorageMetadataException {
        try {
            this.createBucketExtPropTable(bucket.getName());
        }
        catch (ObjectStorageMetadataException e) {
            this.logger.error("\u521b\u5efa{}_m\u5931\u8d25, {}", bucket.getName(), e.getMessage(), e);
        }
        this.insertBucketInfo(bucket);
    }

    private Connection newConnection() throws SQLException {
        return this.metaConnProvider.openConnection();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getObjectExtPropMetaTableName(String bucketName) {
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT BT_NAME FROM OSS_BUCKET WHERE BT_NAME = ?");){
            ps.setString(1, bucketName.toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return "OSS_B_" + bucketName.toUpperCase() + "_M";
            String string = "OSS_B_" + rs.getString("BT_NAME") + "_M";
            return string;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void syncMetadata() throws SQLException, SQLMetadataException, SQLInterpretException {
        if (sync) {
            return;
        }
        try (Connection conn = this.newConnection();){
            IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            this.syncBucketTable(conn, db);
            this.syncBucketObjPropTable(conn, db);
            this.syncBucketObjPropLinkTable(conn, db);
        }
        sync = true;
    }

    private void syncBucketTable(Connection conn, IDatabase db) throws SQLException, SQLMetadataException, SQLInterpretException {
        ISQLMetadata meta = db.createMetadata(conn);
        LogicTable table = meta.getTableByName(T_BUCKET);
        CreateTableStatement statement = this.newBucketLogicTable();
        if (table == null) {
            this.logger.debug("\u7cfb\u7edf\u4e2d\u4e0d\u5b58\u5728\u8868OSS_BUCKET\uff0c\u7a0b\u5e8f\u5c06\u81ea\u52a8\u521b\u5efa\u8868");
            this.executeStatement(conn, db, (SqlStatement)statement);
        } else {
            List fields = meta.getFieldsByTableName(T_BUCKET);
            List all = statement.getColumns();
            HashSet<String> sets = new HashSet<String>();
            for (LogicField f : fields) {
                sets.add(f.getFieldName().toUpperCase());
            }
            for (LogicField f : all) {
                if (sets.contains(f.getFieldName().toUpperCase())) continue;
                this.logger.debug("OSS_BUCKET\u7f3a\u5c11\u5b57\u6bb5\u3010" + f.getFieldName() + "\u3011\uff0c\u7a0b\u5e8f\u5c06\u81ea\u52a8\u6dfb\u52a0");
                AlterColumnStatement acs = new AlterColumnStatement(T_BUCKET, AlterType.ADD);
                acs.setNewColumn(f);
                this.executeStatement(conn, db, (SqlStatement)acs);
            }
        }
    }

    private void syncBucketObjPropTable(Connection conn, IDatabase db) throws SQLException, SQLMetadataException, SQLInterpretException {
        ISQLMetadata meta = db.createMetadata(conn);
        LogicTable table = meta.getTableByName(T_META_OBJ);
        if (table == null) {
            CreateTableStatement statement = this.newBucketObjPropTable();
            this.executeStatement(conn, db, (SqlStatement)statement);
            CreateIndexStatement nameIdx = this.genMetaObjTableObjNameFieldIndex();
            this.executeStatement(conn, db, (SqlStatement)nameIdx);
            CreateIndexStatement md5Idx = this.genMetaObjTableMD5FieldIndex();
            this.executeStatement(conn, db, (SqlStatement)md5Idx);
            return;
        }
        List indexList = meta.getIndexesByTableName(T_META_OBJ);
        String objNameFieldIndexName = "IDX1_OSS_META_OBJ";
        String md5FieldIndexName = "IDX2_OSS_META_OBJ";
        if (indexList.stream().noneMatch(idx -> "IDX2_OSS_META_OBJ".equalsIgnoreCase(idx.getIndexName()))) {
            CreateIndexStatement nameIdx = this.genMetaObjTableObjNameFieldIndex();
            this.executeStatement(conn, db, (SqlStatement)nameIdx);
        }
        if (indexList.stream().noneMatch(idx -> "IDX1_OSS_META_OBJ".equalsIgnoreCase(idx.getIndexName()))) {
            CreateIndexStatement md5Idx = this.genMetaObjTableMD5FieldIndex();
            this.executeStatement(conn, db, (SqlStatement)md5Idx);
        }
    }

    private CreateIndexStatement genMetaObjTableObjNameFieldIndex() {
        CreateIndexStatement nameIdx = new CreateIndexStatement(null, "IDX1_OSS_META_OBJ");
        nameIdx.setTableName(T_META_OBJ);
        nameIdx.addIndexColumn(F_META_OBJ_NAME);
        return nameIdx;
    }

    private CreateIndexStatement genMetaObjTableMD5FieldIndex() {
        CreateIndexStatement md5Idx = new CreateIndexStatement(null, "IDX2_OSS_META_OBJ");
        md5Idx.setTableName(T_META_OBJ);
        md5Idx.addIndexColumn(F_META_OBJ_MD5);
        return md5Idx;
    }

    private void syncBucketObjPropLinkTable(Connection conn, IDatabase db) throws SQLException, SQLMetadataException, SQLInterpretException {
        ISQLMetadata meta = db.createMetadata(conn);
        LogicTable table = meta.getTableByName(T_META_OBJ_LINKOF);
        if (table == null) {
            CreateTableStatement statement = this.newBucketObjPropLinkTable();
            this.executeStatement(conn, db, (SqlStatement)statement);
        }
    }

    private CreateTableStatement newBucketLogicTable() {
        CreateTableStatement cts = new CreateTableStatement("", T_BUCKET);
        cts.addColumn(this.newField("BT_NAME", 6, "\u540d\u79f0", false, 20, null));
        cts.addColumn(this.newField(F_BT_OWNER, 6, "\u6240\u6709\u8005", true, 100, null));
        cts.addColumn(this.newField(F_BT_DESC, 6, "bucket\u7684\u63cf\u8ff0\u4fe1\u606f", true, 500, null));
        cts.addColumn(this.newField(F_BT_EXPIRETIME, 5, "bucket\u4e2d\u5bf9\u8c61\u5931\u6548\u65f6\u95f4", true, 10, "-1"));
        cts.addColumn(this.newField(F_BT_OPEN, 5, "\u662f\u5426\u5141\u8bb8\u901a\u8fc7\u670d\u52a1\u8bbf\u95ee", true, 1, "0"));
        cts.addColumn(this.newField(F_BT_STATUS, 6, "bucket\u7684\u72b6\u6001", true, 2, "rw"));
        cts.addColumn(this.newField(F_BT_CREATETIME, 6, "\u521b\u5efa\u65f6\u95f4", true, 20, null));
        cts.addColumn(this.newField(F_BT_STORAGETYPE, 6, "bucket\u7684\u5b58\u50a8\u4ecb\u8d28\u7c7b\u578b", true, 50, null));
        cts.addColumn(this.newField(F_BT_STORAGECONFIG, 6, "bucket\u7684\u5b58\u50a8\u4ecb\u8d28\u914d\u7f6e\u9879", true, 500, null));
        cts.addColumn(this.newField(F_BT_GROUP, 6, "bucket\u6240\u5c5e\u5206\u7ec4", true, 50, null));
        cts.addColumn(this.newField(F_BT_MANUAL, 5, "\u901a\u8fc7\u624b\u52a8\u65b9\u5f0f\u521b\u5efa\u7684bucket", true, 1, "0"));
        cts.addColumn(this.newField(F_BT_JOBID, 6, "\u8fc1\u79fb\u4efb\u52a1 id", true, 50, null));
        cts.addColumn(this.newField(F_BT_LINK, 5, "\u662f\u5426\u542f\u7528\u6587\u4ef6\u8f6f\u94fe\u63a5", true, 1, "0"));
        cts.getPrimaryKeys().add("BT_NAME");
        return cts;
    }

    private CreateTableStatement newBucketObjPropTable() {
        CreateTableStatement cts = new CreateTableStatement("", T_META_OBJ);
        cts.addColumn(this.newField("BT_NAME", 6, "\u6240\u5c5e\u6876\u7684\u540d\u79f0", false, 20, null));
        cts.addColumn(this.newField("OBJ_KEY", 6, "\u5bf9\u8c61\u7684key", false, 200, null));
        cts.addColumn(this.newField(F_META_OBJ_NAME, 6, "\u5bf9\u8c61\u7684\u540d\u79f0", true, 200, null));
        cts.addColumn(this.newField(F_META_OBJ_MD5, 6, "\u5bf9\u8c61\u7684MD5\u503c", false, 150, null));
        cts.addColumn(this.newField(F_META_OBJ_SIZE, 5, "\u5bf9\u8c61\u7684\u5b57\u8282\u6570", false, 10, "0"));
        cts.addColumn(this.newField(F_META_OBJ_CREATETIME, 6, "\u5bf9\u8c61\u7684\u521b\u5efa\u65f6\u95f4", false, 20, null));
        cts.addColumn(this.newField(F_META_OBJ_OWNER, 6, "\u5bf9\u8c61\u7684\u6240\u6709\u8005", true, 200, null));
        cts.addColumn(this.newField(F_META_OBJ_DIR, 6, "\u5bf9\u8c61\u6240\u5c5e\u76ee\u5f55", true, 200, null));
        cts.addColumn(this.newField(F_META_OBJ_CONTENTTYPE, 6, "\u8d44\u6e90\u7c7b\u578b", true, 50, null));
        cts.addColumn(this.newField(F_META_OBJ_EXTENSION, 6, "\u8d44\u6e90\u6269\u5c55\u540d", true, 50, null));
        cts.getPrimaryKeys().add("BT_NAME");
        cts.getPrimaryKeys().add("OBJ_KEY");
        return cts;
    }

    private CreateTableStatement newBucketObjPropLinkTable() {
        CreateTableStatement cts = new CreateTableStatement("", T_META_OBJ_LINKOF);
        cts.addColumn(this.newField("BT_NAME", 6, "\u6240\u5c5e\u6876\u7684\u540d\u79f0", false, 20, null));
        cts.addColumn(this.newField(F_META_OBJ_LINKKEY, 6, "\u5bf9\u8c61\u7684key", false, 200, null));
        cts.addColumn(this.newField(F_META_OBJ_REALKEY, 6, "\u5bf9\u8c61\u5b9e\u9645\u5b58\u50a8\u8d44\u6e90\u5bf9\u5e94\u7684key", false, 200, null));
        cts.addColumn(this.newField(F_META_OBJ_DELETE_TAG, 5, "\u5220\u9664\u6807\u8bb0\u4f4d", true, 1, "0"));
        cts.getPrimaryKeys().add("BT_NAME");
        cts.getPrimaryKeys().add(F_META_OBJ_LINKKEY);
        return cts;
    }

    private void executeStatement(Connection conn, IDatabase db, SqlStatement statement) throws SQLInterpretException, SQLException {
        List sqls = statement.interpret(db, conn);
        for (String sql : sqls) {
            PreparedStatement ps = conn.prepareStatement(sql);
            Throwable throwable = null;
            try {
                ps.executeUpdate();
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (ps == null) continue;
                if (throwable != null) {
                    try {
                        ps.close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    continue;
                }
                ps.close();
            }
        }
    }

    private LogicField newField(String name, int dataType, String title, boolean nullable, int size, String defaultValue) {
        LogicField f = new LogicField();
        f.setFieldName(name);
        f.setDataType(dataType);
        f.setFieldTitle(title);
        f.setNullable(nullable);
        f.setSize(size);
        f.setDefaultValue(defaultValue);
        return f;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void insertBucketInfo(Bucket bucket) throws ObjectStorageMetadataException {
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_BUCKET);){
            ps.setString(1, bucket.getName().toUpperCase());
            ps.setString(2, bucket.getOwner());
            ps.setInt(3, bucket.isOpen() ? 1 : 0);
            ps.setString(4, bucket.getDesc());
            ps.setString(5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            ps.setInt(6, bucket.getExpireTime());
            ps.setString(7, bucket.getStorageType());
            ps.setString(8, bucket.getStorageConfig());
            ps.setString(9, bucket.getGroup());
            ps.setInt(10, bucket.isManual() ? 1 : 0);
            ps.setInt(11, bucket.isLinkWhenExist() ? 1 : 0);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void createBucketExtPropTable(String bucketName) throws ObjectStorageMetadataException {
        String metaTableName = this.getObjectExtPropMetaTableName(bucketName);
        CreateTableStatement cts = new CreateTableStatement("", metaTableName);
        cts.setJudgeExists(true);
        LogicField keyField = this.newField("OBJ_KEY", 6, "\u5bf9\u8c61\u4e3b\u952e", false, 200, null);
        cts.addColumn(keyField);
        LogicField propNameField = this.newField(F_OBJ_PNAME, 6, "\u5c5e\u6027\u7684\u540d\u79f0", false, 20, null);
        cts.addColumn(propNameField);
        LogicField propValField = this.newField(F_OBJ_PVALUE, 6, "\u5c5e\u6027\u7684\u503c", false, 500, null);
        cts.addColumn(propValField);
        cts.getPrimaryKeys().add("OBJ_KEY");
        cts.getPrimaryKeys().add(F_OBJ_PNAME);
        CreateIndexStatement propIdx = new CreateIndexStatement(null, "IDX_P_" + metaTableName);
        propIdx.setTableName(metaTableName);
        propIdx.addIndexColumn(F_OBJ_PNAME);
        propIdx.addIndexColumn(F_OBJ_PVALUE);
        try (Connection conn = this.newConnection();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            this.executeStatement(conn, database, (SqlStatement)cts);
            this.executeStatement(conn, database, (SqlStatement)propIdx);
        }
        catch (SQLInterpretException e) {
            throw new ObjectStorageMetadataException("\u6784\u9020\u5efa\u8868\u8bed\u53e5\u5931\u8d25", e);
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteBucket(String bucketName) throws ObjectStorageMetadataException {
        String dataTableName = this.getObjectExtPropMetaTableName(bucketName);
        String dropDataSql = "drop table " + dataTableName;
        try (Connection conn = this.newConnection();){
            try (PreparedStatement ps = conn.prepareStatement(DELETE_BUCKET);){
                ps.setString(1, bucketName);
                ps.executeUpdate();
            }
            ps = conn.prepareStatement(dropDataSql);
            try {
                ps.executeUpdate();
            }
            finally {
                ps.close();
            }
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException("\u5220\u9664bucket\u5931\u8d25", e);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public Bucket getBucket(String bucketName) throws ObjectStorageMetadataException {
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

    @Override
    public boolean existBucket(String bucketName) throws ObjectStorageMetadataException {
        return this.getBucket(bucketName) != null;
    }

    @Override
    public void updateBucketConfig(Bucket bucket) throws ObjectStorageMetadataException {
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_BUCKETCONFIG);){
            ps.setString(1, bucket.getStorageType());
            ps.setString(2, bucket.getStorageConfig());
            ps.setString(3, bucket.getName());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException(e.getMessage(), e);
        }
    }

    @Override
    public void updateBucketDesc(String bucketName, String desc) throws ObjectStorageMetadataException {
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_BUCKET_DESC);){
            ps.setString(1, desc);
            ps.setString(2, bucketName);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException(e.getMessage(), e);
        }
    }

    @Override
    public void makeObjectLinkEnable(String bucketName) throws ObjectStorageMetadataException {
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(OPEN_OBJECT_LINK);){
            ps.setString(1, bucketName);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void markBucketStatus(String bucketName, String status) throws ObjectStorageMetadataException {
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(MARK_BUCKETS);){
            ps.setString(1, status);
            ps.setString(2, bucketName);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException(e.getMessage(), e);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public boolean isBucketLocked(String bucketName) throws ObjectStorageMetadataException {
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
     * Exception decompiling
     */
    @Override
    public List<Bucket> listBucket() throws ObjectStorageMetadataException {
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchModifyObjectProp(String bucketName, List<String> objKeys, String propName, String propVal) throws ObjectStorageMetadataException {
        boolean updated = false;
        if (SYS_FIELDS.containsKey(propName)) {
            StringBuilder buf = new StringBuilder();
            buf.append("UPDATE ").append(T_META_OBJ).append(" SET ").append(SYS_FIELDS.get(propName).getFieldName()).append("=? WHERE ");
            buf.append("BT_NAME").append("=? AND ");
            if (objKeys.size() > 1) {
                buf.append("(");
                for (int i = 0; i < objKeys.size(); ++i) {
                    if (i != 0) {
                        buf.append(" or ");
                    }
                    buf.append("OBJ_KEY").append("=?");
                }
                buf.append(")");
            } else {
                buf.append("OBJ_KEY").append("=?");
            }
            try (Connection conn = this.newConnection();
                 PreparedStatement ps = conn.prepareStatement(buf.toString());){
                ps.setString(1, propVal);
                ps.setString(2, bucketName);
                for (int i = 0; i < objKeys.size(); ++i) {
                    ps.setString(3 + i, objKeys.get(i));
                }
                updated = ps.executeUpdate() > 0;
            }
            catch (SQLException e) {
                throw new ObjectStorageMetadataException(e.getMessage(), e);
            }
        }
        if (!updated) {
            String tableName = this.getObjectExtPropMetaTableName(bucketName);
            StringBuilder buf = new StringBuilder();
            buf.append("UPDATE ").append(tableName).append(" SET ").append(F_OBJ_PVALUE).append("=? ");
            buf.append(" WHERE ").append(F_OBJ_PNAME).append("=? AND ");
            if (objKeys.size() > 1) {
                buf.append("(");
                for (int i = 0; i < objKeys.size(); ++i) {
                    if (i != 0) {
                        buf.append(" or ");
                    }
                    buf.append("OBJ_KEY").append("=?");
                }
                buf.append(")");
            } else {
                buf.append("OBJ_KEY").append("=?");
            }
            String sql = buf.toString();
            this.logger.debug(sql);
            try (Connection conn = this.newConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);){
                ps.setString(1, propVal);
                ps.setString(2, propName);
                for (int i = 0; i < objKeys.size(); ++i) {
                    ps.setString(3 + i, objKeys.get(i));
                }
                ps.executeUpdate();
            }
            catch (SQLException e) {
                throw new ObjectStorageMetadataException(e.getMessage(), e);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<ObjectInfo> find(String bucketName, ObjectFilterCondition cond, ObjectOrderField orderField) throws ObjectStorageMetadataException {
        List<Object> values;
        StringBuffer buf;
        block135: {
            Throwable throwable;
            String tempTableName;
            boolean useTempTable;
            List<Object> values2;
            StringBuffer buf2;
            ArrayList<ObjectInfo> list;
            block130: {
                block134: {
                    block133: {
                        if (!SYS_FIELDS.containsKey(cond.getPropName())) break block133;
                        list = new ArrayList<ObjectInfo>();
                        buf2 = new StringBuffer();
                        buf2.append("SELECT * FROM ").append(T_META_OBJ).append(" WHERE ").append("BT_NAME").append("=? AND ");
                        values2 = cond.getValue();
                        buf2.append("(");
                        useTempTable = false;
                        tempTableName = "";
                        if (!cond.isExact()) break block134;
                        if (values2.size() < 1000) {
                            buf2.append(SYS_FIELDS.get(cond.getPropName()).getFieldName()).append(" in ( ");
                            buf2.append(String.join((CharSequence)", ", Collections.nCopies(values2.size(), "?")));
                            buf2.append(" ) ");
                            break block130;
                        } else {
                            try {
                                throwable = null;
                                try (Connection conn = this.newConnection();){
                                    tempTableName = this.generateTempTableName();
                                    useTempTable = this.createTempTable(conn, tempTableName);
                                    if (!useTempTable) break block130;
                                    this.insertIntoTempTable(conn, tempTableName, values2);
                                    buf2.append(String.format(" EXISTS(select code from %s t where %s = t.code) ", tempTableName, SYS_FIELDS.get(cond.getPropName()).getFieldName()));
                                    break block130;
                                }
                                catch (Throwable throwable2) {
                                    throwable = throwable2;
                                    throw throwable2;
                                }
                            }
                            catch (SQLException e) {
                                this.logger.error(e.getLocalizedMessage(), e);
                            }
                        }
                        break block130;
                    }
                    String tableName = this.getObjectExtPropMetaTableName(bucketName);
                    buf = new StringBuffer();
                    buf.append("SELECT ").append("OBJ_KEY").append(" FROM ").append(tableName).append(" s ").append(" WHERE ");
                    buf.append("s.").append(F_OBJ_PNAME).append("=? AND ");
                    buf.append("(");
                    values = cond.getValue();
                    break block135;
                }
                for (int i = 0; i < values2.size(); ++i) {
                    if (i != 0) {
                        buf2.append(" or ");
                    }
                    buf2.append(SYS_FIELDS.get(cond.getPropName()).getFieldName()).append(cond.isExact() ? "=?" : " like ?");
                }
            }
            buf2.append(")");
            if (orderField != null) {
                buf2.append(" ORDER BY ").append(orderField.getFieldName()).append(" ").append(orderField.getOrderType());
            }
            try {
                throwable = null;
                try (Connection conn = this.newConnection();
                     PreparedStatement ps = conn.prepareStatement(buf2.toString());){
                    ps.setString(1, bucketName);
                    for (int i = 0; i < values2.size(); ++i) {
                        ps.setString(2 + i, cond.isExact() ? values2.get(i).toString() : "%" + values2.get(i) + "%");
                    }
                    try (ResultSet rs = ps.executeQuery();){
                        while (rs.next()) {
                            ObjectInfo info = new ObjectInfo();
                            info.setKey(rs.getString("OBJ_KEY"));
                            info.setName(rs.getString(F_META_OBJ_NAME));
                            info.setOwner(rs.getString(F_META_OBJ_OWNER));
                            info.setSize(rs.getLong(F_META_OBJ_SIZE));
                            info.setContentType(rs.getString(F_META_OBJ_CONTENTTYPE));
                            info.setCreateTime(rs.getString(F_META_OBJ_CREATETIME));
                            info.setDir(rs.getString(F_META_OBJ_DIR));
                            info.setExtension(rs.getString(F_META_OBJ_EXTENSION));
                            info.setMd5(rs.getString(F_META_OBJ_MD5));
                            ObjectInfo ext = this._getObjectExtInfo(bucketName, info.getKey());
                            if (ext != null) {
                                info.getExtProp().putAll(ext.getExtProp());
                            }
                            list.add(info);
                        }
                    }
                }
                catch (Throwable throwable3) {
                    throwable = throwable3;
                    throw throwable3;
                }
            }
            catch (SQLException e) {
                throw new ObjectStorageMetadataException(e.getMessage(), e);
            }
            finally {
                if (useTempTable && StringUtils.isNotEmpty((String)tempTableName)) {
                    try (Connection conn = this.newConnection();){
                        this.dropTempTable(conn, tempTableName);
                    }
                    catch (SQLException e) {
                        this.logger.error(e.getLocalizedMessage(), e);
                    }
                }
            }
            HashSet set = new HashSet();
            list.forEach(c -> set.add(c.getKey()));
            List<ObjectInfo> old = this._findObjectByProp(bucketName, cond, orderField);
            old.stream().filter(c -> !set.contains(c.getKey())).forEach(list::add);
            return list;
        }
        for (int i = 0; i < values.size(); ++i) {
            if (i != 0) {
                buf.append(" or ");
            }
            buf.append("s.").append(F_OBJ_PVALUE).append(cond.isExact() ? "=?" : " like ?");
        }
        buf.append(")");
        ArrayList<ObjectInfo> list = new ArrayList<ObjectInfo>();
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(buf.toString());){
            ps.setString(1, cond.getPropName());
            for (int i = 0; i < values.size(); ++i) {
                ps.setString(2, cond.isExact() ? values.get(i).toString() : "%" + values.get(i) + "%");
            }
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    String key = rs.getString(1);
                    ObjectInfo info = this.getObjectInfo(bucketName, key);
                    if (info == null) continue;
                    list.add(info);
                }
                return list;
            }
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive exception aggregation
     */
    private List<ObjectInfo> _findObjectByProp(String bucketName, ObjectFilterCondition cond, ObjectOrderField orderField) throws ObjectStorageMetadataException {
        int i;
        String tableName = this.getObjectExtPropMetaTableName(bucketName);
        if (cond.getPropName().equals("OBJ_KEY")) {
            if (!cond.isExact()) {
                throw new ObjectStorageMetadataException("\u5bf9\u8c61\u6309\u7167key\u65b9\u5f0f\u67e5\u627e\uff0c\u4e0d\u652f\u6301\u6a21\u7cca\u5339\u914d");
            }
            ArrayList<ObjectInfo> list = new ArrayList<ObjectInfo>();
            for (Object v : cond.getValue()) {
                ObjectInfo info = this._getObjectExtInfo(bucketName, v.toString());
                if (info == null) continue;
                list.add(info);
            }
            return list;
        }
        StringBuilder basic = new StringBuilder();
        basic.append("SELECT ").append("OBJ_KEY").append(", ").append(F_OBJ_PNAME).append(", ").append(F_OBJ_PVALUE);
        basic.append(" FROM ").append(tableName).append(" t WHERE ").append("OBJ_KEY").append(" IN (");
        basic.append("SELECT ").append("OBJ_KEY").append(" FROM ").append(tableName).append(" s ").append(" WHERE ");
        List<Object> values = cond.getValue();
        if (cond.isExact()) {
            basic.append("s.").append(F_OBJ_PNAME).append("=? and (");
            for (i = 0; i < values.size(); ++i) {
                if (i != 0) {
                    basic.append(" or ");
                }
                basic.append("s.").append(F_OBJ_PVALUE).append("=?");
            }
            basic.append(")");
        } else {
            basic.append("s.").append(F_OBJ_PNAME).append("=? and (");
            for (i = 0; i < values.size(); ++i) {
                if (i != 0) {
                    basic.append(" or ");
                }
                basic.append("s.").append(F_OBJ_PVALUE).append(" like ?");
            }
            basic.append(")");
        }
        basic.append(")");
        HashMap<String, ObjectInfo> map = new HashMap<String, ObjectInfo>();
        try (Connection conn = this.newConnection();){
            ArrayList<ObjectInfo> arrayList;
            String sql;
            if (orderField == null) {
                sql = basic.toString();
            } else {
                StringBuilder buf = new StringBuilder();
                IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                if (db.getDescriptor().supportWithClause()) {
                    buf.append("WITH BTQ AS (").append((CharSequence)basic).append(")\r\n");
                    buf.append(" SELECT ").append("OBJ_KEY").append(", ").append(F_OBJ_PNAME).append(", ").append(F_OBJ_PVALUE);
                    buf.append(" FROM BTQ bc WHERE bc.").append(F_OBJ_PNAME).append("='").append(orderField.getFieldName()).append("'");
                    buf.append(" ORDER BY ").append(orderField.getFieldName()).append(" ").append(orderField.getOrderType());
                    buf.append(" union all ");
                    buf.append(" SELECT ").append("OBJ_KEY").append(", ").append(F_OBJ_PNAME).append(", ").append(F_OBJ_PVALUE);
                    buf.append(" FROM BTQ bq WHERE bq.").append(F_OBJ_PNAME).append("<>'").append(orderField.getFieldName()).append("'");
                } else {
                    buf.append(" SELECT ").append("OBJ_KEY").append(", ").append(F_OBJ_PNAME).append(", ").append(F_OBJ_PVALUE);
                    buf.append(" FROM (").append((CharSequence)basic).append(") bc WHERE bc.").append(F_OBJ_PNAME).append("='").append(orderField.getFieldName()).append("'");
                    buf.append(" ORDER BY ").append(orderField.getFieldName()).append(" ").append(orderField.getOrderType());
                    buf.append(" union all ");
                    buf.append((CharSequence)basic);
                }
                sql = buf.toString();
            }
            this.logger.debug(sql);
            PreparedStatement ps = conn.prepareStatement(sql);
            try {
                ps.setString(1, cond.getPropName());
                for (int i2 = 0; i2 < values.size(); ++i2) {
                    ps.setString(2 + i2, cond.isExact() ? values.get(i2).toString() : "%" + values.get(i2) + "%");
                }
                ResultSet rs = ps.executeQuery();
                ArrayList<ObjectInfo> list = new ArrayList<ObjectInfo>();
                while (rs.next()) {
                    String key = rs.getString(1);
                    ObjectInfo v = (ObjectInfo)map.get(key);
                    if (v == null) {
                        v = new ObjectInfo(key);
                        map.put(key, v);
                        list.add(v);
                    }
                    this.packageInfo(v, rs);
                }
                arrayList = list;
            }
            catch (Throwable throwable) {
                ps.close();
                throw throwable;
            }
            ps.close();
            return arrayList;
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addObjectInfo(String bucketName, ObjectInfo info) throws ObjectStorageMetadataException {
        block14: {
            try (Connection conn = this.newConnection();){
                try (PreparedStatement ps = conn.prepareStatement(INSERT_OBJECT);){
                    ps.setString(1, bucketName);
                    ps.setString(2, info.getKey());
                    ps.setString(3, info.getName());
                    ps.setString(4, info.getOwner());
                    ps.setString(5, info.getDir());
                    ps.setString(6, info.getContentType());
                    ps.setString(7, info.getExtension());
                    ps.setString(8, info.getMd5());
                    ps.setLong(9, info.getSize());
                    ps.setString(10, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    ps.executeUpdate();
                }
                if (info.getExtProp().isEmpty()) break block14;
                String tableName = this.getObjectExtPropMetaTableName(bucketName);
                String insertSql = DBSQLBuilder.buildInsert(tableName, new String[]{"OBJ_KEY", F_OBJ_PNAME, F_OBJ_PVALUE});
                ps = conn.prepareStatement(insertSql);
                try {
                    ps.setString(1, info.getKey());
                    Map<String, String> exts = info.getExtProp();
                    if (exts.size() > 0) {
                        for (Map.Entry<String, String> entry : exts.entrySet()) {
                            ps.setString(2, entry.getKey());
                            ps.setString(3, entry.getValue());
                            ps.addBatch();
                        }
                    }
                    ps.executeBatch();
                }
                finally {
                    ps.close();
                }
            }
            catch (SQLException e) {
                throw new ObjectStorageMetadataException("\u63d2\u5165\u5bf9\u8c61\u7684\u5143\u6570\u636e\u4fe1\u606f\u51fa\u9519", e);
            }
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public ObjectInfo getObjectInfo(String bucketName, String key) throws ObjectStorageMetadataException {
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

    /*
     * Exception decompiling
     */
    private ObjectInfo _getObjectExtInfo(String bucketName, String key) throws ObjectStorageMetadataException {
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

    private void packageInfo(ObjectInfo info, ResultSet rs) throws SQLException {
        String k = rs.getString(2);
        String v = rs.getString(3);
        IPropertyRecorder recorder = SYS_FIELDS.get(k);
        if (recorder == null) {
            info.getExtProp().put(k, v);
        } else {
            recorder.setValue(info, v);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getObjectSize(String bucketName) throws ObjectStorageMetadataException {
        int size = 0;
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(GET_OBJ_SIZE);){
            ps.setString(1, bucketName);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    size = rs.getInt(1);
                }
            }
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException("\u83b7\u53d6\u5bf9\u8c61\u8bb0\u5f55\u6570\u5931\u8d25", e);
        }
        return size += this._getObjectExtPropSize(bucketName);
    }

    /*
     * Exception decompiling
     */
    private int _getObjectExtPropSize(String bucketName) throws ObjectStorageMetadataException {
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
     * Exception decompiling
     */
    @Override
    public String findObjectStoreKeyByObjKey(String bucketName, String objKey) throws ObjectStorageMetadataException {
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
    public String findObjectStoreKeyByMd5(String bucketName, String md5, long size) throws ObjectStorageMetadataException {
        ObjectInfo info = null;
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_OBJECT_BY_MD5);){
            ps.setString(1, bucketName);
            ps.setString(2, md5);
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    info = new ObjectInfo();
                    info.setKey(rs.getString(1));
                    info.setName(rs.getString(2));
                    info.setOwner(rs.getString(3));
                    info.setDir(rs.getString(4));
                    info.setContentType(rs.getString(5));
                    info.setExtension(rs.getString(6));
                    info.setMd5(rs.getString(7));
                    info.setSize(rs.getLong(8));
                    info.setCreateTime(rs.getString(9));
                    if (info.getSize() == size) {
                        break;
                    }
                    info = null;
                }
            }
        }
        catch (Exception e) {
            throw new ObjectStorageMetadataException(e.getMessage(), e);
        }
        if (info == null) {
            return null;
        }
        return this.findObjectStoreKeyByObjKey(bucketName, info.getKey());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ObjectInfo> listObject(String bucketName, int startRow, int pageSize) throws ObjectStorageMetadataException {
        ArrayList<ObjectInfo> list = new ArrayList<ObjectInfo>();
        try (Connection conn = this.newConnection();){
            String selectSql = LIST_OBJECT;
            if (pageSize > 0) {
                IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                IPagingSQLBuilder pageBuilder = database.createPagingSQLBuilder();
                pageBuilder.setRawSQL(LIST_OBJECT);
                selectSql = pageBuilder.buildSQL(startRow, startRow + pageSize);
            }
            try (PreparedStatement ps = conn.prepareStatement(selectSql);){
                ps.setString(1, bucketName);
                try (ResultSet rs = ps.executeQuery();){
                    while (rs.next()) {
                        ObjectInfo info = new ObjectInfo();
                        info.setName(rs.getString(1));
                        info.setOwner(rs.getString(2));
                        info.setDir(rs.getString(3));
                        info.setContentType(rs.getString(4));
                        info.setExtension(rs.getString(5));
                        info.setMd5(rs.getString(6));
                        info.setSize(rs.getLong(7));
                        info.setCreateTime(rs.getString(8));
                        info.setKey(rs.getString(9));
                        list.add(info);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new ObjectStorageMetadataException(e.getMessage(), e);
        }
        List<String> keys = null;
        if (pageSize <= 0) {
            keys = this._listObjectExtKeys(bucketName, 0, 0x7FFFFFFE);
        } else if (list.size() == 0) {
            keys = this._listObjectExtKeys(bucketName, startRow, pageSize);
        } else if (list.size() < pageSize) {
            keys = this._listObjectExtKeys(bucketName, startRow, pageSize - list.size());
        }
        if (keys != null) {
            for (String key : keys) {
                list.add(this._getObjectExtInfo(bucketName, key));
            }
        }
        return list;
    }

    /*
     * Exception decompiling
     */
    private List<String> _listObjectExtKeys(String bucketName, int startRow, int pageSize) throws ObjectStorageMetadataException {
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
    public boolean existObject(String bucketName, String key) throws ObjectStorageMetadataException {
        return this.getObjectInfo(bucketName, key) != null;
    }

    @Override
    public void addObjectLink(String bucketName, String objKey, String realKey) throws ObjectStorageMetadataException {
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_LINK);){
            ps.setString(1, bucketName);
            ps.setString(2, objKey);
            ps.setString(3, realKey);
            ps.setInt(4, 0);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException("\u6dfb\u52a0\u5bf9\u8c61\u5931\u8d25", e);
        }
    }

    @Override
    public boolean deleteObject(String bucketName, String key) throws ObjectStorageMetadataException {
        boolean contain = false;
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_OBJECT);){
            ps.setString(1, bucketName);
            ps.setString(2, key);
            contain = ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException("\u5220\u9664\u5bf9\u8c61\u5931\u8d25", e);
        }
        int size = this._deleteObject(bucketName, Arrays.asList(key));
        return contain || size > 0;
    }

    @Override
    public int batchDeleteObject(String bucketName, List<String> keys) throws ObjectStorageMetadataException {
        StringBuffer buf = new StringBuffer();
        buf.append("DELETE FROM ").append(T_META_OBJ).append(" WHERE ").append("BT_NAME").append("=? AND ");
        buf.append("OBJ_KEY");
        this.appendKeyListToWhereSQL(buf, keys);
        String del_sql = buf.toString();
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(del_sql);){
            ps.setString(1, bucketName);
            for (int i = 0; i < keys.size(); ++i) {
                ps.setString(2 + i, keys.get(i));
            }
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException("\u5220\u9664\u5bf9\u8c61\u5931\u8d25", e);
        }
        return this._deleteObject(bucketName, keys);
    }

    /*
     * Exception decompiling
     */
    @Override
    public int batchDeleteObjectLink(String bucketName, List<String> keys) throws ObjectStorageMetadataException {
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

    /*
     * Exception decompiling
     */
    @Override
    public int getObjectLinkTag(String bucketName, String objKey) throws ObjectStorageMetadataException {
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

    /*
     * Exception decompiling
     */
    @Override
    public boolean deleteObjectLink(String bucketName, String key) throws ObjectStorageMetadataException {
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
    public void updateObjectLinkDelTag(String bucketName, String objKey) throws ObjectStorageMetadataException {
        try (Connection conn = this.newConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_DEL_TAG);){
            ps.setString(1, bucketName);
            ps.setString(2, objKey);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException("\u66f4\u65b0\u5bf9\u8c61\u6807\u8bb0\u4f4d\u5931\u8d25", e);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public Map<String, String> getDeadObjectLinks() throws ObjectStorageMetadataException {
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
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive exception aggregation
     */
    private int _deleteObject(String bucketName, List<String> keys) throws ObjectStorageMetadataException {
        String tableName = this.getObjectExtPropMetaTableName(bucketName);
        StringBuffer buf = new StringBuffer();
        buf.append("delete from ").append(tableName).append(" where ").append("OBJ_KEY");
        this.appendKeyListToWhereSQL(buf, keys);
        String deleteMetaSql = buf.toString();
        try (Connection conn = this.newConnection();){
            int n;
            PreparedStatement ps = conn.prepareStatement(deleteMetaSql);
            try {
                for (int i = 0; i < keys.size(); ++i) {
                    ps.setString(1 + i, keys.get(i));
                }
                n = ps.executeUpdate();
            }
            catch (Throwable throwable) {
                ps.close();
                throw throwable;
            }
            ps.close();
            return n;
        }
        catch (SQLException e) {
            throw new ObjectStorageMetadataException("\u5220\u9664\u5bf9\u8c61\u5931\u8d25", e);
        }
    }

    private void appendKeyListToWhereSQL(StringBuffer buf, List<String> keys) {
        if (keys.size() > 1) {
            buf.append(" in (");
            for (int i = 0; i < keys.size(); ++i) {
                if (i != 0) {
                    buf.append(",");
                }
                buf.append("?");
            }
            buf.append(")");
        } else {
            buf.append("=?");
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public List<String> getExpireObjectKeys(String bucketName) throws ObjectStorageMetadataException {
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

    private String generateTempTableName() {
        Random rand = new Random();
        int tableIndex = rand.nextInt(10000);
        String tableName = OrderGenerator.newOrder();
        return String.format("TMP_%s_%s", tableName, tableIndex);
    }

    private boolean createTempTable(Connection connection, String tableName) {
        try {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("CREATE TABLE ").append(tableName).append(" (");
            sqlBuilder.append("CODE").append(" ");
            sqlBuilder.append("VARCHAR(256)");
            sqlBuilder.append(" NOT NULL,");
            sqlBuilder.append("CONSTRAINT PK_").append(tableName).append(" PRIMARY KEY (CODE)");
            sqlBuilder.append(");");
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            SQLParser sqlParser = new SQLParser();
            List statements = sqlParser.parse(sqlBuilder.toString());
            for (SqlStatement statement : statements) {
                List sqls = statement.interpret(database, connection);
                for (String sql : sqls) {
                    PreparedStatement prep = connection.prepareStatement(sql);
                    Throwable throwable = null;
                    try {
                        prep.execute();
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (prep == null) continue;
                        if (throwable != null) {
                            try {
                                prep.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        prep.close();
                    }
                }
            }
            return true;
        }
        catch (SQLInterpretException | SQLParserException | SQLException e) {
            this.logger.error("\u521b\u5efa\u4e34\u65f6\u8868\u51fa\u9519\uff01 " + e.getMessage(), e);
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void insertIntoTempTable(Connection connection, String tableName, List<?> filterValues) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" Insert Into ").append(tableName).append("(");
        insertSql.append("CODE");
        insertSql.append(")").append(" values (?)");
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (Object filterValue : filterValues) {
            Object[] batchArray = new Object[1];
            Object resultValue = filterValue;
            batchArray[0] = filterValue;
            batchValues.add(batchArray);
        }
        PreparedStatement prep = null;
        try {
            prep = connection.prepareStatement(insertSql.toString());
            int batchSize = 1000;
            int count = 0;
            for (Object[] batchValue : batchValues) {
                for (int i = 0; i < batchValue.length; ++i) {
                    prep.setObject(i + 1, batchValue[i]);
                }
                prep.addBatch();
                if (++count % 1000 != 0) continue;
                prep.executeBatch();
            }
            prep.executeBatch();
        }
        catch (Exception e) {
            StringBuilder msg = new StringBuilder();
            msg.append("sql: ").append((CharSequence)insertSql).append("\n");
            msg.append("argsCount: ").append(batchValues.size()).append("\n");
            msg.append(e.getMessage());
            this.logger.error(msg.toString(), e);
        }
        finally {
            try {
                if (prep != null) {
                    prep.close();
                }
            }
            catch (SQLException e) {
                this.logger.error("\u4e34\u65f6\u8868\u63d2\u5165\u6570\u636e\u5931\u8d25", e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void dropTempTable(Connection connection, String tableName) {
        Statement prep = null;
        try {
            prep = connection.prepareStatement("DROP TABLE " + tableName);
            prep.execute();
        }
        catch (SQLException e) {
            this.logger.error("\u5220\u9664\u4e34\u65f6\u8868\u5931\u8d25", e);
        }
        finally {
            try {
                if (prep != null) {
                    prep.close();
                }
            }
            catch (SQLException e) {
                this.logger.error("\u5220\u9664\u4e34\u65f6\u8868\u5931\u8d25", e);
            }
        }
    }

    static {
        SYS_FIELDS.put("OBJ_KEY", new KeyPropertyRecorder());
        SYS_FIELDS.put(SYS_FIELD_NAME, new NamePropertyRecorder());
        SYS_FIELDS.put(SYS_FIELD_OWNER, new OwnerPropertyRecorder());
        SYS_FIELDS.put(SYS_FIELD_DIR, new DirPropertyRecorder());
        SYS_FIELDS.put(SYS_FIELD_CONTENTTYPE, new ContentTypePropertyRecorder());
        SYS_FIELDS.put(SYS_FIELD_EXTENSION, new ExtensionPropertyRecorder());
        SYS_FIELDS.put(SYS_FIELD_MD5, new Md5PropertyRecorder());
        SYS_FIELDS.put(SYS_FIELD_SIZE, new SizePropertyRecorder());
        SYS_FIELDS.put(SYS_FIELD_CREATETIME, new CreateTimePropertyRecorder());
        sync = false;
        UPDATE_BUCKETCONFIG = "update OSS_BUCKET set BT_STORAGETYPE=?, BT_STORAGECONFIG=? where BT_NAME=?";
        UPDATE_BUCKET_DESC = "update OSS_BUCKET set BT_DESC=? where BT_NAME=?";
        OPEN_OBJECT_LINK = "update OSS_BUCKET set BT_LINK=1 where BT_NAME=?";
        MARK_BUCKETS = "update OSS_BUCKET set BT_STATUS=? where BT_NAME=?";
        QUERY_LOCK = "select BT_STATUS from OSS_BUCKET where BT_NAME=?";
        DELETE_BUCKET = "delete from OSS_BUCKET where BT_NAME=?";
    }

    static class CreateTimePropertyRecorder
    implements IPropertyRecorder {
        CreateTimePropertyRecorder() {
        }

        @Override
        public void setValue(ObjectInfo info, String value) {
            info.setCreateTime(value);
        }

        @Override
        public String getFieldName() {
            return DBMetaAdapter.F_META_OBJ_CREATETIME;
        }
    }

    static class ContentTypePropertyRecorder
    implements IPropertyRecorder {
        ContentTypePropertyRecorder() {
        }

        @Override
        public void setValue(ObjectInfo info, String value) {
            info.setContentType(value);
        }

        @Override
        public String getFieldName() {
            return DBMetaAdapter.F_META_OBJ_CONTENTTYPE;
        }
    }

    static class DirPropertyRecorder
    implements IPropertyRecorder {
        DirPropertyRecorder() {
        }

        @Override
        public void setValue(ObjectInfo info, String value) {
            info.setDir(value);
        }

        @Override
        public String getFieldName() {
            return DBMetaAdapter.F_META_OBJ_DIR;
        }
    }

    static class SizePropertyRecorder
    implements IPropertyRecorder {
        SizePropertyRecorder() {
        }

        @Override
        public void setValue(ObjectInfo info, String value) {
            info.setSize(Long.valueOf(value));
        }

        @Override
        public String getFieldName() {
            return DBMetaAdapter.F_META_OBJ_SIZE;
        }
    }

    static class Md5PropertyRecorder
    implements IPropertyRecorder {
        Md5PropertyRecorder() {
        }

        @Override
        public void setValue(ObjectInfo info, String value) {
            info.setMd5(value);
        }

        @Override
        public String getFieldName() {
            return DBMetaAdapter.F_META_OBJ_MD5;
        }
    }

    static class ExtensionPropertyRecorder
    implements IPropertyRecorder {
        ExtensionPropertyRecorder() {
        }

        @Override
        public void setValue(ObjectInfo info, String value) {
            info.setExtension(value);
        }

        @Override
        public String getFieldName() {
            return DBMetaAdapter.F_META_OBJ_EXTENSION;
        }
    }

    static class OwnerPropertyRecorder
    implements IPropertyRecorder {
        OwnerPropertyRecorder() {
        }

        @Override
        public void setValue(ObjectInfo info, String value) {
            info.setOwner(value);
        }

        @Override
        public String getFieldName() {
            return DBMetaAdapter.F_META_OBJ_OWNER;
        }
    }

    static class NamePropertyRecorder
    implements IPropertyRecorder {
        NamePropertyRecorder() {
        }

        @Override
        public void setValue(ObjectInfo info, String value) {
            info.setName(value);
        }

        @Override
        public String getFieldName() {
            return DBMetaAdapter.F_META_OBJ_NAME;
        }
    }

    static class KeyPropertyRecorder
    implements IPropertyRecorder {
        KeyPropertyRecorder() {
        }

        @Override
        public void setValue(ObjectInfo info, String value) {
            info.setKey(value);
        }

        @Override
        public String getFieldName() {
            return "OBJ_KEY";
        }
    }

    static interface IPropertyRecorder {
        public void setValue(ObjectInfo var1, String var2);

        public String getFieldName();
    }
}

