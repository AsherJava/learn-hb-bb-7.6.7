/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.fileupload.service;

import com.jiuqi.nr.fileupload.FileUploadReturnInfo;
import com.jiuqi.nr.fileupload.FilesUploadReturnInfo;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface CheckUploadFileService {
    public FilesUploadReturnInfo checkUploadFileInfo(MultipartFile[] var1, List<String> var2, String var3);

    public FilesUploadReturnInfo checkUploadFileSuffix(Map<String, Long> var1, List<String> var2, String var3);

    public Map<String, Object> getAllCheckInfo();

    public FileUploadReturnInfo checkFileInfo(String var1, Long var2, List<String> var3, String var4);

    public FilesUploadReturnInfo checkFileType(MultipartFile[] var1);
}

