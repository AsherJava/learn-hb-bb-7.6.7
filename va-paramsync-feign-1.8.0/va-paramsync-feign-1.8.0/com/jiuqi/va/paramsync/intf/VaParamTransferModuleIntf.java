/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.intf;

import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleExtend;
import java.util.List;

public class VaParamTransferModuleIntf
implements VaParamTransferModuleExtend {
    private String name;
    private String title;
    private String moduleId;
    private String parentCategoryId;
    private List<VaParamTransferCategory> categorys;
    private List<String> dependenceFactoryIds;

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getModuleId() {
        return this.moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public String getParentCategoryId() {
        return this.parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    @Override
    public List<VaParamTransferCategory> getCategorys() {
        return this.categorys;
    }

    public void setCategorys(List<VaParamTransferCategory> categorys) {
        this.categorys = categorys;
    }

    @Override
    public List<String> getDependenceFactoryIds() {
        return this.dependenceFactoryIds;
    }

    public void setDependenceFactoryIds(List<String> dependenceFactoryIds) {
        this.dependenceFactoryIds = dependenceFactoryIds;
    }
}

