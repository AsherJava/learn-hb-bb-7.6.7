/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckReturnInfo
 */
package com.jiuqi.nr.datacheck.attachment.vo;

import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckReturnInfo;
import com.jiuqi.nr.datacheck.attachment.AttachmentConfig;
import java.util.List;

public class AttachmentResVO {
    private AttachmentConfig config;
    private AttachmentCheckReturnInfo info;
    private List<List<String>> table;

    public AttachmentConfig getConfig() {
        return this.config;
    }

    public void setConfig(AttachmentConfig config) {
        this.config = config;
    }

    public AttachmentCheckReturnInfo getInfo() {
        return this.info;
    }

    public void setInfo(AttachmentCheckReturnInfo info) {
        this.info = info;
    }

    public List<List<String>> getTable() {
        return this.table;
    }

    public void setTable(List<List<String>> table) {
        this.table = table;
    }
}

