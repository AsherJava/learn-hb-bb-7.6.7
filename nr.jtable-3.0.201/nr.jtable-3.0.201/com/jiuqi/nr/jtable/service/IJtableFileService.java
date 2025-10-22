/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.file.FileInfo
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.LinkFileOptionInfo;
import com.jiuqi.nr.jtable.params.input.LinkImgOptionInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface IJtableFileService {
    public List<FileInfo> getJableFiles(JtableContext var1);

    public List<SecretLevelItem> getFileSecretItems(JtableContext var1);

    @Deprecated
    public com.jiuqi.nr.file.FileInfo uploadJableFiles(byte[] var1, String var2, LinkFileOptionInfo var3);

    public void downloadJtableFiles(LinkFileOptionInfo var1, HttpServletResponse var2);

    @Deprecated
    public ReturnInfo updateJtableFilesSecret(LinkFileOptionInfo var1);

    public ReturnInfo removeJtableFiles(LinkFileOptionInfo var1);

    @Deprecated
    public void copyFileGroup(FieldDefine var1, String var2, String var3);

    public void getFileDataMap(List<String> var1, DataFormaterCache var2);

    public Map<String, List<byte[]>> queryImgDatas(LinkImgOptionInfo var1);
}

