/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news2.service;

import com.jiuqi.nr.portal.news2.impl.FileImpl;
import java.util.List;

public interface IPortalFileService {
    public FileImpl queryFileByid(String var1);

    public List<FileImpl> queryFileByMidAndPortalId(String var1, String var2, String var3);

    default public List<FileImpl> queryFileByMidAndPortalId(String mid, String portalId) {
        return this.queryFileByMidAndPortalId(mid, portalId, "design");
    }

    public List<FileImpl> queryAllFile();

    public Boolean deleteFileByMid(String var1, String var2, String var3);

    public Boolean deleteFileByFileId(String var1);

    public Boolean saveFile(FileImpl var1);

    public Boolean modifyFile(FileImpl var1);

    public Boolean modifyFileRunning(FileImpl var1);

    public Boolean publishFiles(String var1, String var2);

    public Boolean modifyFileOrder(String var1, Integer var2);

    public Boolean saveFileRunning(FileImpl var1);

    public Boolean saveFile(FileImpl var1, Boolean var2);

    public void updateCache(FileImpl var1, String var2);

    public void removeCache(String var1, String var2, String var3);
}

