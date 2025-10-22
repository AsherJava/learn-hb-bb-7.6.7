/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileInfo
 */
package nr.single.para.parain.service;

import com.jiuqi.nr.file.FileInfo;
import java.util.List;

public interface IAttachmentFileImportService {
    public String uploadFile(String var1, String var2, byte[] var3, String var4);

    public List<FileInfo> getFileInGroup(String var1);

    public List<FileInfo> getFileInGroup(String var1, String var2);

    public FileInfo deleteFile(FileInfo var1, Boolean var2);

    public void deleteFiles(String var1);
}

