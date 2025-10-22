/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.transfer.query.bean;

import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class TemplateEntity
implements Serializable {
    private String id;
    private String title;
    private int order;
    private String taskId;
    private String groupId;
    private String conditions;
    private String creator;
    private String layout;
    private String description;
    private String schemeId;
    private QueryModelType modelType;
    private Date updateTime;
    private String modelExtension;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getConditions() {
        return this.conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLayout() {
        return this.layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public QueryModelType getModelType() {
        return this.modelType;
    }

    public void setModelType(QueryModelType modelType) {
        this.modelType = modelType;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getModelExtension() {
        return this.modelExtension;
    }

    public void setModelExtension(String modelExtension) {
        this.modelExtension = modelExtension;
    }

    public static List<QueryModalDefine> transferToQueryTemplates(List<TemplateEntity> entities) {
        ArrayList<QueryModalDefine> modals = new ArrayList<QueryModalDefine>();
        for (TemplateEntity entity : entities) {
            QueryModalDefine modalDefine = new QueryModalDefine();
            BeanUtils.copyProperties(entity, modalDefine);
            modalDefine.JsonToModelExtension(entity.getModelExtension());
            modals.add(modalDefine);
        }
        return modals;
    }

    public static List<TemplateEntity> transferFromQueryTemplates(List<QueryModalDefine> entities) {
        ArrayList<TemplateEntity> modals = new ArrayList<TemplateEntity>();
        for (QueryModalDefine entity : entities) {
            TemplateEntity modalDefine = new TemplateEntity();
            BeanUtils.copyProperties(entity, modalDefine);
            modalDefine.setModelExtension(entity.getModelExtension());
            modals.add(modalDefine);
        }
        return modals;
    }
}

