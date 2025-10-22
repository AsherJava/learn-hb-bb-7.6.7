/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckResource
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckResourceGroup
 */
package com.jiuqi.nr.system.check2.vo;

import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResourceGroup;
import com.jiuqi.nr.system.check2.vo.ResourceVO;
import java.util.ArrayList;
import java.util.List;

public class ResourceGroupVO {
    String key;
    String title;
    String icon;
    List<ResourceVO> resources = new ArrayList<ResourceVO>();

    public ResourceGroupVO() {
    }

    public ResourceGroupVO(ICheckResourceGroup checkResourceGroup) {
        this.key = checkResourceGroup.getKey();
        this.title = checkResourceGroup.getTitle();
        this.icon = checkResourceGroup.getIcon();
    }

    public ResourceGroupVO(ICheckResource checkResource) {
        this.key = checkResource.getKey();
        this.title = checkResource.getTitle();
        this.icon = checkResource.getIcon();
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

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ResourceVO> getResources() {
        return this.resources;
    }

    public void setResources(List<ResourceVO> resources) {
        this.resources = resources;
    }

    public void addResource(ResourceVO resource) {
        this.resources.add(resource);
    }

    public void addResources(List<ResourceVO> resources) {
        this.resources.addAll(resources);
    }
}

