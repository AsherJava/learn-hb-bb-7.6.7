/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 */
package com.jiuqi.nr.dataentry.internal.service.dto;

import com.jiuqi.nr.dataentry.internal.service.util.obj.DWSplit;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;

@Deprecated
public class CheckResultDataSetDTO {
    private DWSplit dwSplit;
    private NvwaQueryModel queryModel;

    public NvwaQueryModel getQueryModel() {
        return this.queryModel;
    }

    public void setQueryModel(NvwaQueryModel queryModel) {
        this.queryModel = queryModel;
    }

    public DWSplit getDwSplit() {
        return this.dwSplit;
    }

    public void setDwSplit(DWSplit dwSplit) {
        this.dwSplit = dwSplit;
    }
}

