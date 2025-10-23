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
 */
package com.jiuqi.nr.file.impl;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.storage.DBSQLBuilder;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.IFileInfoDao;
import com.jiuqi.nr.file.utils.FileUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

public class FileInfoMigrationService
implements ApplicationContextAware,
Ordered,
ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(FileInfoMigrationService.class);
    private static final String F_OBJ_KEY = "OBJ_KEY";
    private static final String F_OBJ_PNAME = "OBJ_PNAME";
    private static final String F_OBJ_PVALUE = "OBJ_PVALUE";
    @Autowired
    private Map<String, IFileInfoDao> fileInfoDao;
    @Autowired
    private DataSource dataSource;

    public String migrate() throws Exception {
        IFileInfoDao dao = this.fileInfoDao.get("FileInfoMulDataVerDao");
        IFileInfoDao dao2 = this.fileInfoDao.get("fileInfoDao");
        List<String> areas = dao2.getAreas();
        String ver = "DataVer";
        for (String area : areas) {
            this.createBucket(area);
            List<FileInfo> fileInfos = dao2.getFileInfos(area);
            for (FileInfo fileInfo : fileInfos) {
                ObjectInfo info = this.buildInfo(fileInfo);
                this.addObjectInfo(area, info);
                dao2.delete(fileInfo.getKey());
            }
        }
        List<FileInfo> fileInfos = dao.getFileInfos(ver);
        for (FileInfo fileInfo : fileInfos) {
            this.createBucket(ver);
            ObjectInfo info = this.buildInfo(fileInfo);
            this.addObjectInfo(ver, info);
            dao.delete(fileInfo.getKey());
        }
        return "success";
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

    public void addObjectInfo(String bucketName, ObjectInfo info) throws ObjectStorageException {
        String tableName = this.getObjectMetaTableName(bucketName);
        String insertSql = DBSQLBuilder.buildInsert((String)tableName, (String[])new String[]{F_OBJ_KEY, F_OBJ_PNAME, F_OBJ_PVALUE});
        try (Connection conn = this.dataSource.getConnection();
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

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    }

    public void init() throws Exception {
    }

    public void initWhenStarted() throws Exception {
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    Thread.sleep(300000L);
                    FileInfoMigrationService.this.migrate();
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }).start();
    }
}

