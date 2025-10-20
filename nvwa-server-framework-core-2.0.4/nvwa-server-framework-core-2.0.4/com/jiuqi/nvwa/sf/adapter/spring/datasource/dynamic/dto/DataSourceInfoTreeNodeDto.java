/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceGroup;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto.NvwaDataSourceInfoDto;
import java.util.List;
import java.util.Optional;

public class DataSourceInfoTreeNodeDto {
    private String key;
    private String name;
    private String title;
    private NvwaDataSourceGroup group;
    private List<DataSourceInfoTreeNodeDto> children;

    public DataSourceInfoTreeNodeDto() {
    }

    public DataSourceInfoTreeNodeDto(String key, String name, String title) {
        this.key = key;
        this.name = name;
        this.title = title;
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

    public NvwaDataSourceGroup getGroup() {
        return this.group;
    }

    public void setGroup(NvwaDataSourceGroup group) {
        this.group = group;
    }

    public List<DataSourceInfoTreeNodeDto> getChildren() {
        return this.children;
    }

    public void setChildren(List<DataSourceInfoTreeNodeDto> children) {
        this.children = children;
    }

    public static DataSourceInfoTreeNodeDto fromInfoDto(NvwaDataSourceInfoDto infoDto) {
        DataSourceInfoTreeNodeDto node = new DataSourceInfoTreeNodeDto();
        node.setKey(infoDto.getKey());
        node.setName(infoDto.getName());
        node.setTitle(Optional.ofNullable(infoDto.getTitle()).orElse(infoDto.getName()));
        node.setGroup(infoDto.getGroup());
        return node;
    }

    public String toString() {
        return "DataSourceInfoTreeNodeDto{key='" + this.key + '\'' + ", name='" + this.name + '\'' + ", title='" + this.title + '\'' + ", group=" + this.group + ", children=" + this.children + '}';
    }
}

