/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

import com.jiuqi.nr.migration.transferdata.bean.OptionConfig;
import com.jiuqi.nr.migration.transferdata.bean.TransOrgInfo;
import com.jiuqi.nr.migration.transferdata.bean.TransferExportDTO;
import java.util.List;

public class ExportParam {
    private String taskId;
    private String formSchemeId;
    private String mappingSchemeId;
    private List<TransOrgInfo> orgInfos;
    private int synRange;
    private boolean synCommitMemo;
    private boolean createXM;
    private boolean synCheckErrorDes;
    private String dwEntityId;
    private String periodEntityId;
    private String periodValue;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getMappingSchemeId() {
        return this.mappingSchemeId;
    }

    public void setMappingSchemeId(String mappingSchemeId) {
        this.mappingSchemeId = mappingSchemeId;
    }

    public boolean isCreateXM() {
        return this.createXM;
    }

    public void setCreateXM(boolean createXM) {
        this.createXM = createXM;
    }

    public List<TransOrgInfo> getOrgInfos() {
        return this.orgInfos;
    }

    public void setOrgInfos(List<TransOrgInfo> orgInfos) {
        this.orgInfos = orgInfos;
    }

    public int getSynRange() {
        return this.synRange;
    }

    public void setSynRange(int synRange) {
        this.synRange = synRange;
    }

    public String getDwEntityId() {
        return this.dwEntityId;
    }

    public void setDwEntityId(String dwEntityId) {
        this.dwEntityId = dwEntityId;
    }

    public String getPeriodEntityId() {
        return this.periodEntityId;
    }

    public void setPeriodEntityId(String periodEntityId) {
        this.periodEntityId = periodEntityId;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public boolean isSynCommitMemo() {
        return this.synCommitMemo;
    }

    public void setSynCommitMemo(boolean synCommitMemo) {
    }

    public boolean isSynCheckErrorDes() {
        return this.synCheckErrorDes;
    }

    public void setSynCheckErrorDes(boolean synCheckErrorDes) {
        this.synCheckErrorDes = synCheckErrorDes;
    }

    public TransferExportDTO toTransferExport() {
        TransferExportDTO transferExport = new TransferExportDTO();
        transferExport.setTaskId(this.taskId);
        transferExport.setFormSchemeId(this.formSchemeId);
        transferExport.setMappingSchemeId(this.mappingSchemeId);
        OptionConfig optionConfig = new OptionConfig();
        optionConfig.setCreateXM(this.createXM);
        optionConfig.setSynRange(this.synRange);
        optionConfig.setSynCommitMemo(this.synCommitMemo);
        optionConfig.setSynCheckErrorDesc(this.synCheckErrorDes);
        transferExport.setOption(optionConfig);
        return transferExport;
    }
}

