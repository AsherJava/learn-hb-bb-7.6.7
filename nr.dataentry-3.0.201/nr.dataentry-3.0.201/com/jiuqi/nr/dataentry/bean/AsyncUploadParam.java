/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.UploadParam;
import java.io.File;
import java.io.Serializable;

public class AsyncUploadParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private UploadParam param;
    private String suffix;
    private File file;

    public UploadParam getParam() {
        return this.param;
    }

    public void setParam(UploadParam param) {
        this.param = param;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

