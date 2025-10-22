/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jcraft.jsch.ChannelSftp
 *  com.jiuqi.common.base.util.StringUtils
 *  javax.annotation.PostConstruct
 *  org.apache.commons.pool2.PooledObjectFactory
 *  org.apache.commons.pool2.impl.GenericObjectPool
 *  org.apache.commons.pool2.impl.GenericObjectPoolConfig
 */
package com.jiuqi.gcreport.archive.util.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.archive.common.ArchiveProperties;
import com.jiuqi.gcreport.archive.util.sftp.ArchiveChannelSFTPFactory;
import javax.annotation.PostConstruct;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArchiveSFTPClientPool {
    private GenericObjectPool<ChannelSftp> pool;
    @Autowired
    private ArchiveProperties archiveProperties;
    @Autowired
    private ArchiveChannelSFTPFactory factory;
    private Logger log = LoggerFactory.getLogger(ArchiveSFTPClientPool.class);

    @PostConstruct
    private void initPool() {
        if (StringUtils.isEmpty((String)this.archiveProperties.getHost())) {
            this.log.debug("\u672a\u914d\u7f6esftp\u767b\u5f55\u4fe1\u606f,\u8df3\u8fc7\u521d\u59cb\u5316sftp\u8fde\u63a5\u6c60");
            return;
        }
        if (!this.archiveProperties.isSFTP().booleanValue()) {
            this.log.info("\u914d\u7f6e\u4e86ftp\u767b\u5f55\u4fe1\u606f,\u8df3\u8fc7\u521d\u59cb\u5316sftp\u8fde\u63a5\u6c60");
            return;
        }
        this.archiveProperties.setTestOnBorrow(true);
        this.pool = new GenericObjectPool((PooledObjectFactory)this.factory, (GenericObjectPoolConfig)this.archiveProperties);
    }

    public ChannelSftp borrowObject() {
        if (this.pool != null) {
            try {
                ChannelSftp channelSftp = (ChannelSftp)this.pool.borrowObject();
                if (!channelSftp.isConnected()) {
                    this.log.error("SFTP \u83b7\u53d6 ChannelSftp \u5931\u8d25");
                }
                if (!channelSftp.getSession().isConnected()) {
                    this.log.error("SFTP \u83b7\u53d6 Session \u5931\u8d25");
                }
                return channelSftp;
            }
            catch (Exception e) {
                this.log.error("\u83b7\u53d6 ChannelSftp \u5931\u8d25", e);
            }
        }
        return null;
    }

    public void returnObject(ChannelSftp channelSftp) {
        if (this.pool != null && channelSftp != null) {
            this.pool.returnObject((Object)channelSftp);
        }
    }
}

