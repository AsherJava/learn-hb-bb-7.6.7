/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.file.FileInfo
 */
package nr.midstore2.core.util;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.file.FileInfo;
import java.io.InputStream;
import java.io.OutputStream;

public interface IMidstoreFileServcie {
    public String uploadFile(String var1, byte[] var2) throws Exception;

    public String uploadFile(String var1, String var2) throws Exception;

    public String uploadFile(String var1, InputStream var2) throws Exception;

    public String downFile(String var1, String var2) throws JQException;

    public String downFile(String var1, OutputStream var2);

    public FileInfo getFileInfo(String var1);

    public void deleteFile(String var1);
}

