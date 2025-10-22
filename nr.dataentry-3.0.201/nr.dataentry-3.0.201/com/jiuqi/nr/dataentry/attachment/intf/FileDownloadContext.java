/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import java.io.Serializable;
import java.util.List;

public class FileDownloadContext
implements Serializable {
    private static final long serialVersionUID = 1753538783444439939L;
    private String dataSchemeCode;
    private List<String> fileKeys;

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public List<String> getFileKeys() {
        return this.fileKeys;
    }

    public void setFileKeys(List<String> fileKeys) {
        this.fileKeys = fileKeys;
    }
}

