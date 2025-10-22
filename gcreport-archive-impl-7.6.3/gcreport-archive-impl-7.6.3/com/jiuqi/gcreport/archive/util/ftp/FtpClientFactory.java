/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.net.ftp.FTPClient
 *  org.apache.commons.net.ftp.FTPReply
 */
package com.jiuqi.gcreport.archive.util.ftp;

import com.jiuqi.gcreport.archive.common.ArchiveProperties;
import com.jiuqi.gcreport.archive.util.ftp.ArchiveFTPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FtpClientFactory {
    private Logger logger = LoggerFactory.getLogger(FtpClientFactory.class);
    static String LOCAL_CHARSET = "GBK";
    @Autowired
    private ArchiveProperties archiveProperties;

    public ArchiveFTPClient makeFtpClient() throws Exception {
        ArchiveFTPClient ftpClient = new ArchiveFTPClient();
        ftpClient.setConnectTimeout(10000);
        try {
            ftpClient.connect(this.archiveProperties.getHost(), this.archiveProperties.getPort());
            boolean result = ftpClient.login(this.archiveProperties.getUserName(), this.archiveProperties.getPwd());
            if (!result) {
                this.logger.info("ftp\u767b\u5f55\u5931\u8d25\uff0cusername:" + this.archiveProperties.getUserName());
                return null;
            }
            if (FTPReply.isPositiveCompletion((int)ftpClient.getReplyCode()) && FTPReply.isPositiveCompletion((int)ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                LOCAL_CHARSET = "UTF-8";
            }
            ftpClient.setControlEncoding(LOCAL_CHARSET);
            ftpClient.setFileType(2);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setRootPatch(ftpClient.printWorkingDirectory());
        }
        catch (Exception e) {
            this.logger.error("makeClient exception", e);
            this.destroyClient(ftpClient);
        }
        return ftpClient;
    }

    public void destroyClient(FTPClient ftpClient) {
        try {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
        }
        catch (Exception e) {
            this.logger.error("ftpClient logout exception", e);
        }
        finally {
            try {
                if (ftpClient != null) {
                    ftpClient.disconnect();
                }
            }
            catch (Exception e2) {
                this.logger.error("ftpClient disconnect exception", e2);
            }
        }
    }

    public boolean validateClient(FTPClient ftpClient) {
        try {
            return ftpClient.sendNoOp();
        }
        catch (Exception e) {
            this.logger.warn("ftpClient validate exception", e);
            return false;
        }
    }
}

