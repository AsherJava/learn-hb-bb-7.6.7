/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow
 */
package com.jiuqi.nr.batch.gather.gzw.service.entity;

import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import java.util.List;

public class ApiResponseResult {
    private boolean success;
    private List<CustomCalibreRow> data;

    public ApiResponseResult(boolean success, List<CustomCalibreRow> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<CustomCalibreRow> getData() {
        return this.data;
    }

    public void setData(List<CustomCalibreRow> data) {
        this.data = data;
    }
}

