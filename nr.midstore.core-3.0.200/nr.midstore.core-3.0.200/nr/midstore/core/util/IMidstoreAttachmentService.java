/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.util;

import nr.midstore.core.definition.bean.MidstoreFileInfo;

public interface IMidstoreAttachmentService {
    public String saveFileFieldDataToNR(byte[] var1, MidstoreFileInfo var2) throws Exception;

    public String saveFileFieldDataToNR(byte[] var1) throws Exception;

    public byte[] getFieldDataFromNR(String var1) throws Exception;

    public byte[] getFieldDataFromNR(String var1, String var2, String var3) throws Exception;
}

