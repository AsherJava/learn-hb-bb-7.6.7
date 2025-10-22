/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  nr.single.map.data.SingleFieldFileInfo
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.data.util.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.attachment.message.FileInfo;
import java.io.OutputStream;
import java.util.List;
import nr.single.data.util.bean.SingleAttachmentResult;
import nr.single.map.data.SingleFieldFileInfo;
import nr.single.map.data.exception.SingleDataException;

public interface ISingleAttachmentService {
    public String uploadSingleFiles(List<String> var1, String var2) throws SingleDataException;

    public String uploadSingleFiles(List<String> var1, SingleFieldFileInfo var2) throws SingleDataException;

    public String uploadSingleFileInfos(List<SingleFieldFileInfo> var1, SingleFieldFileInfo var2) throws SingleDataException;

    public SingleAttachmentResult uploadSingleFileInfosR(List<SingleFieldFileInfo> var1, SingleFieldFileInfo var2) throws SingleDataException;

    public byte[] downloadSingleFiles(String var1) throws SingleDataException;

    public byte[] downloadSingleFiles(String var1, String var2, String var3) throws SingleDataException;

    public List<FileInfo> getFileInfoByGroup(String var1, String var2, String var3);

    public List<FileInfo> getFileInfoByGroup(String var1, String var2, String var3, String var4);

    public byte[] download(String var1, String var2, String var3);

    public void download(String var1, String var2, String var3, OutputStream var4);

    public void deleteMarkFile(String var1, AsyncTaskMonitor var2);
}

