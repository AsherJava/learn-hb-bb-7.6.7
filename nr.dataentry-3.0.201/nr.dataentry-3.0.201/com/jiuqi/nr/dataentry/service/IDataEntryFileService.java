/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.dataentry.bean.FilesCheckUploadResult;
import com.jiuqi.nr.dataentry.paramInfo.FileUploadParams;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface IDataEntryFileService {
    public Map<String, Object> getAllSysUploadConfig();

    public FilesCheckUploadResult checkAndUploadFiles(MultipartFile[] var1, FileUploadParams var2);
}

