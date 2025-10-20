/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.CharsetUtil
 *  cn.hutool.extra.ftp.Ftp
 *  cn.hutool.extra.ftp.FtpMode
 *  cn.hutool.extra.ssh.Sftp
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.scheduler.impl.method;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpMode;
import cn.hutool.extra.ssh.Sftp;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;
import com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.MultilevelExtendHandler;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FtpSyncScheduler
implements ISyncMethodScheduler {
    private static Logger LOGGER = LoggerFactory.getLogger(FtpSyncScheduler.class);

    @Override
    public MultilevelExtendHandler getHandler() {
        return null;
    }

    @Override
    public String code() {
        return "ftp";
    }

    @Override
    public String name() {
        return "FTP";
    }

    @Override
    public boolean sync(MultilevelSyncContext syncContext) {
        SyncTypeEnums type = syncContext.getType();
        String content = syncContext.getServerInfoVO().getContent();
        FtpInfo ftpInfo = (FtpInfo)JsonUtils.readValue((String)content, FtpInfo.class);
        ReportSyncFileDTO fileDTO = syncContext.getFileDTO();
        try {
            Object ftp = null;
            LOGGER.info("\u5f00\u59cb\u8fde\u63a5ftp");
            ftp = ftpInfo.getFtpType().equals("ftp") ? new Ftp(ftpInfo.getFtpHost(), ftpInfo.getFtpPort().intValue(), ftpInfo.getFtpUserName(), ftpInfo.getFtpPassword(), CharsetUtil.CHARSET_UTF_8, null, null, FtpMode.Active) : new Sftp(ftpInfo.getFtpHost(), ftpInfo.getFtpPort().intValue(), ftpInfo.getFtpUserName(), ftpInfo.getFtpPassword());
            MultipartFile multipartFile = fileDTO.getMainFile();
            File file = new File(System.getProperty("java.io.tmpdir") + File.separator + type + "_" + multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
            ftp.upload(ftpInfo.getFtpFilePath(), file);
            ftp.close();
            file.delete();
        }
        catch (Exception e) {
            String errorLog = e.getMessage();
            syncContext.setErrorLogs(errorLog);
            LOGGER.error(e.getMessage());
            return Boolean.FALSE;
        }
        return true;
    }

    @Override
    public void afterSync(boolean syncResult, MultilevelSyncContext envContent) {
    }

    @Override
    public boolean testConnection(ReportDataSyncServerInfoVO serverInfoVO) {
        String content = serverInfoVO.getContent();
        FtpInfo ftpInfo = (FtpInfo)JsonUtils.readValue((String)content, FtpInfo.class);
        try {
            Object ftp = null;
            LOGGER.info("\u5f00\u59cb\u8fde\u63a5ftp");
            ftp = ftpInfo.getFtpType().equals("ftp") ? new Ftp(ftpInfo.getFtpHost(), ftpInfo.getFtpPort().intValue(), ftpInfo.getFtpUserName(), ftpInfo.getFtpPassword(), CharsetUtil.CHARSET_UTF_8, null, null, FtpMode.Active) : new Sftp(ftpInfo.getFtpHost(), ftpInfo.getFtpPort().intValue(), ftpInfo.getFtpUserName(), ftpInfo.getFtpPassword());
            ftp.close();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("ftp\u8fde\u63a5\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
        }
        return true;
    }

    private static class FtpInfo {
        private String ftpUserName;
        private String ftpPassword;
        private String ftpHost;
        private Integer ftpPort;
        private String ftpFilePath;
        private String ftpType;

        private FtpInfo() {
        }

        public String getFtpUserName() {
            return this.ftpUserName;
        }

        public void setFtpUserName(String ftpUserName) {
            this.ftpUserName = ftpUserName;
        }

        public String getFtpPassword() {
            return this.ftpPassword;
        }

        public void setFtpPassword(String ftpPassword) {
            this.ftpPassword = ftpPassword;
        }

        public String getFtpHost() {
            return this.ftpHost;
        }

        public void setFtpHost(String ftpHost) {
            this.ftpHost = ftpHost;
        }

        public Integer getFtpPort() {
            return this.ftpPort;
        }

        public void setFtpPort(Integer ftpPort) {
            this.ftpPort = ftpPort;
        }

        public String getFtpFilePath() {
            return this.ftpFilePath;
        }

        public void setFtpFilePath(String ftpFilePath) {
            this.ftpFilePath = ftpFilePath;
        }

        public String getFtpType() {
            return this.ftpType;
        }

        public void setFtpType(String ftpType) {
            this.ftpType = ftpType;
        }
    }
}

