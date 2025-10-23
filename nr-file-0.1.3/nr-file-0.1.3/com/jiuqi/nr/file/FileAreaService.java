/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectStorageException
 */
package com.jiuqi.nr.file;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.nr.file.FileAreaConfig;
import com.jiuqi.nr.file.FileInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

public interface FileAreaService {
    public FileAreaConfig getAreaConfig();

    public FileInfo upload(InputStream var1) throws IOException;

    public FileInfo upload(byte[] var1);

    public FileInfo upload(String var1, InputStream var2) throws IOException;

    public FileInfo uploadTemp(String var1, InputStream var2);

    public FileInfo upload(String var1, byte[] var2);

    public FileInfo uploadByGroup(String var1, String var2, byte[] var3);

    public FileInfo uploadByGroup(String var1, String var2, byte[] var3, String var4);

    public FileInfo upload(String var1, String var2, InputStream var3) throws IOException;

    public FileInfo upload(String var1, String var2, byte[] var3);

    public FileInfo rename(String var1, String var2);

    public FileInfo rename(String var1, String var2, String var3);

    public FileInfo delete(String var1);

    public FileInfo delete(String var1, Boolean var2);

    public FileInfo recover(String var1);

    public String getPath(String var1, String var2);

    public byte[] download(String var1);

    public void download(String var1, OutputStream var2);

    public FileInfo getInfo(String var1);

    public Map<String, FileInfo> getInfo(Collection<String> var1);

    public FileInfo uploadByKey(String var1, String var2, String var3, byte[] var4);

    public FileInfo uploadByGroup(String var1, String var2, InputStream var3) throws IOException;

    public FileInfo uploadByKeyLevle(String var1, String var2, String var3, byte[] var4, String var5);

    public FileInfo uploadByGroupLevel(String var1, String var2, String var3, byte[] var4);

    public void updateLevel(String var1, String var2) throws ObjectStorageException;

    public void copyFileGroup(String var1, String var2, String var3);
}

