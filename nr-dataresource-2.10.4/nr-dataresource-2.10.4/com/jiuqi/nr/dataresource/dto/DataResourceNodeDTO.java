/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.dto;

import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.common.DataResourceConvert;

public class DataResourceNodeDTO
implements DataResourceNode {
    protected String key;
    protected String code;
    protected String title;
    protected int type;
    protected String parentKey;
    private String order;
    private String dimKey;
    private String dataTableKey;
    private String source;
    private String dataSchemeKey;
    private boolean CanWrite;
    private String linkZb;

    public DataResourceNodeDTO(DataResourceDefine define) {
        this.key = define.getKey();
        this.code = define.getOrder();
        this.title = define.getTitle();
        this.type = NodeType.TREE.getValue();
        this.order = define.getOrder();
    }

    public DataResourceNodeDTO() {
    }

    public DataResourceNodeDTO(DataResource dataGroup) {
        this.key = dataGroup.getKey();
        this.code = dataGroup.getOrder();
        this.title = dataGroup.getTitle();
        DataResourceKind kind = dataGroup.getResourceKind();
        NodeType nodeType = DataResourceConvert.resourceKind2NodeType(kind);
        if (nodeType != null) {
            this.type = nodeType.getValue();
        }
        this.parentKey = dataGroup.getParentKey();
        if (this.parentKey == null) {
            this.parentKey = dataGroup.getResourceDefineKey();
        }
        this.dimKey = dataGroup.getDimKey();
        this.order = dataGroup.getOrder();
        this.dataTableKey = dataGroup.getDataTableKey();
        this.source = dataGroup.getSource();
        this.dataSchemeKey = dataGroup.getDataSchemeKey();
        this.linkZb = dataGroup.getLinkZb();
    }

    public DataResourceNodeDTO(DataResourceDefineGroup group) {
        this.key = group.getKey();
        this.code = group.getOrder();
        this.title = group.getTitle();
        this.type = NodeType.TREE_GROUP.getValue();
        this.parentKey = group.getParentKey();
        this.order = group.getOrder();
    }

    @Override
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

    @Override
    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    @Override
    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public boolean isCanWrite() {
        return this.CanWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.CanWrite = canWrite;
    }

    @Override
    public String getLinkZb() {
        return this.linkZb;
    }

    public void setLinkZb(String linkZb) {
        this.linkZb = linkZb;
    }

    public String toString() {
        return "DataResourceNodeDTO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", type=" + this.type + ", parentKey='" + this.parentKey + '\'' + ", order='" + this.order + '\'' + ", dimKey='" + this.dimKey + '\'' + '}';
    }

    @Override
    public int compareTo(DataResourceNode o) {
        if (this.type == o.getType()) {
            return this.order.compareTo(o.getOrder());
        }
        if (o.getType() == DataResourceKind.MD_INFO.getValue() && this.type == DataResourceKind.DIM_GROUP.getValue()) {
            return -1;
        }
        if (this.type == DataResourceKind.MD_INFO.getValue() && o.getType() == DataResourceKind.DIM_GROUP.getValue()) {
            return 1;
        }
        return o.getType() - this.type;
    }
}

