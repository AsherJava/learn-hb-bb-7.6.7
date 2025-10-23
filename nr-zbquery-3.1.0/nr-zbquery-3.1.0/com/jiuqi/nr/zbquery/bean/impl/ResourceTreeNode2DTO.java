/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataresource.NodeType
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.zbquery.bean.impl;

import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.zbquery.bean.facade.IResourceTreeNode;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTreeNodeDTO;
import com.jiuqi.nr.zbquery.bean.impl.SelectedFieldDefineEx;
import com.jiuqi.nr.zbquery.model.FormulaField;
import java.util.ArrayList;
import java.util.List;

public class ResourceTreeNode2DTO
extends ResourceTreeNodeDTO {
    private boolean checked;
    private List<IResourceTreeNode> children = new ArrayList<IResourceTreeNode>();

    public ResourceTreeNode2DTO() {
    }

    public ResourceTreeNode2DTO(DataField dataField) {
        String id = "nrfs_";
        int type = 0;
        switch (dataField.getDataFieldKind()) {
            case FIELD: 
            case BUILT_IN_FIELD: {
                type = NodeType.FIELD_LINK.getValue();
                id = id + "field";
                break;
            }
            case FIELD_ZB: {
                type = NodeType.FIELD_ZB_LINK.getValue();
                id = id + "zb";
                break;
            }
            case TABLE_FIELD_DIM: {
                type = NodeType.TABLE_DIM_GROUP.getValue();
                id = id + "dim";
                break;
            }
            case PUBLIC_FIELD_DIM: {
                type = NodeType.DIM_GROUP.getValue();
                id = id + "dim";
            }
        }
        id = id + "_" + dataField.getKey();
        this.setKey(id);
        this.setChecked(true);
        this.setCode(dataField.getCode());
        this.setTitle(dataField.getTitle());
        this.setOrder(dataField.getOrder());
        this.setParentKey(null);
        this.setDefineKey(null);
        this.setType(type);
        this.setDataTableKey(dataField.getDataTableKey());
        this.setSource(dataField.getKey());
        this.setDataSchemeKey(dataField.getDataSchemeKey());
    }

    public ResourceTreeNode2DTO(SelectedFieldDefineEx selectedField, DataTable dataTable) {
        String id = "nrfs_";
        int type = 0;
        if (dataTable == null) {
            type = NodeType.FIELD_ZB_LINK.getValue();
            id = id + "zb";
        } else {
            switch (dataTable.getDataTableType()) {
                case TABLE: {
                    type = NodeType.FIELD_ZB_LINK.getValue();
                    id = id + "zb";
                    break;
                }
                case DETAIL: {
                    type = NodeType.FIELD_LINK.getValue();
                    id = id + "field";
                }
            }
        }
        id = id + "_" + selectedField.getFieldCode();
        this.setKey(id);
        this.setChecked(true);
        this.setCode(selectedField.getCode());
        this.setTitle(selectedField.getFieldTitle());
        this.setOrder(selectedField.getCode());
        this.setParentKey(null);
        this.setDefineKey(null);
        this.setType(type);
        this.setDataTableKey(selectedField.getTableKey());
        this.setSource(selectedField.getFieldCode());
    }

    public ResourceTreeNode2DTO(FormulaField formulaField) {
        this.setKey(formulaField.getId());
        this.setChecked(true);
        this.setCode(formulaField.getName());
        this.setTitle(formulaField.getTitle());
        this.setOrder(formulaField.getName());
        this.setParentKey(null);
        this.setDefineKey(null);
        this.setType(98);
        this.setDataTableKey(null);
        this.setSource(null);
        this.setQueryObject(formulaField);
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<IResourceTreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<IResourceTreeNode> children) {
        this.children = children;
    }
}

