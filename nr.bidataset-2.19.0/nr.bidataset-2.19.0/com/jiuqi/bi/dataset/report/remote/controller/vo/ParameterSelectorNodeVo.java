/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 */
package com.jiuqi.bi.dataset.report.remote.controller.vo;

import com.jiuqi.bi.dataset.report.model.DefaultValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import java.util.ArrayList;
import java.util.List;

public class ParameterSelectorNodeVo {
    private String key;
    private String name;
    private String title;
    private List<ParameterSelectorNodeVo> childs = new ArrayList<ParameterSelectorNodeVo>();
    private String type;
    private int dataType;
    private ParameterSelectMode selectMode;
    private DefaultValueMode defaultValueMode = DefaultValueMode.NONE;
    private String messageAlias;
    private String entityId;

    public void addChild(ParameterSelectorNodeVo node) {
        this.childs.add(node);
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ParameterSelectorNodeVo> getChilds() {
        return this.childs;
    }

    public void setChilds(List<ParameterSelectorNodeVo> childs) {
        this.childs = childs;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getMessageAlias() {
        return this.messageAlias;
    }

    public void setMessageAlias(String messageAlias) {
        this.messageAlias = messageAlias;
    }

    public ParameterSelectMode getSelectMode() {
        return this.selectMode;
    }

    public void setSelectMode(ParameterSelectMode selectMode) {
        this.selectMode = selectMode;
    }

    public DefaultValueMode getDefaultValueMode() {
        return this.defaultValueMode;
    }

    public void setDefaultValueMode(DefaultValueMode defaultValueMode) {
        this.defaultValueMode = defaultValueMode;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}

