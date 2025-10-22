/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.internal.model.impl;

import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.ITreeStruct;

public class EntityDefineImpl
implements IEntityDefine {
    private static final long serialVersionUID = -4898430514897704574L;
    private String id;
    private String code;
    private String title;
    private String desc;
    private int dimensionFlag;
    private String dimensionName;
    private int includeSubTreeEntity;
    private ITreeStruct treeStruct;
    private int isolation;
    private int version;
    private boolean isTree;
    private String group;
    private boolean authFlag;
    private String category;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public Integer getDimensionFlag() {
        return this.dimensionFlag;
    }

    @Override
    public Integer getIncludeSubTreeEntity() {
        return this.includeSubTreeEntity;
    }

    public void setIncludeSubTreeEntity(int includeSubTreeEntity) {
        this.includeSubTreeEntity = includeSubTreeEntity;
    }

    @Override
    public String getDimensionName() {
        return this.dimensionName;
    }

    @Override
    public ITreeStruct getTreeStruct() {
        return this.treeStruct;
    }

    @Override
    public Integer getIsolation() {
        return this.isolation;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    @Override
    public Boolean isTree() {
        return this.isTree;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setTree(boolean tree) {
        this.isTree = tree;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setIsolation(int isolation) {
        this.isolation = isolation;
    }

    public void setTreeStruct(ITreeStruct treeStruct) {
        this.treeStruct = treeStruct;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDimensionFlag(int dimensionFlag) {
        this.dimensionFlag = dimensionFlag;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    @Override
    public Boolean isAuthFlag() {
        return this.authFlag;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    public void setAuthFlag(boolean authFlag) {
        this.authFlag = authFlag;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

