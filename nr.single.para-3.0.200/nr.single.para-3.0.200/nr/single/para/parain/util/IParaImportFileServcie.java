/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 */
package nr.single.para.parain.util;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import java.io.OutputStream;

public interface IParaImportFileServcie {
    public String uploadFile(String var1, byte[] var2) throws Exception;

    public String uploadFile(String var1, String var2) throws Exception;

    public String downFile(String var1, byte[] var2, String var3) throws JQException;

    public String downFile(String var1, String var2, byte[] var3, String var4) throws JQException, SingleFileException;

    public String downFile(String var1, String var2) throws JQException;

    public String downFile(String var1, OutputStream var2);

    public FileInfo getFileInfo(String var1);

    public void deleteFile(String var1);
}

