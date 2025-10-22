/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import java.io.Serializable;

public class AsyncUploadInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private DataFillDataQueryInfo queryInfo;
    private String fileKey;
    private String suffix;

    public DataFillDataQueryInfo getQueryInfo() {
        return this.queryInfo;
    }

    public void setQueryInfo(DataFillDataQueryInfo queryInfo) {
        this.queryInfo = queryInfo;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}

