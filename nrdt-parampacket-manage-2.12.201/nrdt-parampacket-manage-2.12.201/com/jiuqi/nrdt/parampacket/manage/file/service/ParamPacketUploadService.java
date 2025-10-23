/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileInfo
 */
package com.jiuqi.nrdt.parampacket.manage.file.service;

import com.jiuqi.nr.file.FileInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ParamPacketUploadService {
    public FileInfo uploadByGroup(String var1, String var2, InputStream var3) throws IOException;

    public FileInfo upload(String var1, String var2, InputStream var3) throws IOException;

    public byte[] download(String var1);

    public void download(String var1, OutputStream var2);
}

