/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoService
 */
package com.jiuqi.nr.snapshot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SnapshotQuery {
    private static final Logger logger = LoggerFactory.getLogger(SnapshotQuery.class);
    @Autowired
    private FileService fileService;
    @Autowired
    private FileInfoService fileInfoService;

    public List<FileInfo> getTableFiles(String formKey, String versionID) {
        List fileList = this.fileInfoService.getFileInfoByGroup(formKey, "DataVer", FileStatus.AVAILABLE);
        ArrayList<FileInfo> tableFile = new ArrayList<FileInfo>();
        if (null == fileList) {
            return null;
        }
        for (FileInfo item : fileList) {
            if (!item.getName().equals(versionID)) continue;
            tableFile.add(item);
        }
        return tableFile;
    }

    public List<?> getFormList(FileInfo fileInfo) {
        byte[] bs = this.fileService.area("DataVer").download(fileInfo.getKey());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String result = null;
        try {
            out.write(bs);
            result = new String(out.toByteArray());
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List formList = new ArrayList();
        try {
            formList = (List)objectMapper.readValue(result, Object.class);
        }
        catch (JsonProcessingException e2) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e2.getMessage(), e2);
        }
        return formList;
    }
}

