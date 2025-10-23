/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.internal.dto.SearchDataFieldDTO;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.DataTableNodeDTO;

public class SearchDataFieldVO {
    private String key;
    private String code;
    private String title;
    private int type;
    private DataSchemeNodeDTO table;

    public SearchDataFieldVO() {
    }

    public SearchDataFieldVO(SearchDataFieldDTO t) {
        DesignDataField field = t.getField();
        if (field == null) {
            return;
        }
        this.key = field.getKey();
        this.code = field.getCode();
        this.title = field.getTitle();
        if (DataFieldKind.FIELD_ZB == field.getDataFieldKind()) {
            this.type = NodeType.FIELD_ZB.getValue();
        } else if (DataFieldKind.FIELD == field.getDataFieldKind()) {
            this.type = NodeType.FIELD.getValue();
        }
        this.table = new DataTableNodeDTO(t.getDesignDataTable());
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

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DataSchemeNodeDTO getTable() {
        return this.table;
    }

    public void setTable(DataSchemeNodeDTO table) {
        this.table = table;
    }

    public String toString() {
        return "SearchDataFieldVO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", type=" + this.type + ", table=" + this.table + '}';
    }
}

