/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file;

import com.jiuqi.nr.file.FileInfo;
import java.util.Collection;
import java.util.List;

public interface IFileInfoDao {
    public void insert(FileInfo var1);

    public Collection<FileInfo> getFileInfos(Collection<String> var1, String var2);

    public void update(FileInfo var1);

    public List<FileInfo> getFileInfosByGroup(String var1, String var2);

    public FileInfo getFileInfo(String var1, String var2);

    public void delete(String var1);

    public List<FileInfo> getFileInfos(String var1);

    public List<String> getAreas();
}

