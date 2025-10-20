/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.attachment.entity.ftp.FtpConfig
 *  com.jiuqi.va.attachment.entity.sftp.SftpConfig
 *  com.jiuqi.va.attachment.utils.ftp.FtpUtils
 *  com.jiuqi.va.attachment.utils.sftp.SftpUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.va.attachment.utils;

import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.attachment.entity.ftp.FtpConfig;
import com.jiuqi.va.attachment.entity.sftp.SftpConfig;
import com.jiuqi.va.attachment.utils.VaAttachmentIOUtils;
import com.jiuqi.va.attachment.utils.ftp.FtpUtils;
import com.jiuqi.va.attachment.utils.sftp.SftpUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;
import org.springframework.util.StringUtils;

public final class VaAttachmentZipUtils {
    private VaAttachmentZipUtils() {
    }

    public static void writeSuccessInToZip(ZipOutputStream zipOutputStream, String zipName) {
        String zipFilePath = zipName + "/success.txt";
        VaAttachmentIOUtils.writeFileToZip(zipOutputStream, zipFilePath, new ByteArrayInputStream("success".getBytes(StandardCharsets.UTF_8)));
    }

    public static void writeErrorFolderInToZip(ZipOutputStream zipOutputStream, String zipName, List<String> infoList) {
        String zipFilePath = zipName + "/error-list.txt";
        String content = String.join((CharSequence)System.lineSeparator(), infoList);
        VaAttachmentIOUtils.writeFileToZip(zipOutputStream, zipFilePath, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
    }

    public static void writeErrorInToZip(ZipOutputStream zipOutputStream, String zipName, String message) {
        String zipFilePath = zipName + "/error.txt";
        String errorMessage = "\u6587\u4ef6\u4e0b\u8f7d\u8fc7\u7a0b\u4e2d\u51fa\u73b0\u9519\u8bef\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff0c\u67e5\u770b\u540e\u7aef\u65e5\u5fd7\uff01";
        if (StringUtils.hasText(message)) {
            errorMessage = message;
        }
        VaAttachmentIOUtils.writeFileToZip(zipOutputStream, zipFilePath, new ByteArrayInputStream(errorMessage.getBytes(StandardCharsets.UTF_8)));
    }

    public static FtpConfig getFtpConfig(SchemeEntity schemeEntity, String workPath) {
        String config = schemeEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        int port = Integer.valueOf((String)schemeConfig.get("port"));
        String address = (String)schemeConfig.get("attaddress");
        String username = (String)schemeConfig.get("username");
        String pwd = (String)schemeConfig.get("pwd");
        return FtpUtils.getFTPConfig((String)workPath, (String)address, (int)port, (String)username, (String)pwd);
    }

    public static SftpConfig getSftpConfig(SchemeEntity schemeEntity, String workPath) {
        String config = schemeEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        int port = Integer.valueOf(schemeConfig.get("port").toString());
        String address = (String)schemeConfig.get("attaddress");
        String username = (String)schemeConfig.get("username");
        String pwd = (String)schemeConfig.get("pwd");
        return SftpUtils.getSftpConfig((String)address, (int)port, (String)username, (String)pwd, (String)workPath);
    }
}

