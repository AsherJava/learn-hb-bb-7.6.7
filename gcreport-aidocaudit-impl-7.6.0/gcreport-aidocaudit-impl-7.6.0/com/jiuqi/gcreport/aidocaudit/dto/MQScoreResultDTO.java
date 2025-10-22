/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.gcreport.aidocaudit.dto.ResultItemDTO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MQScoreResultDTO {
    @JsonProperty(value="task_status")
    private String taskStatus;
    @JsonProperty(value="error_msg")
    private String errorMsg;
    @JsonProperty(value="temp_id")
    private String tempId;
    @JsonProperty(value="total_score")
    private BigDecimal totalScore;
    @JsonProperty(value="biz_prop1")
    private String bizProp1;
    @JsonProperty(value="biz_prop2")
    private String bizProp2;
    @JsonProperty(value="biz_title1")
    private String bizTitle1;
    @JsonProperty(value="biz_title2")
    private String bizTitle2;
    @JsonProperty(value="create_time")
    private Date createTime;
    @JsonProperty(value="update_time")
    private Date updateTime;
    @JsonProperty(value="task_id")
    private String taskId;
    @JsonProperty(value="file_attach_id")
    private String fileAttachId;
    @JsonProperty(value="doc_title")
    private String docTitle;
    @JsonProperty(value="rep_id")
    private String repId;
    @JsonProperty(value="business")
    private Map<String, String> business;
    @JsonProperty(value="result_id")
    private String resultId;
    @JsonProperty(value="result_items")
    private List<ResultItemDTO> resultItems;
    @JsonProperty(value="rid")
    private String rid;

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

    public BigDecimal getTotalScore() {
        return this.totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public String getBizProp1() {
        return this.bizProp1;
    }

    public void setBizProp1(String bizProp1) {
        this.bizProp1 = bizProp1;
    }

    public String getBizProp2() {
        return this.bizProp2;
    }

    public void setBizProp2(String bizProp2) {
        this.bizProp2 = bizProp2;
    }

    public String getBizTitle1() {
        return this.bizTitle1;
    }

    public void setBizTitle1(String bizTitle1) {
        this.bizTitle1 = bizTitle1;
    }

    public String getBizTitle2() {
        return this.bizTitle2;
    }

    public void setBizTitle2(String bizTitle2) {
        this.bizTitle2 = bizTitle2;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFileAttachId() {
        return this.fileAttachId;
    }

    public void setFileAttachId(String fileAttachId) {
        this.fileAttachId = fileAttachId;
    }

    public String getDocTitle() {
        return this.docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getRepId() {
        return this.repId;
    }

    public void setRepId(String repId) {
        this.repId = repId;
    }

    public Map<String, String> getBusiness() {
        return this.business;
    }

    public void setBusiness(Map<String, String> business) {
        this.business = business;
    }

    public String getResultId() {
        return this.resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public List<ResultItemDTO> getResultItems() {
        return this.resultItems;
    }

    public void setResultItems(List<ResultItemDTO> resultItems) {
        this.resultItems = resultItems;
    }

    public String getRid() {
        return this.rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}

