/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.impl.FileInfoImpl
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.task.form.service;

import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface IAttachmentService {
    public String uploadFile(String var1, String var2, byte[] var3, String var4);

    public FileInfoImpl getField(String var1);

    public FileInfo deleteFile(FileInfo var1, Boolean var2);

    public void downFile(HttpServletResponse var1, String var2, String var3);

    public List<FileInfo> getFileInGroup(String var1);
}

