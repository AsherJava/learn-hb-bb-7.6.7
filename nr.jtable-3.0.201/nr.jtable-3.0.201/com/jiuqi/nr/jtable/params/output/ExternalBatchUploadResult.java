/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.ExternalUploadResultItem;
import java.util.List;

public class ExternalBatchUploadResult {
    private String linkTitle;
    private String uploadDes;
    private String labelTitle;
    private List<ExternalUploadResultItem> noUploadResultItem;

    public String getLinkTitle() {
        return this.linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getUploadDes() {
        return this.uploadDes;
    }

    public void setUploadDes(String uploadDes) {
        this.uploadDes = uploadDes;
    }

    public String getLabelTitle() {
        return this.labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public List<ExternalUploadResultItem> getNoUploadResultItem() {
        return this.noUploadResultItem;
    }

    public void setNoUploadResultItem(List<ExternalUploadResultItem> noUploadResultItem) {
        this.noUploadResultItem = noUploadResultItem;
    }
}

