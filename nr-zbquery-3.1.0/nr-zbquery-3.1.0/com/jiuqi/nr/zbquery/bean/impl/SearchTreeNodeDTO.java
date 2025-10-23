/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataresource.DataResourceNode
 *  com.jiuqi.nr.dataresource.NodeType
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 */
package com.jiuqi.nr.zbquery.bean.impl;

import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.zbquery.bean.facade.IResourceTreeNode;
import com.jiuqi.nr.zbquery.util.ResTreeUtils;
import java.util.List;

public class SearchTreeNodeDTO {
    private String key;
    private String code;
    private String icon;
    private String title;
    private String titlePath;
    private List<String> keyPath;

    public SearchTreeNodeDTO() {
    }

    public SearchTreeNodeDTO(DataResourceNode dataResourceNode) {
        this.key = ResTreeUtils.generateKey(dataResourceNode);
        this.title = dataResourceNode.getTitle();
        if (dataResourceNode.getType() == NodeType.RESOURCE_GROUP.getValue()) {
            this.icon = NodeIconGetter.getIconByType((com.jiuqi.nr.datascheme.api.core.NodeType)com.jiuqi.nr.datascheme.api.core.NodeType.GROUP);
        } else if (dataResourceNode.getType() == NodeType.DIM_GROUP.getValue()) {
            this.icon = NodeIconGetter.getIconByType((com.jiuqi.nr.datascheme.api.core.NodeType)com.jiuqi.nr.datascheme.api.core.NodeType.DIM);
        } else if (dataResourceNode.getType() == NodeType.TABLE_DIM_GROUP.getValue()) {
            this.icon = NodeIconGetter.getIconByType((com.jiuqi.nr.datascheme.api.core.NodeType)com.jiuqi.nr.datascheme.api.core.NodeType.DIM);
        }
    }

    public SearchTreeNodeDTO(DataField dataField, IResourceTreeNode resGroupNode) {
        this.key = ResTreeUtils.generateKey(dataField, resGroupNode);
        this.code = dataField.getCode();
        this.title = dataField.getTitle();
        DataFieldKind dataFieldKind = dataField.getDataFieldKind();
        switch (dataFieldKind) {
            case FIELD: {
                this.icon = NodeIconGetter.getIconByType((com.jiuqi.nr.datascheme.api.core.NodeType)com.jiuqi.nr.datascheme.api.core.NodeType.FIELD);
                break;
            }
            case FIELD_ZB: {
                this.icon = NodeIconGetter.getIconByType((com.jiuqi.nr.datascheme.api.core.NodeType)com.jiuqi.nr.datascheme.api.core.NodeType.FIELD_ZB);
                break;
            }
            case TABLE_FIELD_DIM: {
                this.icon = NodeIconGetter.getIconByType((com.jiuqi.nr.datascheme.api.core.NodeType)com.jiuqi.nr.datascheme.api.core.NodeType.DIM);
            }
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

    public String getTitlePath() {
        return this.titlePath;
    }

    public void setTitlePath(List<String> titlePath) {
        StringBuilder sb = new StringBuilder();
        for (String titld : titlePath) {
            sb.append(titld).append("/");
        }
        sb.setLength(sb.length() - 1);
        this.titlePath = sb.toString();
    }

    public List<String> getKeyPath() {
        return this.keyPath;
    }

    public void setKeyPath(List<String> keyPath) {
        this.keyPath = keyPath;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

