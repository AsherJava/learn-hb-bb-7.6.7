/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.reminder.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class ReminderJobEntity {
    private String id;
    private String jobId;
    private String jobGroupId;
    private String jobCronExpression;
    private Map<String, Object> jobParam;
    private String reminderId;

    public ReminderJobEntity() {
    }

    public ReminderJobEntity(String id, String jobId, String jobGroupId, String jobCronExpression) {
        this.id = id;
        this.jobId = jobId;
        this.jobGroupId = jobGroupId;
        this.jobCronExpression = jobCronExpression;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return this.jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobGroupId() {
        return this.jobGroupId;
    }

    public void setJobGroupId(String jobGroupId) {
        this.jobGroupId = jobGroupId;
    }

    public String getJobCronExpression() {
        return this.jobCronExpression;
    }

    public void setJobCronExpression(String jobCronExpression) {
        this.jobCronExpression = jobCronExpression;
    }

    public Map<String, Object> getJobParam() {
        return this.jobParam;
    }

    public void setJobParam(Map<String, Object> jobParam) {
        this.jobParam = jobParam;
    }

    public void setJobParamByJsonString(String jsonString) {
        if (jsonString != null && !"".equals(jsonString)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Map map;
                this.jobParam = map = (Map)mapper.readValue(jsonString, (TypeReference)new TypeReference<Map<String, Object>>(){});
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getJobParamJsonString() {
        if (this.jobParam == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this.jobParam);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ReminderJobEntity jobEntity = (ReminderJobEntity)o;
        return this.id != null ? this.id.equals(jobEntity.id) : jobEntity.id == null;
    }

    public int hashCode() {
        return this.id != null ? this.id.hashCode() : 0;
    }

    public String toString() {
        return "JobEntity{id='" + this.id + '\'' + ", jobId='" + this.jobId + '\'' + ", jobGroupId='" + this.jobGroupId + '\'' + ", jobCronExpression='" + this.jobCronExpression + '\'' + '}';
    }

    public String getReminderId() {
        return this.reminderId;
    }

    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }
}

