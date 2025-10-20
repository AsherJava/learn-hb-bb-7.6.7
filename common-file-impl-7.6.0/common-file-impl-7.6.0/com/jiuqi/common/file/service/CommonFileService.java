/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.common.file.dto.CommonFileDTO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.file.service;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.common.file.dto.CommonFileDTO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CommonFileService {
    public ObjectInfo uploadFileToOss(MultipartFile var1);

    public ObjectInfo uploadFileToOss(MultipartFile var1, String var2);

    public void downloadOssFile(HttpServletRequest var1, HttpServletResponse var2, String var3);

    public ObjectInfo getObjectInfo(String var1);

    public void downloadOssZipFile(HttpServletRequest var1, HttpServletResponse var2, List<String> var3, String var4);

    public CommonFileDTO queryOssFileByFileKey(String var1);

    public void deleteOssFile(String var1);

    public ObjectInfo uploadFileToOss(String var1, MultipartFile var2);

    public ObjectInfo uploadFileToOss(String var1, MultipartFile var2, String var3);

    public void downloadOssFile(String var1, HttpServletRequest var2, HttpServletResponse var3, String var4);

    public ObjectInfo getObjectInfo(String var1, String var2);

    public void downloadOssZipFile(String var1, HttpServletRequest var2, HttpServletResponse var3, List<String> var4, String var5);

    public CommonFileDTO queryOssFileByFileKey(String var1, String var2);

    public void deleteOssFile(String var1, String var2);
}

