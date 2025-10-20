/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  javax.annotation.PostConstruct
 *  org.apache.commons.net.ftp.FTPClient
 */
package com.jiuqi.gcreport.archive.util.ftp;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.archive.common.ArchiveProperties;
import com.jiuqi.gcreport.archive.util.ftp.ArchiveFTPClient;
import com.jiuqi.gcreport.archive.util.ftp.FtpClientFactory;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FtpClientPool {
    private static final int DEFAULT_POOL_SIZE = 5;
    private BlockingQueue<FTPClient> pool;
    @Autowired
    private FtpClientFactory factory;
    @Autowired
    private ArchiveProperties archiveProperties;
    private Logger logger = LoggerFactory.getLogger(FtpClientPool.class);

    @PostConstruct
    private void initPool() {
        if (StringUtils.isEmpty((String)this.archiveProperties.getHost())) {
            this.logger.debug("\u672a\u914d\u7f6eftp\u767b\u5f55\u4fe1\u606f,\u8df3\u8fc7\u521d\u59cb\u5316ftp\u8fde\u63a5\u6c60");
            return;
        }
        if (this.archiveProperties.isSFTP().booleanValue()) {
            this.logger.info("\u914d\u7f6e\u4e86sftp\u767b\u5f55\u4fe1\u606f,\u8df3\u8fc7\u521d\u59cb\u5316ftp\u8fde\u63a5\u6c60");
            return;
        }
        this.pool = new ArrayBlockingQueue<FTPClient>(5);
        int maxPoolSize = 5;
        try {
            for (int count = 0; count < maxPoolSize; ++count) {
                this.pool.offer(this.factory.makeFtpClient(), 10L, TimeUnit.SECONDS);
            }
        }
        catch (Exception e) {
            this.logger.error("ftp\u8fde\u63a5\u6c60\u521d\u59cb\u5316\u5931\u8d25", e);
        }
    }

    public FTPClient borrowClient() throws Exception {
        ArchiveFTPClient ftpClient = (ArchiveFTPClient)this.pool.take();
        if (ftpClient == null) {
            ftpClient = this.factory.makeFtpClient();
        } else if (!this.factory.validateClient(ftpClient)) {
            this.logger.info("\u8fde\u63a5\u5931\u6548\uff0c\u91cd\u7f6e\u8fde\u63a5\uff01");
            this.factory.destroyClient(ftpClient);
            ftpClient = this.factory.makeFtpClient();
        } else {
            ftpClient.changeWorkingDirectory(ftpClient.getRootPatch());
        }
        return ftpClient;
    }

    public void returnFtpClient(FTPClient ftpClient) {
        try {
            if (ftpClient != null && !this.pool.offer(ftpClient, 10L, TimeUnit.SECONDS)) {
                this.factory.destroyClient(ftpClient);
            }
        }
        catch (Exception e) {
            this.logger.error("\u5f52\u8fd8\u5bf9\u8c61\u5931\u8d25", e);
        }
    }

    public int getPoolSize() {
        return this.pool.size();
    }
}

