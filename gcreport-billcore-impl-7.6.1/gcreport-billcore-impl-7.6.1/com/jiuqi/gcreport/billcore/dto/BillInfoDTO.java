/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.vo.SelectFloatLineOptionTreeVO
 */
package com.jiuqi.gcreport.billcore.dto;

import com.jiuqi.gcreport.unionrule.vo.SelectFloatLineOptionTreeVO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class BillInfoDTO {
    private String id;
    private String name;
    private String title;
    private String moduleName;
    private String metaType;
    private String parentName;
    private String parentId;
    private List<BillInfoDTO> children;

    public SelectFloatLineOptionTreeVO converToSelectOptionTree() {
        SelectFloatLineOptionTreeVO baseData = new SelectFloatLineOptionTreeVO();
        baseData.setValue((Object)this.getId());
        baseData.setLabel(this.getTitle());
        if (!CollectionUtils.isEmpty(this.children)) {
            baseData.setChildren(this.children.stream().map(BillInfoDTO::converToSelectOptionTree).collect(Collectors.toList()));
        }
        return baseData;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getMetaType() {
        return this.metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<BillInfoDTO> getChildren() {
        return this.children;
    }

    public void setChildren(List<BillInfoDTO> children) {
        this.children = children;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String toString() {
        return "BillInfoDTO{id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", title='" + this.title + '\'' + ", moduleName='" + this.moduleName + '\'' + ", metaType='" + this.metaType + '\'' + ", parentName='" + this.parentName + '\'' + ", parentId='" + this.parentId + '\'' + ", children=" + this.children + '}';
    }
}

