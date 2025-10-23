/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  com.jiuqi.nr.param.transfer.definition.dto.taskgroup.TaskGroupDTO
 */
package com.jiuqi.nr.nrdx.param.task.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.param.transfer.definition.dto.taskgroup.TaskGroupDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NrdxTaskGroupDTO
implements DesignTaskGroupDefine {
    private String parentKey;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String code;
    private String description;
    private List<String> taskKeys;

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTaskKeys() {
        return this.taskKeys;
    }

    public void setTaskKeys(List<String> taskKeys) {
        this.taskKeys = taskKeys;
    }

    public static NrdxTaskGroupDTO valueOf(TaskGroupDefine groupDefine) {
        if (groupDefine == null) {
            return null;
        }
        NrdxTaskGroupDTO taskGroupDTO = new NrdxTaskGroupDTO();
        taskGroupDTO.setKey(groupDefine.getKey());
        taskGroupDTO.setCode(groupDefine.getCode());
        taskGroupDTO.setTitle(groupDefine.getTitle());
        taskGroupDTO.setDescription(groupDefine.getDescription());
        taskGroupDTO.setOrder(groupDefine.getOrder());
        taskGroupDTO.setOwnerLevelAndId(groupDefine.getOwnerLevelAndId());
        taskGroupDTO.setParentKey(groupDefine.getParentKey());
        taskGroupDTO.setUpdateTime(groupDefine.getUpdateTime());
        taskGroupDTO.setVersion(groupDefine.getVersion());
        return taskGroupDTO;
    }

    public static TaskGroupDTO valueOf(byte[] bytes, ObjectMapper objectMapper) throws IOException {
        return (TaskGroupDTO)objectMapper.readValue(bytes, TaskGroupDTO.class);
    }

    public List<DesignTaskGroupLink> value2Define(DesignTaskGroupDefine groupDefine) {
        groupDefine.setKey(this.key);
        groupDefine.setCode(this.code);
        groupDefine.setParentKey(this.parentKey);
        groupDefine.setTitle(this.title);
        groupDefine.setOrder(this.order);
        groupDefine.setVersion(this.version);
        groupDefine.setOwnerLevelAndId(this.ownerLevelAndId);
        groupDefine.setUpdateTime(this.updateTime);
        groupDefine.setDescription(this.description);
        if (this.taskKeys != null) {
            ArrayList<DesignTaskGroupLink> links = new ArrayList<DesignTaskGroupLink>();
            for (String taskKey : this.taskKeys) {
                DesignTaskGroupLink link = new DesignTaskGroupLink();
                link.setTaskKey(taskKey);
                link.setGroupKey(this.key);
                link.setOrder(OrderGenerator.newOrder());
                links.add(link);
            }
            return links;
        }
        return null;
    }
}

