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
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.nr.designer.service.QuestionnaireService;
import com.jiuqi.nr.designer.web.facade.AttachmentFieleConfig;
import com.jiuqi.nr.file.FileAreaConfig;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import com.jiuqi.nr.file.impl.FileInfoService;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireServiceImpl
implements QuestionnaireService {
    private static final Logger log = LoggerFactory.getLogger(QuestionnaireServiceImpl.class);
    @Autowired
    private FileService fileService;
    @Autowired
    private FileInfoService fileInfoService;
    @Value(value="${jiuqi.np.blob.type}")
    private String type;

    @Override
    public String uploadFile(String fileName, byte[] bytes, String partition) {
        AttachmentFieleConfig fileConfig = new AttachmentFieleConfig();
        fileConfig.setAreaName(partition);
        FileAreaService areaService = this.fileService.area((FileAreaConfig)fileConfig);
        FileInfo fileInfo = areaService.upload(fileName, bytes);
        String path = "api/v1/designer/preview/";
        path = this.type.equals("db") ? path + partition + "/" + fileInfo.getKey() : path + partition + "/" + fileInfo.getKey();
        return path;
    }

    @Override
    public FileInfoImpl getField(String fileKey) {
        return null;
    }

    @Override
    public FileInfo deleteFile(FileInfo fileInfo, Boolean record) {
        FileInfo fileInfo1 = this.fileInfoService.deleteFile(fileInfo, record);
        return fileInfo1;
    }

    @Override
    public void downFild(HttpServletResponse response, String fileKey, String parent) {
        FileAreaService service = this.fileService.area(parent);
        byte[] download = service.download(fileKey);
        FileInfo info = service.getInfo(fileKey);
        try (BufferedOutputStream ous = new BufferedOutputStream((OutputStream)response.getOutputStream());){
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(info.getName().getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Cache-Control", "max-age=600");
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
        List fileInfoByGroup = this.fileInfoService.getFileInfoByGroup(split[0], split[1], FileStatus.AVAILABLE);
        return fileInfoByGroup;
    }
}

