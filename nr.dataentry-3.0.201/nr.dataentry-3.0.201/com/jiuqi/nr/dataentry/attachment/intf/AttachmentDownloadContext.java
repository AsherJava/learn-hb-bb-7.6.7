/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.dataentry.attachment.message.DownloadInfo;
import java.util.List;
import java.util.Map;

public class AttachmentDownloadContext
extends NRContext {
    private String task;
    private Map<String, DimensionValue> dimensionSet;
    private String formscheme;
    private List<DownloadInfo> downloadInfos;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getFormscheme() {
        return this.formscheme;
    }

    public void setFormscheme(String formscheme) {
        this.formscheme = formscheme;
    }

    public List<DownloadInfo> getDownloadInfos() {
        return this.downloadInfos;
    }

    public void setDownloadInfos(List<DownloadInfo> downloadInfos) {
        this.downloadInfos = downloadInfos;
    }
}

