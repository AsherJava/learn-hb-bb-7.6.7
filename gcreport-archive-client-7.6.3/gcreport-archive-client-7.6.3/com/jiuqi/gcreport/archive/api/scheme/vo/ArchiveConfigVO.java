/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.tree.FormTree
 */
package com.jiuqi.gcreport.archive.api.scheme.vo;

import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigFormInfo;
import com.jiuqi.nr.dataentry.tree.FormTree;
import java.util.List;

public class ArchiveConfigVO {
    private String pluginName;
    private String schemeId;
    private String schemeTitle;
    private String orgType;
    private FormTree canSelectedFormsTree;
    private FormTree canSelectedFormsTreeWithAttachment;
    private List<String> excelFormKeys;
    private List<ArchiveConfigFormInfo> excelFormInfo;
    private List<String> pdfFormKeys;
    private List<ArchiveConfigFormInfo> pdfFormInfo;
    private List<String> attachmentFormKeys;
    private List<ArchiveConfigFormInfo> attachmentFormInfo;
    private List<String> ofdFormKeys;
    private List<ArchiveConfigFormInfo> ofdFormInfo;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSchemeTitle() {
        return this.schemeTitle;
    }

    public void setSchemeTitle(String schemeTitle) {
        this.schemeTitle = schemeTitle;
    }

    public FormTree getCanSelectedFormsTree() {
        return this.canSelectedFormsTree;
    }

    public void setCanSelectedFormsTree(FormTree canSelectedFormsTree) {
        this.canSelectedFormsTree = canSelectedFormsTree;
    }

    public List<String> getPdfFormKeys() {
        return this.pdfFormKeys;
    }

    public void setPdfFormKeys(List<String> pdfFormKeys) {
        this.pdfFormKeys = pdfFormKeys;
    }

    public List<String> getExcelFormKeys() {
        return this.excelFormKeys;
    }

    public void setExcelFormKeys(List<String> excelFormKeys) {
        this.excelFormKeys = excelFormKeys;
    }

    public List<ArchiveConfigFormInfo> getExcelFormInfo() {
        return this.excelFormInfo;
    }

    public void setExcelFormInfo(List<ArchiveConfigFormInfo> excelFormInfo) {
        this.excelFormInfo = excelFormInfo;
    }

    public List<ArchiveConfigFormInfo> getPdfFormInfo() {
        return this.pdfFormInfo;
    }

    public void setPdfFormInfo(List<ArchiveConfigFormInfo> pdfFormInfo) {
        this.pdfFormInfo = pdfFormInfo;
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public List<String> getAttachmentFormKeys() {
        return this.attachmentFormKeys;
    }

    public void setAttachmentFormKeys(List<String> attachmentFormKeys) {
        this.attachmentFormKeys = attachmentFormKeys;
    }

    public List<ArchiveConfigFormInfo> getAttachmentFormInfo() {
        return this.attachmentFormInfo;
    }

    public void setAttachmentFormInfo(List<ArchiveConfigFormInfo> attachmentFormInfo) {
        this.attachmentFormInfo = attachmentFormInfo;
    }

    public FormTree getCanSelectedFormsTreeWithAttachment() {
        return this.canSelectedFormsTreeWithAttachment;
    }

    public void setCanSelectedFormsTreeWithAttachment(FormTree canSelectedFormsTreeWithAttachment) {
        this.canSelectedFormsTreeWithAttachment = canSelectedFormsTreeWithAttachment;
    }

    public List<String> getOfdFormKeys() {
        return this.ofdFormKeys;
    }

    public void setOfdFormKeys(List<String> ofdFormKeys) {
        this.ofdFormKeys = ofdFormKeys;
    }

    public List<ArchiveConfigFormInfo> getOfdFormInfo() {
        return this.ofdFormInfo;
    }

    public void setOfdFormInfo(List<ArchiveConfigFormInfo> ofdFormInfo) {
        this.ofdFormInfo = ofdFormInfo;
    }
}

