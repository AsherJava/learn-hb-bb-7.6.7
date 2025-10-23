/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.storage.ObjectStorageFactory
 *  com.jiuqi.bi.oss.storage.ObjectStorageFactoryManager
 *  com.jiuqi.np.blob.BlobContainerProvider
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.support.rowset.SqlRowSet
 */
package com.jiuqi.nr.file.oss;

import com.jiuqi.bi.oss.storage.ObjectStorageFactory;
import com.jiuqi.bi.oss.storage.ObjectStorageFactoryManager;
import com.jiuqi.np.blob.BlobContainerProvider;
import com.jiuqi.nr.file.oss.BlobStorageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class InitBlobOss {
    @Autowired
    private BlobContainerProvider provider;
    @Value(value="${np.blob.ossadpter.enable:false}")
    private boolean forceUseNr;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void init() throws Exception {
        String sql = "SELECT 1 FROM NVWA_SYSTEM_OPTIONS WHERE  ID='oss' AND VALUE LIKE '%\u62a5\u8868\u5b58\u50a8%'";
        boolean isNrOs = false;
        SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql);
        if (rs.next()) {
            isNrOs = true;
        }
        if (this.forceUseNr || isNrOs) {
            BlobStorageFactory blobStorageFactory = new BlobStorageFactory(this.provider);
            ObjectStorageFactoryManager.getInstance().registerObjectStorageFactory(blobStorageFactory.getType(), (ObjectStorageFactory)blobStorageFactory);
        }
    }

    public void initWhenStarted() throws Exception {
    }
}

