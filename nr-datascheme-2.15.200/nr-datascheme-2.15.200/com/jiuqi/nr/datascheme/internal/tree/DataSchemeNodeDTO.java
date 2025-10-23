/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.datascheme.internal.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.loader.BaseLevelLoader;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class DataSchemeNodeDTO
implements DataSchemeNode {
    protected String key;
    protected String code;
    protected String title;
    protected int type;
    protected String parentKey;
    protected String owner;
    @JsonIgnore
    protected Object data;

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public DataSchemeNodeDTO(DesignDataScheme dataScheme) {
        this.key = dataScheme.getKey();
        this.code = dataScheme.getCode();
        this.title = dataScheme.getTitle();
        this.type = NodeType.SCHEME.getValue();
        this.parentKey = dataScheme.getDataGroupKey();
        this.data = dataScheme;
    }

    public DataSchemeNodeDTO() {
    }

    public DataSchemeNodeDTO(DesignDataGroup group) {
        this.data = group;
        this.key = group.getKey();
        this.code = group.getCode();
        this.title = group.getTitle();
        DataGroupKind dataGroupKind = group.getDataGroupKind();
        if (dataGroupKind == DataGroupKind.SCHEME_GROUP || dataGroupKind == DataGroupKind.QUERY_SCHEME_GROUP) {
            this.type = NodeType.SCHEME_GROUP.getValue();
        } else if (dataGroupKind == DataGroupKind.TABLE_GROUP) {
            this.type = NodeType.GROUP.getValue();
        } else {
            throw new UnsupportedOperationException("\u6682\u4e0d\u652f\u6301");
        }
        this.parentKey = group.getParentKey();
        if (this.parentKey == null) {
            this.parentKey = group.getDataSchemeKey();
        }
    }

    public DataSchemeNodeDTO(DesignDataTable table) {
        if (table == null) {
            return;
        }
        this.data = table;
        this.key = table.getKey();
        this.code = table.getCode();
        this.title = table.getTitle();
        DataTableType dataTableType = table.getDataTableType();
        this.type = BaseLevelLoader.getNodeTypeByTableType(dataTableType);
        this.parentKey = table.getDataGroupKey();
        if (this.parentKey == null) {
            this.parentKey = table.getDataSchemeKey();
        }
        this.owner = table.getOwner();
    }

    public DataSchemeNodeDTO(DesignDataField dataField, String dimKey) {
        this(dataField);
        this.parentKey = dimKey;
    }

    public DataSchemeNodeDTO(DesignDataField dataField) {
        this.data = dataField;
        this.key = dataField.getKey();
        this.code = dataField.getCode();
        this.title = dataField.getTitle();
        if (DataFieldKind.FIELD_ZB == dataField.getDataFieldKind()) {
            this.type = NodeType.FIELD_ZB.getValue();
        } else if (DataFieldKind.FIELD == dataField.getDataFieldKind()) {
            this.type = NodeType.FIELD.getValue();
        } else if (DataFieldKind.TABLE_FIELD_DIM == dataField.getDataFieldKind()) {
            this.type = NodeType.FIELD.getValue();
        } else {
            throw new UnsupportedOperationException("\u6682\u4e0d\u652f\u6301");
        }
        this.parentKey = dataField.getDataTableKey();
    }

    public DataSchemeNodeDTO(IEntityDefine iEntityDefine, String schemeKey) {
        this.data = iEntityDefine;
        this.key = schemeKey + ":" + iEntityDefine.getId();
        this.code = iEntityDefine.getCode();
        this.title = iEntityDefine.getTitle();
        this.type = NodeType.DIM.getValue();
        this.parentKey = schemeKey;
    }

    public DataSchemeNodeDTO(IPeriodEntity periodEntity, String schemeKey) {
        this.data = periodEntity;
        this.key = schemeKey + ":" + periodEntity.getKey();
        this.code = periodEntity.getCode();
        this.title = periodEntity.getTitle();
        this.type = NodeType.DIM.getValue();
        this.parentKey = schemeKey;
    }

    public DataSchemeNodeDTO(ColumnModelDefine attribute, String dimKey) {
        this.data = attribute;
        this.key = attribute.getID();
        this.code = attribute.getCode();
        this.title = attribute.getTitle();
        this.type = NodeType.ENTITY_ATTRIBUTE.getValue();
        this.parentKey = dimKey;
    }

    public DataSchemeNodeDTO(String key, String code, String title, int type, String parentKey) {
        this.key = key;
        this.code = code;
        this.title = title;
        this.type = type;
        this.parentKey = parentKey;
    }

    public int getType() {
        return this.type;
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String toString() {
        return "DataSchemeNodeVO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", type=" + this.type + ", parentKey='" + this.parentKey + '\'' + '}';
    }
}

