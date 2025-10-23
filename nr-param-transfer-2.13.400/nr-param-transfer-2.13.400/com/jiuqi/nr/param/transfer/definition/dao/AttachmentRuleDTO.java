/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.param.transfer.definition.dao;

import com.jiuqi.nr.param.transfer.definition.dao.TemplateFile;
import java.util.List;

public class AttachmentRuleDTO {
    private String dlKey;
    private byte[] attachment;
    private List<TemplateFile> templateFiles;

    public byte[] getAttachment() {
        return this.attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getDlKey() {
        return this.dlKey;
    }

    public void setDlKey(String dlKey) {
        this.dlKey = dlKey;
    }

    public List<TemplateFile> getTemplateFiles() {
        return this.templateFiles;
    }

    public void setTemplateFiles(List<TemplateFile> templateFiles) {
        this.templateFiles = templateFiles;
    }
}

