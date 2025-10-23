/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoImpl
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.task.form.service.impl;

import com.jiuqi.nr.file.FileAreaConfig;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.task.form.service.IAttachmentService;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentService
implements IAttachmentService {
    private static final Logger log = LoggerFactory.getLogger(AttachmentService.class);
    @Autowired
    private FileService fileService;
    @Autowired
    private FileInfoService fileInfoService;

    @Override
    public String uploadFile(String fileName, String fileGroup, byte[] bytes, String partition) {
        AttachmentFileConfig fileConfig = new AttachmentFileConfig();
        fileConfig.setAreaName(partition);
        FileAreaService areaService = this.fileService.area((FileAreaConfig)fileConfig);
        FileInfo fileInfo = areaService.uploadByGroup(fileName, fileGroup, bytes);
        return fileInfo.getFileGroupKey() + "|" + partition;
    }

    @Override
    public FileInfoImpl getField(String fileKey) {
        return null;
    }

    @Override
    public FileInfo deleteFile(FileInfo fileInfo, Boolean record) {
        return this.fileInfoService.deleteFile(fileInfo, record);
    }

    @Override
    public void downFile(HttpServletResponse response, String fileKey, String area) {
        FileAreaService service = this.fileService.area(area);
        byte[] download = service.download(fileKey);
        try (BufferedOutputStream ous = new BufferedOutputStream((OutputStream)response.getOutputStream());){
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            ((OutputStream)ous).write(download);
            ((OutputStream)ous).flush();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public List<FileInfo> getFileInGroup(String fileGroup) {
        String[] split = fileGroup.split("\\|");
        return this.fileInfoService.getFileInfoByGroup(split[0], split[1], FileStatus.AVAILABLE);
    }

    static class AttachmentFileConfig
    implements FileAreaConfig {
        private String areaName = "";
        private static long MAX_FILE_SIZE = 10240000L;

        AttachmentFileConfig() {
        }

        public String getName() {
            return this.areaName;
        }

        public long getMaxFileSize() {
            return MAX_FILE_SIZE;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getDesc() {
            return "\u9644\u4ef6\u7c7b\u578b\u6307\u6807\u4e0a\u4f20\u9ed8\u8ba4\u6a21\u677f";
        }
    }
}

