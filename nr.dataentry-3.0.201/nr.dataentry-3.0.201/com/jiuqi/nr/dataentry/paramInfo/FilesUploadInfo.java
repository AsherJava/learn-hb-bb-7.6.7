/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.dataentry.paramInfo.AttachmentInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Map;

public class FilesUploadInfo
extends NRContext {
    private Map<String, AttachmentInfo> fileUploadInfoMap;
    private JtableContext context;
    private String fieldKey;
    private String groupKey;
    private boolean covered = false;

    public Map<String, AttachmentInfo> getFileUploadInfoMap() {
        return this.fileUploadInfoMap;
    }

    public void setFileUploadInfoMap(Map<String, AttachmentInfo> fileUploadInfoMap) {
        this.fileUploadInfoMap = fileUploadInfoMap;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public boolean isCovered() {
        return this.covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }
}

