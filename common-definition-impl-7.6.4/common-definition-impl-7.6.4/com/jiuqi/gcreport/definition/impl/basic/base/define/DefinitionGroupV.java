/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.define;

import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import org.springframework.util.StringUtils;

public class DefinitionGroupV {
    private String id;
    private String code;
    private String parentId;
    private String title;
    private String description;
    private DefinitionTableV table;

    public DefinitionGroupV(DBTableGroup group, DefinitionTableV table) {
        this.setId(group.id());
        this.setCode(group.code());
        this.setTitle(StringUtils.isEmpty(group.title()) ? this.getCode() : group.title());
        this.setDescription(StringUtils.isEmpty(group.description()) ? this.getTitle() : group.description());
        this.setTable(table);
    }

    public DefinitionGroupV() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DefinitionTableV getTable() {
        return this.table;
    }

    public void setTable(DefinitionTableV table) {
        this.table = table;
    }
}

