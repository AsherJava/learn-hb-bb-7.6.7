/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.impl.FileInfoImpl
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface QuestionnaireService {
    public String uploadFile(String var1, byte[] var2, String var3);

    public FileInfoImpl getField(String var1);

    public FileInfo deleteFile(FileInfo var1, Boolean var2);

    public void downFild(HttpServletResponse var1, String var2, String var3);

    public List<FileInfo> getFileInGroup(String var1);
}

