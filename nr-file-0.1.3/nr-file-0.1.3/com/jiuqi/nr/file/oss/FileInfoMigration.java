/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.BucketService
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.storage.DBSQLBuilder
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.file.oss;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.storage.DBSQLBuilder;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.utils.FileUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileInfoMigration
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FileInfoMigration.class);
    private static final String F_OBJ_KEY = "OBJ_KEY";
    private static final String F_OBJ_PNAME = "OBJ_PNAME";
    private static final String F_OBJ_PVALUE = "OBJ_PVALUE";
    private static final String TD_FILE_VERSION = "SYS_DATA_FILE";
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
    private static final String VER = "DataVer";

    public void execute(DataSource dataSource) throws Exception {
        boolean needMigrate = this.needMigrate(dataSource);
        if (!needMigrate) {
            return;
        }
        List<String> areas = this.getAreas(dataSource);
        for (String area : areas) {
            this.createBucket(area);
            List<FileInfo> fileInfos = this.getFileInfos(area, dataSource);
            for (FileInfo fileInfo : fileInfos) {
                ObjectInfo info = this.buildInfo(fileInfo);
                this.addObjectInfo(area, info, dataSource);
                this.delete(fileInfo.getKey(), dataSource, area);
            }
        }
        List<FileInfo> fileInfos = this.getFileInfos(VER, dataSource);
        for (FileInfo fileInfo : fileInfos) {
            this.createBucket(VER);
            ObjectInfo info = this.buildInfo(fileInfo);
            this.addObjectInfo(VER, info, dataSource);
            this.delete(fileInfo.getKey(), dataSource, VER);
        }
        this.setOptions(dataSource);
    }

    private ObjectInfo buildInfo(FileInfo fileInfo) {
        ObjectInfo info = new ObjectInfo();
        info.setCreateTime(FileUtils.getFormateDate(fileInfo.getCreateTime()));
        info.setDir(fileInfo.getPath());
        info.setExtension(fileInfo.getExtension());
        info.setKey(fileInfo.getKey());
        info.setName(fileInfo.getName());
        info.setOwner(fileInfo.getCreater());
        info.setSize(fileInfo.getSize());
        if (fileInfo.getFileGroupKey() != null) {
            info.getExtProp().put(FileUtils.GROUPKEY, fileInfo.getFileGroupKey());
        }
        if (fileInfo.getSecretlevel() != null) {
            info.getExtProp().put(FileUtils.SECRETLEVEL, fileInfo.getSecretlevel());
        }
        return info;
    }

    private void createBucket(String bucketName) throws Exception {
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        boolean exist = bucketService.existBucket(bucketName);
        if (!exist) {
            Bucket bucket = new Bucket(bucketName);
            bucketService.createBucket(bucket);
            bucketService.close();
        }
    }

    public void addObjectInfo(String bucketName, ObjectInfo info, DataSource dataSource) throws ObjectStorageException {
        String tableName = this.getObjectMetaTableName(bucketName);
        String insertSql = DBSQLBuilder.buildInsert((String)tableName, (String[])new String[]{F_OBJ_KEY, F_OBJ_PNAME, F_OBJ_PVALUE});
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql);){
            if (StringUtils.isNotEmpty((String)info.getName())) {
                ps.setString(1, info.getKey());
                ps.setString(2, "name");
                ps.setString(3, info.getName());
                ps.addBatch();
            }
            if (StringUtils.isNotEmpty((String)info.getOwner())) {
                ps.setString(1, info.getKey());
                ps.setString(2, "owner");
                ps.setString(3, info.getOwner());
                ps.addBatch();
            }
            if (StringUtils.isNotEmpty((String)info.getDir())) {
                ps.setString(1, info.getKey());
                ps.setString(2, "dir");
                ps.setString(3, info.getDir());
                ps.addBatch();
            }
            if (StringUtils.isNotEmpty((String)info.getExtension())) {
                ps.setString(1, info.getKey());
                ps.setString(2, "extension");
                ps.setString(3, info.getExtension());
                ps.addBatch();
            }
            if (StringUtils.isNotEmpty((String)info.getMd5())) {
                ps.setString(1, info.getKey());
                ps.setString(2, "md5");
                ps.setString(3, info.getMd5());
                ps.addBatch();
            }
            if (info.getSize() > 0L) {
                ps.setString(1, info.getKey());
                ps.setString(2, "size");
                ps.setString(3, String.valueOf(info.getSize()));
                ps.addBatch();
            }
            ps.setString(1, info.getKey());
            ps.setString(2, "createtime");
            ps.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            ps.addBatch();
            Map exts = info.getExtProp();
            for (Map.Entry entry : exts.entrySet()) {
                ps.setString(2, (String)entry.getKey());
                ps.setString(3, (String)entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String getObjectMetaTableName(String bucketName) {
        return "OSS_B_" + bucketName.toUpperCase() + "_M";
    }

    public static void main(String[] args) {
        FileInfoMigration f = new FileInfoMigration();
        f.getFileInfos(VER, null);
    }

    public List<FileInfo> getFileInfos(String area, DataSource dataSource) {
        if (area == null) {
            return null;
        }
        String sql = String.format("select %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s from %s where %s=?", FD_FILE_KEY, FD_FILE_AREA, FD_FILE_NAME, FD_FILE_EXTENSION, FD_FILE_SIZE, FD_FILE_STATUS, FD_FILE_CREATER, FD_FILE_CREATETIME, FD_FILE_LASTOPERATOR, FD_FILE_LASTOPERATETIME, FD_FILE_VERSION, FD_FILE_GROUP, area.equals(VER) ? TD_FILE_VERSION : TD_FILE, FD_FILE_AREA);
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement(sql);){
            prepareStatement.setString(1, area);
            try (ResultSet rs = prepareStatement.executeQuery();){
                if (rs.next()) {
                    while (rs.next()) {
                        fileInfos.add(FileInfoMigration.parseFileInfo(rs));
                    }
                }
            }
        }
        catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return fileInfos;
    }

    private static FileInfo parseFileInfo(ResultSet rs) throws SQLException {
        int col = 1;
        return FileInfoBuilder.newFileInfo(rs.getString(col++), rs.getString(col++), rs.getString(col++), rs.getString(col++), rs.getLong(col++), FileStatus.valueOf(rs.getString(col++)), rs.getString(col++), rs.getTimestamp(col++), rs.getString(col++), rs.getTimestamp(col++), rs.getInt(col++), rs.getString(col));
    }

    private List<String> getAreas(DataSource dataSource) {
        String sql = String.format("select %s from %s group by %s", FD_FILE_AREA, TD_FILE, FD_FILE_AREA);
        ArrayList<String> areas = new ArrayList<String>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement(sql);
             ResultSet rs = prepareStatement.executeQuery();){
            if (rs.next()) {
                while (rs.next()) {
                    areas.add(rs.getString(1));
                }
            }
        }
        catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return areas;
    }

    public void delete(String fileKey, DataSource dataSource, String area) {
        String sql = String.format("delete from %s where %s=?", area.equals(VER) ? TD_FILE_VERSION : TD_FILE, FD_FILE_KEY);
        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement(sql);){
            prepareStatement.setString(1, fileKey);
            prepareStatement.execute();
        }
        catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    private boolean needMigrate(DataSource dataSource) {
        String sql = "select count(1) from sys_file";
        boolean need = false;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepareStatement = conn.prepareStatement(sql);
             ResultSet rs = prepareStatement.executeQuery();){
            if (rs.next()) {
                while (rs.next()) {
                    int int1 = rs.getInt(1);
                    if (int1 > 0) {
                        need = true;
                        continue;
                    }
                    need = false;
                }
            }
        }
        catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return need;
    }

    private void setOptions(DataSource dataSource) {
        String delSql = "delete from nvwa_system_options where  id='oss'";
        String insertSql = " insert into nvwa_system_options (ID, VALUE, MODIFY_USER)  values ('oss', '{\"storageConfigType\":\"\u62a5\u8868\u5b58\u50a8\",\"storageConfig\":{},\"params\":null,\"temp\":false}', 'sys_user_admin')";
        try (Connection conn = dataSource.getConnection();){
            try (PreparedStatement deleteStatement = conn.prepareStatement(delSql);){
                deleteStatement.execute();
            }
            var7_10 = null;
            try (PreparedStatement insertStatement = conn.prepareStatement(insertSql);){
                insertStatement.execute();
            }
            catch (Throwable throwable) {
                var7_10 = throwable;
                throw throwable;
            }
        }
        catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }
}

