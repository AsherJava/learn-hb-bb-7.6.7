/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.transfer.query.bean;

import com.jiuqi.nr.query.querymodal.QueryModalGroup;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class GroupEntity
implements Serializable {
    private String taskId;
    private String description;
    private Date updateTime;
    private String groupId;
    private QueryModelType type;
    private String groupName;
    private String parentGroupId;
    private String groupOrder;
    private String creator;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public QueryModelType getType() {
        return this.type;
    }

    public void setType(QueryModelType type) {
        this.type = type;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getParentGroupId() {
        return this.parentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public String getGroupOrder() {
        return this.groupOrder;
    }

    public void setGroupOrder(String groupOrder) {
        this.groupOrder = groupOrder;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public static List<GroupEntity> transferFromQueryGroups(List<QueryModalGroup> groups) {
        ArrayList<GroupEntity> entities = new ArrayList<GroupEntity>();
        for (QueryModalGroup group : groups) {
            GroupEntity entity = new GroupEntity();
            BeanUtils.copyProperties(group, entity);
            entity.setCreator(group.getCreator());
            entity.setType(group.getModelType());
            entities.add(entity);
        }
        return entities;
    }

    public static List<QueryModalGroup> transferToQueryGroups(List<GroupEntity> groups) {
        ArrayList<QueryModalGroup> entities = new ArrayList<QueryModalGroup>();
        for (GroupEntity group : groups) {
            QueryModalGroup entity = new QueryModalGroup();
            BeanUtils.copyProperties(group, entity);
            entity.setCreator(group.getCreator());
            entity.setModelType(group.getType());
            entities.add(entity);
        }
        return entities;
    }
}

