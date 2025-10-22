/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news2.service;

import com.jiuqi.nr.portal.news2.impl.FileImpl;
import java.util.List;

public interface IPortalFileDao {
    public List<FileImpl> queryAllFile();

    public FileImpl queryFileByFileId(String var1);

    public Boolean deleteFileByMid(String var1, String var2, String var3);

    public Boolean deleteFileByFileId(String var1);

    public Boolean insertFile(FileImpl var1);

    public Boolean insertFileRunning(FileImpl var1);

    public Boolean updateFile(FileImpl var1);

    public Boolean publishFiles(String var1, String var2);

    public List<FileImpl> queryFileByMidAndPortalId(String var1, String var2, String var3);

    default public List<FileImpl> queryFileByMidAndPortalId(String mid, String portalId) {
        return this.queryFileByMidAndPortalId(mid, portalId, "design");
    }

    public Boolean modifyFileOrder(String var1, Integer var2);

    public Boolean updateFileRunning(FileImpl var1);

    public Integer getMaxOrder();
}

