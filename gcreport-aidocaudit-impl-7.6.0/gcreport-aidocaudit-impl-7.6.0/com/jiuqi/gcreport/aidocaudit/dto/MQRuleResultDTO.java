/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.gcreport.aidocaudit.dto.MQRuleItemResultDTO;
import java.math.BigDecimal;
import java.util.List;

public class MQRuleResultDTO {
    @JsonProperty(value="task_status")
    private String taskStatus;
    @JsonProperty(value="error_msg")
    private String errorMsg;
    @JsonProperty(value="temp_id")
    private String tempId;
    @JsonProperty(value="temp_name")
    private String tempName;
    @JsonProperty(value="biz_dim")
    private String bizDim;
    @JsonProperty(value="total_score")
    private BigDecimal totalScore;
    @JsonProperty(value="file_attach_id")
    private String fileAttachId;
    @JsonProperty(value="score_items")
    private List<MQRuleItemResultDTO> scoreItems;

    public String getTaskStatus() {
        return this.taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTempId() {
        return this.tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getTempName() {
        return this.tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getBizDim() {
        return this.bizDim;
    }

    public void setBizDim(String bizDim) {
        this.bizDim = bizDim;
    }

    public BigDecimal getTotalScore() {
        return this.totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public String getFileAttachId() {
        return this.fileAttachId;
    }

    public void setFileAttachId(String fileAttachId) {
        this.fileAttachId = fileAttachId;
    }

    public List<MQRuleItemResultDTO> getScoreItems() {
        return this.scoreItems;
    }

    public void setScoreItems(List<MQRuleItemResultDTO> scoreItems) {
        this.scoreItems = scoreItems;
    }
}

