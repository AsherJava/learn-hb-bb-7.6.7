/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jcraft.jsch.ChannelSftp
 *  com.jcraft.jsch.JSch
 *  com.jcraft.jsch.JSchException
 *  com.jcraft.jsch.Session
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.pool2.BasePooledObjectFactory
 *  org.apache.commons.pool2.PooledObject
 *  org.apache.commons.pool2.impl.DefaultPooledObject
 */
package com.jiuqi.gcreport.archive.util.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.archive.common.ArchiveProperties;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArchiveChannelSFTPFactory
extends BasePooledObjectFactory<ChannelSftp> {
    @Autowired
    private ArchiveProperties archiveProperties;
    private Logger log = LoggerFactory.getLogger(ArchiveChannelSFTPFactory.class);

    public ChannelSftp create() {
        ChannelSftp channel = null;
        try {
            if (StringUtils.isBlank((CharSequence)this.archiveProperties.getUserName()) || StringUtils.isBlank((CharSequence)this.archiveProperties.getPwd())) {
                this.log.error("\u8fde\u63a5sftp\u670d\u52a1\u7528\u6237\u540d\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a\u3002");
                return null;
            }
            JSch jsch = new JSch();
            if (StringUtils.isNotBlank((CharSequence)this.archiveProperties.getPrivateKey())) {
                jsch.addIdentity(this.archiveProperties.getPrivateKey());
            }
            Session sshSession = jsch.getSession(this.archiveProperties.getUserName(), this.archiveProperties.getHost(), this.archiveProperties.getPort());
            sshSession.setPassword(this.archiveProperties.getPwd());
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.setTimeout(5000);
            sshSession.connect();
            channel = (ChannelSftp)sshSession.openChannel("sftp");
            channel.connect();
        }
        catch (Exception e) {
            this.log.error("\u8fde\u63a5 sftp \u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u914d\u7f6e", e);
            throw new BusinessRuntimeException("\u8fde\u63a5 sftp \u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u914d\u7f6e", (Throwable)e);
        }
        return channel;
    }

    public PooledObject<ChannelSftp> wrap(ChannelSftp channelSftp) {
        return new DefaultPooledObject((Object)channelSftp);
    }

    public void destroyObject(PooledObject<ChannelSftp> p) {
        ChannelSftp channelSftp = (ChannelSftp)p.getObject();
        if (channelSftp != null && channelSftp.isConnected()) {
            channelSftp.disconnect();
        }
        Session sshSession = null;
        try {
            sshSession = ((ChannelSftp)p.getObject()).getSession();
        }
        catch (JSchException e) {
            this.log.error("\u83b7\u53d6sftp\u8fde\u63a5Session\u5f02\u5e38", e);
        }
        if (sshSession != null) {
            sshSession.disconnect();
        }
    }

    public boolean validateObject(PooledObject<ChannelSftp> p) {
        ChannelSftp channelSftp = (ChannelSftp)p.getObject();
        try {
            if (channelSftp.isClosed()) {
                return false;
            }
            channelSftp.cd("/");
        }
        catch (Exception e) {
            this.log.error("channelSftp \u4e0d\u53ef\u7528 ", e);
            return false;
        }
        return true;
    }
}

