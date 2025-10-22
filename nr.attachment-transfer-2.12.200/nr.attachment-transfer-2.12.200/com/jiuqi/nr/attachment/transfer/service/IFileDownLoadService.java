/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.attachment.transfer.service;

import com.jiuqi.nr.attachment.transfer.vo.DownLoadInfo;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface IFileDownLoadService {
    public void downLoadOne(String var1, String var2, HttpServletResponse var3);

    public void downLoadOne(String var1, String var2, String var3, HttpServletResponse var4);

    public List<DownLoadInfo> batchDownLoad(int var1);

    public String downLoadInfo(List<String> var1);
}

