/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.params.input;

import com.jiuqi.nr.io.params.input.ImportErrorData;
import java.util.List;

public class ImportError {
    private String regionKey;
    private List<ImportErrorData> errorDataInfos;

    public ImportError() {
    }

    public ImportError(String regionKey, List<ImportErrorData> errorDataInfos) {
        this.regionKey = regionKey;
        this.errorDataInfos = errorDataInfos;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public List<ImportErrorData> getErrorDataInfos() {
        return this.errorDataInfos;
    }

    public void setErrorDataInfos(List<ImportErrorData> errorDataInfos) {
        this.errorDataInfos = errorDataInfos;
    }
}

