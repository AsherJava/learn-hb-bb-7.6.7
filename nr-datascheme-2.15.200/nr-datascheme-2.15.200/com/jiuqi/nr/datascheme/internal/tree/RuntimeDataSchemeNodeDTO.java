/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.datascheme.internal.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.Objects;
import org.springframework.lang.NonNull;

public class RuntimeDataSchemeNodeDTO
implements RuntimeDataSchemeNode,
Comparable<RuntimeDataSchemeNodeDTO> {
    protected String key;
    protected String code;
    protected String title;
    protected int type;
    protected String parentKey;
    protected String refDataEntityKey;
    protected boolean tableDim;
    protected String order;
    protected String dataTableKey;
    @JsonIgnore
    protected Object data;
    public static final int TABLE_VALUE = NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MD_INFO.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.FMDM_TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue();
    public static final int FIELD_VALUE = NodeType.FIELD.getValue() | NodeType.TABLE_DIM.getValue();

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public RuntimeDataSchemeNodeDTO(DataScheme dataScheme) {
        this.data = dataScheme;
        this.key = dataScheme.getKey();
        this.code = dataScheme.getCode();
        this.title = dataScheme.getTitle();
        this.type = NodeType.SCHEME.getValue();
        this.parentKey = dataScheme.getDataGroupKey();
        this.order = dataScheme.getOrder();
    }

    public RuntimeDataSchemeNodeDTO() {
    }

    public RuntimeDataSchemeNodeDTO(DataGroup group) {
        this.data = group;
        this.key = group.getKey();
        this.code = group.getCode();
        this.title = group.getTitle();
        this.order = group.getOrder();
        DataGroupKind dataGroupKind = group.getDataGroupKind();
        if (dataGroupKind == DataGroupKind.SCHEME_GROUP || dataGroupKind == DataGroupKind.QUERY_SCHEME_GROUP) {
            this.type = NodeType.SCHEME_GROUP.getValue();
        } else if (dataGroupKind == DataGroupKind.TABLE_GROUP) {
            this.type = NodeType.GROUP.getValue();
        } else {
            throw new IllegalArgumentException("\u6682\u4e0d\u652f\u6301");
        }
        this.parentKey = group.getParentKey();
        if (this.parentKey == null) {
            this.parentKey = group.getDataSchemeKey();
        }
    }

    public RuntimeDataSchemeNodeDTO(DataTable table) {
        if (table == null) {
            return;
        }
        this.data = table;
        this.key = table.getKey();
        this.code = table.getCode();
        this.title = table.getTitle();
        this.order = table.getOrder();
        DataTableType dataTableType = table.getDataTableType();
        if (DataTableType.TABLE == dataTableType) {
            this.type = NodeType.TABLE.getValue();
        } else if (DataTableType.MD_INFO == dataTableType) {
            this.type = NodeType.MD_INFO.getValue();
        } else if (DataTableType.DETAIL == dataTableType) {
            this.type = NodeType.DETAIL_TABLE.getValue();
        } else if (DataTableType.ACCOUNT == dataTableType) {
            this.type = NodeType.ACCOUNT_TABLE.getValue();
        } else if (DataTableType.SUB_TABLE == dataTableType) {
            this.type = NodeType.DETAIL_TABLE.getValue();
        } else {
            throw new UnsupportedOperationException("\u6682\u4e0d\u652f\u6301");
        }
        this.parentKey = table.getDataGroupKey();
        if (this.parentKey == null) {
            this.parentKey = table.getDataSchemeKey();
        }
    }

    public RuntimeDataSchemeNodeDTO(DataField dataField) {
        this.data = dataField;
        this.key = dataField.getKey();
        this.code = dataField.getCode();
        this.title = dataField.getTitle();
        this.order = dataField.getOrder();
        if (DataFieldKind.FIELD_ZB == dataField.getDataFieldKind()) {
            this.type = NodeType.FIELD_ZB.getValue();
        } else if (DataFieldKind.FIELD == dataField.getDataFieldKind()) {
            this.type = NodeType.FIELD.getValue();
        } else if (DataFieldKind.TABLE_FIELD_DIM == dataField.getDataFieldKind()) {
            this.type = NodeType.TABLE_DIM.getValue();
        } else {
            throw new UnsupportedOperationException("\u6682\u4e0d\u652f\u6301");
        }
        this.refDataEntityKey = dataField.getRefDataEntityKey();
        this.parentKey = dataField.getDataTableKey();
    }

    public RuntimeDataSchemeNodeDTO(IEntityDefine iEntityDefine, String schemeKey) {
        this.data = iEntityDefine;
        this.key = schemeKey + ":" + iEntityDefine.getId();
        this.code = iEntityDefine.getCode();
        this.title = iEntityDefine.getTitle();
        this.type = NodeType.DIM.getValue();
        this.parentKey = schemeKey;
    }

    public RuntimeDataSchemeNodeDTO(IPeriodEntity periodEntity, String schemeKey) {
        this.data = periodEntity;
        this.key = schemeKey + ":" + periodEntity.getKey();
        this.code = periodEntity.getCode();
        this.title = periodEntity.getTitle();
        this.type = NodeType.DIM.getValue();
        this.parentKey = schemeKey;
    }

    public RuntimeDataSchemeNodeDTO(ColumnModelDefine attribute, String dimKey) {
        this.data = attribute;
        this.key = attribute.getID();
        this.code = attribute.getCode();
        this.title = attribute.getTitle();
        this.type = NodeType.ENTITY_ATTRIBUTE.getValue();
        this.parentKey = dimKey;
    }

    public RuntimeDataSchemeNodeDTO(DataField dataField, String dimKey) {
        this(dataField);
        this.parentKey = dimKey;
    }

    public RuntimeDataSchemeNodeDTO(String key, String code, String title, int type, String parentKey) {
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

    public String getRefDataEntityKey() {
        return this.refDataEntityKey;
    }

    public void setRefDataEntityKey(String refDataEntityKey) {
        this.refDataEntityKey = refDataEntityKey;
    }

    public boolean isTableDim() {
        return this.tableDim;
    }

    public void setTableDim(boolean tableDim) {
        this.tableDim = tableDim;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public String toString() {
        return "DataSchemeNodeVO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", type=" + this.type + ", parentKey='" + this.parentKey + '\'' + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        RuntimeDataSchemeNodeDTO that = (RuntimeDataSchemeNodeDTO)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public int compareTo(@NonNull RuntimeDataSchemeNodeDTO o) {
        int typeCompare = this.type - o.type;
        if (typeCompare != 0) {
            boolean flag;
            boolean bl = flag = (this.type & TABLE_VALUE) == 0 || (o.getType() & TABLE_VALUE) == 0;
            if ((this.type & FIELD_VALUE) != 0 && (o.getType() & FIELD_VALUE) != 0) {
                flag = true;
                typeCompare = -typeCompare;
            }
            if (flag) {
                return typeCompare;
            }
        }
        if (o.getOrder() == null) {
            return 1;
        }
        if (this.order == null) {
            return -1;
        }
        return this.order.compareTo(o.getOrder());
    }
}

