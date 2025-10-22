/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  org.apache.commons.net.ftp.FTPClient
 *  org.apache.commons.net.ftp.FTPReply
 */
package com.jiuqi.gcreport.intermediatelibrary.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.intermediatelibrary.utils.FTPConfig;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpUtils {
    private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

    public static FTPClient getFtpConnection(FTPConfig ftpConfig) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.setConnectTimeout(10000);
            ftpClient.connect(ftpConfig.getHost(), ftpConfig.getPort().intValue());
            ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());
            ftpClient.setFileType(2);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setControlEncoding("GBK");
            if (!FTPReply.isPositiveCompletion((int)ftpClient.getReplyCode())) {
                logger.error("\u8fde\u63a5FTP\u5931\u8d25\uff0c\u7528\u6237\u540d\u6216\u5bc6\u7801\u9519\u8bef\u3002");
                ftpClient.disconnect();
                throw new BusinessRuntimeException("\u8fde\u63a5FTP\u5931\u8d25\uff0c\u7528\u6237\u540d\u6216\u5bc6\u7801\u9519\u8bef\u3002" + JsonUtils.writeValueAsString((Object)ftpClient));
            }
            return ftpClient;
        }
        catch (Exception e) {
            logger.error("\u767b\u9646FTP\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5FTP\u76f8\u5173\u914d\u7f6e\u4fe1\u606f\u662f\u5426\u6b63\u786e\uff01" + JsonUtils.writeValueAsString((Object)ftpConfig), e);
            throw new BusinessRuntimeException("\u767b\u9646FTP\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5FTP\u76f8\u5173\u914d\u7f6e\u4fe1\u606f\u662f\u5426\u6b63\u786e\uff01" + JsonUtils.writeValueAsString((Object)ftpConfig), (Throwable)e);
        }
    }
}

