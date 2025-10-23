/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.web.param;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.loader.BaseLevelLoader;

public class TreeNodeVO
implements INode {
    private String key;
    private String code;
    private String title;
    private String parentKey;
    private int type;

    public TreeNodeVO() {
    }

    public TreeNodeVO(DataScheme dataScheme) {
        this.key = dataScheme.getKey();
        this.code = dataScheme.getCode();
        this.title = dataScheme.getTitle();
        this.type = NodeType.SCHEME.getValue();
        this.parentKey = dataScheme.getDataGroupKey();
    }

    public TreeNodeVO(DataGroup group) {
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

    public TreeNodeVO(DataTable table) {
        this.key = table.getKey();
        this.code = table.getCode();
        this.title = table.getTitle();
        DataTableType dataTableType = table.getDataTableType();
        this.type = BaseLevelLoader.getNodeTypeByTableType(dataTableType);
        this.parentKey = table.getDataGroupKey();
        if (this.parentKey == null) {
            this.parentKey = table.getDataSchemeKey();
        }
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

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

