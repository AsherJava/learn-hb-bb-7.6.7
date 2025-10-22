/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.reminder.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class AutoUserEntityElement
implements Serializable {
    private static final long serialVersionUID = -5256098290672932375L;
    private String key;
    private String code;
    private String title;
    private boolean selected;
    private Boolean expanded;
    private boolean checked;
    private Boolean loading;
    private Boolean indeterminate;
    private List<AutoUserEntityElement> children;
    private String type;
    public static final String ENTITYDATA = "entityData";
    public static final String TABLE = "table";
    private String parentKey;

    public AutoUserEntityElement() {
    }

    public AutoUserEntityElement(TableDefine tableDefine) {
        this.key = tableDefine.getKey();
        this.code = tableDefine.getCode();
        this.title = "\u3010" + this.code + "|" + tableDefine.getTitle() + "\u3011";
        this.type = TABLE;
    }

    public AutoUserEntityElement(IEntityRow iEntityRow) {
        this.key = iEntityRow.getEntityKeyData();
        this.code = iEntityRow.getCode();
        this.title = iEntityRow.getTitle();
        this.type = ENTITYDATA;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public Boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public Boolean isLoading() {
        return this.loading;
    }

    public void setLoading(Boolean loading) {
        this.loading = loading;
    }

    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public Boolean isIndeterminate() {
        return this.indeterminate;
    }

    public void setIndeterminate(Boolean indeterminate) {
        this.indeterminate = indeterminate;
    }

    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public List<AutoUserEntityElement> getChildren() {
        return this.children;
    }

    public void setChildren(List<AutoUserEntityElement> children) {
        this.children = children;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        AutoUserEntityElement that = (AutoUserEntityElement)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }
}

