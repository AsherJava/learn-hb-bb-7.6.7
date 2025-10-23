/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.file;

import com.jiuqi.nr.task.api.file.dto.FileAreaDTO;
import com.jiuqi.nr.task.api.file.dto.FileInfoDTO;
import java.io.InputStream;
import java.io.OutputStream;

public interface IFileAreaService {
    public FileInfoDTO fileUpload(String var1, InputStream var2, FileAreaDTO var3);

    public FileInfoDTO fileUploadByKey(String var1, InputStream var2, String var3, FileAreaDTO var4);

    public FileInfoDTO getFile(String var1, FileAreaDTO var2);

    public void updateFileName(String var1, String var2, FileAreaDTO var3);

    public boolean existFile(String var1, FileAreaDTO var2);

    public void deleteFile(String var1, FileAreaDTO var2);

    public byte[] download(String var1, FileAreaDTO var2);

    public void download(String var1, OutputStream var2, FileAreaDTO var3);
}

