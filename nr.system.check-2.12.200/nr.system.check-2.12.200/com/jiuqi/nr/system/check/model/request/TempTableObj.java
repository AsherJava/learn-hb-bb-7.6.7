/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonProperty$Access
 *  org.apache.commons.lang3.builder.ToStringBuilder
 */
package com.jiuqi.nr.system.check.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TempTableObj
implements Serializable {
    private String type;
    private String typeTitle;
    @JsonProperty(access=JsonProperty.Access.READ_ONLY)
    private String description;
    private List<String> tableNames;
    private Integer totalCount = 0;
    private Integer expandCount = 0;
    private Integer freeCount = 0;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeTitle() {
        return this.typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public List<String> getTableNames() {
        return this.tableNames;
    }

    public void setTableNames(List<String> tableNames) {
        this.tableNames = tableNames;
    }

    public Integer getExpandCount() {
        return this.expandCount;
    }

    public void setExpandCount(Integer expandCount) {
        this.expandCount = expandCount;
    }

    public Integer getFreeCount() {
        return this.freeCount;
    }

    public void setFreeCount(Integer freeCount) {
        this.freeCount = freeCount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String toString() {
        return new ToStringBuilder((Object)this).append("type", (Object)this.type).append("typeTitle", (Object)this.typeTitle).append("description", (Object)this.description).append("tableNames", this.tableNames).append("expandCount", (Object)this.expandCount).append("freeCount", (Object)this.freeCount).toString();
    }
}

