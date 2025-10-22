/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.fileupload.service;

import com.jiuqi.nr.fileupload.FileUploadReturnExtInfo;
import org.springframework.web.multipart.MultipartFile;

public interface CheckUploadFileExtendService {
    public FileUploadReturnExtInfo checkUploadFileInfo(MultipartFile var1);
}

